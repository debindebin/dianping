package com.yiyiglobal.dp.exception;

import com.yiyiglobal.dp.common.ResultEnum;

/**
 * Created by wangzukun on 2018/4/25.
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
