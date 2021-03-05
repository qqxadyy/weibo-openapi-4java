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
package weibo4j;

import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

public class PublicService extends Weibo {

    private static final long serialVersionUID = -2783541874923814897L;

    public PublicService(String access_token) {
        this.accessToken = access_token;
    }

    /**
     * 通过地址编码获取地址名称
     * 
     * @param codes
     *            需要查询的地址编码，多个之间用逗号分隔
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/common/code_to_location
     * @since JDK 1.5
     */
    public JSONArray getLocationByCode(String codes) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_CODE_TO_LOCATION),
            new PostParameter[] {new PostParameter("codes", codes)}, accessToken).asJSONArray();
    }

    /**
     * 获取省份列表
     * 
     * @param country
     *            国家的国家代码
     * @param capital
     *            省份的首字母，a-z，可为空代表返回全部，默认为全部
     * @param language
     *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/common/get_province
     * @since JDK 1.5
     */
    public JSONArray provinceList(String country) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_PROVINCE),
            new PostParameter[] {new PostParameter("country", country)}, accessToken).asJSONArray();
    }

    public JSONArray provinceListOfCapital(String country, String capital) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_PROVINCE),
            new PostParameter[] {new PostParameter("country", country), new PostParameter("capital", capital)},
            accessToken).asJSONArray();
    }

    public JSONArray provinceList(String country, String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_PROVINCE),
            new PostParameter[] {new PostParameter("country", country), new PostParameter("language", language)},
            accessToken).asJSONArray();
    }

    public JSONArray provinceList(String country, String capital, String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_PROVINCE),
            new PostParameter[] {new PostParameter("country", country), new PostParameter("capital", capital),
                new PostParameter("language", language)},
            accessToken).asJSONArray();
    }

    /**
     * 获取城市列表
     * 
     * @param province
     *            省份的省份代码
     * @param capital
     *            城市的首字母，a-z，可为空代表返回全部，默认为全部
     * @param language
     *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/common/get_city
     * @since JDK 1.5
     */
    public JSONArray cityList(String province) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_CITY),
            new PostParameter[] {new PostParameter("province", province)}, accessToken).asJSONArray();
    }

    public JSONArray cityListOfCapital(String province, String capital) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_CITY),
            new PostParameter[] {new PostParameter("province", province), new PostParameter("capital", capital)},
            accessToken).asJSONArray();
    }

    public JSONArray cityList(String province, String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_CITY),
            new PostParameter[] {new PostParameter("province", province), new PostParameter("language", language)},
            accessToken).asJSONArray();
    }

    public JSONArray cityList(String province, String capital, String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_CITY),
            new PostParameter[] {new PostParameter("province", province), new PostParameter("capital", capital),
                new PostParameter("language", language)},
            accessToken).asJSONArray();
    }

    /**
     * 获取国家列表
     * 
     * @param capital
     *            国家的首字母，a-z，可为空代表返回全部，默认为全部
     * @param language
     *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/common/get_country
     * @since JDK 1.5
     */
    public JSONArray countryList() throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_COUNTRY), accessToken).asJSONArray();
    }

    public JSONArray countryListOfCapital(String capital) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_COUNTRY),
            new PostParameter[] {new PostParameter("capital", capital)}, accessToken).asJSONArray();
    }

    public JSONArray countryList(String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_COUNTRY),
            new PostParameter[] {new PostParameter("language", language)}, accessToken).asJSONArray();
    }

    public JSONArray countryList(String capital, String language) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_COUNTRY),
            new PostParameter[] {new PostParameter("capital", capital), new PostParameter("language", language)},
            accessToken).asJSONArray();
    }

    /**
     * 获取时区配置表
     * 
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/common/get_timezone
     * @since JDK 1.5
     */
    public JSONObject getTomeZone() throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_TIMEZONE), accessToken).asJSONObject();
    }

}
