package com.loveqh.restful.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String value(); // 执行的操作
    String extra(); // 额外的信息
}
