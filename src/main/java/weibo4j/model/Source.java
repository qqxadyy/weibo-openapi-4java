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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Source implements java.io.Serializable {

    private static final long serialVersionUID = -8972443458374235866L;
    private String url; // 来源连接
    private String relationShip;
    private String name; // 来源文案名称

    public Source(String str) {
        super();
        String[] source = str.split("\"", 5);
        url = source[1];
        relationShip = source[3];
        name = source[4].replace(">", "").replace("</a", "");
    }

    @Override
    public String toString() {
        return "Source [url=" + url + ", relationShip=" + relationShip + ", name=" + name + "]";
    }

}
