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
import pjq.commons.utils.CheckUtils;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 获取用户的粉丝数、关注数、微博数、悄悄关注数
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserCounts extends WeiboResponse {
    private String id;
    private @WeiboJsonName("followers_count") Long followersCount;// 粉丝数
    private @WeiboJsonName("friends_count") Long friendsCount;// 关注数
    private @WeiboJsonName("statuses_count") Long statusesCount;// 微博数
    private @WeiboJsonName("private_friends_count") Long privateFriendsCount;// 悄悄关注数
    private @WeiboJsonName(value = "pagefriends_count", isNewAndNoDesc = true) Long pageFriendsCount;

    public UserCounts(Response res) {
        super(res);
    }

    public UserCounts(JSONObject json) {
        super(json);
    }

    public long toLongValue(Long value) {
        return CheckUtils.isNull(value) ? 0 : value;
    }
}