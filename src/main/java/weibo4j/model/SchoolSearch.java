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
 * 搜学校搜索建议
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class SchoolSearch extends WeiboResponse {

    private static final long serialVersionUID = 4059782919675941016L;

    private String schoolName;
    private String location;
    private long id;
    private long type;

    public SchoolSearch(Response res) throws WeiboException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            id = json.getInt("id");
            location = json.getString("location");
            type = json.getLong("type");
            schoolName = json.getString("school_name");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public SchoolSearch(JSONObject json) throws WeiboException {
        try {
            id = json.getInt("id");
            location = json.getString("location");
            type = json.getLong("type");
            schoolName = json.getString("school_name");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public static List<SchoolSearch> constructSchoolSearch(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONArray();
            int size = list.length();
            List<SchoolSearch> schools = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                schools.add(new SchoolSearch(list.getJSONObject(i)));
            }
            return schools;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        } catch (WeiboException te) {
            throw te;
        }
    }

    @Override
    public String toString() {
        return "SchoolSearch [id=" + id + ",school_name=" + schoolName + ",location" + location + ", type=" + type
            + "]";
    }
}
