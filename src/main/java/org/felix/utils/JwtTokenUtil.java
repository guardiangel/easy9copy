package org.felix.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * @author Felix
 */
public final class JwtTokenUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static String secretKey;

    private static Duration accessTokenExpireTime;

    private static Duration refreshTokenExpireTime;

    private static Duration refreshTokenExpireAppTime;

    private static String issuer;

    public static void setTokenSettings(TokenSettings tokenSettings) {
        secretKey = tokenSettings.getSecretKey();
        accessTokenExpireTime = tokenSettings.getAccessTokenExpireTime();
        refreshTokenExpireTime = tokenSettings.getRefreshTokenExpireTime();
        refreshTokenExpireAppTime = tokenSettings.getRefreshTokenExpireAppTime();
        issuer = tokenSettings.getIssuer();
    }



}
