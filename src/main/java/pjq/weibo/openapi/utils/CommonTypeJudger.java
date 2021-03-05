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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * -常见类型判断类
 * <p>
 * Create at 2018年11月19日
 * 
 * @author pengjianqiang
 */
public final class CommonTypeJudger {
    private CommonTypeJudger() {}

    public static boolean isVoidType(Class<?> classType) {
        return void.class.equals(classType);
    }

    public static boolean isByteType(Class<?> classType) {
        return byte.class.equals(classType) || Byte.class.equals(classType);
    }

    public static boolean isShortType(Class<?> classType) {
        return short.class.equals(classType) || Short.class.equals(classType);
    }

    public static boolean isIntType(Class<?> classType) {
        return int.class.equals(classType) || Integer.class.equals(classType);
    }

    public static boolean isLongType(Class<?> classType) {
        return long.class.equals(classType) || Long.class.equals(classType);
    }

    public static boolean isFloatType(Class<?> classType) {
        return float.class.equals(classType) || Float.class.equals(classType);
    }

    public static boolean isDoubleType(Class<?> classType) {
        return double.class.equals(classType) || Double.class.equals(classType);
    }

    public static boolean isNumberType(Class<?> classType) {
        return isByteType(classType) || isShortType(classType) || isIntType(classType) || isLongType(classType)
            || isFloatType(classType) || isDoubleType(classType);
    }

    public static boolean isBigDecimalType(Class<?> classType) {
        return BigDecimal.class.equals(classType);
    }

    public static boolean isBigIntegerType(Class<?> classType) {
        return BigInteger.class.equals(classType);
    }

    public static boolean isStringType(Class<?> classType) {
        return String.class.equals(classType);
    }

    public static boolean isCharType(Class<?> classType) {
        return char.class.equals(classType) || Character.class.equals(classType);
    }

    public static boolean isBooleanType(Class<?> classType) {
        return boolean.class.equals(classType) || Boolean.class.equals(classType);
    }

    public static boolean isDateType(Class<?> classType) {
        return Date.class.equals(classType);
    }

    public static boolean isLocalDateTimeType(Class<?> classType) {
        return LocalDateTime.class.equals(classType);
    }

    public static boolean isObjectType(Class<?> classType) {
        return Object.class.equals(classType);
    }

    public static boolean isBaseType(Class<?> classType) {
        return isNumberType(classType) || isBigDecimalType(classType) || isBigIntegerType(classType)
            || isStringType(classType) || isCharType(classType) || isBooleanType(classType) || isDateType(classType)
            || isObjectType(classType);
    }

    public static boolean isCollectionType(Class<?> classType) throws Exception {
        return Collection.class.isAssignableFrom(classType) || isMapType(classType) || isArrayType(classType);
    }

    public static boolean isListType(Class<?> classType) throws Exception {
        return List.class.isAssignableFrom(classType);
    }

    public static boolean isLinkedListType(Class<?> classType) throws Exception {
        return LinkedList.class.equals(classType);
    }

    public static boolean isSetType(Class<?> classType) throws Exception {
        return Set.class.isAssignableFrom(classType);
    }

    public static boolean isLinkedHashSetType(Class<?> classType) throws Exception {
        return LinkedHashSet.class.equals(classType);
    }

    public static boolean isMapType(Class<?> classType) throws Exception {
        return Map.class.isAssignableFrom(classType);
    }

    public static boolean isLinkedHashMapType(Class<?> classType) throws Exception {
        return LinkedHashMap.class.equals(classType);
    }

    public static boolean isArrayType(Class<?> classType) throws Exception {
        return classType.isArray();
    }
}