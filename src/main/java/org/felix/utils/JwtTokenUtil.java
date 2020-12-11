package org.felix.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;


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
        Date now = new Date(nowMillis);
        byte[] signingKey = DatatypeConverter.parseBase64Binary(secretKey);
        JwtBuilder builder = Jwts.builder();
        if (null != claims) {
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        builder.setIssuedAt(now);
        if (toMillis >= 0) {
            long expMillis = nowMillis + toMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        builder.signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        String userId = null;
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            userId = claims.getSubject();
        }

        return userId;
    }

    /**
     * 从令牌中获得数据声明
     *
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(token))
                .parseClaimsJws(token).getBody();
    }

    /**
     * 检验令牌
     *
     * @param token
     * @return
     */
    public static Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return claimsFromToken != null && !isTokenExpired(token);
    }


    /**
     * 验证TOKEN是否过期
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } else {
            return false;
        }
    }

    /**
     * 刷新token
     *
     * @param refreshToken
     * @param claims
     * @return
     */
    public static String refreshToken(String refreshToken, Map<String, Object> claims) {

        String refreshedToken = null;
        Claims parserClaims = getClaimsFromToken(refreshToken);
        claims = claims == null ? parserClaims : claims;
        refreshedToken = generateToken(parserClaims.getIssuer(),
                parserClaims.getSubject(), claims,
                accessTokenExpireTime.toMillis(), secretKey);

        return refreshedToken;
    }

    public static long getRemainingTime(String token) {
        long result = 0;
        long nowMills = System.currentTimeMillis();
        result = getClaimsFromToken(token).getExpiration().getTime() - nowMills;
        return result;
    }

}
