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

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import pjq.weibo.openapi.utils.DateTimeUtils;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * get_token_info的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class AccessTokenInfo extends WeiboResponse implements Serializable {
    private String uid;
    private String appkey;
    private String scope;
    private @WeiboJsonName("create_at") String createAt; // 秒级别的时间戳，例如1612230566
    private @WeiboJsonName("expire_in") String expireIn;

    public AccessTokenInfo(Response res) {
        super(res);
    }

    public AccessTokenInfo(JSONObject json) {
        super(json);
    }

    public AccessToken toAccessTokenObj(String clientId, String accessToken) {
        AccessToken tokenInfo = new AccessToken();
        tokenInfo.setClientId(clientId);
        tokenInfo.setAccessToken(accessToken);
        tokenInfo.setExpiresIn(expireIn);
        tokenInfo.setUid(uid);
        tokenInfo.setCreateAt(DateTimeUtils.timestampToDate(Long.valueOf(createAt) * 1000));
        return tokenInfo;
    }
}