package org.felix.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.felix.aop.constants.Constant;
import org.felix.exception.ServiceException;
import org.felix.model.entity.SysUser;
import org.felix.service.UserService;
import org.felix.utils.JwtTokenUtil;
import org.felix.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Felix
 * @Description: AOP实现登录验证
 */
@Component
@Aspect
public class LoginCheckAspect {
    private static final Logger log = LoggerFactory.getLogger(LoginCheckAspect.class);

    @Autowired
    private UserService userService;

    @Pointcut("@annotation(org.felix.aop.annotation.LoginCheck)")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        /**
         * 1.不要在请求方法里另起一个子线程调用该方法；
         * 2.在请求周期中，尽可能不要传递Request实例给多线程使用，
         * 因为子线程可能在Request生命周期结束销毁后再使用Request时获取不了参数，
         * 否则必须同步线程 让其在生命周期结束前调用；
         * 可看到之所以能通过静态方法getRequestAttributes获取Request实例，
         * 是因为ThreadLocal获取。一个请求到达容器后，Spring会把该请求Request实例通过setRequestAttributes方法
         * 把Request实例放入该请求线程内ThreadLocalMap中，然后就可以通过静态方法取到。原理就是ThreadLocal，
         * 但ThreadLocal不能让子线程继承ThreadLocalMap信息，可以使用InherbritableThreadLocal实现子线程信息传递。
         * 但Spring Boot 默认使用ThreadLocal把Request设置进请求线程中，
         * 这样如果在请求方法里面另起一个子线程然后再通过getRequestAttributes方法获取，是获取不到的
         */
        ServletRequestAttributes attributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        //获得前端传入的token
        String access_token = request.getHeader(Constant.ACCESS_TOKEN);

        log.info("前端传入access_token={}", access_token);

        String desc = "未登录，请先登录";

        if (Tool.isBlank(access_token) || Constant.UNDEFINED.equals(access_token)) {
            throw new ServiceException(9999, desc);
        }

        String user_id = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));

        if (Tool.isBlank(user_id)) {
            throw new ServiceException(9999, desc);
        } else {
            SysUser user = userService.detailInfo(user_id);
            if (user == null) {
                throw new ServiceException(9999, desc);
            }
            log.info("北方社区-用户[{}]登录校验成功", user.getRealName());
        }

    }



}
