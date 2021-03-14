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
package pjq.weibo.openapi.support;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * 描述API接口参数的使用范围(仅起描述作用)
 * 
 * @author pengjianqiang
 * @date 2021年3月14日
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface WeiboApiParamScope {
    public static final String DEFAULT_SUFFIX = "可用";
    public static final String ALL = "全部接口" + DEFAULT_SUFFIX;
    public static final String SOURCE = "商业接口及部分其它接口" + DEFAULT_SUFFIX;
    public static final String OAUTH_BUILD_AUTHORIZE_URL = "OAuth-构建授权链接接口" + DEFAULT_SUFFIX;
    public static final String PAGER = "分页类接口" + DEFAULT_SUFFIX;
    public static final String STATUSES_TIMELINE = "statuses-查询用户微博类接口" + DEFAULT_SUFFIX;
    public static final String STATUSES_TIMELINE_OTHER = "statuses-查询转发微博/@用户微博类接口" + DEFAULT_SUFFIX;
    public static final String STATUSES_QUERYID = "statuses-查询ID/MID类接口" + DEFAULT_SUFFIX;
    public static final String STATUSES_PUBLISH = "statuses-发布微博类接口" + DEFAULT_SUFFIX;
    public static final String COMMENTS_QUERY = "comments-查询评论类接口" + DEFAULT_SUFFIX;
    public static final String COMMENTS_WRITE = "comments-写评论类接口" + DEFAULT_SUFFIX;
    public static final String COMMON_COMMON = "common-公共类接口" + DEFAULT_SUFFIX;
    public static final String COMMON_LOCATION = "common-查国家/省份/城市类接口" + DEFAULT_SUFFIX;
    public static final String EMOTIONS = "emotions-查表情接口" + DEFAULT_SUFFIX;
    public static final String FRIENDSHIPS_QUEYR_USER = "friendships-查用户类接口" + DEFAULT_SUFFIX;
    public static final String USERS_QUERY = "users-查用户类接口" + DEFAULT_SUFFIX;

    /**
     * 作用范围
     * 
     * @return
     */
    String value() default "";
}