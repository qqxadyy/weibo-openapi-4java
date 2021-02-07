package pjq.weibo.openapi.constant;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import pjq.weibo.openapi.support.EnhanceEnum;

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

    DateTimePattern(String value) {
        this.value = value;
    }

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