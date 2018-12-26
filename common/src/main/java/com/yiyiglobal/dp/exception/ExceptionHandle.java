package com.yiyiglobal.dp.exception;

import com.yiyiglobal.dp.common.ResultEnum;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangdebin on 2018/4/26.
 */
@ControllerAdvice
public class ExceptionHandle {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> handler(Exception e){
        Map<String, Object> result = new HashMap<String, Object>();

        if( e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            result.put("code", businessException.getCode());
            result.put("msg", businessException.getMessage());
            return result;
        }else if(e instanceof BindException){
            BindException  bindException =(BindException) e;
            result.put("code", ResultEnum.PARAM_INVALID.getCode());
            result.put("msg", bindException.getFieldError().getDefaultMessage());
            return result;
        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException  validException =(MethodArgumentNotValidException) e;
            result.put("code", ResultEnum.PARAM_INVALID.getCode());
            result.put("msg", validException.getBindingResult().getFieldError().getDefaultMessage());
            return result;
        }else if(e instanceof ConstraintViolationException){
            ConstraintViolationException  exception =(ConstraintViolationException) e;
            result.put("code", ResultEnum.PARAM_INVALID.getCode());
            if(exception.getMessage()!=null&&exception.getMessage().contains(":")){
                result.put("msg",exception.getMessage().substring(exception.getMessage().indexOf(":") + 1) );
            }else{
                result.put("msg", exception.getMessage());
            }
            return result;
        }else {
            logger.error("[系统异常] {}",e);
            result.put("code", ResultEnum.UNKONOWN_ERROR.getCode());
            result.put("msg",ResultEnum.UNKONOWN_ERROR.getMsg());
            return result;
        }
    }
}
