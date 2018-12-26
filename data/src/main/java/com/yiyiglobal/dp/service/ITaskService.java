package com.yiyiglobal.dp.service;



import com.yiyiglobal.dp.domain.Res;

import java.util.Map;
import java.util.Set;

public interface ITaskService {

    Map<String,Object> getTaskHomeData(Integer userId);

    boolean   signIn(Integer userId);



}
