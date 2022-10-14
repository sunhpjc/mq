package com.sunhp.activiti.we_chat_inter.annotations_interception;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunhp
 * @Description 对外接口注解
 * @Date 2022/10/14 9:43
 **/
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyOutAccess {
}
