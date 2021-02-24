package pjq.weibo.openapi.support;

import java.lang.reflect.Field;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weibo4j.model.WeiboException;

/**
 * 用于替换jar包中的默认实现类，要替换时在应用启动完成后马上调用相关方法即可
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class WeiboImplRegister {
    /**
     * 注册一个新的http请求实现类，替换默认的okhttp实现
     * 
     * @param customInstance
     * @throws WeiboException
     */
    public static void registWeiboClient(WeiboHttpClient customInstance) throws WeiboException {
        try {
            setStaticValue(WeiboHttpClient.class.getDeclaredField("customInstance"), customInstance);
            log.info("注册新的微博HTTP请求实现[" + customInstance.getClass() + "]");
        } catch (Throwable e) {
            throw new WeiboException("注册新的微博HTTP请求实现时报错：" + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    /**
     * 注册一个新的缓存实现类，替换默认的本地缓存实现
     * 
     * @param customInstance
     * @throws WeiboException
     */
    public static void registCacheHandler(WeiboCacheHandler customInstance) throws WeiboException {
        try {
            setStaticValue(WeiboCacheHandler.class.getDeclaredField("customInstance"), customInstance);
            log.info("注册新的微博缓存实现[" + customInstance.getClass() + "]");
        } catch (Throwable e) {
            throw new WeiboException("注册新的微缓存实现时报错：" + ExceptionUtils.getRootCauseMessage(e));
        }
    }

    private static void setStaticValue(Field field, Object value)
        throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(null, value);
    }
}