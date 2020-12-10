/*CoreUtil*/
/*工具类，类似java静态工具类*/

let environment_flag = "2";//1-生产环境；2-测试环境
let SystemBaseIP = "";
if(environment_flag == "1"){
    SystemBaseIP = "119.45.157.94";
}else if(environment_flag == "2"){
    SystemBaseIP = "127.0.0.1";
}




//此处的var不能改成let
var CoreUtil = (function () {
    let coreUtil = {};
    /*ajax请求*/
    coreUtil.sendAjax = function (url, params, ft, method, headers, noAuthorityFt, contentType, async) {
        let roleSaveLoading = top.layer.msg('请稍候...', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: url,
            cache: false,
            async: async == undefined ? true : async,
            data: params,
            type: method == undefined ? "POST" : method,
            contentType: contentType == undefined ? 'application/json; charset=UTF-8' : contentType,
            dataType: "json",
            beforeSend: function (request) {
                if (headers == undefined) {
                } else if (headers) {
                    request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
                    request.setRequestHeader("refresh_token", CoreUtil.getData("refresh_token"));
                } else {
                    request.setRequestHeader("authorization", CoreUtil.getData("access_token"));
                }

            },
            success: function (res) {
                top.layer.close(roleSaveLoading);
                if (typeof ft == "function") {
                    if (res.code == 401001) { //凭证过期重新登录
                        layer.msg("凭证过期请重新登录")
                        top.window.location.href = "/index/login"
                    } else if (res.code == 401002) {  //根据后端提示刷新token
                        /*记录要重复刷新的参数*/
                        let reUrl = url;
                        let reParams = params;
                        let reFt = ft;
                        let reMethod = method;
                        let reHeaders = headers;
                        let reNoAuthorityFt = noAuthorityFt;
                        let reContentType = contentType;
                        let reAsync = async;
                        /*刷新token  然后存入缓存*/
                        CoreUtil.sendAjax("/sys/user/token", null, function (res) {
                            if (res.code == 0) {
                                CoreUtil.setData("access_token", res.data);
                                /*刷新成功后继续重复请求*/
                                CoreUtil.sendAjax(reUrl, reParams, reFt, reMethod, reHeaders, reNoAuthorityFt, reContentType, reAsync);
                            } else {
                                layer.msg("凭证过期请重新登录");
                                top.window.location.href = "/index/login"
                            }
                        }, "GET", true)
                    } else if (res.code == 0) {
                        if (ft != null && ft != undefined) {
                            ft(res);
                        }
                    } else if (res.code == 401008) {//无权限响应
                        if (ft != null && ft != undefined) {
                            noAuthorityFt(res);
                        }
                    } else {
                        layer.msg(res.msg)
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                top.layer.close(roleSaveLoading);
                console.log(XMLHttpRequest);
                console.log(textStatus);
                console.log(errorThrown);
                if (XMLHttpRequest.status == 404) {
                    top.window.location.href = "/index/404";
                } else if (XMLHttpRequest.status == 500) {
                    top.window.location.href = "/index/500";
                } else if (XMLHttpRequest.status == 200) {
                    console.log(XMLHttpRequest.responseText);
                } else {
                    layer.msg("服务器好像出了点问题！请稍后试试");
                }
            }
        });
    };

    /*导出Excel*/
    coreUtil.exportExcel = function(formId, url){ // formId: form表单的id; url: 请求url
        // console.log("formId="+formId);
        // console.log("url="+url);
        try {
            let queryForm = $2("#" + formId);
            let exportForm = $2("<form action='" + url + "' method='post'></form>");

            queryForm.find("input").each(function() {
                let name = $2(this).attr("name");
                let value = $2(this).val();
                exportForm.append("<input type='hidden' name='" + name + "' value='" + value + "'/>");
            });

            queryForm.find("select").each(function() {
                let name = $2(this).attr("name");
                let value = $2(this).val();
                exportForm.append("<input type='hidden' name='" + name + "' value='" + value + "'/>");
            });
            $(document.body).append(exportForm);
            exportForm.submit();
        } catch (e) {
            console.log(e);
        }
    };


    /*表单数据封装成 json String*/
    coreUtil.formJson = function (formId) {  //formId：form表单的id
        let o = {};
        let a = $("#" + formId).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON.stringify(o);
    };

    /*存入本地缓存*/
    coreUtil.setData = function (key, value) {
        layui.data('LocalData', {
            key: key,
            value: value
        })
    };
    /*从本地缓存拿数据*/
    coreUtil.getData = function (key) {
        let localData = layui.data('LocalData');
        return localData[key];
    };

    /*获取服务器IP*/
    coreUtil.getSystemBaseIP = function () {
        return SystemBaseIP;
    };

    coreUtil.formattime = function (val) {
        let date = new Date(val);
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        month = month > 9 ? month : ('0' + month);
        let day = date.getDate();
        day = day > 9 ? day : ('0' + day);
        let hh = date.getHours();
        hh = hh > 9 ? hh : ('0' + hh);
        let mm = date.getMinutes();
        mm = mm > 9 ? mm : ('0' + mm);
        let ss = date.getSeconds();
        ss = ss > 9 ? ss : ('0' + ss);
        let time = year + '-' + month + '-' + day + ' ' + hh + ':' + mm + ':' + ss;
        return time;
    };

    return coreUtil;
})(CoreUtil, window);
