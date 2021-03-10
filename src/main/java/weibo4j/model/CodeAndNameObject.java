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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.commons.utils.CheckUtils;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

/**
 * code_to_location的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class CodeAndNameObject extends WeiboResponse implements Serializable {
    private String code;
    private String name;

    /**
     * 和父类的builList不通用
     * 
     * @param jsonArray
     * @return
     */
    public static List<CodeAndNameObject> builList(JSONArray jsonArray) {
        List<CodeAndNameObject> list = new ArrayList<>();
        if (CheckUtils.isNull(jsonArray)) {
            return list;
        }

        try {
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String code = (String)jo.keys().next();
                String name = jo.getString(code);
                list.add(new CodeAndNameObject(code, name));
            }
            return list;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<CodeAndNameObject> builListForTimeZone(JSONObject jsonObject) {
        List<CodeAndNameObject> list = new ArrayList<>();
        if (CheckUtils.isNull(jsonObject)) {
            return list;
        }

        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String code = keys.next();
                String name = jsonObject.getString(code);
                list.add(new CodeAndNameObject(code, name));
            }
            return list;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }
}