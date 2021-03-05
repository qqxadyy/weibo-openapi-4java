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

import java.util.Map;

import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.UserPager;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Friendships extends Weibo {
    /**
     * 
     */
    private static final long serialVersionUID = 3603512821159421447L;

    public Friendships(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------关系接口----------------------------------------*/
    /**
     * 获取用户的关注列表
     * 
     * @return list of the user's follow
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends
     * @since JDK 1.5
     */
    public UserPager getFriendsByID(String id) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS),
            new PostParameter[] {new PostParameter("uid", id)}, accessToken));
    }

    /**
     * 获取用户的关注列表
     * 
     * @return list of the user's follow
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends
     * @since JDK 1.5
     */
    public UserPager getFriendsByScreenName(String screen_name) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken));
    }

    /**
     * 获取用户的关注列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends
     * @since JDK 1.5
     */
    public UserPager getFriends(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new UserPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS), parList, accessToken));
    }

    /**
     * 获取两个用户之间的共同关注人列表
     * 
     * @param uid
     *            需要获取共同关注关系的用户UID
     * @return list of the user's follow
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
     * @since JDK 1.5
     */
    public UserPager getFriendsInCommon(String uid) throws WeiboException {
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/in_common.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取两个用户之间的共同关注人列表
     * 
     * @param uid
     *            需要获取共同关注关系的用户UID
     * @param suid
     *            需要获取共同关注关系的用户UID，默认为当前登录用户
     * @param count
     *            单页返回的记录条数，默认为50
     * @param page
     *            返回结果的页码，默认为1
     * @return list of the user's follow
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
     * @since JDK 1.5
     */
    public UserPager getFriendsInCommon(String uid, String suid, Paging page) throws WeiboException {
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/in_common.json",
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("suid", suid)}, page, accessToken));
    }

    /**
     * 获取两个用户之间的共同关注人列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
     * @since JDK 1.5
     */
    public UserPager getFriendsInCommon(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new UserPager(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/in_common.json", parList, accessToken));
    }

    /**
     * 获取用户的双向关注列表，即互粉列表
     * 
     * @param uid
     *            需要获取双向关注列表的用户UID
     * @return list of the user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
     * @since JDK 1.5
     */
    public UserPager getFriendsBilateral(String uid) throws WeiboException {
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户的双向关注列表，即互粉列表
     * 
     * @param uid
     *            需要获取双向关注列表的用户UID
     * @param count
     *            单页返回的记录条数，默认为50。
     * @param page
     *            返回结果的页码，默认为1。
     * @param sort
     *            排序类型，0：按关注时间最近排序，默认为0。
     * @return list of the user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
     * @since JDK 1.5
     */
    public UserPager getFriendsBilateral(String uid, Integer sort, Paging page) throws WeiboException {
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral.json",
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("sort", sort.toString())}, page,
            accessToken));
    }

    /**
     * 获取用户的双向关注列表，即互粉列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
     * @since JDK 1.5
     */
    public UserPager getFriendsBilateral(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new UserPager(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral.json", parList, accessToken));
    }

    /**
     * 获取用户双向关注的用户ID列表，即互粉UID列表
     * 
     * @param uid
     *            需要获取双向关注列表的用户UID
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
     * @since JDK 1.5
     */
    public String[] getFriendsBilateralIds(String uid) throws WeiboException {
        return User.constructIds(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral/ids.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户双向关注的用户ID列表，即互粉UID列表
     * 
     * @param uid
     *            需要获取双向关注列表的用户UID
     * @param count
     *            单页返回的记录条数，默认为50。
     * @param page
     *            返回结果的页码，默认为1。
     * @param sort
     *            排序类型，0：按关注时间最近排序，默认为0。
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
     * @since JDK 1.5
     */
    public String[] getFriendsBilateralIds(String uid, Integer sort, Paging page) throws WeiboException {
        return User.constructIds(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral/ids.json",
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("sort", sort.toString())}, page,
            accessToken));
    }

    /**
     * 获取用户双向关注的用户ID列表，即互粉UID列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
     * @since JDK 1.5
     */
    public String[] getFriendsBilateralIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return User.constructIds(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/bilateral/ids.json",
            parList, accessToken));
    }

    /**
     * 获取用户关注的用户UID列表
     * 
     * @param uid
     *            需要查询的用户UID
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/ids
     * @since JDK 1.5
     */
    public String[] getFriendsIdsByUid(String uid) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户关注的用户UID列表
     * 
     * @param uid
     *            需要查询的用户UID
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/ids
     * @since JDK 1.5
     */
    public String[] getFriendsIdsByName(String screen_name) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken));
    }

    /**
     * 获取用户关注的用户UID列表
     * 
     * @param uid
     *            需要查询的用户UID
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/ids
     * @since JDK 1.5
     */
    public String[] getFriendsIdsByUid(String uid, Integer count, Integer cursor) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS),
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("count", count.toString()),
                new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户关注的用户UID列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return ids
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/ids
     * @since JDK 1.5
     */
    public String[] getFriendsIdsByName(String screen_name, Integer count, Integer cursor) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name),
                new PostParameter("count", count.toString()), new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户关注的用户UID列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends/ids
     * @since JDK 1.5
     */
    public String[] getFriendsIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return User.constructIds(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS), parList, accessToken));
    }

    /**
     * 批量获取当前登录用户的关注人的备注信息
     * 
     * @param uids
     *            需要获取备注的用户UID，用半角逗号分隔，最多不超过50个
     * @return list of user's remark
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends/remark_batch
     * @since JDK 1.5
     */
    public JSONArray getRemark(String uids) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends/remark_batch.json",
            new PostParameter[] {new PostParameter("uids", uids)}, accessToken).asJSONArray();
    }

    /**
     * 获取用户的粉丝列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers
     * @since JDK 1.5
     */
    public UserPager getFollowersByName(String screen_name) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken));
    }

    /**
     * 获取用户的粉丝列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers
     * @since JDK 1.5
     */
    public UserPager getFollowersByName(String screen_name, Integer count, Integer cursor) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS),
            new PostParameter[] {new PostParameter("screen_name", screen_name),
                new PostParameter("count", count.toString()), new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户的粉丝列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers
     * @since JDK 1.5
     */
    public UserPager getFollowersById(String uid) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户的粉丝列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers
     * @since JDK 1.5
     */
    public UserPager getFollowersById(String uid, Integer count, Integer cursor) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS),
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("count", count.toString()),
                new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户的粉丝列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/followers
     * @since JDK 1.5
     */
    public UserPager getFollowers(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new UserPager(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS), parList, accessToken));
    }

    /**
     * 获取用户粉丝的用户UID列表
     * 
     * @param uid
     *            需要查询的用户ID
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/ids
     * @since JDK 1.5
     */
    public String[] getFollowersIdsById(String uid) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户粉丝的用户UID列表
     * 
     * @param uid
     *            需要查询的用户ID
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/ids
     * @since JDK 1.5
     */
    public String[] getFollowersIdsById(String uid, Integer count, Integer cursor) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS),
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("count", count.toString()),
                new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户粉丝的用户UID列表
     * 
     * @param screen_name
     *            需要查询的用户昵称
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/ids
     * @since JDK 1.5
     */
    public String[] getFollowersIdsByName(String screen_name) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken));
    }

    /**
     * 获取用户粉丝的用户UID列表
     * 
     * @param screen_name
     *            需要查询的用户ID
     * @param count
     *            单页返回的记录条数，默认为500，最大不超过5000
     * @param cursor
     *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/ids
     * @since JDK 1.5
     */
    public String[] getFollowersIdsByName(String screen_name, Integer count, Integer cursor) throws WeiboException {
        return User.constructIds(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS),
            new PostParameter[] {new PostParameter("screen_name", screen_name),
                new PostParameter("count", count.toString()), new PostParameter("cursor", cursor.toString())},
            accessToken));
    }

    /**
     * 获取用户粉丝的用户UID列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/followers/ids
     * @since JDK 1.5
     */
    public String[] getFollowersIdsByName(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return User.constructIds(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS), parList, accessToken));
    }

    /**
     * 获取用户的活跃粉丝列表
     * 
     * @param uid
     *            需要查询的用户ID
     * @return list of user's id
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/active
     * @since JDK 1.5
     */
    public UserPager getFollowersActive(String uid) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_ACTIVE),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取用户的活跃粉丝列表
     * 
     * @param uid
     *            需要查询的用户ID
     * @param count
     *            返回的记录条数，默认为20，最大不超过200。
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/followers/active
     * @since JDK 1.5
     */
    public UserPager getFollowersActive(String uid, Integer count) throws WeiboException {
        return new UserPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_ACTIVE),
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("count", count.toString())},
            accessToken));
    }

    /**
     * 获取当前登录用户的关注人中又关注了指定用户的用户列表
     * 
     * @param uid
     *            需要查询的用户ID
     * @return list of users
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/friends_chain/followers
     * @since JDK 1.5
     */
    public UserPager getFriendsChainFollowers(String uid) throws WeiboException {
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends_chain/followers.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 获取当前登录用户的关注人中又关注了指定用户的用户列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/friends_chain/followers
     * @since JDK 1.5
     */
    public UserPager getFriendsChainFollowers(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new UserPager(client.get(WeiboConfig.getOpenAPIBaseURL() + "friendships/friends_chain/followers.json",
            parList, accessToken));
    }

    /**
     * 获取两个用户之间的详细关注关系情况
     * 
     * @param source
     *            源用户的UID
     * @param target
     *            目标用户的UID
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/show
     * @since JDK 1.5
     */
    public JSONObject getFriendshipsById(long source, long target) throws WeiboException {
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_SHOW),
            new PostParameter[] {new PostParameter("source_id", source), new PostParameter("target_id", target)},
            accessToken).asJSONObject();
    }

    /**
     * 获取两个用户之间的详细关注关系情况
     * 
     * @param source
     *            源用户的微博昵称
     * @param target
     *            目标用户的微博昵称
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/show
     * @since JDK 1.5
     */
    public JSONObject getFriendshipsByName(String source, String target) throws WeiboException {
        return client.get(
            WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_SHOW), new PostParameter[] {
                new PostParameter("source_screen_name", source), new PostParameter("target_screen_name", target)},
            accessToken).asJSONObject();
    }

    /**
     * 获取两个用户之间的详细关注关系情况
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/show
     * @since JDK 1.5
     */
    public JSONObject getFriendships(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_SHOW), parList, accessToken).asJSONObject();
    }

    /**
     * 关注一个用户
     * 
     * @param uid
     *            需要查询的用户ID
     * @return user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/create
     * @since JDK 1.5
     */
    public User createFriendshipsById(String uid) throws WeiboException {
        return new User(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_CREATE),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken).asJSONObject());
    }

    /**
     * 关注一个用户
     * 
     * @param screen_name
     *            需要查询的用户screen_name
     * @return user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/create
     * @since JDK 1.5
     */
    public User createFriendshipsByName(String screen_name) throws WeiboException {
        return new User(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_CREATE),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken).asJSONObject());
    }

    /**
     * 关注一个用户
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/friendships/create
     * @since JDK 1.5
     */
    public User createFriendships(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return new User(
            client.post(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_CREATE), parList, accessToken).asJSONObject());
    }

    /**
     * 取消关注一个用户
     * 
     * @param uid
     *            需要查询的用户ID
     * @return user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/destroy
     * @since JDK 1.5
     */
    public User destroyFriendshipsById(String uid) throws WeiboException {
        return new User(client.post(WeiboConfig.getOpenAPIBaseURL() + "friendships/destroy.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken).asJSONObject());
    }

    /**
     * 取消关注一个用户
     * 
     * @param screen_name
     *            需要查询的用户screen_name
     * @return user
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/friendships/destroy
     * @since JDK 1.5
     */
    public User destroyFriendshipsByName(String screen_name) throws WeiboException {
        return new User(client.post(WeiboConfig.getOpenAPIBaseURL() + "friendships/destroy.json",
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken).asJSONObject());
    }
}
