package pjq.weibo.openapi.support;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.model.WeiboException;

/**
 * 微博相关缓存处理器父类<br/>
 * 已有用本地内存实现的默认类，如果需要自行实现缓存的部分，需要继承该父类，并只实现相关方法
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
            Caffeine.newBuilder().initialCapacity(300).maximumSize(1000).expireAfter(new Expiry<String, String>() {
                @Override
                public long expireAfterCreate(@NonNull String key, @NonNull String value, long currentTime) {
                    Duration expireDuration =
                        expiresMap.containsKey(key) ? Duration.ofSeconds(expiresMap.get(key)) : NO_EXPIRES;
                    if (expireDuration.compareTo(NO_EXPIRES) > 0) {
                        expireDuration = NO_EXPIRES; // 如果过期时间超过300天，则按300天设置，避免缓存太久(开发者获取到的授权有效期是5年)
                    }
                    expiresMap.remove(key);
                    return expireDuration.toNanos(); // 注意方法要求返回的时nanoseconds(微毫秒)单位
                }

                @Override
                public long expireAfterUpdate(@NonNull String key, @NonNull String value, long currentTime,
                    @NonNegative long currentDuration) {
                    return currentDuration; // 更新缓存后的过期时间直接返回currentDuration即可，表示按当前剩下的缓存时间
                }

                @Override
                public long expireAfterRead(@NonNull String key, @NonNull String value, long currentTime,
                    @NonNegative long currentDuration) {
                    if (key.startsWith(WeiboCacher.KEY_PREFIX_STATE)) {
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