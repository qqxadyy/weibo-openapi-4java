package weibo4j.util;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;

import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.PropertiesUtils;
import pjq.weibo.openapi.utils.ThreeDesUtils;
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
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_NAME));

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

            // 配置文件中更新为加密值
            Map<String, String> propsToAddOrUpdate = new HashedMap<>();
            propsToAddOrUpdate.put(WeiboConfigs.CONFIG_CLIENT_ID, clientIdInfo[1]);
            propsToAddOrUpdate.put(WeiboConfigs.CONFIG_CLIENT_SECRET, clientSecretInfo[1]);
            propsToAddOrUpdate.put(WeiboConfigs.CONFIG_REDIRECT_URI, redirectUriInfo[1]);
            propsToAddOrUpdate.put(WeiboConfigs.CONFIG_SAFE_DOMAINS, safeDomainsInfo[1]);
            PropertiesUtils.updateProperties(PROP_NAME, propsToAddOrUpdate, null);
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
        if (CheckUtils.isEmpty(value) || !value.startsWith(ENCRYPT_PREFIX)) {
            // 加密
            infos[0] = value;
            infos[1] = ENCRYPT_PREFIX + ThreeDesUtils.encrypt(value, THREE_DES_KEY);
        } else {
            // 解密
            infos[0] = ThreeDesUtils.decrypt(value.substring(ENCRYPT_PREFIX.length()), THREE_DES_KEY);
            infos[1] = value;
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