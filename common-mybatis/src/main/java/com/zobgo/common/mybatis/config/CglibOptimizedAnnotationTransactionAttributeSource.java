package com.zobgo.common.mybatis.config;

import org.springframework.aop.support.AopUtils;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/10/30.
 */
@Slf4j
public class CglibOptimizedAnnotationTransactionAttributeSource extends AnnotationTransactionAttributeSource {

    @Override
    protected TransactionAttribute findTransactionAttribute(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.getName().startsWith("com.cafintech")) {
            log.info(declaringClass.getName());
        }
        if (declaringClass.getName().contains("DepartmentAndRoleServiceImpl")){
            log.info("");
        }
        if (AopUtils.isCglibProxy(declaringClass)) {
            try {
                //find appropriate method on parent class
                Method superMethod = declaringClass.getSuperclass().getMethod(method.getName(), method.getParameterTypes());
                return super.findTransactionAttribute(superMethod);
            } catch (Exception ex) {
                if(log.isWarnEnabled()) {
                    log.warn("Cannot find superclass method for Cglib method: " + method.toGenericString());
                }
                return super.findTransactionAttribute(method);
            }
        } else {
            return super.findTransactionAttribute(method);
        }
    }

}

