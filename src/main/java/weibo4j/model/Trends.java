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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * A data class representing Treands.
 * 
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since weibo4j-V2 1.0.1
 */
public class Trends extends WeiboResponse implements Comparable<Trends> {
    private @Getter Date asOf; // asof
    private @Getter Date trendAt; // 话题时间
    private @Getter Trend[] trends; // 话题对象
    private static final long serialVersionUID = -7151479143843312309L;

    @Override
    public int compareTo(Trends that) {
        return this.trendAt.compareTo(that.trendAt);
    }

    /* package */ Trends(Response res, Date asOf, Date trendAt, Trend[] trends) throws WeiboException {
        super(res);
        this.asOf = asOf;
        this.trendAt = trendAt;
        this.trends = trends;
    }

    /* package */
    @SuppressWarnings("unchecked")
    public static List<Trends> constructTrendsList(Response res) throws WeiboException {
        JSONObject json = res.asJSONObject();
        List<Trends> trends;
        try {
            Date asOf = parseDate(json.getString("as_of"));
            JSONObject trendsJson = json.getJSONObject("trends");
            trends = new ArrayList<>(trendsJson.length());
            Iterator<String> ite = trendsJson.keys();
            while (ite.hasNext()) {
                String key = ite.next();
                JSONArray array = trendsJson.getJSONArray(key);
                Trend[] trendsArray = jsonArrayToTrendArray(array);
                if (key.length() == 19) {
                    // current trends
                    trends.add(new Trends(res, asOf, parseDate(key, "yyyy-MM-dd HH:mm:ss"), trendsArray));
                } else if (key.length() == 16) {
                    // daily trends
                    trends.add(new Trends(res, asOf, parseDate(key, "yyyy-MM-dd HH:mm"), trendsArray));
                } else if (key.length() == 10) {
                    // weekly trends
                    trends.add(new Trends(res, asOf, parseDate(key, "yyyy-MM-dd"), trendsArray));
                }
            }
            Collections.sort(trends);
            return trends;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone.getMessage() + ":" + res.asString(), jsone);
        }
    }

    /* package */
    static Trends constructTrends(Response res) throws WeiboException {
        JSONObject json = res.asJSONObject();
        try {
            Date asOf = parseDate(json.getString("as_of"));
            JSONArray array = json.getJSONArray("trends");
            Trend[] trendsArray = jsonArrayToTrendArray(array);
            return new Trends(res, asOf, asOf, trendsArray);
        } catch (JSONException jsone) {
            throw new WeiboException(jsone.getMessage() + ":" + res.asString(), jsone);
        }
    }

    private static Date parseDate(String asOfStr) throws WeiboException {
        Date parsed;
        if (asOfStr.length() == 10) {
            parsed = new Date(Long.parseLong(asOfStr) * 1000);
        } else {
            parsed = WeiboResponse.parseDate(asOfStr, "EEE, d MMM yyyy HH:mm:ss z");
        }
        return parsed;
    }

    private static Trend[] jsonArrayToTrendArray(JSONArray array) throws JSONException {
        Trend[] trends = new Trend[array.length()];
        for (int i = 0; i < array.length(); i++) {
            JSONObject trend = array.getJSONObject(i);
            trends[i] = new Trend(trend);
        }
        return trends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trends)) {
            return false;
        }

        Trends trends1 = (Trends)o;

        if (asOf != null ? !asOf.equals(trends1.asOf) : trends1.asOf != null) {
            return false;
        }
        if (trendAt != null ? !trendAt.equals(trends1.trendAt) : trends1.trendAt != null) {
            return false;
        }
        if (!Arrays.equals(trends, trends1.trends)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = asOf != null ? asOf.hashCode() : 0;
        result = 31 * result + (trendAt != null ? trendAt.hashCode() : 0);
        result = 31 * result + (trends != null ? Arrays.hashCode(trends) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trends{" + "asOf=" + asOf + ", trendAt=" + trendAt + ", trends="
            + (trends == null ? null : Arrays.asList(trends)) + '}';
    }
}
