package com.zobgo.job.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 10/25/18.
 */

@Aspect
@Slf4j
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LogInfoAspect {


    @Before("@within(com.zobgo.job.aop.LogInfo) || @annotation(com.zobgo.job.aop.LogInfo)")
    public void begin(){
        log.info("开始事务---");
    }

    @After("@within(com.zobgo.job.aop.LogInfo) || @annotation(com.zobgo.job.aop.LogInfo)")
    public void finish(){
        log.info("结束事务---");
    }
}
