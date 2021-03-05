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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pjq.weibo.openapi.constant.WeiboConfigs;

public interface WeiboApiAnnos {
    /**
     * 定义可替换微博API接口路径的配置名
     * 
     * @author pengjianqiang
     * @date 2021年1月20日
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface WeiboPropName {
        /**
         * 配置项名，为空时使用变量的值代替<br>
         * (实际配置时要配置成{@link WeiboConfigs.CONFIG_API_PREFIX}的值+value值，例如weibo.api.propName)
         * 
         * @return
         */
        String value() default "";
    }

    /**
     * 定义微博API的类型、URL中的多级前缀及后缀
     * 
     * @author pengjianqiang
     * @date 2021年1月20日
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface WeiboApi {
        /**
         * 接口URL的前段，默认为BASE_URL
         * 
         * @return
         */
        String baseUrl() default "";

        /**
         * URL中的多级前缀，例如{"search/","statuses/"}表示有三级前缀
         * 
         * @return
         */
        String[] prefixes() default {};

        /**
         * URL接口的后缀，例如.json
         * 
         * @return
         */
        String suffix() default "";
    }
}