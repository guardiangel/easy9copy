package org.felix.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Felix
 * @Description: 登录检查注解，用在Controller中的方法上，表示接口需要进行登录验证
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginCheck {
    /**
     * 登录时，校验权限
     * @return
     */
    String permission() default "";
}
