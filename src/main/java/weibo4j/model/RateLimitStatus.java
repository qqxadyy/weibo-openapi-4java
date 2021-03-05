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

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月25日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class RateLimitStatus extends WeiboResponse {
    private @WeiboJsonName("ip_limit") Integer ipLimit;
    private @WeiboJsonName(value = "limit_time_unit", isNewAndNoDesc = true) String limitTimeUnit;
    private @WeiboJsonName("remaining_ip_hits") Integer remainingIpHits;
    private @WeiboJsonName("remaining_user_hits") Integer remainingUserHits;
    private @WeiboJsonName("reset_time") Date resetTime;
    private @WeiboJsonName("reset_time_in_seconds") Integer resetTimeInSeconds;
    private @WeiboJsonName("user_limit") Integer userLimit;
    private @WeiboJsonName("api_rate_limits") List<ApiRateLimits> apiRateLimit;

    public RateLimitStatus(Response res) {
        super(res);
    }

    public RateLimitStatus(JSONObject json) {
        super(json);
    }
}