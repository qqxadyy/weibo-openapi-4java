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
 * 获取地点分类
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class PoisitionCategory extends WeiboResponse {

    private static final long serialVersionUID = 6795534455304308918L;

    private long id;
    private String name;
    private long pid;

    public PoisitionCategory(Response res) throws WeiboException {
        super(res);
        JSONObject json = null;
        try {
            json = res.asJSONObject();
            id = json.getLong("id");
            pid = json.getLong("pid");
            name = json.getString("name");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public PoisitionCategory(JSONObject json) throws WeiboException {
        try {
            id = json.getLong("id");
            pid = json.getLong("pid");
            name = json.getString("name");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public static List<PoisitionCategory> constructPoisCategory(Response res) throws WeiboException {
        try {
            JSONArray json = res.asJSONArray();
            int size = json.length();
            List<PoisitionCategory> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(new PoisitionCategory(json.getJSONObject(i)));
            }
            return list;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    @Override
    public String toString() {
        return "PoisitionCategory [" + "id=" + id + ", name=" + name + ", pid=" + pid + "]";
    }
}
