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
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

@Getter
@Setter
public class Favorites extends WeiboResponse {

    private static final long serialVersionUID = 3355536191107298448L;
    private Date favoritedTime; // 添加收藏的时间
    private Status status; // 收藏的status
    private List<FavoritesTag> tags; // 收藏的tags
    private static int totalNumber;

    public Favorites(Response res) throws WeiboException {
        super(res);
        JSONObject json = null;
        try {
            json = res.asJSONObject();
            favoritedTime = parseDate(json.getString("favorited_time"), "EEE MMM dd HH:mm:ss z yyyy");
            if (!json.isNull("status")) {
                status = new Status(json.getJSONObject("status"));
            }
            if (!json.isNull("tags")) {
                JSONArray list = json.getJSONArray("tags");
                int size = list.length();
                tags = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    tags.add(new FavoritesTag(list.getJSONObject(i)));
                }
            }
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    Favorites(JSONObject json) throws WeiboException, JSONException {
        favoritedTime = parseDate(json.getString("favorited_time"), "EEE MMM dd HH:mm:ss z yyyy");
        if (!json.isNull("status")) {
            status = new Status(json.getJSONObject("status"));
        }
        if (!json.isNull("tags")) {
            JSONArray list = json.getJSONArray("tags");
            int size = list.length();
            tags = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                tags.add(new FavoritesTag(list.getJSONObject(i)));
            }
        }

    }

    public static List<Favorites> constructFavorites(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONObject().getJSONArray("favorites");
            int size = list.length();
            List<Favorites> favorites = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                favorites.add(new Favorites(list.getJSONObject(i)));
            }
            totalNumber = res.asJSONObject().getInt("total_number");
            return favorites;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((favoritedTime == null) ? 0 : favoritedTime.hashCode());
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
        Favorites other = (Favorites)obj;
        if (favoritedTime == null) {
            if (other.favoritedTime != null) {
                return false;
            }
        } else if (!favoritedTime.equals(other.favoritedTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Favorites [" + "favorited_time=" + favoritedTime + ", status=" + status.toString() + ", FavoritesTag="
            + ((tags == null) ? "null" : tags.toString()) + ", total_number = " + totalNumber + "]";
    }

}
