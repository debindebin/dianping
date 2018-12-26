package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.Task;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.mapper.AdvertisementMapper;
import com.yiyiglobal.dp.mapper.IncomeDetailMapper;
import com.yiyiglobal.dp.mapper.ResReviewMapper;
import com.yiyiglobal.dp.mapper.TaskMapper;
import com.yiyiglobal.dp.service.IHomeService;
import com.yiyiglobal.dp.service.ITaskService;
import com.yiyiglobal.dp.util.DateUtil;
import com.yiyiglobal.dp.util.StringUtil;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import com.yiyiglobal.dp.util.redis.service.StringRedisService;
import com.yiyiglobal.dp.vo.task.TaskForListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TaskServiceImpl implements ITaskService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IncomeDetailMapper  incomeDetailMapper;
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    private StringRedisService stringRedisService;

    @Value("${qiniu.image.prefix}")
    private String prefix;



    @Override
    public Map<String,Object> getTaskHomeData(Integer userId){
        Map<String,Object> map  = new HashMap<>();
        if(userId!=null){
            map.put("income",incomeDetailMapper.getHomeIncomeData(userId));
        }
        List<TaskForListVo>  tasks= taskMapper.selectAll(prefix);
        for(TaskForListVo task:tasks){
            if(Cons.TaskId.Sign_In.equals(task.getId())){
                // 已签到的关闭当天签到任务
                String lastSignIn =(String)stringRedisService.hmGet(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.LAST_SIGN_IN);
                String today   = DateUtil.date2Str(new Date(),DateUtil.DATE_PATTERN);
                if(!ValidateUtil.isEmpty(lastSignIn)&&lastSignIn.equals(today)){
                    task.setFinishStatus(1);
                }
            }
        }
        map.put("taskList",tasks);
        return  map;
    }

    @Override
    public synchronized boolean  signIn(Integer userId){
        Date date =new Date();
        String month = DateUtil.date2Str(date,"yyyy-MM");
        String day   = DateUtil.date2Str(date,DateUtil.DATE_PATTERN);
        String lastSignIn =(String)stringRedisService.hmGet(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.LAST_SIGN_IN);
        if(!ValidateUtil.isEmpty(lastSignIn)&&lastSignIn.equals(day)){
            throw  new BusinessException(ResultEnum.ALREADY_SIGNIN);
        }
        stringRedisService.hmSet(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.LAST_SIGN_IN,day);
        String monthDays=(String)stringRedisService.hmGet(RedisCons.USER_SIGN_IN_INFO+userId,month);
        stringRedisService.hmSet(RedisCons.USER_SIGN_IN_INFO+userId,month,ValidateUtil.isEmpty(monthDays)?day:monthDays+","+day);
        stringRedisService.hmIncrement(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.SIGN_IN_SUM_COUNT,1l);
        String yesterday= DateUtil.dateAdd(day,-1,DateUtil.DATE_PATTERN,DateUtil.DATE_PATTERN);
        if(!ValidateUtil.isEmpty(lastSignIn)&&lastSignIn.equals(yesterday)){
            stringRedisService.hmIncrement(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.SIGN_IN_CONTINUITY_COUNT,1l);
        }else{
            stringRedisService.hmDel(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.SIGN_IN_CONTINUITY_COUNT);
            stringRedisService.hmIncrement(RedisCons.USER_SIGN_IN_INFO+userId,RedisCons.SIGN_IN_CONTINUITY_COUNT,1l);
        }
        return  true;
    }
}
