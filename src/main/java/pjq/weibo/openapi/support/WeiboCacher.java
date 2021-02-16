package pjq.weibo.openapi.support;

import java.time.Duration;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.WeiboConfiguration;
import pjq.weibo.openapi.apis.WeiboApiUsers;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.DateTimeUtils;
import pjq.weibo.openapi.utils.collection.CollectionUtils;
import weibo4j.Weibo;
import weibo4j.model.*;

/**
 * 微博缓存业务类
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeiboCacher {
    private static WeiboCacheHandler cacheHandler = WeiboCacheHandler.getInstance();

    public static final String COMMON_PREFIX = "weibo:";
    public static final String KEY_PREFIX_ACCESS_TOKEN = COMMON_PREFIX + "accessTokens:";
    public static final String KEY_PREFIX_STATE = COMMON_PREFIX + "states:";
    public static final String KEY_PREFIX_USER = COMMON_PREFIX + "users:";
    public static final Duration EXPIRES_IN_STATE = Duration.ofSeconds(120L); // state缓存有效期为2分钟

    private static String getKey(String prefix, String suffix) {
        if (CheckUtils.isEmpty(suffix)) {
            throw WeiboException.ofParamCanNotNull("缓存key");
        }
        return prefix.concat(DigestUtils.md5Hex(suffix));
    }

    /**
     * 缓存登录授权时的state参数信息(clientId也缓存进去，用于有多个微博应用配置时获取正确的clientId)
     * 
     * @param state
     * @param clientId
     */
    public static void cacheStateInfoOfAuthorize(String state, String clientId) {
        try {
            if (CheckUtils.isEmpty(state)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.STATE);
            } else if (CheckUtils.isEmpty(clientId)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.CLIENT_ID);
            }
            StateClientId stateInfo = new StateClientId(state, clientId);
            cacheHandler.cache(getKey(KEY_PREFIX_STATE, state), JSON.toJSONString(stateInfo),
                EXPIRES_IN_STATE.getSeconds());
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 判断授权回调返回的state是否在缓存中，用于安全验证<br>
     * 验证成功后返回clientId
     * 
     * @param state
     * @return
     */
    public static void existsStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isNotEmpty(state)) {
                String stateInfoStr = cacheHandler.popup(getKey(KEY_PREFIX_STATE, state)); // state的缓存读取成功后马上失效，防止被二次使用
                StateClientId stateInfo = JSON.parseObject(stateInfoStr, StateClientId.class);
                if (state.equals(stateInfo.getState())) {
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new WeiboException("不存在" + MoreUseParamNames.STATE + "信息，该授权回调可能不安全");
        }
    }

    /**
     * 根据state从缓存中获取clientId<br>
     * 使用于不从配置文件读取微博配置的情况(因为授权回调只有state和code参数，但是又不能从配置文件中获取配置值)
     * 
     * @param state
     * @return
     */
    public static String getClientByStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isEmpty(state)) {
                return null;
            }
            String stateInfoStr = cacheHandler.get(getKey(KEY_PREFIX_STATE, state)); // 这里只是获取缓存信息，获取后不要删掉缓存，check方法还要使用
            if (CheckUtils.isEmpty(stateInfoStr)) {
                return null;
            }

            StateClientId stateInfo = JSON.parseObject(stateInfoStr, StateClientId.class);
            if (state.equals(stateInfo.getState())) {
                return stateInfo.getClientId();
            } else {
                return null;
            }
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 移除state的缓存信息
     * 
     * @param state
     */
    public static void removeStateOfAuthorize(String state) {
        try {
            cacheHandler.remove(getKey(KEY_PREFIX_STATE, state));
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 缓存用code换取到的accessToken信息
     * 
     * @param tokenInfo
     * @return
     */
    public static AccessToken cacheAccessToken(AccessToken tokenInfo) {
        try {
            if (CheckUtils.isNull(tokenInfo)) {
                throw WeiboException.ofParamCanNotNull("accessToken对象");
            }
            String accessToken = tokenInfo.getAccessToken();
            cacheHandler.cache(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken), JSON.toJSONString(tokenInfo),
                Long.valueOf(tokenInfo.getExpiresIn()) - 120L); // 接口返回的expires_in减去2分钟，提早让用户知道授权即将失效
            return tokenInfo;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 检查accessToken信息在缓存中，是的话返回该对象，否则抛异常
     * 
     * @param accessToken
     * @return
     */
    public static AccessToken checkAccessTokenExists(String accessToken) {
        try {
            String tokenInfo = cacheHandler.get(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken));
            if (CheckUtils.isEmpty(tokenInfo)) {
                throw new WeiboException(MoreUseParamNames.ACCESS_TOKEN + "[" + accessToken + "]已失效，请重新获取授权");
            }
            AccessToken tokenInCache = JSON.parseObject(tokenInfo, AccessToken.class);
            if (CheckUtils.isNull(tokenInCache) || !accessToken.equals(tokenInCache.getAccessToken())) {
                throw new WeiboException(MoreUseParamNames.ACCESS_TOKEN + "[" + accessToken + "]与缓存不匹配，请重新获取授权");
            }
            return tokenInCache;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 缓存User信息
     * 
     * @param user
     * @return
     */
    public static User cacheUser(User user) {
        try {
            if (CheckUtils.isNull(user)) {
                throw WeiboException.ofParamCanNotNull("user对象");
            }
            try {
                // 如果缓存同一个微博用户，则更新其用户信息并把旧的token信息添加到新的用户对象中
                User userInCache = getUserById(user.getId());
                user.getAccessTokens().addAll(userInCache.getAccessTokens());
            } catch (Exception e) {
                // 表示缓存中没有用户对象
            }
            cacheHandler.cache(getKey(KEY_PREFIX_USER, user.getId()), JSON.toJSONString(user));
            return user;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 通过用户ID从缓存中获取User对象
     * 
     * @param uid
     *            用户ID
     * @return
     */
    public static User getUserById(String uid) {
        try {
            String userInfo = cacheHandler.get(getKey(KEY_PREFIX_USER, uid));
            if (CheckUtils.isEmpty(userInfo)) {
                throw new WeiboException("用户ID[" + uid + "]已失效，请重新获取授权");
            }
            User userInCache = JSON.parseObject(userInfo, User.class);
            if (CheckUtils.isNull(userInCache) || !uid.equals(userInCache.getId())) {
                throw new WeiboException("用户ID[" + uid + "]与缓存不匹配，请重新获取授权");
            }
            return userInCache;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 通过授权token从缓存中获取User对象
     * 
     * @param accessToken
     *            已授权的token
     * @param weiboConfiguration
     *            微博配置对象，为空时从配置文件加载配置信息；不为空时从该对象加载配置信息(用于缓存中没有用用户信息时先根据token查询一次用户信息再缓存)
     * @return
     */
    public static User getUserByToken(String accessToken, WeiboConfiguration weiboConfiguration) {
        try {
            AccessToken tokenInfo = checkAccessTokenExists(accessToken);
            String uid = tokenInfo.getUid();
            User userInCache = null;
            try {
                userInCache = getUserById(uid);
            } catch (Exception e) {
                // 如果从缓存获取失效，则根据token查询一次用户信息再缓存
                User user = Weibo.of(WeiboApiUsers.class, accessToken, weiboConfiguration).apiShowUserById(uid);
                user.addAccessToken(tokenInfo);
                userInCache = cacheUser(user);
            }
            return userInCache;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 根据token移除缓存的授权信息
     * 
     * @param accessToken
     */
    public static void removeCachesByTokenWhenRevokeOAuth(String accessToken) {
        try {
            AccessToken tokenInfo = checkAccessTokenExists(accessToken);
            cacheHandler.remove(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken)); // 清除缓存的token信息
            User userInCache = getUserById(tokenInfo.getUid());
            CollectionUtils.forEach(userInCache.getAccessTokens(), tokenInfoInUser -> {
                if (accessToken.equals(tokenInfoInUser.getAccessToken())) {
                    tokenInfoInUser.setAuthEnd(DateTimeUtils.currentDateObj());
                    tokenInfoInUser.setExpiresIn("0");
                }
            });
            cacheUser(userInCache);
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }
}