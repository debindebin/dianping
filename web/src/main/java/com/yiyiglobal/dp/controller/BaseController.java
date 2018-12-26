package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    protected final Logger logger = Logger.getLogger("infoLog");

    protected Map<String, Object> success() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "suc");
        return result;
    }

    protected Map<String, Object> success(Map<String, Object> result) {
        result.put("status", "suc");
        return result;
    }

//    protected Map<String, Object> error(ResultEnum error) {
//        Map<String, Object> result = new HashMap<String, Object>();
//
//        result.put("code",error.getCode());
//        result.put("msg",error.getMsg());
//
//        return result;
//    }

}
