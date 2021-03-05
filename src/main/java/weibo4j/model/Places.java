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

/**
 * 地点信息
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class Places extends WeiboResponse {

    private static final long serialVersionUID = -1423136187811594673L;

    private String poiid;// 地点ID
    private String title;// 地点名称
    private String address;// 地址及
    private double lon;// 经度
    private double lat;// 纬度
    private String category;
    private String city;
    private String province;
    private String country;
    private String url;
    private String phone;
    private String postcode;
    private long weiboId;
    private String categorys;
    private String categoryName;
    private String icon;
    private long checkinNum;
    private long checkinUserNum;
    private Date checkinTime;
    private long tipNum;
    private long photoNum;
    private long todoNum;
    private long distance;

    private static long totalNumber;

    public Places(Response res) throws WeiboException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            poiid = json.getString("poiid");
            title = json.getString("title");
            address = json.getString("address");
            lon = json.getDouble("lon");
            lat = json.getDouble("lat");
            category = json.getString("category");
            city = json.getString("city");
            province = json.getString("privince");
            country = json.getString("country");
            url = json.getString("url");
            phone = json.getString("url");
            postcode = json.getString("postcode");
            weiboId = json.getLong("weibo_id");
            categorys = json.getString("categorys");
            categoryName = json.getString("category_name");
            icon = json.getString("icon");
            checkinUserNum = json.getLong("checkin_user_num");
            checkinTime = parseDate(json.getString("checkin_time"), "yyyy-MM-dd hh:mm:ss");
            checkinNum = json.getLong("checkin_num");
            tipNum = json.getLong("tip_num");
            photoNum = json.getLong("photo_num");
            todoNum = json.getLong("todo_num");
            distance = json.getLong("distance");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public Places(JSONObject json) throws WeiboException {
        try {
            poiid = json.getString("poiid");
            title = json.getString("title");
            address = json.getString("address");
            lon = json.getDouble("lon");
            lat = json.getDouble("lat");
            category = json.getString("category");
            city = json.getString("city");
            province = json.getString("privince");
            country = json.getString("country");
            url = json.getString("url");
            phone = json.getString("url");
            postcode = json.getString("postcode");
            weiboId = json.getLong("weibo_id");
            categorys = json.getString("categorys");
            categoryName = json.getString("category_name");
            icon = json.getString("icon");
            checkinUserNum = json.getLong("checkin_user_num");
            checkinTime = parseDate(json.getString("checkin_time"), "yyyy-MM-dd hh:mm:ss");
            checkinNum = json.getLong("checkin_num");
            tipNum = json.getLong("tip_num");
            photoNum = json.getLong("photo_num");
            todoNum = json.getLong("todo_num");
            distance = json.getLong("distance");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public static List<Places> constructPlace(Response res) throws WeiboException {
        try {
            JSONObject jsonObj = res.asJSONObject();
            totalNumber = jsonObj.getLong("total_number");
            JSONArray json = jsonObj.getJSONArray("pois");
            int size = json.length();
            List<Places> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(new Places(json.getJSONObject(i)));
            }
            return list;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }

    }

    @Override
    public String toString() {
        return "Place [" + "poiid=" + poiid + ",title=" + title + ",address=" + address + ",lon=" + lon + ",lat=" + lat
            + ",category=" + category + ",city=" + city + ",province=" + province + ",country=" + country + ",url="
            + url + ",phone=" + phone + ",postcode=" + postcode + ",weiboId=" + weiboId + ",categorys=" + categorys
            + ",categoryName=" + categoryName + ",icon=" + icon + ",checkinUserNum=" + checkinUserNum + ",checkinTime="
            + checkinTime + ",tip_num=" + tipNum + ",photo_num=" + photoNum + ",todo_num=" + todoNum + ",distance="
            + distance + ",total_number=" + totalNumber + "]";
    }

}
