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
    private CommonTypeJudger() {
    }

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