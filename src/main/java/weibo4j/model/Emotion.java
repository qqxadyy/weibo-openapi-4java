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

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsType;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * @author SinaWeibo
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class Emotion extends WeiboResponse {
    private String phrase; // 表情使用的替代文字
    private @JSONField(serialize = false) EmotionsType type; // 表情类型
    private String url; // 表情图片存放的位置
    private @JSONField(serialize = false) Boolean hot; // 是否为热门表情
    private @JSONField(serialize = false) Boolean common; // 是否是常用表情
    private @JSONField(serialize = false) String value; // 同phrase
    private String category; // 表情分类
    private @JSONField(serialize = false) String picid;
    private @JSONField(serialize = false) String icon;// 路径跟url的有点差别，但表情图实际的内容没什么差别

    public Emotion(Response res) {
        super(res);
    }

    public Emotion(JSONObject json) {
        super(json);
    }
}
