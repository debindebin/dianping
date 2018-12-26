package com.yiyiglobal.dp.task;

import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.dto.HotResDto;
import com.yiyiglobal.dp.mapper.ResReviewMapper;
import com.yiyiglobal.dp.service.IHomeService;
import com.yiyiglobal.dp.service.IResService;
import com.yiyiglobal.dp.service.IWalletService;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wangzukun on 2018/4/19.
 */
@Component
public class ScheduledTasks {

    protected static final Logger logger = Logger.getLogger(ScheduledTasks.class);

    @Autowired
    IResService  resService;
    @Autowired
    IWalletService   walletService;

    @Autowired
    private ObjectRedisService objectRedisService;

//    @Scheduled(cron="0 0 */1 * * *")
//    public void setTopRes(){
//        logger.info("更新24小时热评店铺开始");
//        Set<HotResDto>  set = resService.getHotResFromDB(-1);
//        if(set.size()>0){
//            objectRedisService.remove(RedisCons.HOT_REVIEW_RES);
//            Iterator<HotResDto> iterator = set.iterator();
//            while (iterator.hasNext()) {
//                objectRedisService.lPush(RedisCons.HOT_REVIEW_RES,iterator.next());
//            }
//        }
//        logger.info("更新24小时热评店铺结束");
//    }


//    @Scheduled(cron="0 49 14 * * ?")
    @Scheduled(cron="0 0 1 * * ?")
    public void settlementContribution2Coin(){
        logger.info("结算贡献值兑换币值开始");
        walletService.SettlementContribution2Coin();
        logger.info("结算贡献值兑换币值结束");
    }

}
