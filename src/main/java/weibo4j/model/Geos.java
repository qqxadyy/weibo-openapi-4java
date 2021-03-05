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
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 地理信息
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Geos extends WeiboResponse {
    private String longitude; // 经度坐标
    private String latitude; // 维度坐标
    private String city; // 所在城市的城市代码
    private String province; // 所在省份的省份代码
    private @WeiboJsonName("city_name") String cityName; // 所在城市的城市名称
    private @WeiboJsonName("province_name") String provinceName;// 所在省份的省份名称
    private String address; // 所在的实际地址，可以为空
    private String pinyin; // 地址的汉语拼音，不是所有情况都会返回该字段
    private String more; // 更多信息，不是所有情况都会返回该字段

    public Geos(Response res) {
        super(res);
    }

    public Geos(JSONObject json) {
        super(json);
    }
}