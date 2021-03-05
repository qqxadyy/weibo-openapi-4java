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

import weibo4j.model.PostParameter;
import weibo4j.model.SchoolSearch;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Search extends Weibo {

    private static final long serialVersionUID = 1060145395982699914L;

    public Search(String access_token) {
        this.accessToken = access_token;
    }

    // ---------------------------------搜索接口-----------------------------------------------

    /**
     * 搜索用户时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/users
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsUsers(String q) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/users.json",
            new PostParameter[] {new PostParameter("q", q)}, accessToken).asJSONArray();
    }

    /**
     * 搜索用户时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param count
     *            返回的记录条数，默认为10
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/users
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsUsers(String q, int count) throws WeiboException {
        return client
            .get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/users.json",
                new PostParameter[] {new PostParameter("q", q), new PostParameter("count", count)}, accessToken)
            .asJSONArray();
    }

    /**
     * 搜索公司时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/companies
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsCompanies(String q) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/companies.json",
            new PostParameter[] {new PostParameter("q", q)}, accessToken).asJSONArray();
    }

    /**
     * 搜索公司时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param count
     *            返回的记录条数，默认为10
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/companies
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsCompanies(String q, int count) throws WeiboException {
        return client
            .get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/companies.json",
                new PostParameter[] {new PostParameter("q", q), new PostParameter("count", count)}, accessToken)
            .asJSONArray();
    }

    /**
     * 搜索应用时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/apps
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsApps(String q) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/apps.json",
            new PostParameter[] {new PostParameter("q", q)}, accessToken).asJSONArray();
    }

    /**
     * 搜索应用时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param count
     *            返回的记录条数，默认为10
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/apps
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsApps(String q, int count) throws WeiboException {
        return client
            .get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/apps.json",
                new PostParameter[] {new PostParameter("q", q), new PostParameter("count", count)}, accessToken)
            .asJSONArray();
    }

    /**
     * 搜索学校时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/schools
     * @since JDK 1.5
     */
    public List<SchoolSearch> searchSuggestionsSchools(String q) throws WeiboException {
        return SchoolSearch
            .constructSchoolSearch(client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/schools.json",
                new PostParameter[] {new PostParameter("q", q)}, accessToken));
    }

    /**
     * 搜索学校时的联想搜索建议
     * 
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param count
     *            返回的记录条数，默认为10
     * @param type
     *            学校类型，0：全部、1：大学、2：高中、3：中专技校、4：初中、5：小学，默认为0
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/schools
     * @since JDK 1.5
     */
    public List<SchoolSearch> searchSuggestionsSchools(String q, int count, int type) throws WeiboException {
        return SchoolSearch.constructSchoolSearch(client.get(
            WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/schools.json", new PostParameter[] {
                new PostParameter("q", q), new PostParameter("count", count), new PostParameter("type", type)},
            accessToken));
    }

    /**
     * 搜索学校时的联想搜索建议
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/schools
     * @since JDK 1.5
     */
    public List<SchoolSearch> searchSuggestionsSchools(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return SchoolSearch.constructSchoolSearch(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/schools.json", parList, accessToken));
    }

    /**
     * @用户时的联想建议
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param type
     *            联想类型，0：关注、1：粉丝
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/at_users
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsAtUsers(String q, int type) throws WeiboException {
        return client
            .get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/at_users.json",
                new PostParameter[] {new PostParameter("q", q), new PostParameter("type", type)}, accessToken)
            .asJSONArray();
    }

    /**
     * @用户时的联想建议
     * @param q
     *            搜索的关键字，必须做URLencoding
     * @param count
     *            返回的记录条数，默认为10，粉丝最多1000，关注最多2000
     * @param type
     *            联想类型，0：关注、1：粉丝
     * @param range
     *            联想范围，0：只联想关注人、1：只联想关注人的备注、2：全部，默认为2
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/at_users
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsAtUsers(String q, int count, int type, int range) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/at_users.json",
            new PostParameter[] {new PostParameter("q", q), new PostParameter("count", count),
                new PostParameter("type", type), new PostParameter("range", range)},
            accessToken).asJSONArray();
    }

    /**
     * @用户时的联想建议
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/search/suggestions/at_users
     * @since JDK 1.5
     */
    public JSONArray searchSuggestionsAtUsers(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "search/suggestions/at_users.json", parList, accessToken)
            .asJSONArray();
    }
}
