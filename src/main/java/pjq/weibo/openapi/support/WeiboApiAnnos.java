package pjq.weibo.openapi.support;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import pjq.weibo.openapi.constant.WeiboConfigs;

public interface WeiboApiAnnos {
    /**
     * 定义可替换微博API接口路径的配置名
     * 
     * @author pengjianqiang
     * @date 2021年1月20日
     */
    @Retention(RUNTIME)
    @Target(FIELD)
    public @interface WeiboPropName {
        /**
         * 配置项名，为空时使用变量的值代替<br/>
         * (实际配置时要配置成{@link WeiboConfigs.CONFIG_API_PREFIX}的值+value值，例如weibo.api.propName)
         * 
         * @return
         */
        String value() default "";
    }

    /**
     * 定义微博API的类型、URL中的多级前缀及后缀
     * 
     * @author pengjianqiang
     * @date 2021年1月20日
     */
    @Retention(RUNTIME)
    @Target(FIELD)
    public @interface WeiboApi {
        /**
         * 接口URL的前段，默认为BASE_URL
         * 
         * @return
         */
        String baseUrl() default "";

        /**
         * URL中的多级前缀，例如{"search/","statuses/"}表示有三级前缀
         * 
         * @return
         */
        String[] prefixes() default {};

        /**
         * URL接口的后缀，例如.json
         * 
         * @return
         */
        String suffix() default "";
    }
}