package com.xfmeet.core.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xfmeet.core.log.annotation.CommonLog;
import com.xfmeet.core.log.common.CommonLogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author meet
 */
@Aspect
@Component
public class CommonLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogAspect.class);

    @Pointcut("@annotation(com.xfmeet.core.log.annotation.CommonLog)")
    public void serviceLog() {
    }

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) {
        String level = getLogLevel(joinPoint);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", Arrays.toString(joinPoint.getArgs())));
        level = level.toUpperCase();
        CommonLogUtils.log(LOGGER, level, stringBuffer);
    }

    @AfterReturning(returning = "object", pointcut = "serviceLog()")
    public void doAfter(JoinPoint joinPoint, Object object) {
        String level = getLogLevel(joinPoint);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", JSON.toJSONString(object)));
        level = level.toUpperCase();
        CommonLogUtils.log(LOGGER, level, stringBuffer);
    }

    private String getLogLevel(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CommonLog log = method.getAnnotation(CommonLog.class);
        return log.value();
    }
}
