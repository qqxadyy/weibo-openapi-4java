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
package weibo4j;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pjq.commons.utils.CheckUtils;
import weibo4j.model.PostParameter;

/**
 * 带分页参数的接口类
 * 
 * @author pengjianqiang
 * @date 2021年1月27日
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Accessors(fluent = true)
@SuppressWarnings("serial")
public abstract class WeiboParamPager<T> extends Weibo<T> {
    /**
     * 若指定此参数，则返回ID比since_id大的记录（即比since_id时间晚的记录），默认为0
     */
    private String sinceId;

    /**
     * 若指定此参数，则返回ID小于或等于max_id的记录，默认为0
     */
    private String maxId;

    /**
     * 单页返回的记录条数，默认为50
     */
    private Integer count;

    /**
     * 返回结果的页码，默认为1
     */
    private Integer page;

    /**
     * 若指定此参数，则返回ID比since_id大的记录（即比since_id时间晚的记录），默认为0
     * 
     * @param sinceId
     * @return
     */
    @SuppressWarnings("unchecked")
    public T sinceId(String sinceId) {
        this.sinceId = sinceId;
        return (T)this;
    }

    /**
     * 若指定此参数，则返回ID小于或等于max_id的记录，默认为0
     * 
     * @param maxId
     * @return
     */
    @SuppressWarnings("unchecked")
    public T maxId(String maxId) {
        this.maxId = maxId;
        return (T)this;
    }

    /**
     * 单页返回的记录条数，默认为50
     * 
     * @param count
     * @return
     */
    @SuppressWarnings("unchecked")
    public T count(Integer count) {
        this.count = count;
        return (T)this;
    }

    /**
     * 返回结果的页码，默认为1
     * 
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
    public T page(Integer page) {
        this.page = page;
        return (T)this;
    }

    /**
     * 返回分页参数
     * 
     * @return
     */
    protected List<PostParameter> pageParam() {
        List<PostParameter> paramList = new ArrayList<>();
        if (CheckUtils.isNotEmpty(sinceId)) {
            paramList.add(new PostParameter("since_id", sinceId));
        }
        if (CheckUtils.isNotEmpty(maxId)) {
            paramList.add(new PostParameter("max_id", maxId));
        }
        if (CheckUtils.isNotNull(count) && count >= 0) {
            paramList.add(new PostParameter("count", count));
        }
        if (CheckUtils.isNotNull(page) && page >= 1) {
            paramList.add(new PostParameter("page", page));
        }
        return paramList;
    }
}