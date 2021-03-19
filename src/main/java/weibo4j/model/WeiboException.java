/*
 * Copyright © 2021 pengjianqiang
 * All rights reserved.
 * 项目名称：微博开放平台API-JAVA SDK
 * 项目描述：基于微博开放平台官网的weibo4j-oauth2-beta3.1.1包及新版接口做二次开发
 * 项目地址：https://github.com/qqxadyy/weibo-openapi-4java
 * 许可证信息：见下文
 *
 * ======================================================================
 *
 * src/main/java/weibo4j下的文件是从weibo4j-oauth2-beta3.1.1.zip中复制出来的
 * 本项目对这个目录下的部分源码做了重新改造
 * 但是许可信息和"https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1"或源码中已存在的保持一致
 */
package weibo4j.model;

import lombok.Getter;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.DefaultValueGetter;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * An exception class that will be thrown when WeiboAPI calls are failed.<br>
 * In case the Weibo server returned HTTP error code, you can get the HTTP status code using getStatusCode() method.
 * 
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class WeiboException extends RuntimeException {
    private @Getter int statusCode = -1;
    private @Getter int errorCode = -1;
    private @Getter String request;
    private @Getter String error;
    private static final long serialVersionUID = -2623309261327598087L;

    public WeiboException(String msg) {
        super(msg);
    }

    public WeiboException(Exception cause) {
        super(cause);
    }

    public WeiboException(String msg, int statusCode) throws JSONException {
        super(msg);
        this.statusCode = statusCode;
    }

    public WeiboException(String msg, JSONObject json, int statusCode) throws JSONException {
        super(msg + "\n error[" + json.getString("error") + "],error_code[" + json.getInt("error_code") + "],request["
            + json.getString("request") + "]");
        this.statusCode = statusCode;
        this.errorCode = json.getInt("error_code");
        this.error = json.getString("error");
        this.request = json.getString("request");
    }

    public WeiboException(String msg, Exception cause) {
        super(msg, cause);
    }

    public WeiboException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;

    }

    @Override
    public String getMessage() {
        // 把较常用的微博错误码转成中文，其它的还是按照英文返回
        String oriMsg = super.getMessage();
        String chineseMsg = null;
        switch (this.errorCode) {
            case 10006:
                chineseMsg = "缺少source(appkey)参数，或授权码不存在";
                break;
            case 10020:
                chineseMsg = "微博API接口不存在";
                break;
            case 10014:
                chineseMsg = "应用的接口访问权限受限";
                break;
            case 10017:
                if (CheckUtils.isNotEmpty(oriMsg) && oriMsg.toLowerCase().contains("text not find domain")) {
                    chineseMsg = "微博正文缺少带安全域名下的链接URL";
                } else {
                    chineseMsg = "参数值非法";
                }
                break;
            case 10022:
            case 10023:
            case 10024:
                chineseMsg = "接口请求频次超过上限";
                break;
            case 21325:
                chineseMsg = "换取授权码的code已失效";
                break;
            case 21321:
                chineseMsg = "用户授权已被取消，不能调用接口";
                break;
            case 21332:
                chineseMsg = "授权码不存在";
                break;
            case 20012:
                chineseMsg = "文本内容长度不能超过130(包括空格、换行等，纯英文不超过260)";
                break;
            case 20049:
                chineseMsg = "文本内容长度不能超过4900(包括空格、换行等，纯英文不超过9800)";
                break;
            case 21314:
                chineseMsg = "授权码已经被使用";
                break;
            case 21315:
                chineseMsg = "授权码已过期";
                break;
            case 21316:
            case 21317:
                chineseMsg = "授权码不合法";
                break;
            case 20101:
                chineseMsg = "目标微博不存在";
                break;
            default:
                break;
        }
        return DefaultValueGetter.getValue(oriMsg, chineseMsg);
    }

    public static WeiboException ofParamCanNotNull(String paramName) {
        throw new WeiboException("参数" + paramName + "不能为空");
    }

    public static WeiboException ofParamIdsOutOfLimit(String paramName, int limitNum) {
        throw new WeiboException(paramName + "的数量不能超过" + limitNum);
    }
}