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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单的经纬度对象，主要用作参数
 * 
 * @author pengjianqiang
 * @date 2021年3月12日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGeo {
    private String longitude; // 经度坐标
    private String latitude; // 维度坐标

    public void check() {
        SimpleGeo.check(getLongitude(), getLatitude());
    }

    public static void check(String longitude, String latitude) {
        try {
            Double longDouble = Double.parseDouble(longitude);
            Double latDouble = Double.parseDouble(latitude);
            if (latDouble < -90D || latDouble > 90D || longDouble < -180D || longDouble > 180D) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new WeiboException("坐标[" + longitude + "," + latitude + "]不合法");
        }
    }
}