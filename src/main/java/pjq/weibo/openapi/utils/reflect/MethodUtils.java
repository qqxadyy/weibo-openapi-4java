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
package pjq.weibo.openapi.utils.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 * -{@link Method}工具类<br/>
 * <p>
 * Create at 2018年12月17日
 * 
 * @author pengjianqiang
 */
public final class MethodUtils {
    private MethodUtils() {}

    /**
     * -调用类/接口的静态或默认方法(只适用于无参方法，有参方法需要使用另一个{@link #invokeStaticOrDefault(Class, Method, Object...)}方法)
     * 
     * @param clazz
     * @param noArgsMethodName
     * @param args
     * @return
     * @throws Throwable
     */
    public static Object invokeStaticOrDefault(Class<?> clazz, String noArgsMethodName) throws Throwable {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(noArgsMethodName);
        } catch (NoSuchMethodException e) {
            throw e;
        }
        return invokeStaticOrDefault(clazz, method, (Object[])null);
    }

    /**
     * -调用类/接口的静态或默认方法(有参或无参方法都适用)
     * 
     * @param clazz
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public static Object invokeStaticOrDefault(Class<?> clazz, Method method, Object... args) throws Throwable {
        if (method.isDefault()) {
            // 默认方法不能直接method.invoke
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return null; // 不用实际实现
                }
            };
            Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, handler);
            Constructor<MethodHandles.Lookup> constructor =
                MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);

            int allModes = MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                | MethodHandles.Lookup.PACKAGE;

            return constructor.newInstance(clazz, allModes).unreflectSpecial(method, clazz).bindTo(proxy)
                .invokeWithArguments(args);
        } else {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(null, args);
        }
    }
}