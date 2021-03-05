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
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * A data class representing Treand.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Weibo4J 1.2.1
 */
@Getter
@Setter
public class Trend implements java.io.Serializable {
    private String name;
    private String query = null;
    private long amount;
    private long delta;
    private static final long serialVersionUID = 1925956704460743946L;

    public Trend(JSONObject json) throws JSONException {
        this.name = json.getString("name");
        if (!json.isNull("query")) {
            this.query = json.getString("query");
        }
        this.amount = json.getLong("amount");
        this.delta = json.getLong("delta");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trend)) {
            return false;
        }

        Trend trend = (Trend)o;

        if (!name.equals(trend.name)) {
            return false;
        }
        if (query != null ? !query.equals(trend.query) : trend.query != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (query != null ? query.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trend [name=" + name + ", query=" + query + ", amount=" + amount + ", delta=" + delta + "]";
    }

}
