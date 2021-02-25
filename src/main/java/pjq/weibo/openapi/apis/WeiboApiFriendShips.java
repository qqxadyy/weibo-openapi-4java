package pjq.weibo.openapi.apis;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.TrimStatus;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.http.Response;
import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.UserIdsPager;
import weibo4j.model.UserPager;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;
import weibo4j.org.json.JSONObject;

/**
 * FriendShips相关接口<br>
 * 使用{@code Weibo.of(WeiboApiFriendShips.class,accessToken)}生成对象<br>
 * 1.相关接口好像有如下问题：即使实际有多个对象也只能返回一个对象信息，page、count等参数也控制不了<br>
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiFriendShips extends Weibo<WeiboApiFriendShips> {
    /**
     * 单页返回的记录条数，默认为5，最大不超过5
     */
    private Integer count;

    /**
     * 返回结果的页码，默认为1
     */
    private Integer page;

    /**
     * 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
     */
    private String cursor;

    /**
     * 返回值中user字段中的status字段开关，0：返回完整status字段、1：status字段仅返回status_id，默认为1
     */
    private TrimStatus trimStatus;

    /**
     * 开发者上报的操作用户真实IP
     */
    private String rip;

    /**
     * 根据用户ID获取授权用户的关注列表
     * 
     * @param uid
     *            必须是当前授权的用户ID
     * @return
     * @throws WeiboException
     */
    public UserPager apiGetMyFriendsById(String uid) throws WeiboException {
        return getMyFriends(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称获取授权用户的关注列表
     * 
     * @param screenName
     *            必须是当前授权的用户昵称
     * @return
     * @throws WeiboException
     */
    public UserPager apiGetMyFriendsByScreenName(String screenName) throws WeiboException {
        return getMyFriends(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private UserPager getMyFriends(String uidOrScreenName, String val) throws WeiboException {
        return new UserPager(commonGet(WeiboConfigs.FRIENDSHIPS_FRIENDS, uidOrScreenName, val));
    }

    /**
     * 根据用户ID获取授权用户的关注用户的ID列表
     * 
     * @param uid
     *            必须是当前授权的用户ID
     * @return
     * @throws WeiboException
     */
    public UserIdsPager apiGetMyFriendsIdsById(String uid) throws WeiboException {
        return getMyFriendsIds(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称获取授权用户的关注用户的ID列表
     * 
     * @param screenName
     *            必须是当前授权的用户昵称
     * @return
     * @throws WeiboException
     */
    public UserIdsPager apiGetMyFriendsIdsByScreenName(String screenName) throws WeiboException {
        return getMyFriendsIds(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private UserIdsPager getMyFriendsIds(String uidOrScreenName, String val) throws WeiboException {
        return new UserIdsPager(commonGet(WeiboConfigs.FRIENDSHIPS_FRIENDS_IDS, uidOrScreenName, val));
    }

    /**
     * 根据用户ID获取授权用户的粉丝列表
     * 
     * @param uid
     *            必须是当前授权的用户ID
     * @return
     * @throws WeiboException
     */
    public UserPager apiGetMyFansById(String uid) throws WeiboException {
        return getMyFans(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称获取授权用户的粉丝列表
     * 
     * @param screenName
     *            必须是当前授权的用户昵称
     * @return
     * @throws WeiboException
     */
    public UserPager apiGetMyFansByScreenName(String screenName) throws WeiboException {
        return getMyFans(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private UserPager getMyFans(String uidOrScreenName, String val) throws WeiboException {
        return new UserPager(commonGet(WeiboConfigs.FRIENDSHIPS_FOLLOWERS, uidOrScreenName, val));
    }

    /**
     * 根据用户ID获取授权用户的粉丝的ID列表
     * 
     * @param uid
     *            必须是当前授权的用户ID
     * @return
     * @throws WeiboException
     */
    public UserIdsPager apiGetMyFansIdsById(String uid) throws WeiboException {
        return getMyFansIds(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称获取授权用户的粉丝的ID列表
     * 
     * @param screenName
     *            必须是当前授权的用户昵称
     * @return
     * @throws WeiboException
     */
    public UserIdsPager apiGetMyFansIdsByScreenName(String screenName) throws WeiboException {
        return getMyFansIds(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private UserIdsPager getMyFansIds(String uidOrScreenName, String val) throws WeiboException {
        return new UserIdsPager(commonGet(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_IDS, uidOrScreenName, val));
    }

    private Response commonGet(String apiName, String uidOrScreenName, String val) throws WeiboException {
        if (CheckUtils.isEmpty(val)) {
            throw WeiboException.ofParamCanNotNull(uidOrScreenName);
        }
        List<PostParameter> paramList = commonParam();
        paramList.add(new PostParameter(uidOrScreenName, val));
        return client.get(WeiboConfigs.getApiUrl(apiName), paramListToArray(paramList), accessToken);
    }

    /**
     * 根据用户ID获取两个用户之间的详细关注关系情况
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public JSONObject apiShowFriendShipBetweenUsersById(String sourceUid, String targetUid) throws WeiboException {
        return showFriendShipBetweenUsers("source_id", sourceUid, "target_id", targetUid);
    }

    /**
     * 根据用户昵称获取两个用户之间的详细关注关系情况
     * 
     * @param screenName
     *            用户昵称
     * @return
     * @throws WeiboException
     */
    public JSONObject apiShowFriendShipBetweenUsersBySereenName(String sourceScreenName, String targetScreenName)
        throws WeiboException {
        return showFriendShipBetweenUsers("source_screen_name", sourceScreenName, "target_screen_name",
            targetScreenName);
    }

    private JSONObject showFriendShipBetweenUsers(String sourceUidOrScreenName, String sourceVal,
        String targetUidOrScreenName, String targetVal) throws WeiboException {
        if (CheckUtils.isEmpty(sourceVal)) {
            throw WeiboException.ofParamCanNotNull(sourceUidOrScreenName);
        }
        if (CheckUtils.isEmpty(targetVal)) {
            throw WeiboException.ofParamCanNotNull(targetUidOrScreenName);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(sourceUidOrScreenName, sourceVal));
        paramList.add(new PostParameter(targetUidOrScreenName, targetVal));
        return client
            .get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_SHOW), paramListToArray(paramList), accessToken)
            .asJSONObject();
    }

    /**
     * 获取用户的活跃粉丝列表
     * 
     * @param uid
     *            必须是当前授权的用户ID
     * @return
     * @throws WeiboException
     */
    public List<User> apiGetActiveFollowers(String uid) throws WeiboException {
        if (CheckUtils.isEmpty(uid)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.UID);
        }
        List<PostParameter> paramList = commonParam();
        paramList.add(new PostParameter(MoreUseParamNames.UID, uid));
        return WeiboResponse.buildList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_FOLLOWERS_ACTIVE),
            paramListToArray(paramList), accessToken), User.class);
    }

    private List<PostParameter> commonParam() {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNotNull(count) && count >= 0) {
            paramList.add(new PostParameter("count", count));
        }
        if (CheckUtils.isNotNull(page) && page >= 1) {
            paramList.add(new PostParameter("page", page));
        } else {
            // friendships类接口有问题，官网没有写page参数，如果不传page参数则接口的记录列表不能正常获取，需要指定默认传1
            paramList.add(new PostParameter("page", 1));
        }
        if (CheckUtils.isNotEmpty(cursor)) {
            paramList.add(new PostParameter("cursor", cursor));
        }
        if (CheckUtils.isNotNull(trimStatus)) {
            paramList.add(new PostParameter("trim_status", trimStatus.value()));
        }
        return paramList;
    }

    /**
     * 根据用户ID关注用户
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public User apiFollowerUserById(String uid) throws WeiboException {
        return followerUser(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称关注用户
     * 
     * @param screenName
     *            用户昵称
     * @return
     * @throws WeiboException
     */
    public User apiFollowerUserByScreenName(String screenName) throws WeiboException {
        return followerUser(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private User followerUser(String uidOrScreenName, String val) throws WeiboException {
        if (CheckUtils.isEmpty(val)) {
            throw WeiboException.ofParamCanNotNull(uidOrScreenName);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(uidOrScreenName, val));
        if (CheckUtils.isNotEmpty(rip)) {
            paramList.add(new PostParameter(MoreUseParamNames.REAL_IP, rip));
        }
        return new User(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_CREATE),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 根据用户ID取关用户
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public User apiUnFollowerUserById(String uid) throws WeiboException {
        return unFollowerUser(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称取关用户
     * 
     * @param screenName
     *            用户昵称
     * @return
     * @throws WeiboException
     */
    public User apiUnFollowerUserByScreenName(String screenName) throws WeiboException {
        return unFollowerUser(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    private User unFollowerUser(String uidOrScreenName, String val) throws WeiboException {
        if (CheckUtils.isEmpty(val)) {
            throw WeiboException.ofParamCanNotNull(uidOrScreenName);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(uidOrScreenName, val));
        return new User(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.FRIENDSHIPS_DESTROY),
            paramListToArray(paramList), accessToken));
    }
}