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