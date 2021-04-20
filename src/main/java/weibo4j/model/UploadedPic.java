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

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.StreamUtils;
import pjq.commons.utils.collection.CollectionUtils;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 上传图片后的图片信息
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UploadedPic extends WeiboResponse {
    private @WeiboJsonName("pic_id") String picid; // 图片ID
    private @WeiboJsonName("thumbnail_pic") String thumbnailPic; // 微博内容中的图片的缩略地址
    private @WeiboJsonName("bmiddle_pic") String bmiddlePic; // 中型图片
    private @WeiboJsonName("original_pic") String originalPic; // 原始图片

    public UploadedPic(Response res) {
        super(res);
    }

    public UploadedPic(JSONObject json) {
        super(json);
    }

    public static String toPicIds(List<UploadedPic> list) {
        if (CheckUtils.isEmpty(list)) {
            return "";
        }
        return StreamUtils
            .joinString(CollectionUtils.transformToList(list, uploadedPic -> uploadedPic.getPicid()).stream(), ",");
    }
}