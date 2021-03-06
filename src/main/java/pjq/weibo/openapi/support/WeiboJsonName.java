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
 * 定义微博API返回的json字段名<br>
 * 1.在类定义中使用注解即可，表示整个类的private属性都从json串中转换获取，且默认转换为String类型的值(只处理private属性)<br>
 * 2.对于需要特殊定义json字段名，在需要转换的属性中再单独使用属性
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface WeiboJsonName {
    /**
     * json字段名，为空时用field的名
     * 
     * @return
     */
    String value() default "";

    /**
     * 默认true，如果有不需要从json中转换获取的属性值，则设为false
     * 
     * @return
     */
    boolean fromJson() default true;

    /**
     * 表示是否新版接口新增的返回(仅作标识用)
     * 
     * @return
     */
    boolean isNew() default false;

    /**
     * 表示是否新版接口新增的返回，且官网上没有相关描述(仅作标识用)
     * 
     * @return
     */
    boolean isNewAndNoDesc() default false;

    /**
     * 表示是否新版接口已废弃的返回(仅作标识用)
     * 
     * @return
     */
    boolean isDeleted() default false;
}