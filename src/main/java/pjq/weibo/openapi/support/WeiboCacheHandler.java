package pjq.weibo.openapi.support;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.model.AccessToken;
import weibo4j.model.WeiboException;

/**
 * 微博相关缓存处理器父类<br/>
 * 已有用本地内存实现的默认类，如果需要自行实现缓存的部分，需要继承该父类，并只实现抽象方法即可(尽量不要重写其它非抽象方法)
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
public abstract class WeiboCacheHandler {
    private static WeiboCacheHandler customInstance;

    private static class InstanceHolder {
        private static WeiboCacheHandler INSTANCE =
            (null != customInstance ? customInstance : new DefaultWeiboCacheHandler());
    }

    /**
     * 返回微博缓存处理器的实例<br/>
     * 如果有调用{@link WeiboImplRegister#registCacheHandler(WeiboCacheHandler)}方法，则返回其参数对象；否则返回SDK默认的缓存实现
     * 
     * @return
     */
    public static WeiboCacheHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String COMMON_PREFIX = "weibo:";
    private static final String KEY_PREFIX_ACCESS_TOKEN = COMMON_PREFIX + "accessTokens:";
    private static final String KEY_PREFIX_STATE = COMMON_PREFIX + "states:";
    private static final Duration EXPIRES_IN_STATE = Duration.ofSeconds(120L); // state缓存有效期为2分钟

    private String getKey(String prefix, String suffix) {
        return prefix.concat(DigestUtils.md5Hex(suffix));
    }

    /**
     * 缓存登录授权时的state参数
     * 
     * @param state
     */
    public void cacheStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isEmpty(state)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.STATE);
            }
            cache(getKey(KEY_PREFIX_STATE, state), state, EXPIRES_IN_STATE.getSeconds());
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
    public boolean existsStateOfAuthorize(String state) {
        try {
            if (CheckUtils.isEmpty(state)) {
                return false;
            }
            return existsKey(getKey(KEY_PREFIX_STATE, state));
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
    public AccessToken cacheAccessToken(AccessToken tokenInfo) {
        try {
            if (CheckUtils.isNull(tokenInfo)) {
                throw WeiboException.ofParamCanNotNull("accessToken对象");
            }
            String accessToken = tokenInfo.getAccessToken();
            cache(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken), JSON.toJSONString(tokenInfo),
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
     * 检查accessToken信息在缓存中
     * 
     * @param accessToken
     * @return
     */
    public AccessToken checkAccessTokenExists(String accessToken) {
        try {
            if (CheckUtils.isEmpty(accessToken)) {
                throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
            }
            String tokenInfo = getFromCache(getKey(KEY_PREFIX_ACCESS_TOKEN, accessToken));
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
     * 根据key缓存value
     * 
     * @param key
     *            缓存key
     * @param value
     *            缓存值
     * @throws WeiboException
     */
    public abstract void cache(String key, String value) throws WeiboException;

    /**
     * 根据key缓存value
     * 
     * @param key
     *            缓存key
     * @param value
     *            缓存值
     * @param expiresInSeconds
     *            key的有效时间，单位秒
     * @throws WeiboException
     */
    public abstract void cache(String key, String value, Long expiresInSeconds) throws WeiboException;

    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     * @throws WeiboException
     */
    public abstract boolean existsKey(String key) throws WeiboException;

    /**
     * 从缓存中读取值，key不存在时返回null
     * 
     * @param key
     * @return
     * @throws WeiboException
     */
    public abstract String getFromCache(String key) throws WeiboException;

    private static class DefaultWeiboCacheHandler extends WeiboCacheHandler {
        private static final Duration NO_EXPIRES = Duration.ofDays(300L);
        private static final Map<String, Long> expiresMap = new ConcurrentHashMap<>();

        private static Cache<String, String> cache =
            Caffeine.newBuilder().initialCapacity(100).maximumSize(500).expireAfter(new Expiry<String, String>() {
                @Override
                public long expireAfterCreate(@NonNull String key, @NonNull String value, long currentTime) {
                    Long nanoExpires =
                        (expiresMap.containsKey(key) ? Duration.ofSeconds(expiresMap.get(key)) : NO_EXPIRES).toNanos();
                    expiresMap.remove(key);
                    return nanoExpires; // 注意方法要求返回的时nanoseconds(微毫秒)单位
                }

                @Override
                public long expireAfterUpdate(@NonNull String key, @NonNull String value, long currentTime,
                    @NonNegative long currentDuration) {
                    return currentDuration; // 更新缓存后的过期时间直接返回currentDuration即可，表示按当前剩下的缓存时间
                }

                @Override
                public long expireAfterRead(@NonNull String key, @NonNull String value, long currentTime,
                    @NonNegative long currentDuration) {
                    if (key.startsWith(KEY_PREFIX_STATE)) {
                        return 0L; // state的缓存读取后马上失效，防止被二次使用
                    } else {
                        return currentDuration; // 读取缓存后的过期时间直接返回currentDuration即可，表示按当前剩下的缓存时间
                    }
                }
            }).build();

        @Override
        public void cache(String key, String value) throws WeiboException {
            cache(key, value, NO_EXPIRES.getSeconds());
        }

        @Override
        public void cache(String key, String value, Long expiresInSeconds) throws WeiboException {
            // 由于默认使用的Caffeine缓存是在builder中设置有效期策略，需要expiresMap把有效期带进expireXXX方法中
            expiresMap.put(key, expiresInSeconds);
            cache.put(key, value);
        }

        @Override
        public boolean existsKey(String key) throws WeiboException {
            return CheckUtils.isNotEmpty(getFromCache(key));
        }

        @Override
        public String getFromCache(String key) throws WeiboException {
            return cache.getIfPresent(key);
        }
    }
}