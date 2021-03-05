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
import weibo4j.http.ImageItem;
import weibo4j.model.Emotion;
import weibo4j.model.FriendsTimelineIds;
import weibo4j.model.MentionsIds;
import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.RepostTimelineIds;
import weibo4j.model.Status;
import weibo4j.model.StatusPager;
import weibo4j.model.UserTimelineIds;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Timeline extends Weibo {

    private static final long serialVersionUID = 6235150828015082132L;

    public Timeline(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------读取接口----------------------------------------*/

    /**
     * 返回最新的公共微博
     * 
     * @return list of statuses of the Public Timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/public_timeline
     * @since JDK 1.5
     */
    public StatusPager getPublicTimeline() throws WeiboException {
        return new StatusPager(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "statuses/public_timeline.json", accessToken));
    }

    /**
     * 返回最新的公共微博
     * 
     * @param count
     *            单页返回的记录条数，默认为20。
     * @param baseApp
     *            是否仅获取当前应用发布的信息。0为所有，1为仅本应用。
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/public_timeline
     * @since JDK 1.5
     */
    public StatusPager getPublicTimeline(int count, int baseApp) throws WeiboException {
        return new StatusPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "statuses/public_timeline.json",
            new PostParameter[] {new PostParameter("count", count), new PostParameter("base_app", baseApp)},
            accessToken));
    }

    /**
     * 获取当前登录用户及其所关注用户的最新20条微博消息。 和用户登录 http://weibo.com 后在“我的首页”中看到的内容相同。 This method calls
     * http://api.t.sina.com.cn/statuses/friends_timeline.format
     * 
     * @return list of the Friends Timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/friends_timeline
     * @since JDK 1.5
     */
    public StatusPager getFriendsTimeline() throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), accessToken));

    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博消息。<br/>
     * 和用户登录 http://weibo.com 后在“我的首页”中看到的内容相同。
     * 
     * @param paging
     *            相关分页参数
     * @param 过滤类型ID
     *            ，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @return list of the Friends Timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/friends_timeline
     * @since JDK 1.5
     */
    public StatusPager getFriendsTimeline(Integer baseAPP, Integer feature, Paging paging) throws WeiboException {
        return new StatusPager(client.get(
            WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), new PostParameter[] {
                new PostParameter("base_app", baseAPP.toString()), new PostParameter("feature", feature.toString())},
            paging, accessToken));
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/friends_timeline
     * @since JDK 1.5
     */
    public StatusPager getFriendsTimeline(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), parList, accessToken));
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博的ID。 This method calls http://api.t.sina.com.cn/statuses/friends_timeline.format
     * 
     * @return list of the Friends Timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/friends_timeline/ids
     * @since JDK 1.5
     */
    public FriendsTimelineIds getFriendsTimelineIds() throws WeiboException {
        return new FriendsTimelineIds(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_FRIENDS_TIMELINE_IDS), accessToken));

    }

    public JSONObject getFriendsTimelineIds(Integer baseAPP, Integer feature, Paging paging) throws WeiboException {
        return client.get(
            WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_FRIENDS_TIMELINE_IDS), new PostParameter[] {
                new PostParameter("base_app", baseAPP.toString()), new PostParameter("feature", feature.toString())},
            paging, accessToken).asJSONObject();
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博的ID
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/friends_timeline/ids
     * @since JDK 1.5
     */
    public JSONObject getFriendsTimelineIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_FRIENDS_TIMELINE_IDS), parList, accessToken)
            .asJSONObject();
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博消息。<br/>
     * 和用户登录 http://weibo.com 后在“我的首页”中看到的内容相同。
     * 
     * @return list of status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/home_timeline
     * @since JDK 1.5
     */
    public StatusPager getHomeTimeline() throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), accessToken));

    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博消息。<br/>
     * 和用户登录 http://weibo.com 后在“我的首页”中看到的内容相同。
     * 
     * @param paging
     *            相关分页参数
     * @param 过滤类型ID
     *            ，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @return list of the Friends Timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/home_timeline
     * @since JDK 1.5
     */
    public StatusPager getHomeTimeline(Integer baseAPP, Integer feature, Paging paging) throws WeiboException {
        return new StatusPager(client.get(
            WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), new PostParameter[] {
                new PostParameter("base_app", baseAPP.toString()), new PostParameter("feature", feature.toString())},
            paging, accessToken));
    }

    /**
     * 获取当前登录用户及其所关注用户的最新微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/home_timeline
     * @since JDK 1.5
     */
    public StatusPager getHomeTimeline(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE), parList, accessToken));
    }

    /**
     * 获取某个用户最新发表的微博列表
     * 
     * @return list of the user_timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/user_timeline
     * @since JDK 1.5
     */
    public StatusPager getUserTimeline() throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE), accessToken));
    }

    public StatusPager getUserTimelineByUid(String uid) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    public StatusPager getUserTimelineByName(String screen_name) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken));
    }

    /**
     * 获取某个用户最新发表的微博列表
     * 
     * @param uid
     *            需要查询的用户ID。
     * @param screen_name
     *            需要查询的用户昵称。
     * @param count
     *            单页返回的记录条数，默认为50。
     * @param page
     *            返回结果的页码，默认为1。
     * @param base_app
     *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @param feature
     *            过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
     * @return list of the user_timeline
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/user_timeline
     * @since JDK 1.5
     */
    public StatusPager getUserTimelineByUid(String uid, Paging page, Integer base_app, Integer feature)
        throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE),
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("base_app", base_app.toString()),
                new PostParameter("feature", feature.toString())},
            page, accessToken));
    }

    public StatusPager getUserTimelineByName(String screen_name, Paging page, Integer base_app, Integer feature)
        throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE),
            new PostParameter[] {new PostParameter("screen_name", screen_name),
                new PostParameter("base_app", base_app.toString()), new PostParameter("feature", feature.toString())},
            page, accessToken));
    }

    /**
     * 获取某个用户最新发表的微博列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/user_timeline
     * @since JDK 1.5
     */
    public StatusPager getUserTimeline(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE), parList, accessToken));
    }

    /**
     * 获取某个用户最新发表的微博列表ID
     * 
     * @return user_timeline IDS
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/user_timeline
     * @since JDK 1.5
     */
    public UserTimelineIds getUserTimelineIdsByUid(String uid) throws WeiboException {
        return new UserTimelineIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE_IDS),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    public JSONObject getUserTimelineIdsByName(String screen_name) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE_IDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken).asJSONObject();
    }

    /**
     * 获取用户发布的微博的ID
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/user_timeline/ids
     * @since JDK 1.5
     */
    public JSONObject getUserTimelineIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE_IDS), parList, accessToken)
            .asJSONObject();
    }

    /**
     * 获取指定微博的转发微博列表
     * 
     * @param id
     *            需要查询的微博ID
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost_timeline
     * @since JDK 1.5
     */
    public StatusPager getRepostTimeline(String id) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE),
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 获取指定微博的转发微博列表
     * 
     * @param id
     *            需要查询的微博ID
     * @param count
     *            单页返回的记录条数，默认为50
     * @param page
     *            返回结果的页码，默认为1
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost_timeline
     * @since JDK 1.5
     */
    public StatusPager getRepostTimeline(String id, Paging page) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE),
            new PostParameter[] {new PostParameter("id", id)}, page, accessToken));
    }

    /**
     * 获取指定微博的转发微博列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/repost_timeline
     * @since JDK 1.5
     */
    public StatusPager getRepostTimeline(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE), parList, accessToken));
    }

    /**
     * 获取一条原创微博的最新转发微博的ID
     * 
     * @param id
     *            需要查询的微博ID
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost_timeline/ids
     * @since JDK 1.5
     */
    public RepostTimelineIds getRepostTimelineIds(String id) throws WeiboException {
        return new RepostTimelineIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE_IDS),
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 获取一条原创微博的最新转发微博的ID
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/repost_timeline/ids
     * @since JDK 1.5
     */
    public RepostTimelineIds getRepostTimelineIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new RepostTimelineIds(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE_IDS), parList, accessToken));
    }

    /**
     * 获取当前用户最新转发的微博列表
     * 
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost_by_me
     * @since JDK 1.5
     */
    public StatusPager getRepostByMe() throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_BY_ME), accessToken));
    }

    /**
     * 获取当前用户最新转发的微博列表
     * 
     * @param page
     *            返回结果的页码，默认为1
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost_by_me
     * @since JDK 1.5
     */
    public StatusPager getRepostByMe(Paging page) throws WeiboException {
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_BY_ME), null, page, accessToken));
    }

    /**
     * 获取最新的提到登录用户的微博列表，即@我的微博
     * 
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/mentions
     * @since JDK 1.5
     */
    public StatusPager getMentions() throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS), accessToken));
    }

    /**
     * 获取最新的提到登录用户的微博列表，即@我的微博
     * 
     * @param count
     *            单页返回的记录条数，默认为50。
     * @param page
     *            返回结果的页码，默认为1。
     * @param filter_by_author
     *            作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
     * @param filter_by_source
     *            来源筛选类型，0：全部、1：来自微博、2：来自微群，默认为0。
     * @param filter_by_type
     *            原创筛选类型，0：全部微博、1：原创的微博，默认为0。
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/mentions
     * @since JDK 1.5
     */
    public StatusPager getMentions(Paging page, Integer filter_by_author, Integer filter_by_source,
        Integer filter_by_type) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS),
            new PostParameter[] {new PostParameter("filter_by_author", filter_by_author.toString()),
                new PostParameter("filter_by_source", filter_by_source.toString()),
                new PostParameter("filter_by_type", filter_by_type.toString())},
            page, accessToken));
    }

    /**
     * 获取最新的提到登录用户的微博列表，即@我的微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/mentions
     * @since JDK 1.5
     */
    public StatusPager getMentions(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS), parList, accessToken));
    }

    /**
     * 获取最新的提到登录用户的微博ID列表，即@我的微博
     * 
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/mentions/ids
     * @since JDK 1.5
     */
    public MentionsIds getMentionsIds() throws WeiboException {
        return new MentionsIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS_IDS), accessToken));
    }

    public JSONObject getMentionsIds(Paging page, Integer filter_by_author, Integer filter_by_source,
        Integer filter_by_type) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS_IDS),
            new PostParameter[] {new PostParameter("filter_by_author", filter_by_author.toString()),
                new PostParameter("filter_by_source", filter_by_source.toString()),
                new PostParameter("filter_by_type", filter_by_type.toString())},
            page, accessToken).asJSONObject();
    }

    /**
     * 获取最新的提到登录用户的微博ID列表，即@我的微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/mentions/ids
     * @since JDK 1.5
     */
    public JSONObject getMentionsIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS_IDS), parList, accessToken)
            .asJSONObject();
    }

    /**
     * 获取双向关注用户的最新微博
     * 
     * @return list of Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/bilateral_timeline
     * @since JDK 1.5
     */
    public StatusPager getBilateralTimeline() throws WeiboException {
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_BILATERAL_TIMELINE), accessToken));
    }

    public StatusPager getBilateralTimeline(Integer base_app, Integer feature) throws WeiboException {
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_BILATERAL_TIMELINE),
            new PostParameter[] {new PostParameter("base_app", base_app), new PostParameter("feature", feature)},
            accessToken));
    }

    /**
     * 获取双向关注用户的最新微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/bilateral_timeline
     * @since JDK 1.5
     */
    public StatusPager getBilateralTimeline(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new StatusPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_BILATERAL_TIMELINE), parList, accessToken));
    }

    /**
     * 根据微博ID获取单条微博内容
     * 
     * @param id
     *            需要获取的微博ID。
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/statuses/show
     * @since JDK 1.5
     */
    public Status showStatus(String id) throws WeiboException {
        return new Status(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_SHOW),
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 通过微博ID获取其MID
     * 
     * @param id
     *            需要查询的微博ID，批量模式下，用半角逗号分隔，最多不超过20个。
     * @param type
     *            获取类型，1：微博、2：评论、3：私信，默认为1。
     * @return Status's mid
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/querymid
     * @since JDK 1.5
     */
    public JSONObject queryMid(Integer type, String id) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_QUERY_MID),
            new PostParameter[] {new PostParameter("id", id), new PostParameter("type", type.toString())}, accessToken)
            .asJSONObject();
    }

    /**
     * 通过微博ID获取其MID
     * 
     * @param id
     *            需要查询的微博ID，批量模式下，用半角逗号分隔，最多不超过20个。
     * @param type
     *            获取类型，1：微博、2：评论、3：私信，默认为1。
     * @param is_batch
     *            是否使用批量模式，0：否、1：是，默认为0。
     * @return Status's mid
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/querymid
     * @since JDK 1.5
     */
    public JSONObject queryMid(Integer type, String id, int is_batch) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_QUERY_MID),
            new PostParameter[] {new PostParameter("id", id), new PostParameter("type", type.toString()),
                new PostParameter("is_batch", is_batch)},
            accessToken).asJSONObject();
    }

    /**
     * 通过微博MID获取其ID
     * 
     * @param mid
     *            true string 需要查询的微博MID，批量模式下，用半角逗号分隔，最多不超过20个
     * @param type
     *            获取类型，1：微博、2：评论、3：私信，默认为1。
     * @return Status's id
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/queryid
     * @since JDK 1.5
     */
    public JSONObject queryId(String mid, Integer type, int isBase62) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_QUERY_ID),
            new PostParameter[] {new PostParameter("mid", mid), new PostParameter("type", type.toString()),
                new PostParameter("isBase62", isBase62)},
            accessToken).asJSONObject();
    }

    /**
     * 通过微博MID获取其ID
     * 
     * @param mid
     *            true string 需要查询的微博MID，批量模式下，用半角逗号分隔，最多不超过20个
     * @param type
     *            获取类型，1：微博、2：评论、3：私信，默认为1。
     * @param is_batch
     *            是否使用批量模式，0：否、1：是，默认为0。
     * @param inbox
     *            仅对私信有效，当MID类型为私信时用此参数，0：发件箱、1：收件箱，默认为0 。
     * @param isBase62
     *            MID是否是base62编码，0：否、1：是，默认为0。
     * @return Status's id
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/queryid
     * @since JDK 1.5
     */
    public JSONObject queryId(String mid, Integer type, Integer isBatch, Integer isBase62) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_QUERY_ID),
            new PostParameter[] {new PostParameter("mid", mid), new PostParameter("type", type.toString()),
                new PostParameter("is_batch", isBatch.toString()), new PostParameter("isBase62", isBase62.toString())},
            accessToken).asJSONObject();
    }

    /**
     * 通过微博MID获取其ID
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/queryid
     * @since JDK 1.5
     */
    public JSONObject queryId(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_QUERY_ID), parList, accessToken).asJSONObject();
    }

    /**
     * 转发一条新微博
     * 
     * @param id
     *            要转发的微博ID
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost
     * @since JDK 1.5
     */
    public Status repost(String id) throws WeiboException {
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST),
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 转发一条微博
     * 
     * @param id
     *            要转发的微博ID
     * @param status
     *            添加的转发文本，必须做URLencode，内容不超过140个汉字，不填则默认为“转发微博”
     * @param is_comment
     *            是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论，默认为0
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/repost
     * @since JDK 1.5
     */
    public Status repost(String id, String status, Integer is_comment) throws WeiboException {
        return new Status(client.post(
            WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST), new PostParameter[] {new PostParameter("id", id),
                new PostParameter("status", status), new PostParameter("is_comment", is_comment.toString())},
            accessToken));
    }

    /**
     * 转发一条微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/repost
     * @since JDK 1.5
     */
    public Status repost(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST), parList, accessToken));
    }

    /**
     * 根据微博ID删除指定微博
     * 
     * @param id
     *            需要删除的微博ID
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/destroy
     * @since JDK 1.5
     */
    public Status destroy(String id) throws WeiboException {
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_DESTROY),
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 发布一条新微博
     * 
     * @param status
     *            要发布的微博文本内容，必须做URLencode，内容不超过140个汉字
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/update
     * @since JDK 1.5
     */
    public Status updateStatus(String status) throws WeiboException {
        return new Status(client.post(WeiboConfig.getOpenAPIBaseURL() + "statuses/update.json",
            new PostParameter[] {new PostParameter("status", status)}, accessToken));
    }

    /**
     * 发布一条新微博
     * 
     * @param status
     *            要发布的微博文本内容，必须做URLencode，内容不超过140个汉字
     * @param lat
     *            纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     * @param long
     *            经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0。
     * @param annotations
     *            元数据，主要是为了方便第三方应用记录一些适合于自己使用的信息，每条微博可以包含一个或者多个元数据， 必须以json字串的形式提交，字串长度不超过512个字符，具体内容可以自定
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/update
     * @since JDK 1.5
     */
    public Status updateStatus(String status, Float lat, Float longs, String annotations) throws WeiboException {
        return new Status(client.post(WeiboConfig.getOpenAPIBaseURL() + "statuses/update.json",
            new PostParameter[] {new PostParameter("status", status), new PostParameter("lat", lat.toString()),
                new PostParameter("long", longs.toString()), new PostParameter("annotations", annotations)},
            accessToken));
    }

    /**
     * 发布一条新微博
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/update
     * @since JDK 1.5
     */
    public Status updateStatus(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new Status(client.post(WeiboConfig.getOpenAPIBaseURL() + "statuses/update.json", parList, accessToken));
    }

    /**
     * 上传图片并发布一条新微博
     * 
     * @param status
     *            要发布的微博文本内容，必须做URLencode，内容不超过140个汉字
     * @param pic
     *            要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/upload
     * @since JDK 1.5
     */
    public Status uploadStatus(String status, ImageItem item) throws WeiboException {
        return new Status(client.postMultipartForm(WeiboConfig.getOpenAPIBaseURL() + "statuses/upload.json",
            new PostParameter[] {new PostParameter("status", status)}, accessToken, item.getName(), null, ""));
    }

    /**
     * 上传图片并发布一条新微博
     * 
     * @param status
     *            要发布的微博文本内容，必须做URLencode，内容不超过140个汉字
     * @param pic
     *            要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
     * @param lat
     *            纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
     * @param long
     *            经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0。
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/statuses/upload
     * @since JDK 1.5
     */
    public Status uploadStatus(String status, ImageItem item, Float lat, Float longs) throws WeiboException {
        return new Status(client.postMultipartForm(WeiboConfig.getOpenAPIBaseURL() + "statuses/upload.json",
            new PostParameter[] {new PostParameter("status", status), new PostParameter("lat", lat.toString()),
                new PostParameter("long", longs.toString())},
            accessToken, item.getName(), null, ""));
    }

    /**
     * 上传图片并发布一条新微博
     * 
     * @param map
     * @param item
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/statuses/upload
     * @since JDK 1.5
     */
    public Status uploadStatus(Map<String, String> map, ImageItem item) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new Status(client.postMultipartForm(WeiboConfig.getOpenAPIBaseURL() + "statuses/upload.json", parList,
            accessToken, item.getName(), null, ""));
    }

    /**
     * 获取微博官方表情的详细信息
     * 
     * @return Emotion
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/emotions
     * @since JDK 1.5
     */
    public List<Emotion> getEmotions() throws WeiboException {
        return WeiboResponse.buildList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.EMOTIONS), accessToken),
            Emotion.class);
    }

    /**
     * 获取微博官方表情的详细信息
     * 
     * @param type
     *            表情类别，face：普通表情、ani：魔法表情、cartoon：动漫表情，默认为face
     * @param language
     *            语言类别，cnname：简体、twname：繁体，默认为cnname
     * @return Emotion
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/emotions
     * @since JDK 1.5
     */
    public List<Emotion> getEmotions(String type, String language) throws WeiboException {
        return WeiboResponse.buildList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.EMOTIONS),
            new PostParameter[] {new PostParameter("type", type), new PostParameter("language", language)},
            accessToken), Emotion.class);
    }

    /**
     * 批量获取指定微博的转发数评论数
     * 
     * @param ids
     *            需要获取数据的微博ID，多个之间用逗号分隔，最多不超过100个
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/emotions
     * @since JDK 1.5
     */
    public JSONArray getStatusesCount(String ids) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_COUNT),
            new PostParameter[] {new PostParameter("ids", ids)}, accessToken).asJSONArray();
    }

}
