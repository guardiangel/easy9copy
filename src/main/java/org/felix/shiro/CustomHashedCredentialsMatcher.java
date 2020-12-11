package org.felix.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.felix.aop.constants.Constant;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.service.RedisService;
import org.felix.utils.JwtTokenUtil;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    @Resource
    private RedisService redisService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        CustomPasswordToken customPasswordToken = (CustomPasswordToken) token;
        String accessToken = (String) customPasswordToken.getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        if (redisService.hasKey(Constant.ACCOUNT_LOCK_KEY + userId)) {
            throw new ServiceException(BaseResponseCode.ACCOUNT_LOCK_ERROR);
        }
        if (redisService.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST + accessToken)) {
            throw new ServiceException(BaseResponseCode.TOKEN_ERROR);
        }
        if (redisService.hasKey(Constant.JWT_REFRESH_STATUS + accessToken)) {
            return true;
        }
        if (JwtTokenUtil.isTokenExpired(accessToken)) {
            throw new ServiceException(BaseResponseCode.TOKEN_PAST_DUE);
        }
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId) &&
                redisService.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS)
                        > JwtTokenUtil.getRemainingTime(accessToken)) {
            if (!redisService.hasKey(Constant.JWT_REFRESH_IDENTIFICATION + accessToken)) {
                throw new ServiceException(BaseResponseCode.TOKEN_PAST_DUE);
            }
        }
        return true;
    }
}
