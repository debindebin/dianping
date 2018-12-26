package com.yiyiglobal.dp.aop;

import com.yiyiglobal.dp.util.IpUtil;
import com.yiyiglobal.dp.util.JsonUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.Method;

/**
 * 用来打印controoler执行日志
 * Created by huazai on 2017/3/31.
 */
@Aspect
@Component
public class ControllerOperateLogAop {
    private final Logger accessLog = Logger.getLogger("accessLog");
    private final Logger infoLog = Logger.getLogger("infoLog");

    /**
     * 定义注解切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logPoint(){}

    // 前置通知
    @Before("logPoint()")
    public void beforeExec(JoinPoint jp){

    }

    // 后置通知
    @After("logPoint()")
    public void afterExec(JoinPoint jp){

    }
    /**
     * 环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logPoint()")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();

        ControllerRequestInfo annotaion = method.getAnnotation(ControllerRequestInfo.class);
        String description = "未知";
        if (annotaion != null){
            description = annotaion.description();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String ip = IpUtil.getIpAddr(request);          //ip地址
        String actionName = request.getRequestURI();    //action名字
        String params = JsonUtil.object2String(request.getParameterMap());//请求参数
        StringBuffer sb = new StringBuffer();
        sb.append(ip+" | ");
        sb.append(actionName +" | ");
        sb.append(description+" | ");
        sb.append(params);
        accessLog.info(sb.toString());
        return pjp.proceed();
    }

    /**
     * 异常通知
     * @param jp
     * @param e
     */
    @AfterThrowing(pointcut = "logPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint jp, Throwable e){
        infoLog.error("Exception{}",e);
    }
}
