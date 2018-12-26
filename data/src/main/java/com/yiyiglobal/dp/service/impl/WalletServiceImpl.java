package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.NotifyCons;
import com.yiyiglobal.dp.domain.*;
import com.yiyiglobal.dp.dto.wallet.ContributionDto;
import com.yiyiglobal.dp.mapper.*;
import com.yiyiglobal.dp.service.IWalletService;
import com.yiyiglobal.dp.util.DateUtil;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.util.push.NotificationTypeEnum;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
public class WalletServiceImpl implements IWalletService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectRedisService objectRedisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IncomeDetailMapper incomeDetailMapper;
    @Autowired
    private CoinSuplyMapper  coinSuplyMapper;
    @Autowired
    private NotificationMapper  notificationMapper;
    @Autowired
    private AdvertisementMapper  advertisementMapper;
    @Autowired
    private InviteContributionMapper  inviteContributionMapper;

    @Autowired
    private TaskMapper  taskMapper;

    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Override
    public Map<String,Object> getTokenRanks(PageEntity pageEntity){
        Map<String,Object>  map = new HashMap<>();
        PageResult<User> pageResult = new PageResult();
        List<User> users = userMapper.selectTokenRanks(pageEntity);
        pageResult.setResultList(users);
        pageResult.setTotalSize(userMapper.selectTokenRankNum(pageEntity));
        String  date = DateUtil.nowEn(DateUtil.DATE_PATTERN);
        map.put("data",pageResult);
        map.put("date",date);
        map.put("advertisement",advertisementMapper.selectAdvertisementByType(Cons.AdvertisementType.rank,qiniuImagePrefix));
        return map;
    }


    @Override
    public PageResult<IncomeDetail> getMyIncomeDetailList(PageEntity<IncomeDetail> pageEntity){
        PageResult<IncomeDetail> pageResult = new PageResult();
        List<IncomeDetail> details = incomeDetailMapper.selectIncomeDetailList(pageEntity);
        pageResult.setResultList(details);
        pageResult.setTotalSize(incomeDetailMapper.selectIncomeDetailNum(pageEntity));
        return pageResult;
    }

    @Async("taskIncomeExecutor")
    @Override
    public synchronized void  addContributionByTask(Integer taskId,Integer userId,Notification notification){
        Task  task =taskMapper.selectByPrimaryKey(taskId);
        User  user = userMapper.selectByPrimaryKey(userId);
        boolean  flag =false;
        if(task!=null && user!=null){
            if(task.getId().equals(Cons.TaskId.Comment_Review)){
                IncomeDetail  incomeP= new IncomeDetail(userId,Cons.IncomeDetailType.contribution,taskId);
                incomeP.setCreTime(new Date());
                PageEntity<IncomeDetail>  page= new PageEntity<>();
                page.setParams(incomeP);
                page.setOffset(0);
                page.setPageSize(4);
                List<IncomeDetail>  finished = incomeDetailMapper.selectIncomeDetailList(page);
                if(finished==null ||finished.size()<3){
                    flag =true;
                }
            }else{
                flag =true;
            }
            if(flag){
                IncomeDetail  income= new IncomeDetail(userId,task.getTaskName(),new BigDecimal(task.getRewardNum()),Cons.IncomeDetailType.contribution,
                        taskId,new Date());
                int r= incomeDetailMapper.insert(income);
                User u=new User();
                u.setContributionNum(user.getContributionNum()+task.getRewardNum());
                u.setId(user.getId());
                int r1= userMapper.updateByPrimaryKeySelective(u);
                if(r>0&&r1>0){
                    notification.setContent(notification.getContent().replace("{{taskName}}",task.getTaskName()).replace("{{count}}",task.getRewardNum()+""));
                    notificationMapper.insert(notification);
                }
            }
        }
    }

    public void  SettlementContri2Pcontri(String yesterday){
        List<ContributionDto>  list = incomeDetailMapper.getContributionByDate(yesterday);
        if(list!=null&&list.size()>0){
            BigDecimal  zero = new BigDecimal(0);
            Map<Integer,Integer>  map = new HashMap<>();
            for(ContributionDto dto:list){
                if(dto!=null&&dto.getpUserId()!=null&&dto.getUserId()!=null&&dto.getContributionSum().compareTo(zero)>0){
                    Integer  pUserGot = dto.getContributionSum().multiply(new BigDecimal(0.2)).intValue();
                    Integer  pUserSum= map.get(dto.getpUserId());
                    map.put(dto.getpUserId(),(pUserSum!=null&&pUserSum>0)?pUserSum+pUserGot:pUserGot);
                    InviteContribution  ic= inviteContributionMapper.selectLastOne(dto.getUserId(),dto.getpUserId());
                    Integer  pUserGotSum = pUserGot;
                    if(ic!=null&&ic.getSumValue()!=null){
                          pUserGotSum = pUserGotSum+ic.getSumValue();
                    }
                    InviteContribution  icIn = new InviteContribution(dto.getUserId(),dto.getpUserId(),pUserGot,pUserGotSum,new Date(),new Date());
                    inviteContributionMapper.insert(icIn);
                }
            }
            for(Integer key: map.keySet()){
                IncomeDetail  income= new IncomeDetail(key,"被邀请人贡献值",new BigDecimal(map.get(key)),Cons.IncomeDetailType.contribution,
                        null,new Date());
                int r= incomeDetailMapper.insert(income);
                int r1= userMapper.addCoin(key,null,map.get(key));
                if(r>0&&r1>0){
                    Notification  noti = new Notification(key, NotificationTypeEnum.CONTRIBUTION.getTitle(),
                            NotifyCons.contributionContent.replace("{{taskName}}","被邀请人贡献值").replace("{{count}}",map.get(key)+""),NotificationTypeEnum.CONTRIBUTION.getType(),key+"",1,new Date(),0);
                    notificationMapper.insert(noti);
                }
            }
        }
    }



    @Override
    @Transactional
    public synchronized void  SettlementContribution2Coin(){
        Date date =new Date();
        String day   = DateUtil.date2Str(date,DateUtil.DATE_PATTERN);
        String yesterday= DateUtil.dateAdd(day,-1,DateUtil.DATE_PATTERN,DateUtil.DATE_PATTERN);
        // 先给邀请人加 20%
        SettlementContri2Pcontri(yesterday);
        BigDecimal  contributionSum = incomeDetailMapper.getContributionSum(yesterday);
        if(contributionSum!=null){
            List<ContributionDto>  list = incomeDetailMapper.getContributionByDate(yesterday);
            BigDecimal suplyNum = new BigDecimal(0);
            CoinSuply suply= coinSuplyMapper.selectByDate(yesterday);
            if(suply!=null && suply.getNum()!=null && suply.getNum()>0){
                suplyNum = new BigDecimal(suply.getNum());
                BigDecimal tenContributionValue = new BigDecimal(10).multiply(suplyNum.divide(contributionSum,10, RoundingMode.HALF_UP)).setScale(4,BigDecimal.ROUND_HALF_UP);
                suply.setTenContributionValue(tenContributionValue);
                coinSuplyMapper.updateByPrimaryKeySelective(suply);
            }
            for(ContributionDto dto:list){
                BigDecimal  userCoinAdd = suplyNum.multiply(dto.getContributionSum().divide(contributionSum,10, RoundingMode.HALF_UP)).setScale(4,BigDecimal.ROUND_HALF_UP);
                int r= userMapper.addCoin(dto.getUserId(),userCoinAdd,-dto.getContributionSum().intValue());
                if(r>0){
                    IncomeDetail  income= new IncomeDetail(dto.getUserId(),"贡献值兑换成币",userCoinAdd,Cons.IncomeDetailType.token,null,new Date());
                    int r1= incomeDetailMapper.insert(income);
                    if(r1>0){
                        Notification  noti = new Notification(dto.getUserId(), NotificationTypeEnum.TOKENCOIN.getTitle(),
                                NotifyCons.tokenCoinContent.replace("{{count}}",userCoinAdd+"").replace("{{contributionCount}}",dto.getContributionSum()+""),NotificationTypeEnum.TOKENCOIN.getType(),dto.getUserId()+"",1,new Date(),0);
                        notificationMapper.insert(noti);
                    }
                }
            }
        }
    }


}
