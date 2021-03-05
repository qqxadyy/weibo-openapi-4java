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

import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.Trends;
import weibo4j.model.UserTrend;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.WeiboConfig;

public class Trend extends Weibo {

    private static final long serialVersionUID = 903299515334415487L;

    public Trend(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------话题接口----------------------------------------*/
    /**
     * 获取某人的话题列表
     * 
     * @param uid
     *            需要获取话题的用户的UID
     * @return list of the userTrend
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/trends
     * @since JDK 1.5
     */
    public List<UserTrend> getTrends(String uid) throws WeiboException {
        return UserTrend.constructTrendList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取某人的话题列表
     * 
     * @param uid
     *            需要获取话题的用户的UID
     * @param page
     *            返回结果的页码，默认为1
     * @return list of the userTrend
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/trends
     * @since JDK 1.5
     */
    public List<UserTrend> getTrends(String uid, Paging page) throws WeiboException {
        return UserTrend.constructTrendList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends.json",
            new PostParameter[] {new PostParameter("uid", uid)}, page, accessToken));
    }

    /**
     * 判断当前用户是否关注某话题
     * 
     * @param trend_name
     *            话题关键字，必须做URLencode
     * @return jsonobject
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/is_follow
     * @since JDK 1.5
     */
    public JSONObject isFollow(String trend_name) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/is_follow.json",
            new PostParameter[] {new PostParameter("trend_name", trend_name)}, accessToken).asJSONObject();
    }

    /**
     * 返回最近一小时内的热门话题
     * 
     * @param base_app
     *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @return list of trends
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/hourly
     * @since JDK 1.5
     */
    public List<Trends> getTrendsHourly() throws WeiboException {
        return Trends
            .constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/hourly.json", accessToken));
    }

    public List<Trends> getTrendsHourly(Integer base_app) throws WeiboException {
        return Trends.constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/hourly.json",
            new PostParameter[] {new PostParameter("base_app", base_app.toString())}, accessToken));
    }

    /**
     * 返回最近一天内的热门话题
     * 
     * @param base_app
     *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @return list of trends
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/daily
     * @since JDK 1.5
     */
    public List<Trends> getTrendsDaily() throws WeiboException {
        return Trends
            .constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/daily.json", accessToken));
    }

    public List<Trends> getTrendsDaily(Integer base_app) throws WeiboException {
        return Trends.constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/daily.json",
            new PostParameter[] {new PostParameter("base_app", base_app.toString())}, accessToken));
    }

    /**
     * 返回最近一周内的热门话题
     * 
     * @param base_app
     *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     * @return list of trends
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/weekly
     * @since JDK 1.5
     */
    public List<Trends> getTrendsWeekly() throws WeiboException {
        return Trends
            .constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/weekly.json", accessToken));
    }

    public List<Trends> getTrendsWeekly(Integer base_app) throws WeiboException {
        return Trends.constructTrendsList(client.get(WeiboConfig.getOpenAPIBaseURL() + "trends/weekly.json",
            new PostParameter[] {new PostParameter("base_app", base_app.toString())}, accessToken));
    }

    /**
     * 关注某话题
     * 
     * @param trend_name
     *            要关注的话题关键词。
     * @return UserTrend
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/follow
     * @since JDK 1.5
     */
    public UserTrend trendsFollow(String trend_name) throws WeiboException {
        return new UserTrend(client.post(WeiboConfig.getOpenAPIBaseURL() + "trends/follow.json",
            new PostParameter[] {new PostParameter("trend_name", trend_name)}, accessToken));
    }

    /**
     * 取消对某话题的关注
     * 
     * @param trend_id
     *            要取消关注的话题ID
     * @return jsonobject
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/trends/destroy
     * @since JDK 1.5
     */
    public JSONObject trendsDestroy(Integer trend_id) throws WeiboException {
        return client.post(WeiboConfig.getOpenAPIBaseURL() + "trends/destroy.json",
            new PostParameter[] {new PostParameter("trend_id", trend_id.toString())}, accessToken).asJSONObject();
    }

}
