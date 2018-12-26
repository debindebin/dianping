package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.domain.Advertisement;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.dto.HotResDto;
import com.yiyiglobal.dp.mapper.AdvertisementMapper;
import com.yiyiglobal.dp.mapper.IncomeDetailMapper;
import com.yiyiglobal.dp.mapper.ResMapper;
import com.yiyiglobal.dp.mapper.ResReviewMapper;
import com.yiyiglobal.dp.service.IHomeService;
import com.yiyiglobal.dp.service.IResService;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class HomeServiceImpl implements IHomeService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AdvertisementMapper  advertisementMapper;
    @Autowired
    IncomeDetailMapper  incomeDetailMapper;
    @Autowired
    IResService   resService;

    @Autowired
    private ObjectRedisService objectRedisService;

    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Override
    public Map<String,Object> getHomeData(Integer userId,Integer cityId){
        Map<String,Object> map  = new HashMap<>();
        map.put("advertisement",advertisementMapper.selectAdvertisementByType(Cons.AdvertisementType.home,qiniuImagePrefix));
        if(userId!=null){
            map.put("income",incomeDetailMapper.getHomeIncomeData(userId));
        }
//        List<Object> list = objectRedisService.lRange(RedisCons.HOT_REVIEW_RES,0,-1);
//        if(list==null || list.isEmpty()){
//            Set<HotResDto>  set = getHotResFromDB(null);
//            if(set.size()>0){
//                list = new ArrayList<>();
//                Iterator<HotResDto> iterator = set.iterator();
//                while (iterator.hasNext()) {
//                    list.add(iterator.next());
//                }
//            }
//        }
        List<HotResDto> list =new ArrayList<>();
        Set<HotResDto>  set =null;
        if(cityId!=null){
            set = resService.getHotResFromDB(cityId);
            if(set==null|| set.size()<=0){
                set = resService.getHotResFromDB(-1);
            }
        }else{
            set = resService.getHotResFromDB(-1);
        }
        if(set!=null&&set.size()>0){
            list = new ArrayList<>();
            Iterator<HotResDto> iterator = set.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        map.put("resList",list);
        return  map;
    }

}
