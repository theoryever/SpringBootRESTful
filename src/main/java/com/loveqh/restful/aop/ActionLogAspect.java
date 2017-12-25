package com.loveqh.restful.aop;

import org.apache.catalina.core.ApplicationContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Stream;

// 声明为一个切面
@Aspect
@Component
public class ActionLogAspect {

    Logger logger = LoggerFactory.getLogger(ActionLogAspect.class);

    ThreadLocal<Long> startTimeMillis = new ThreadLocal<>();

    /**
     * 声明切点
     */
    @Pointcut("@annotation(com.loveqh.restful.aop.Action)")
    public void annotationPointCut(){}

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Action action = method.getAnnotation(Action.class);
        logger.info("before");
        logger.info(action.value() + " : " + action.extra());
        logger.info("该方法的的返回值类型为： " + method.getReturnType().getName());
        logger.info("该方法的的参数为： ");
        Stream.of(joinPoint.getArgs())
                .forEach(arg -> {
                    logger.info(arg.toString());
                });
        startTimeMillis.set(System.currentTimeMillis());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Action action = method.getAnnotation(Action.class);
        logger.info("after");
        long executeTimeMillis = System.currentTimeMillis() - startTimeMillis.get();
        logger.info("executeTimeMillis： " + executeTimeMillis);
    }
}
