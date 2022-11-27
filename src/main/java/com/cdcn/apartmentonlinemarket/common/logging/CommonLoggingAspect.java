package com.cdcn.apartmentonlinemarket.common.logging;

import com.cdcn.apartmentonlinemarket.common.util.LogUtil;
import com.cdcn.apartmentonlinemarket.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Aspect
@Component
public class CommonLoggingAspect {
    @Autowired
    private WebUtil webUtil;

    @Around("execution(* com.cdcn.apartmentonlinemarket.controller.*.*(..))")
    public Object logEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {
        String uri = webUtil.getRequestUri();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Map<String, Object> params = LogUtil.getParamsAsMap(methodSignature.getParameterNames(),
                joinPoint.getArgs());

        log.debug("[Request]  | Uri: {} [{}.{}] | Params: {}", uri, className,
                methodName, params);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        log.debug("[Response] | Uri: {} [{}.{}] | Elapsed time: {} ms | Result: {}",
                uri, className, methodName, elapsedTime, result);

        return result;
    }
}
