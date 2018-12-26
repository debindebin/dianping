package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.dto.HotResDto;
import com.yiyiglobal.dp.mapper.ResMapper;
import com.yiyiglobal.dp.mapper.ResReviewMapper;
import com.yiyiglobal.dp.service.IResService;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import com.yiyiglobal.dp.vo.res.ResForListVo;
import com.yiyiglobal.dp.vo.res.ResForSearchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@Service
public class ResServiceImpl implements IResService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectRedisService objectRedisService;

    @Autowired
    private ResMapper  resMapper;

    @Autowired
    private ResReviewMapper   resReviewMapper;

    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Override
    public PageResult<ResForListVo> getResList(PageEntity<ResForSearchVo> pageEntity){
        PageResult<ResForListVo> pageResult = new PageResult<ResForListVo>();
        List<ResForListVo> reses = resMapper.selectResByPage(pageEntity,qiniuImagePrefix);
        pageResult.setResultList(reses);
//        pageResult.setTotalSize(resMapper.selectResNumByPage(pageEntity));
        return pageResult;
    }



    @Override
    public Res  getResDetail(Integer id){
        Res res = resMapper.selectByPrimaryKey(id,qiniuImagePrefix);
        return  res;
    }

    @Override
    public List<String>  getContacts(Integer id){
        Res res = resMapper.selectByPrimaryKey(id,qiniuImagePrefix);
        List<String> list =new ArrayList<>();
        if(res !=null && !ValidateUtil.isEmpty(res.getMobiles())){
            String  mobilses= res.getMobiles();
            if(mobilses.contains("\\|")){
                String[] array= mobilses.split("\\|");
                for(String m:array){
                    if(!ValidateUtil.isEmpty(m)){
                        list.add(m);
                    }
                }
            }else{
                list.add(mobilses);
            }
        }
        return  list;
    }

    @Override
    public Set<HotResDto> getHotResFromDB(Integer cityId){
        Set<HotResDto> set = new TreeSet<>();
        for(int i=0;set.size()<20&&i<10;i++){
            List<HotResDto>  resList= resReviewMapper.selectTopRes(i,qiniuImagePrefix,cityId);
            for(HotResDto r:resList){
                if(set.size()<20){
                    set.add(r);
                }
            }
        }
        return  set;
    }
}
