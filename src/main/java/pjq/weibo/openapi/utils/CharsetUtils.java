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
package pjq.weibo.openapi.utils;

import java.nio.charset.Charset;

/**
 * <p>
 * -字符集工具类
 * <p>
 * Create at 2019年1月15日
 * 
 * @author pengjianqiang
 */
public final class CharsetUtils {
    private CharsetUtils() {}

    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";
    public static final String GB18030 = "GB18030";
    public static final String ISO_8859_1 = "ISO-8859-1";

    public static Charset utf8() {
        return forName(UTF_8);
    }

    public static Charset gbk() {
        return forName(GBK);
    }

    public static Charset gb2312() {
        return forName(GB2312);
    }

    public static Charset gb18030() {
        return forName(GB18030);
    }

    public static Charset iso8859_1() {
        return forName(ISO_8859_1);
    }

    public static Charset forName(String charsetName) {
        return Charset.forName(charsetName);
    }
}