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
 * 地理信息
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class Poisition extends WeiboResponse {

    private static final long serialVersionUID = -6156515630521071392L;

    private String srcid;
    private String longitude;
    private String latitude;
    private String name;
    private String cityName;
    private String address;
    private String telephone;
    private String category;
    private String description;
    private String intro;
    private String tags;
    private String url;
    private String traffic;
    private String deal;
    private String pid;

    private static long totalNumber;
    private static String coordinates;

    public Poisition(Response res) throws WeiboException {
        super(res);
        JSONObject json = null;
        try {
            json = res.asJSONObject();
            srcid = json.getString("srcid");
            longitude = json.getString("longitude");
            latitude = json.getString("latitude");
            name = json.getString("name");
            cityName = json.getString("city_name");
            address = json.getString("address");
            telephone = json.getString("telephone");
            category = json.getString("category");
            description = json.getString("description");
            intro = json.getString("intro");
            tags = json.getString("tags");
            url = json.getString("url");
            traffic = json.getString("traffic");
            deal = json.getString("deal");
            pid = json.getString("pid");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public Poisition(JSONObject json) throws WeiboException {
        try {
            srcid = json.getString("srcid");
            longitude = json.getString("longitude");
            latitude = json.getString("latitude");
            name = json.getString("name");
            cityName = json.getString("city_name");
            address = json.getString("address");
            telephone = json.getString("telephone");
            category = json.getString("category");
            description = json.getString("description");
            intro = json.getString("intro");
            tags = json.getString("tags");
            url = json.getString("url");
            traffic = json.getString("traffic");
            deal = json.getString("deal");
            pid = json.getString("pid");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public static List<Poisition> constructPois(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONObject().getJSONArray("pois");
            int size = list.length();
            List<Poisition> pois = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                pois.add(new Poisition(list.getJSONObject(i)));
            }
            totalNumber = res.asJSONObject().getLong("total_number");
            coordinates = res.asJSONObject().getString("coordinates");
            return pois;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public static long getTotalNumber() {
        return totalNumber;
    }

    public static void setTotalNumber(long totalNumber) {
        Poisition.totalNumber = totalNumber;
    }

    public static String getCoordinates() {
        return coordinates;
    }

    public static void setCoordinates(String coordinates) {
        Poisition.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Pois [" + "coordinates = " + coordinates + ", srcid = " + srcid + ", longitude = " + longitude
            + ", latitude = " + latitude + ", name = " + name + ", city_name = " + cityName + ", address = " + address
            + ", telephone = " + telephone + ", category = " + category + ", description = " + description
            + ", intro = " + intro + ", tags = " + tags + ", url = " + url + ", traffic = " + traffic + ", deal = "
            + deal + ", pid = " + pid + ", totalNumber = " + totalNumber + "]";
    }

}
