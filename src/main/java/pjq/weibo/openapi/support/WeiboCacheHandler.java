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
 * The MIT License
 * Copyright © 2021 pengjianqiang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pjq.weibo.openapi.support;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

import pjq.commons.utils.CheckUtils;
import weibo4j.model.WeiboException;

/**
 * 微博相关缓存处理器父类<br>
 * 已有用本地内存实现的默认类，如果需要自行实现缓存的部分，需要继承该父类，并只实现相关方法<br>
 * 实现相关方法时，记得先使用{@link #checkKey}或{@link #checkKeyAndValue}检查key、value
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
     * 返回微博缓存处理器的实例<br>
     * 如果有调用{@link WeiboImplRegister#registCacheHandler(WeiboCacheHandler)}方法，则返回其参数对象；否则返回SDK默认的缓存实现
     * 
     * @return
     */
    public static WeiboCacheHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    protected void checkKey(String key) {
        if (CheckUtils.isEmpty(key)) {
            throw WeiboException.ofParamCanNotNull("key");
        }
    }

    protected void checkKeyAndValue(String key, String value) {
        if (!CheckUtils.areNotEmpty(key, value)) {
            throw WeiboException.ofParamCanNotNull("key和value");
        }
    }

    /**
     * 根据key缓存value，缓存不过期<br>
     * 注意:如果已有缓存值且缓存有过期时间，则更新缓存值时要继续保留过期时间，不能清空该时间
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
     * @param duration
     *            key的有效时间，为空时表示不过期
     * @throws WeiboException
     */
    public void cache(String key, String value, Duration duration) throws WeiboException {
        if (CheckUtils.isNull(duration)) {
            duration = Duration.ofSeconds(0L);
        }
        cache(key, value, duration.getSeconds());
    }

    /**
     * 根据key缓存value
     * 
     * @param key
     *            缓存key
     * @param value
     *            缓存值
     * @param expiresInSeconds
     *            key的有效时间，单位秒，为0时表示不过期
     * @throws WeiboException
     */
    public abstract void cache(String key, String value, long expiresInSeconds) throws WeiboException;

    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     * @throws WeiboException
     */
    public abstract boolean exists(String key) throws WeiboException;

    /**
     * 从缓存中读取值，key不存在时返回null
     * 
     * @param key
     * @return
     * @throws WeiboException
     */
    public abstract String get(String key) throws WeiboException;

    /**
     * 根据key删除缓存
     * 
     * @param key
     * @throws WeiboException
     */
    public abstract void remove(String key) throws WeiboException;

    /**
     * 根据key删除缓存，并返回被删除的缓存值
     * 
     * @param key
     * @throws WeiboException
     */
    public abstract String popup(String key) throws WeiboException;

    private static class DefaultWeiboCacheHandler extends WeiboCacheHandler {
        private static final Duration NO_EXPIRES = Duration.ofNanos(Long.MAX_VALUE);
        private static final Map<String, Long> expiresMap = new ConcurrentHashMap<>();

        private static Cache<String, String> cache =
            Caffeine.newBuilder().initialCapacity(300).maximumSize(1000).expireAfter(new Expiry<String, String>() {
                @Override
                public long expireAfterCreate(@NonNull String key, @NonNull String value, long currentTime) {
                    Duration expireDuration =
                        expiresMap.containsKey(key) ? Duration.ofSeconds(expiresMap.get(key)) : NO_EXPIRES;
                    if (expireDuration.compareTo(Duration.ofSeconds(0L)) <= 0
                        || expireDuration.compareTo(NO_EXPIRES) > 0) {
                        expireDuration = NO_EXPIRES;
                    }
                    expiresMap.remove(key);
                    return expireDuration.toNanos(); // 注意方法要求返回的是nanoseconds(微毫秒)单位
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
            cache(key, value, 0);
        }

        @Override
        public void cache(String key, String value, long expiresInSeconds) throws WeiboException {
            // 由于默认使用的Caffeine缓存是在builder中设置有效期策略，需要expiresMap把有效期带进expireXXX方法中
            checkKeyAndValue(key, value);
            expiresMap.put(key, expiresInSeconds);
            cache.put(key, value); // put实际上Caffeine内部会判断新建还是更新key
        }

        @Override
        public boolean exists(String key) throws WeiboException {
            checkKey(key);
            return CheckUtils.isNotEmpty(get(key));
        }

        @Override
        public String get(String key) throws WeiboException {
            checkKey(key);
            return cache.getIfPresent(key);
        }

        @Override
        public void remove(String key) throws WeiboException {
            checkKey(key);
            cache.invalidate(key);
        }

        @Override
        public String popup(String key) throws WeiboException {
            String cacheValue = get(key);
            remove(key);
            return cacheValue;
        }
    }
}