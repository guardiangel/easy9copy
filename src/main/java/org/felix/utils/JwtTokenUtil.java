package org.felix.utils;

<<<<<<< HEAD
import io.jsonwebtoken.SignatureAlgorithm;
=======
>>>>>>> origin/master
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
<<<<<<< HEAD
import java.util.Map;
=======
>>>>>>> origin/master

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

<<<<<<< HEAD
    public static String getAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    public static String getRefreshAppToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireAppTime.toMillis(), secretKey);
    }

    public static String getRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生成token
     *
     * @param issuer    签发人
     * @param subject   代表这个JWT的主体，即它的所有人 一般是用户id
     * @param claims    存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @param toMillis  有效时间(毫秒)
     * @param secretKey
     * @return
     */
    private static String generateToken(String issuer, String subject,
                                        Map<String, Object> claims, long toMillis, String secretKey) {
        SignatureAlgorithm signatureAlgorithm
                = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

    }
=======
>>>>>>> origin/master


}
