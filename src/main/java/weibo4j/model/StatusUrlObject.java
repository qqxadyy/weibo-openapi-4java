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
 * 微博status下的url_objects
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusUrlObject extends WeiboResponse {
    private @WeiboJsonName(value = "object_id", isNewAndNoDesc = true) String objectId;
    private @WeiboJsonName(value = "like_count", isNewAndNoDesc = true) Long likeCount;
    private @WeiboJsonName(value = "follower_count", isNewAndNoDesc = true) Long followerCount;
    private @WeiboJsonName(value = "search_topic_read_count", isNewAndNoDesc = true) Long searchTopicReadCount;
    private @WeiboJsonName(value = "super_topic_photo_count", isNewAndNoDesc = true) Long superTopicPhotoCount;
    private @WeiboJsonName(value = "search_topic_count", isNewAndNoDesc = true) Long searchTopicCount;
    private @WeiboJsonName(value = "super_topic_status_count", isNewAndNoDesc = true) Long superTopicStatusCount;
    private @WeiboJsonName(value = "asso_like_count", isNewAndNoDesc = true) Long assoLikeCount;
    private @WeiboJsonName(value = "url_ori", isNewAndNoDesc = true) String urlOri;
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean isActionType;
    private @WeiboJsonName(value = "card_info_un_integrity", isNewAndNoDesc = true) Boolean cardInfoUnIntegrity;
    private @WeiboJsonName(isNewAndNoDesc = true) JSONObject info;
    private @WeiboJsonName(isNewAndNoDesc = true) JSONObject object;

    public StatusUrlObject(Response res) {
        super(res);
    }

    public StatusUrlObject(JSONObject json) {
        super(json);
    }
}