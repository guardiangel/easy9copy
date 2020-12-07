package org.felix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 表示开启AOP代理自动配置，如果配@EnableAspectJAutoProxy表示使用cglib进行代理对象的生成；
 * 设置@EnableAspectJAutoProxy(exposeProxy=true)表示通过aop框架暴露该代理对象，具体参考LoginCheckAspect
 * 和SysLogAspect类
 * 启动类
 *
 * @author Felix
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class Easy9CopyStartUpApplication {

    public static void main(String[] args) {

        SpringApplication.run(Easy9CopyStartUpApplication.class, args);
    }

}
