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
 * src/main/java/weibo4j下的文件是从weibo4j-oauth2-beta3.1.1.zip中复制出来的
 * 本项目对这个目录下的部分源码做了重新改造
 * 但是许可信息和"https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1"或源码中已存在的保持一致
 */
package weibo4j.util;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;

import pjq.commons.utils.CharsetUtils;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.codec.ThreeDesUtils;
import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.model.WeiboException;

/**
 * 微博官网SDK自带，官网SDK代码可能还会用到<br/>
 * 但是使用新封装的SDK包则不要直接使用，用{@link WeiboConfigs}代替
 * 
 * @author pengjianqiang
 * @date 2021年1月22日
 */
public class WeiboConfig {
    public WeiboConfig() {}

    private static final String PROP_NAME = "weibo-openapi-config.properties";
    private static final String ENCRYPT_PREFIX = "{Encrypted}";
    private static final String THREE_DES_KEY = "qeqwuepqjd&&(@#*@(&#";
    private static Properties props = new Properties();

    static {
        try {
            props.load(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_NAME), CharsetUtils.utf8()));

            // 加密敏感配置
            String[] clientIdInfo = encryptOrDecrypt(getValue(WeiboConfigs.CONFIG_CLIENT_ID));
            String[] clientSecretInfo = encryptOrDecrypt(getValue(WeiboConfigs.CONFIG_CLIENT_SECRET));
            String[] redirectUriInfo = encryptOrDecrypt(getValue(WeiboConfigs.CONFIG_REDIRECT_URI));
            String[] safeDomainsInfo = encryptOrDecrypt(getValue(WeiboConfigs.CONFIG_SAFE_DOMAINS));

            // 加载原值到内存中
            props.put(WeiboConfigs.CONFIG_CLIENT_ID, clientIdInfo[0]);
            props.put(WeiboConfigs.CONFIG_CLIENT_SECRET, clientSecretInfo[0]);
            props.put(WeiboConfigs.CONFIG_REDIRECT_URI, redirectUriInfo[0]);
            props.put(WeiboConfigs.CONFIG_SAFE_DOMAINS, safeDomainsInfo[0]);

            String path = Thread.currentThread().getContextClassLoader().getResource(PROP_NAME).getPath();
            if (path.indexOf(".jar") < 0) {
                // 配置文件中更新为加密值(配置文件在jar包中的情况下暂时不加密配置值)
                Map<String, String> propsToAddOrUpdate = new HashedMap<>();
                propsToAddOrUpdate.put(WeiboConfigs.CONFIG_CLIENT_ID, clientIdInfo[1]);
                propsToAddOrUpdate.put(WeiboConfigs.CONFIG_CLIENT_SECRET, clientSecretInfo[1]);
                propsToAddOrUpdate.put(WeiboConfigs.CONFIG_REDIRECT_URI, redirectUriInfo[1]);
                propsToAddOrUpdate.put(WeiboConfigs.CONFIG_SAFE_DOMAINS, safeDomainsInfo[1]);
                // PropertiesUtils.updateProperties(PROP_NAME, propsToAddOrUpdate, null);
            }
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 加密或解密配置值
     * 
     * @param value
     *            index=0为原始值，index=1为加密值
     * @return
     */
    private static String[] encryptOrDecrypt(String value) {
        String[] infos = new String[] {"", ""};
        if (CheckUtils.isNotEmpty(value)) {
            // 空配置值不用加密
            if (value.startsWith(ENCRYPT_PREFIX)) {
                // 解密
                infos[0] = ThreeDesUtils.decrypt(value.substring(ENCRYPT_PREFIX.length()), THREE_DES_KEY);
                infos[1] = value;
            } else {
                // 加密
                infos[0] = value;
                infos[1] = ENCRYPT_PREFIX + ThreeDesUtils.encrypt(value, THREE_DES_KEY);
            }
        }
        return infos;
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