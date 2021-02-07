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
     * 缓存登录授权时的state参数
     * 
     * @param state
     */
    public static void cacheStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isEmpty(state)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.STATE);
            }
            cacheHandler.cache(getKey(KEY_PREFIX_STATE, state), state, EXPIRES_IN_STATE.getSeconds());
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 判断授权回调返回的state是否在缓存中，用于安全验证
     * 
     * @param state
     * @return
     */
    public static boolean existsStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isEmpty(state)) {
                return false;
            }
            return cacheHandler.exists(getKey(KEY_PREFIX_STATE, state));
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
     * 缓存用code换取到的accessToken并通过该token获取到的User信息
     * 
     * @param user
     * @param tokenInfo
     * @return
     */
    public static User cacheUser(User user) {
        try {
            if (CheckUtils.isNull(user)) {
                throw WeiboException.ofParamCanNotNull("user对象");
            }
            AccessToken tokenInfo = user.getAccessToken();
            if (CheckUtils.isNull(tokenInfo)) {
                throw WeiboException.ofParamCanNotNull("accessToken对象");
            }
            Long expiresInSeconds = Long.valueOf(tokenInfo.getExpiresIn()) - 120L; // 接口返回的expires_in减去2分钟，提早让用户知道授权即将失效
            cacheHandler.cache(getKey(KEY_PREFIX_USER, user.getId()), JSON.toJSONString(user), expiresInSeconds);
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
     * 更新缓存中的User对象
     * 
     * @param user
     * @return
     */
    public static User updateUser(User user) {
        try {
            if (CheckUtils.isNull(user)) {
                throw WeiboException.ofParamCanNotNull("user对象");
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
                User user = Weibo.of(WeiboApiUsers.class, accessToken).apiShowUserById(uid);
                user.setAccessToken(tokenInfo);
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
            disActiveUserAccessToken(tokenInfo.getUid());
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    /**
     * 根据用户ID移除缓存的授权信息
     * 
     * @param uid
     */
    public static void removeCachesByUidWhenRevokeOAuth(String uid) {
        try {
            User userInCache = disActiveUserAccessToken(uid);
            String accessToken = userInCache.getAccessToken().getAccessToken();
            cacheHandler.remove(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken)); // 清除缓存的token信息
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException(e);
            }
        }
    }

    private static User disActiveUserAccessToken(String uid) {
        User userInCache = getUserById(uid);
        userInCache.getAccessToken().setActive(false); // 不清除缓存的授权用户信息，只把其对应的token状态设成失效
        userInCache.getAccessToken().setAuthEnd(DateTimeUtils.currentDateObj());
        userInCache.getAccessToken().setExpiresIn("0");
        userInCache.getAccessToken().setExpiresInDays(0);
        return updateUser(userInCache);
    }
}