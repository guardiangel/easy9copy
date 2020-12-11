package org.felix.shiro;

import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.felix.aop.constants.Constant;
import org.felix.service.PermissionService;
import org.felix.service.RedisService;
import org.felix.service.RoleService;
import org.felix.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
public class CustomRealm extends AuthorizingRealm {

    private Logger log = LoggerFactory.getLogger(CustomRealm.class);

    @Resource
    private RedisService redisService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomPasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        /**
         * 非正常退出，没有显示调用 SecurityUtils.getSubject().logout();
         * 缓存依旧存在
         */
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String accessToken = (String) SecurityUtils.getSubject().getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        log.info("login userId={}", userId);
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)
                && redisService.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS)
                > JwtTokenUtil.getRemainingTime(accessToken)) {
            List<String> roleNames = roleService.getRoleNames(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                simpleAuthorizationInfo.addRoles(roleNames);
            }
            simpleAuthorizationInfo
                    .setStringPermissions(permissionService.getPermissionsByUserId(userId));
        } else {
            Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(accessToken);
            if (claimsFromToken.get(Constant.JWT_ROLES_KEY) != null) {
                simpleAuthorizationInfo.addRoles((Collection<String>) claimsFromToken.get(Constant.JWT_ROLES_KEY));
            }
            if (claimsFromToken.get(Constant.JWT_PERMISSIONS_KEY) != null) {
                simpleAuthorizationInfo.addRoles((Collection<String>) claimsFromToken.get(Constant.JWT_PERMISSIONS_KEY));
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        CustomPasswordToken customPasswordToken = (CustomPasswordToken) token;
        SimpleAuthenticationInfo authorizationInfo
                = new SimpleAuthenticationInfo(customPasswordToken.getPrincipal(),
                customPasswordToken.getPrincipal(), getName());

        return authorizationInfo;
    }
}
