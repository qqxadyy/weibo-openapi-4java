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
 * The MIT License
 * Copyright © 2021 pengjianqiang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pjq.weibo.openapi.apis.base;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pjq.commons.utils.CheckUtils;
import pjq.weibo.openapi.support.WeiboApiParamScope;
import weibo4j.Weibo;
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
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
    private String sinceId;

    /**
     * 若指定此参数，则返回ID小于或等于max_id的记录，默认为0
     */
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
    private String maxId;

    /**
     * 单页返回的记录条数，默认为50
     */
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
    private Integer count;

    /**
     * 返回结果的页码，默认为1
     */
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
    private Integer page;

    /**
     * 若指定此参数，则返回ID比since_id大的记录（即比since_id时间晚的记录），默认为0
     * 
     * @param sinceId
     * @return
     */
    @SuppressWarnings("unchecked")
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
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
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
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
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
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
    @WeiboApiParamScope(WeiboApiParamScope.PAGER)
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