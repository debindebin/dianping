package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.NotifyCons;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.*;
import com.yiyiglobal.dp.dto.review.ReviewForListDto;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.mapper.MaterialMapper;
import com.yiyiglobal.dp.mapper.ResMapper;
import com.yiyiglobal.dp.mapper.ResReviewMapper;
import com.yiyiglobal.dp.mapper.ReviewReplyMapper;
import com.yiyiglobal.dp.service.IResService;
import com.yiyiglobal.dp.service.IReviewService;
import com.yiyiglobal.dp.service.IWalletService;
import com.yiyiglobal.dp.util.DateUtil;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.push.NotificationTypeEnum;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import com.yiyiglobal.dp.vo.review.ReviewForCreVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yiyiglobal.dp.common.Cons.MaterialRelatedType.Review;


@Service
public class ReviewServiceImpl implements IReviewService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectRedisService objectRedisService;

    @Autowired
    private ResReviewMapper resReviewMapper;
    @Autowired
    private ReviewReplyMapper  reviewReplyMapper;
    @Autowired
    private IWalletService   walletService;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ResMapper  resMapper;
    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Override
    public Integer  addReview(ResReview  review, ReviewForCreVo reviewVo){
        review.setCreTime(new Date());
        review.setDelFlag(0);
        review.setUnsupportNum(0);
        review.setSupportNum(0);
        review.setReviewNum(0);
        Integer reviewId =resReviewMapper.insert(review);
        if(reviewId>0){
            // 图片和视频
            List<String> pictureList = reviewVo.getPictureList();
            if(pictureList!=null&& !pictureList.isEmpty()){
                Material  pic = new Material(Review,review.getId(),new Date(),Cons.MaterialType.pic);
                for(String picUrl:pictureList){
                    pic.setMaterialUrl(picUrl);
                    materialMapper.insert(pic);
                }
            }
            String videoUrl = reviewVo.getVideoUrl();
            if(!ValidateUtil.isEmpty(videoUrl)&&reviewVo.getDuration()!=null&&!ValidateUtil.isEmpty(reviewVo.getPersistentId())){
                Material  video = new Material(Review,review.getId(),new Date(),Cons.MaterialType.video);
                video.setMaterialUrl(videoUrl);
                video.setDuration(reviewVo.getDuration());
                video.setPersistentId(reviewVo.getPersistentId());
                materialMapper.insert(video);
            }
            // 资源增加评论数
            resMapper.updateReviewNum(review.getResId(),1,review.getScore());
            Notification  noti = new Notification(review.getUserId(), NotificationTypeEnum.CONTRIBUTION.getTitle(),
                    NotifyCons.contributionContent,NotificationTypeEnum.CONTRIBUTION.getType(),review.getUserId()+"",2,new Date(),0);
            walletService.addContributionByTask(Cons.TaskId.Comment_Review,review.getUserId(),noti);
        }
        return  reviewId;
    }

    @Override
    public PageResult<ReviewForListDto> getResReviewList(PageEntity<ResReview> pageEntity,Integer loginUserId){
        PageResult<ReviewForListDto> pageResult = new PageResult<>();
        List<ReviewForListDto> reses = resReviewMapper.selectResReviewByPage(pageEntity,loginUserId,qiniuImagePrefix);
        pageResult.setResultList(reses);
        pageResult.setTotalSize(resReviewMapper.selectResReviewNumByPage(pageEntity));
        return pageResult;
    }


    @Override
    @Transactional
    public synchronized boolean support(ReviewReply reply){
        ReviewReply  result =reviewReplyMapper.selectBySuppot(reply);
        System.out.println(reply.getType()+"-"+reply.getUserId()+"-"+reply.getReviewId());
        int r= 0;
        int r2 = 0;
        ResReview review  =new ResReview();
        review.setId(reply.getReviewId());
        if(result==null){ // 首次评论，赞或踩
            // 1-支持；2-不支持；0-既不支持也不不支持
            if(reply.getType()==1){
                reply.setSupportFlag(1);
                review.setSupportNum(1);
            }else if(reply.getType()==2){
                reply.setUnsupportFlag(1);
                review.setUnsupportNum(1);
            }
            reply.setCreTime(new Date());
            r= reviewReplyMapper.insertSelective(reply);
            r2 =resReviewMapper.addReviewNum(review);
            // 首次评论获赞得奖励
            ResReview  review1 = resReviewMapper.selectByPrimaryKey(review.getId());
            if(r>0 && r2>0 && reply.getType()==1&& review1!=null&&!reply.getUserId().equals(review1.getUserId())){
                Notification  noti = new Notification(review1.getUserId(), NotificationTypeEnum.CONTRIBUTION.getTitle(),
                        NotifyCons.contributionContent,NotificationTypeEnum.CONTRIBUTION.getType(),review1.getUserId()+"",2,new Date(),0);
                walletService.addContributionByTask(Cons.TaskId.Harvest_Support,review1.getUserId(),noti);
            }
        }else{ // 非首次评论
            if(reply.getType()==1){// 赞
                reply.setSupportFlag(1);
                review.setSupportNum(1);
            }else if(reply.getType()==2){ // 踩
                reply.setUnsupportFlag(1);
                review.setUnsupportNum(1);
            }else if(reply.getType()==0){ // 取消赞或取消踩
                reply.setUnsupportFlag(0);
                reply.setSupportFlag(0);

            }
            // 取消赞或取消踩
            if(result.getType()==1){
                reply.setSupportFlag(0);
                review.setSupportNum(-1);
            }else if(result.getType()==2){
                reply.setUnsupportFlag(0);
                review.setUnsupportNum(-1);
            }
            r= reviewReplyMapper.updateByPrimaryKeySelective(reply);
            r2 =resReviewMapper.addReviewNum(review);
        }
        if(r>0 && r2>0){
            return  true;
        }else {
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);
        }
    }
}
