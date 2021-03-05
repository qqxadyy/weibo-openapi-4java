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