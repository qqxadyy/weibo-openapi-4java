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
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import weibo4j.Weibo;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * @author sinaWeibo
 * 
 */
@Getter
@Setter
public class Tag extends WeiboResponse {

    private static final long serialVersionUID = 2177657076940291492L;

    private String id; // 标签id

    private String value; // 标签value

    private String weight;

    @SuppressWarnings("unchecked")
    public Tag(JSONObject json) throws WeiboException, JSONException {
        if (!json.getString("id").isEmpty()) {
            id = json.getString("id");
        }
        if (!json.getString("value").isEmpty()) {
            value = json.getString("value");
        } else {
            Iterator<String> keys = json.sortedKeys();
            if (keys.hasNext()) {
                id = keys.next();
                value = json.getString(id);
            }
        }
        weight = json.getString("weight");
    }

    public Tag(JSONObject json, Weibo weibo) throws WeiboException, JSONException {
        System.out.println(json);
        id = json.getString("id");
        value = json.getString("count");
        weight = json.getString("weight");
    }

    public static List<Tag> constructTags(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONArray();
            int size = list.length();
            List<Tag> tags = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                tags.add(new Tag(list.getJSONObject(i)));
            }
            return tags;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        } catch (WeiboException te) {
            throw te;
        }
    }

    public static TagWapper constructTagWapper(Response res) {
        try {
            JSONArray tags = res.asJSONArray();
            List<Tag> tagList = new ArrayList<>();
            for (int i = 0; i < tags.getJSONObject(0).getJSONArray("tags").length(); i++) {
                tagList.add(new Tag(tags.getJSONObject(0).getJSONArray("tags").getJSONObject(i)));
            }
            String id = tags.getJSONObject(0).getString("id");
            return new TagWapper(tagList, id);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (WeiboException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<FavoritesTag> constructTag(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONObject().getJSONArray("tags");
            int size = list.length();
            List<FavoritesTag> tags = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                tags.add(new FavoritesTag(list.getJSONObject(i)));
            }
            return tags;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        } catch (WeiboException te) {
            throw te;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tag other = (Tag)obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", value=" + value + ", weight=" + weight + "]";
    }

}
