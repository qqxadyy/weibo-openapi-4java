package pjq.weibo.openapi.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * -Stream工具类
 * <p>
 * Create at 2019年1月15日
 * 
 * @author pengjianqiang
 */
public final class StreamUtils {
    private StreamUtils() {}

    public static String joinString(Stream<String> stream) {
        return joinString(stream, System.lineSeparator());
    }

    public static String joinString(Stream<String> stream, String delimiter) {
        return stream.collect(Collectors.joining(delimiter));
    }
}