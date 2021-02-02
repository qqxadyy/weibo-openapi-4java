package pjq.weibo.openapi.support;

import java.time.Duration;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.model.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

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
            return cacheHandler.existsKey(getKey(KEY_PREFIX_STATE, state));
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
     * 缓存用code换取到的accessToken并通过该token获取到的User信息
     * 
     * @param user
     * @param tokenInfo
     * @return
     */
    public static User cacheUser(User user, AccessToken tokenInfo) {
        try {
            if (CheckUtils.isNull(user)) {
                throw WeiboException.ofParamCanNotNull("user对象");
            }
            if (CheckUtils.isNull(tokenInfo)) {
                throw WeiboException.ofParamCanNotNull("accessToken对象");
            }
            String accessToken = tokenInfo.getAccessToken();
            cacheHandler.cache(getKey(KEY_PREFIX_USER, accessToken), JSON.toJSONString(user),
                Long.valueOf(tokenInfo.getExpiresIn()) - 120L); // 接口返回的expires_in减去2分钟，提早让用户知道授权即将失效
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
     * 从缓存中获取User对象
     * 
     * @param accessToken
     * @return
     */
    public static User getUser(String accessToken) {
        try {
            if (CheckUtils.isEmpty(accessToken)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
            }
            String userInfo = cacheHandler.getFromCache(getKey(KEY_PREFIX_USER, accessToken));
            if (CheckUtils.isEmpty(userInfo)) {
                throw new WeiboException(MoreUseParamNames.ACCESS_TOKEN + "[" + accessToken + "]已失效，请重新获取授权");
            }
            User userInCache = JSON.parseObject(userInfo, User.class);
            if (CheckUtils.isNull(userInCache) || !accessToken.equals(userInCache.getAccessToken())) {
                throw new WeiboException(MoreUseParamNames.ACCESS_TOKEN + "[" + accessToken + "]与缓存不匹配，请重新获取授权");
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
     * 检查accessToken信息在缓存中
     * 
     * @param accessToken
     * @return
     */
    public static AccessToken checkAccessTokenExists(String accessToken) {
        try {
            if (CheckUtils.isEmpty(accessToken)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
            }
            String tokenInfo = cacheHandler.getFromCache(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken));
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
}