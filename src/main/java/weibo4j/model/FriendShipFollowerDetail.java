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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 两个用户之间的关注关系详情
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class FriendShipFollowerDetail extends WeiboResponse {
    private String id; // 用户id
    private @WeiboJsonName("screen_name") String screenName; // 微博昵称
    private @WeiboJsonName(value = "followed_by", isNewAndNoDesc = true) Boolean followedBy; // 是否被另一个用户关注
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean following; // 是否已关注另一个用户
    private @WeiboJsonName(value = "notifications_enabled", isNewAndNoDesc = true) Boolean notificationsEnabled;

    public FriendShipFollowerDetail(Response res) {
        super(res);
    }

    public FriendShipFollowerDetail(JSONObject json) {
        super(json);
    }
}