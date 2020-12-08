package org.felix.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.felix.aop.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Felix
 */
@Component
public class IPAddressTool {
    private Logger log = LoggerFactory.getLogger(IPAddressTool.class);

    @Value("${system.baidu_ak}")
    private String baidu_ak;

    /**
     * 状态码	定义	注释
     * 0	正常
     * 1	服务器内部错误	该服务响应超时或系统内部错误
     * 10	上传内容超过8M	Post上传数据不能超过8M
     * 101	AK参数不存在	请求消息没有携带AK参数
     * 102	Mcode参数不存在，mobile类型mcode参数必需	对于Mobile类型的应用请求需要携带mcode参数，该错误码代表服务器没有解析到mcode
     * 200	APP不存在，AK有误请检查再重试	根据请求的AK，找不到对应的APP
     * 201	APP被用户自己禁用，请在控制台解禁
     * 202	APP被管理员删除	恶意APP被管理员删除
     * 203	APP类型错误	当前API控制台支持Server(类型1), Mobile(类型2, 新版控制台区分为Mobile_Android(类型21)及Mobile_IPhone（类型22）及Browser（类型3），除此之外的其他类型被认为是APP类型错误
     * 210	APP IP校验失败	在申请Server类型应用的时候选择IP校验，需要填写IP白名单，如果当前请求的IP地址不在IP白名单或者不是0.0.0.0/0就认为IP校验失败
     * 211	APP SN校验失败	SERVER类型APP有两种校验方式：IP校验和SN校验，当用户请求的SN和服务端计算出来的SN不相等的时候，提示SN校验失败
     * 220	APP Referer校验失败	浏览器类型的APP会校验referer字段是否存在，且在referer白名单里面，否则返回该错误码
     * 230	APP Mcode码校验失败	服务器能解析到mcode，但和数据库中不一致，请携带正确的mcode
     * 240	APP 服务被禁用	用户在API控制台中创建或设置某APP的时候禁用了某项服务
     * 250	用户不存在	根据请求的user_id, 数据库中找不到该用户的信息，请携带正确的user_id
     * 251	用户被自己删除	该用户处于未激活状态
     * 252	用户被管理员删除	恶意用户被加入黑名单
     * 260	服务不存在	服务器解析不到用户请求的服务名称
     * 261	服务被禁用	该服务已下线
     * 301	永久配额超限，限制访问	配额超限，如果想增加配额请联系我们
     * 302	天配额超限，限制访问	配额超限，如果想增加配额请联系我们
     * 401	当前并发量已经超过约定并发配额，限制访问	并发控制超限，请控制并发量请联系我们
     * 402	当前并发量已经超过约定并发配额，并且服务总并发量也已经超过设定的总并发配额，限制访问	并发控制超限，请控制并发量请联系我们
     * 1001	没有IPv6地址访问的权限	如需通过IPv6来获取位置信息，请提交工单申请
     */
    public String getAddressById(String ip) {
        String result = "";
        try {
            if (!Tool.isBlank(ip)) {
                //AK需要自己去申请，申请地址：http://lbsyun.baidu.com/index.php?title=webapi/ip-api
                String url = "http://api.map.baidu.com/location/ip?ak=" + baidu_ak + "&ip=" + ip + "&coor=bd09ll";
                log.info("IPAddressTool.getAddressById 开始调用百度接口[根据IP定位]");
                String resultTemp = HttpUtil.get(url, CharsetUtil.charset(Constant.SYSTEM_CHARACTER_ENCODING));
                Map json = (Map) JSONObject.parse(resultTemp);
                String status = json.get("status").toString();
                log.info("结束调用百度接口[根据IP定位]，返回状态码：{}", status);
                if ("0".equalsIgnoreCase(status)) {
                    Map content = (Map) JSONObject.parse(json.get("content").toString());
                    result = content.get("address").toString();
                }
            }
        } catch (Exception e) {
            log.error("调用百度接口[根据IP定位]，发生异常：{}", e);
        }
        return result;
    }
}
