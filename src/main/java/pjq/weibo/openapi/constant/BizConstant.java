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
package pjq.weibo.openapi.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.EnhanceEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BizConstant {
    // ----------------通用枚举----------------
    /**
     * 是或否类型--<br>
     * NO--N：否<br>
     * YES--Y：是
     */
    @AllArgsConstructor
    public static enum YesOrNo implements EnhanceEnum {
        NO("N"), YES("Y");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * 状态类型--<br>
     * INVALID--0：无效<br>
     * VALID--1：有效
     */
    public static enum StatusType implements EnhanceEnum {
        INVALID, VALID
    }

    /**
     * ture或false类型
     */
    @AllArgsConstructor
    public static enum TrueOrFalse implements EnhanceEnum {
        FALSE("false"), TRUE("true");

        @SuppressWarnings("unused")
        private String value;
    }
}