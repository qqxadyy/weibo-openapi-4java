package pjq.weibo.openapi.support;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.utils.reflect.MethodUtils;
import weibo4j.http.HttpClientNew;
import weibo4j.model.WeiboException;

/**
 * 用于替换jar包中的默认实现类，要替换时在应用启动完成后马上调用相关方法即可
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeiboImplRegister {
    /**
     * 注册一个新的http请求实现类，替换默认的okhttp实现
     * 
     * @param newWeiboClient
     */
    public static void registWeiboClient(WeiboHttpClient newWeiboClient) throws WeiboException {
        try {
            MethodUtils.invokeStaticOrDefault(HttpClientNew.class,
                HttpClientNew.class.getDeclaredMethod("initNewWeiboClient", WeiboHttpClient.class), newWeiboClient);
        } catch (Throwable e) {
            throw new WeiboException("注册新的微博HTTP请求实现时报错：" + ExceptionUtils.getRootCauseMessage(e));
        }
    }
}