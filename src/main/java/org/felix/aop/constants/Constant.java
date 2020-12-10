package org.felix.aop.constants;

/**
 * @author Felix
 */
public final class Constant {

    /**
     * permission的父ID
     */
    public static final String PERMISSION_PID = "0";

    /**
     * 系统编码格式
     */
    public static final String SYSTEM_CHARACTER_ENCODING = "UTF-8";

    /**
     * 未定义的值
     */
    public static final String UNDEFINED = "undefined";

    /**
     * 正常的认证token
     */
    public static final String ACCESS_TOKEN = "authorization";

    /**
     *主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY = "jwt-refresh-key:";

    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY = "shiro-cache:org.felix.shiro.CustomRealm.authorizationCache:";
}
