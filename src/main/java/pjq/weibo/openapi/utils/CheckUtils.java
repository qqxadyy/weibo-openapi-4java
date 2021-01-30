package pjq.weibo.openapi.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * -字符串、集合的判断工具类
 * <p>
 * Create at 2019年1月15日
 * 
 * @author pengjianqiang
 */
public final class CheckUtils {
    private CheckUtils() {}

    public static boolean isEmpty(String s) {
        return StringUtils.isBlank(s) || "null".equalsIgnoreCase(s.trim()) || "undefined".equalsIgnoreCase(s.trim());
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean areEmpty(String... ss) {
        if (ArrayUtils.isEmpty(ss)) {
            return true;
        } else {
            for (String s : ss) {
                if (!isEmpty(s)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean areNotEmpty(String... ss) {
        if (ArrayUtils.isEmpty(ss)) {
            return false;
        } else {
            for (String s : ss) {
                if (isEmpty(s)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Object[] array) {
        if (array instanceof String[]) {
            return areEmpty((String[])array);
        } else {
            return ArrayUtils.isEmpty(array);
        }
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * -判断字符串不能为空
     * 
     * @param s
     *            要判断的字符串
     * @param msg
     */
    public static void checkNotEmpty(String s, String msg) {
        if (isEmpty(s)) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotNull(Object obj, String msg) {
        if (isNull(obj)) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotEmpty(Collection<?> collection, String msg) {
        if (isEmpty(collection)) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotEmpty(Map<?, ?> map, String msg) {
        if (isEmpty(map)) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotEmpty(Object[] array, String msg) {
        if (isEmpty(array)) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotFalse(boolean bval, String msg) {
        if (!bval) {
            throw new RuntimeException(msg);
        }
    }

    public static void checkNotTrue(boolean bval, String msg) {
        if (bval) {
            throw new RuntimeException(msg);
        }
    }

    public static String getValue(String defaultValue, String... array) {
        if (isNotEmpty(array) && isNotEmpty(array[0])) {
            return array[0];
        } else {
            return defaultValue;
        }
    }

    public static String getValue(String defaultValue, String str) {
        if (isNotEmpty(str)) {
            return str;
        } else {
            return defaultValue;
        }
    }
}