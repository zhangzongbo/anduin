package com.zobgo.job.aop;

import java.lang.annotation.*;

/**
 * Created by zongbo.zhang on 10/25/18.
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
}
