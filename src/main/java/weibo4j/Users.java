package weibo4j;

import java.util.List;

import pjq.weibo.openapi.apis.WeiboApiUsers;
import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.UserCounts;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;

/**
 * 和新版参数有出入，用{@link WeiboApiUsers}代替
 * 
 * @author pengjianqiang
 * @date 2021年1月24日
 * @see WeiboApiUsers
 */
@Deprecated
public class Users extends Weibo {

    private static final long serialVersionUID = 4742830953302255953L;

    public Users(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------用户接口----------------------------------------*/
    /**
     * 根据用户ID获取用户信息
     * 
     * @param uid
     *            需要查询的用户ID
     * @return User
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/users/show
     * @since JDK 1.5
     */
    public User showUserById(String uid) throws WeiboException {
        return new User(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_SHOW),
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken).asJSONObject());
    }

    /**
     * 根据用户昵称获取用户信息
     * 
     * @param screen_name
     *            用户昵称
     * @return User
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/users/show
     * @since JDK 1.5
     */
    public User showUserByScreenName(String screen_name) throws WeiboException {
        return new User(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_SHOW),
            new PostParameter[] {new PostParameter("screen_name", screen_name)}, accessToken).asJSONObject());
    }

    /**
     * 通过个性化域名获取用户资料以及用户最新的一条微博
     * 
     * @param domain
     *            需要查询的个性化域名。
     * @return User
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/users/domain_show
     * @since JDK 1.5
     */
    public User showUserByDomain(String domain) throws WeiboException {
        return new User(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_DOMAIN_SHOW),
            new PostParameter[] {new PostParameter("domain", domain)}, accessToken).asJSONObject());
    }

    /**
     * 批量获取用户的粉丝数、关注数、微博数
     * 
     * @param uids
     *            需要获取数据的用户UID，多个之间用逗号分隔，最多不超过100个
     * @return jsonobject
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/users/domain_show
     * @since JDK 1.5
     */
    public List<UserCounts> getUserCount(String uids) throws WeiboException {
        return WeiboResponse.buildList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_COUNTS),
            new PostParameter[] {new PostParameter("uids", uids)}, accessToken), UserCounts.class);
    }
}
