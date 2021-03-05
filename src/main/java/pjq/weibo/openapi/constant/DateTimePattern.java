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

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import pjq.weibo.openapi.support.EnhanceEnum;

@AllArgsConstructor
public enum DateTimePattern implements EnhanceEnum {
    /**
     * - 默认日期格式, <code>yyyy-MM-dd</code>
     */
    PATTERN_DEFAULT("yyyy-MM-dd"),

    /**
     * -无间隔符日期格式, <code>yyyyMMdd</code>
     */
    PATTERN_DATE_COMPACT("yyyyMMdd"),

    /**
     * -日期时间格式, <code>yyyy-MM-dd HH:mm:ss</code>, 24小时制
     */
    PATTERN_DATETIME("yyyy-MM-dd HH:mm:ss"),

    /**
     * -无间隔符的日期时间格式, <code>yyyyMMddHHmmss</code>, 24小时制
     */
    PATTERN_DATETIME_COMPACT("yyyyMMddHHmmss"),

    /**
     * -年月格式, <code>yyyyMM</code>
     */
    PATTERN_YEARMONTH("yyyyMM"),

    /**
     * -时间格式, <code>HH:mm:ss</code>
     */
    PATTERN_TIME("HH:mm:ss"),

    /**
     * -无间隔时间格式, <code>HHmmss</code>
     */
    PATTERN_TIME_COMPACT("HHmmss"),

    /**
     * -路径格式, <code>yyyy\MM\dd\</code>
     */
    PATTERN_PATH("yyyy" + File.separator + "MM" + File.separator + "dd" + File.separator);

    private String value;

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(value);
    }

    public static List<DateTimePattern> usuallyUseDateTimePattern() {
        return Arrays.asList(PATTERN_DEFAULT, PATTERN_DATE_COMPACT, PATTERN_DATETIME, PATTERN_DATETIME_COMPACT);
    }

    public static boolean isDatePattern(DateTimePattern pattern) {
        return isDatePattern(pattern.value());
    }

    public static boolean isDateTimePattern(DateTimePattern pattern) {
        return isDateTimePattern(pattern.value());
    }

    public static boolean isDatePattern(String pattern) {
        return pattern.contains("yyyy") && pattern.contains("MM") && pattern.contains("dd") && !pattern.contains("HH")
            && !pattern.contains("mm") && !pattern.contains("ss");
    }

    public static boolean isDateTimePattern(String pattern) {
        return !isDatePattern(pattern);
    }
}