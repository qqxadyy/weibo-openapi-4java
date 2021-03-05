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

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 返回对象的分页包装类(一般不要实例化,不用abstract修饰是有其它作用)
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class WeiboResponsePager extends WeiboResponse {
    private @WeiboJsonName("previous_cursor") BigInteger previousCursor; // 有的接口没有previous_cursor_str,直接用previous_cursor,下同
    private @WeiboJsonName("next_cursor") BigInteger nextCursor;
    private @WeiboJsonName("total_number") Long totalNumber;
    private @WeiboJsonName("display_total_number") Long displayTotalNumber;
    private Boolean hasvisible;
    private @WeiboJsonName(value = "since_id", isNewAndNoDesc = true) String sinceId;
    private @WeiboJsonName(value = "max_id", isNewAndNoDesc = true) String maxId;
    private @WeiboJsonName(value = "tips_show", isNewAndNoDesc = true) Integer tipsShow;
    private @WeiboJsonName(value = "miss_ids", isNewAndNoDesc = true) List<JSONObject> missIds;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer interval;
    private @WeiboJsonName(value = "request_id", isNewAndNoDesc = true) String requestId;
    private @WeiboJsonName(value = "extend_card", isNewAndNoDesc = true) Integer extendCard;

    public WeiboResponsePager(JSONObject json) {
        super(json);
    }

    public WeiboResponsePager(Response res) {
        super(res);
    }
}