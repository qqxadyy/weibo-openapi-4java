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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * 获取用户发布的微博的ID
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class UserTimelineIds extends WeiboResponse {

    private static final long serialVersionUID = -5541221820178631421L;

    private long nextCursor;
    private long previousCursor;
    private long totalNumber;
    private String marks;
    private List<String> statusesIds; // ID列表
    private boolean hasvisible;

    public UserTimelineIds(Response res) throws WeiboException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            nextCursor = json.getLong("next_cursor");
            previousCursor = json.getLong("previous_cursor");
            totalNumber = json.getLong("total_number");
            hasvisible = json.getBoolean("hasvisible");
            marks = json.getString("marks");
            JSONArray list = json.getJSONArray("statuses");
            int size = list.length();
            statusesIds = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                statusesIds.add(list.getString(i));
            }
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }

    }

    public UserTimelineIds(JSONObject json) throws WeiboException {
        try {
            nextCursor = json.getLong("next_cursor");
            previousCursor = json.getLong("previous_cursor");
            totalNumber = json.getLong("total_number");
            hasvisible = json.getBoolean("hasvisible");
            marks = json.getString("marks");
            JSONArray list = json.getJSONArray("statuses");
            int size = list.length();
            statusesIds = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                statusesIds.add(list.getString(i));
            }
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    @Override
    public String toString() {
        return "UserTimelineIds [" + "next_cursor=" + nextCursor + ", previous_cursor=" + previousCursor + ", marks="
            + marks + ", hasvisible=" + hasvisible + ", statusesIds=" + statusesIds + ", total_number = " + totalNumber
            + "]";
    }
}
