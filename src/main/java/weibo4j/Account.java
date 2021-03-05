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

import java.util.List;
import java.util.Map;

import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.model.PostParameter;
import weibo4j.model.Privacy;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.School;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Account extends Weibo {

    private static final long serialVersionUID = 3816005087976772682L;

    public Account(String access_token) {
        this.accessToken = access_token;
    }

    /**
     * 获取当前登录用户的API访问频率限制情况
     * 
     * @return rate limit
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/account/rate_limit_status
     * @since JDK 1.5
     */
    public RateLimitStatus getAccountRateLimitStatus() throws WeiboException {
        return new RateLimitStatus(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.ACCOUNT_RATE_LIMIT_STATUS), accessToken));
    }

    /**
     * OAuth授权之后，获取授权用户的UID
     * 
     * @return uid
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/account/get_uid
     * @since JDK 1.5
     */
    public JSONObject getUid() throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.ACCOUNT_GET_UID), accessToken).asJSONObject();
    }

    /**
     * 获取当前登录用户的隐私设置
     * 
     * @param uid
     * @return User's privacy
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/account/get_privacy
     * @since JDK 1.5
     */
    public Privacy getAccountPrivacy() throws WeiboException {
        return new Privacy(client.get(WeiboConfig.getOpenAPIBaseURL() + "account/get_privacy.json", accessToken));
    }

    /**
     * 获取所有学校列表
     * 
     * @param province
     *            学校名称关键字
     * @return list of school
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/account/profile/school_list
     * @since JDK 1.5
     */
    public List<School> getAccountProfileSchoolList(String keyword) throws WeiboException {
        return School.constructSchool(client.get(WeiboConfig.getOpenAPIBaseURL() + "account/profile/school_list.json",
            new PostParameter[] {new PostParameter("keyword", keyword)}, accessToken));
    }

    /**
     * 获取所有的学校列表
     * 
     * @param province
     *            省份范围，省份ID
     * @param capital
     *            学校首字母
     * @return list of school
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/account/profile/school_list
     * @since JDK 1.5
     */
    public List<School> getAccountProfileSchoolList(String province, String capital) throws WeiboException {
        return School.constructSchool(client.get(WeiboConfig.getOpenAPIBaseURL() + "account/profile/school_list.json",
            new PostParameter[] {new PostParameter("province", province), new PostParameter("capital", capital)},
            accessToken));
    }

    /**
     * 获取所有的学校列表
     * 
     * @param map
     *            参数列表
     * @return list of school
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/account/profile/school_list
     * @since JDK 1.5
     */
    public List<School> getAccountProfileSchoolList(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return School.constructSchool(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "account/profile/school_list.json", parList, accessToken));
    }

    /**
     * 退出登录
     * 
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/account/end_session
     * @since JDK 1.5
     */
    public User endSession() throws WeiboException {
        return new User(client.get(WeiboConfig.getOpenAPIBaseURL() + "account/end_session.json", accessToken));
    }
}
