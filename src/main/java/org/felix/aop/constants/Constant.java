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

    /**
     * 当前用户被锁定
     */
    public static final String ACCOUNT_LOCK_KEY = "account-lock-key:";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist:";

    /**
     * 刷新状态(适用场景如：一个功能点要一次性请求多个接口，当第一个请求刷新接口时候 加入一个节点下一个接口请求进来的时候直接放行)
     */
    public static final String JWT_REFRESH_STATUS = "jwt-refresh-status:";

    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION = "jwt-refresh-identification:";

    /**
     * 角色key
     */
    public static final String JWT_ROLES_KEY = "jwt-roles-key:";

    /**
     * 权限key
     */
    public static final String JWT_PERMISSIONS_KEY = "jwt-permissions-key";

    /**
     *登录图片验证码图片存Redis的key前缀
     */
    public static final String PICTURE_VERIFICATION = "pictureVerification";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME = "jwt-user-name-key";;
}
