package org.felix.code;

/**
 * @Description
 *  基本响应码
 * @author Felix
 */
public enum  BaseResponseCode implements ResponseCodeInterface{


    /**
     * 这个要和前端约定好
     * 引导用户去登录界面的
     * code=401001 引导用户重新登录
     * code=401002 token 过期刷新token
     * code=401008 无权限访问
     */
    SUCCESS(0, "操作成功"),
    SYSTEM_BUSY(500001, "系统繁忙，请稍候再试"),
    OPERATION_ERROR(500002, "操作失败"),
    FILE_NOT_FOUND(500003, "文件找不到"),
    FACE_LOGIN_FAILED(500004, "数据库中不存在您的人脸信息"),
    DB_RESET_SUCCESS(500005, "数据库已还原成功，可以去登录啦！"),
    AUTHORIZATION_EXPIRED_ERROR(500006, "授权过期，请联系管理员(QQ:1134135987)处理"),

    TOKEN_PARSE_ERROR(401001, "登录凭证已过期，请重新登录"),
    TOKEN_ERROR(401001, "登录凭证已过期，请重新登录"),
    ACCOUNT_ERROR(401001, "该账号异常，请联系管理员(QQ:1134135987)处理"),
    ACCOUNT_LOCK_ERROR(401001, "该用户已被锁定，请联系管理员(QQ:1134135987)处理"),
    TOKEN_PAST_DUE(401002, "授权信息已过期，请刷新token"),
    DATA_ERROR(401003, "传入数据异常"),
    NOT_ACCOUNT(401004, "该登录账号不存在"),
    USER_LOCK(401005, "该用户已被锁定，无法登录，请联系管理员(QQ:1134135987)处理"),
    PASSWORD_ERROR(401006, "登录密码错误，请联系管理员(QQ:1134135987)处理"),
    METHODARGUMENTNOTVALIDEXCEPTION(401007, "方法参数校验异常"),
    UNAUTHORIZED_ERROR(401008, "权鉴校验不通过"),
    ROLE_PERMISSION_RELATION(401009, "该菜单权限存在子集关联，不允许删除"),
    OLD_PASSWORD_ERROR(401010, "原密码不正确"),
    NOT_PERMISSION_DELETED_DEPT(401011, "该组织机构下还关联着用户，不允许删除"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(401012, "操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(401013, "操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(401013, "操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_UPDATE(401014, "操作的菜单权限存在子集关联不允许变更"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(401015, "菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(401016, "菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(401017, "菜单权限的请求方式不能为空"),
    PICTURE_CODE_ERROR(401018, "图片验证码错误"),
    OPER_SO_FAST(401019, "操作频繁，请稍候再试！"),
    REGISTER_EXIST(401020, "邮箱已存在，请更换后再试！"),
    REGISTER_CODE_ERROR(401021, "验证码错误，请仔细查看邮件中的验证码！"),
    REGISTER_PASSWORD_ERROR(401022, "两次输入的密码不一致！"),
    REGISTER_EMAIL_ERROR(401023, "邮箱格式错误！"),
    REGISTER_EMAIL_CODE_ERROR(401024, "验证码已发送，请勿重复点击发送！"),
    REGISTER_USERNAME_STYLE_ERROR(401025, "账号格式错误！"),
    REGISTER_PASSWORD_STYLE_ERROR(401026, "密码格式错误！"),
    EMAIL_NOT_EXIST(401027, "该邮箱还未注册！"),
    USERNAME_EXIST(401028, "登录账号已存在，请更换后再试！"),
    REALNAME_EXIST(401028, "真实姓名已存在，请更换后再试！"),
    PHONE_ERROR(401029, "手机号格式错误，请更换！"),
    SYS_CONFIG_KEY_EXIST(401030, "该系统参数键已存在，请更换"),
    SYS_NOTICE_TYPE_EXIST(401031, "该消息类型已存在，请更换"),
    BLACK_IP(401032, "您已被置为黑名单，请稍候再试"),

    //行政管理-错误提示******开始（411开头）
    XZGL_YEARMONTH_ERROR(41100001, "工资发放时间不能为空"),
    //行政管理-错误提示******结束

    //进销存管理-错误提示******开始（412开头）
    WAREHOUSE_NAME_EXIST(41200001, "仓库名称已存在，请更换"),
    GOODS_NAME_MODEL_EXIST(41200002, "该型号的商品名称已存在，请更换"),
    GOODS_CODE_NO_EXIST(41200003, "商品不存在库存，请重新操作"),
    STORE_NOT_ENOUGH(41200004, "库存不足"),
    //进销存管理-错误提示******结束

    //证书相关错误******开始
    LICENSE_ERROR(-1001, "授权失效，请联系管理员(QQ:1134135987)重新申请授权证书！"),
    LICENSE_ERROR_2(-1002, "无法获取当前服务器硬件信息"),
    LICENSE_ERROR_3(-1003, "证书无效，当前服务器的CPU序列号没在授权范围内"),
    LICENSE_ERROR_4(-1004, "证书无效，当前服务器的主板序列号没在授权范围内"),
    LICENSE_ERROR_5(-1005, "证书无效，当前服务器的Mac地址没在授权范围内"),
    LICENSE_ERROR_6(-1006, "证书无效，当前服务器的IP没在授权范围内"),
    LICENSE_ERROR_7(-1007, "证书无效，用户类型为空"),
    LICENSE_ERROR_8(-1008, "证书生效时间不能晚于证书失效时间"),
    LICENSE_ERROR_9(-1009, "证书失效时间不能早于当前时间"),
    LICENSE_ERROR_10(-1010, "无法从License授权文件获取授权硬件信息"),
    //证书相关错误******结束
    ;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误消息
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
