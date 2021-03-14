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
 * src/main/java/weibo4j下的文件是从weibo4j-oauth2-beta3.1.1.zip中复制出来的
 * 本项目对这个目录下的部分源码做了重新改造
 * 但是许可信息和"https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1"或源码中已存在的保持一致
 */
package weibo4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.StreamUtils;
import pjq.weibo.openapi.WeiboConfiguration;
import pjq.weibo.openapi.apis.WeiboApiOauth2;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboApiParamScope;
import pjq.weibo.openapi.support.WeiboCacher;
import weibo4j.http.HttpClientNew;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;

/**
 * 扩展旧版的该类<br/>
 * 1.每个接口的通用参数有accessToken；clientId等参数从对应参数名的方法中获取<br/>
 * 2.每个接口本身的可选参数在接口类中的属性变量中，并使用链式调用方式设置参数值，设置值后再调用api方法(接口类中的apiXXX方法)<br/>
 * 3.可选参数是每个方法都可共用，但实际是否会生效需要参考官网的API文档<br/>
 * 4.每个接口本身的必填参数定义在具体的方法中<br/>
 * 
 * @author pengjianqiang
 * @date 2021年1月24日
 * @see https://open.weibo.com/wiki/首页
 */
@Getter(AccessLevel.PROTECTED)
@Accessors(fluent = true)
public abstract class Weibo<T> implements java.io.Serializable {
    private static final long serialVersionUID = 4282616848978535016L;
    private static final String TOKEN_AND_CLIENTID_SEPERATOR = "withClientId:";

    protected static HttpClientNew client = new HttpClientNew();

    // 如果希望自己设置HttpClient的各种参数，可以使用下面的构造方法
    // protected static HttpClient client = new HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs,
    // int maxSize);

    /**
     * 微博配置对象，为空时从配置文件加载配置信息；不为空时从该对象加载配置信息
     */
    private WeiboConfiguration weiboConfiguration;

    /**
     * 授权后的token
     */
    @WeiboApiParamScope(WeiboApiParamScope.ALL)
    protected String accessToken;

    /**
     * 采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的clientId/AppKey<br>
     * 默认为false，为true时直接从{@link #clientId()}获取值参数值
     */
    @WeiboApiParamScope(WeiboApiParamScope.SOURCE)
    protected boolean useSource;

    /**
     * 用于{@link #of}方法生成对象后，单独改变weiboConfiguration
     * 
     * @param weiboConfiguration
     *            微博配置对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public T weiboConfiguration(WeiboConfiguration weiboConfiguration) {
        this.weiboConfiguration = weiboConfiguration;
        return (T)this;
    }

    /**
     * 用于{@link #of}方法生成对象后，单独改变accessToken
     * 
     * @param accessToken
     *            授权后的token
     * @return
     */
    @SuppressWarnings("unchecked")
    @WeiboApiParamScope(WeiboApiParamScope.ALL)
    public T accessToken(String accessToken) {
        this.accessToken = accessToken;
        return (T)this;
    }

    /**
     * 获取accessToken值<br>
     * 为适应部分接口要传source参数，而不改动{@link HttpClientNew}的post等方法中只传入token的情况，在这里获取token值时做特殊处理
     * 
     * @return
     * @creator pengjianqiang@2021年3月14日
     */
    protected String accessToken() {
        String result = accessToken;
        if (useSource) {
            result += TOKEN_AND_CLIENTID_SEPERATOR + clientId();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @WeiboApiParamScope(WeiboApiParamScope.SOURCE)
    public T useSource(boolean useSource) {
        this.useSource = useSource;
        return (T)this;
    }

    /**
     * 判断是否从配置文件中获取微博配置信息
     * 
     * @return
     */
    protected boolean isLoadConfigFromProperty() {
        return CheckUtils.isNull(weiboConfiguration);
    }

    /**
     * 获取clientId配置
     * 
     * @return
     */
    protected String clientId() {
        return isLoadConfigFromProperty() ? WeiboConfigs.getClientIdFromProperty() : weiboConfiguration.clientId();
    }

    /**
     * 创建clientSecret配置
     * 
     * @return
     */
    protected String clientSecret() {
        return isLoadConfigFromProperty() ? WeiboConfigs.getClientSecretFromProperty()
            : weiboConfiguration.clientSecret();
    }

    /**
     * 获取redirectUri配置
     * 
     * @return
     */
    protected String redirectURI() {
        return isLoadConfigFromProperty() ? WeiboConfigs.getRedirectURIFromProperty()
            : weiboConfiguration.redirectUri();
    }

    /**
     * 获取创建应用时分配的AppKey
     * 
     * @return
     */
    protected List<String> safeDomains() {
        return Arrays.asList(
            (isLoadConfigFromProperty() ? WeiboConfigs.getSafeDomainsFromProperty() : weiboConfiguration.safeDomains())
                .split(","));
    }

    /**
     * 调用完{@link Weibo#of}方法后需要另外做其它处理的方法
     * 
     * @param accessToken
     *            授权后的token
     * @param clientId
     *            申请应用时分配的AppKey
     */
    protected void afterOfInit(String accessToken, String clientId) {}

    /**
     * 接口类是否需要检查access_token参数，需要检查的话返回参数名<br/>
     * 默认需要检查，且返回{@link MoreUseParamNames#ACCESS_TOKEN}<br/>
     * 具体接口类重写该方法即可改变检查逻辑
     * 
     * @return
     */
    protected String checkAccessToken() {
        return MoreUseParamNames.ACCESS_TOKEN;
    }

    /**
     * 接口类是否需要检查client_id/source/appkey参数，需要检查的话返回参数名<br/>
     * 默认不需要检查<br/>
     * 具体接口类重写该方法即可改变检查逻辑
     * 
     * @return
     */
    protected String checkClientId() {
        return null;
    }

    /**
     * 把字符串的数组参数用","连接
     * 
     * @param vals
     * @return
     */
    protected String joinArrayParam(String... vals) {
        if (CheckUtils.isEmpty(vals)) {
            return "";
        }
        return StreamUtils.joinString(Arrays.stream(vals), ",");
    }

    protected List<PostParameter> newParamList() {
        return new ArrayList<>();
    }

    /**
     * 参数list转成array
     * 
     * @param paramList
     * @return
     */
    protected PostParameter[] paramListToArray(List<PostParameter> paramList) {
        return paramList.toArray(new PostParameter[] {});
    }

    /**
     * 构造具体业务接口类(不需要token，从配置文件读取微博配置)<br>
     * 应该只有OAuth接口类会用到
     * 
     * @param apiClass
     *            业务接口类
     * @return
     */
    public static <T extends Weibo<T>> T of(Class<T> apiClass) {
        return of(apiClass, null, null);
    }

    /**
     * 构造具体业务接口类(不需要token，从配置文件读取微博配置)<br>
     * 应该只有OAuth接口类会用到
     * 
     * @param apiClass
     *            业务接口类
     * @param weiboConfiguration
     *            微博配置对象，为空时从配置文件加载配置信息；不为空时从该对象加载配置信息
     * @return
     */
    public static <T extends Weibo<T>> T of(Class<T> apiClass, WeiboConfiguration weiboConfiguration) {
        return of(apiClass, null, weiboConfiguration);
    }

    /**
     * 构造具体业务接口类(需要token，从配置文件读取微博配置)
     * 
     * @param apiClass
     *            业务接口类
     * @param accessToken
     *            授权后的token
     * @param needClientId
     * 
     * @return
     */
    public static <T extends Weibo<T>> T of(Class<T> apiClass, String accessToken) {
        return of(apiClass, accessToken, null);
    }

    /**
     * 构造具体业务接口类(需要token，从配置对象读取微博配置)
     * 
     * @param apiClass
     *            业务接口类
     * @param accessToken
     *            授权后的token
     * @param weiboConfiguration
     *            微博配置对象，为空时从配置文件加载配置信息；不为空时从该对象加载配置信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Weibo<T>> T of(Class<T> apiClass, String accessToken,
        WeiboConfiguration weiboConfiguration) {
        try {
            if (!WeiboApiOauth2.class.equals(apiClass)) {
                // WeiboApiOauth2不需要检查
                checkCanConnectToAPI();
            }

            // 接口实现类只有一个private的无参构造方法
            Constructor<?> privateConstructor = apiClass.getDeclaredConstructors()[0];
            privateConstructor.setAccessible(true);
            T obj = (T)privateConstructor.newInstance();

            String checkAccessTokenName = obj.checkAccessToken();
            if (CheckUtils.isNotEmpty(checkAccessTokenName) && CheckUtils.isEmpty(accessToken)) {
                throw WeiboException.ofParamCanNotNull(checkAccessTokenName);
            }

            // 默认把应用client_id/source/appkey值设到接口类中，实际是否使用和检查根据接口类决定
            String clientId = CheckUtils.isNull(weiboConfiguration) ? WeiboConfigs.getClientIdFromProperty()
                : weiboConfiguration.clientId();
            String checkClientIdName = obj.checkClientId();
            if (CheckUtils.isNotEmpty(checkClientIdName) && CheckUtils.isEmpty(clientId)) {
                throw WeiboException.ofParamCanNotNull(checkClientIdName);
            }

            if (CheckUtils.isNotEmpty(accessToken) && !WeiboConfigs.isDebugMode()) {
                // 初始化接口对象时检查accessToken是否已失效
                WeiboCacher.checkAccessTokenExists(accessToken);
            }

            obj.accessToken(accessToken);
            obj.weiboConfiguration(weiboConfiguration);
            obj.afterOfInit(accessToken, clientId);
            return obj;
        } catch (Exception e) {
            if (e instanceof WeiboException) {
                throw (WeiboException)e;
            } else {
                throw new WeiboException("初始化微博接口对象[" + apiClass.getName() + "]时报错", e);
            }
        }
    }

    /**
     * 检查是否能连通微博API的网络<br/>
     * 暂时只会检查基本API的网络
     * 
     * @throws Exception
     */
    private static void checkCanConnectToAPI() throws Exception {
        String apiBase = WeiboConfigs.getApiUrl(WeiboConfigs.BASE_URL_COMMON);
        URLConnection url = new URL(apiBase).openConnection();
        url.setConnectTimeout(3000); // 3秒超时
        try (InputStream is = url.getInputStream()) {
        } catch (IOException e) {
            throw new WeiboException("微博API接口网络[" + apiBase + "]不可用，请检查网络或稍后再试");
        }
    }

    /**
     * 获取特殊处理的token+clientId中的token值
     * 
     * @param accessToken
     * @return
     * @creator pengjianqiang@2021年3月14日
     */
    public static String splitAccessTokenToToken(String accessToken) {
        if (CheckUtils.isEmpty(accessToken)) {
            return accessToken;
        }
        return accessToken.split(TOKEN_AND_CLIENTID_SEPERATOR)[0];
    }

    /**
     * 获取特殊处理的token+clientId中的clientId值
     * 
     * @param accessToken
     * @return
     * @creator pengjianqiang@2021年3月14日
     */
    public static String splitAccessTokenToClientId(String accessToken) {
        if (CheckUtils.isEmpty(accessToken)) {
            return "";
        }
        String[] info = accessToken.split(TOKEN_AND_CLIENTID_SEPERATOR);
        return info.length > 1 ? info[1] : "";
    }
}