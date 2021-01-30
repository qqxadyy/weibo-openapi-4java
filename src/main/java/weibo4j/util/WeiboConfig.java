package weibo4j.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;

/**
 * 微博官网SDK自带，官网SDK代码可能还会用到<br/>
 * 但是使用新封装的SDK包则不要直接使用，用{@link WeiboConfigs}代替
 * 
 * @author pengjianqiang
 * @date 2021年1月22日
 */
public class WeiboConfig {
    public WeiboConfig() {}

    private static Properties props = new Properties();
    static {
        try {
            props.load(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("weibo-openapi-config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = props.getProperty(key);
        return CheckUtils.isEmpty(value) ? "" : value.trim();
    }

    public static void updateProperties(String key, String value) {
        props.setProperty(key, value);
    }

    public static String getOpenAPIBaseURL() {
        return WeiboConfigs.getOpenAPIBaseURL();
    }
}