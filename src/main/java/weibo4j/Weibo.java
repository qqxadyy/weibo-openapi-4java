package weibo4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboCacheHandler;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.StreamUtils;
import weibo4j.http.HttpClientNew;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;

/**
 * 扩展旧版的该类<br/>
 * 1.每个接口的通用参数有accessToken、clientId<br/>
 * 2.每个接口本身的可选参数在接口类中的属性变量中，并使用链式调用方式设置参数值，设置值后再调用api方法(接口类中的apiXXX方法)<br/>
 * 3.可选参数是每个方法都可共用，但实际是否会生效需要参考官网的API文档<br/>
 * 4.每个接口本身的必填参数定义在具体的方法中<br/>
 * 
 * @author pengjianqiang
 * @date 2021年1月24日
 * @see https://open.weibo.com/wiki/首页
 */
@Getter
@Accessors(fluent = true)
public abstract class Weibo<T> implements java.io.Serializable {
    private static final long serialVersionUID = 4282616848978535016L;

    protected static WeiboCacheHandler cacheHandler = WeiboCacheHandler.getInstance();
    protected static HttpClientNew client = new HttpClientNew();

    // 如果希望自己设置HttpClient的各种参数，可以使用下面的构造方法
    // protected static HttpClient client = new HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs,
    // int maxSize);

    /**
     * 授权后的token
     */
    protected String accessToken;

    /**
     * 申请应用时分配的AppKey
     */
    protected String clientId;

    /**
     * 用于{@link #of}方法生成对象后，单独改变accessToken
     * 
     * @param accessToken
     *            授权后的token
     * @return
     */
    @SuppressWarnings("unchecked")
    public T accessToken(String accessToken) {
        this.accessToken = accessToken;
        return (T)this;
    }

    /**
     * 用于{@link #of}方法生成对象后，单独改变clientId(一般不需要改变，接口调用过程中会直接从配置文件中获取)
     * 
     * @param clientId
     *            申请应用时分配的AppKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public T clientId(String clientId) {
        this.clientId = clientId;
        return (T)this;
    }

    /**
     * 调用完{@link Weibo#of}方法后需要另外做其它处理的方法，且根据具体接口检查accessToken和clientId的为空情况
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
     * 检查推送的微博/评论文本内容是否超过字数限制
     * 
     * @param text
     * @return
     * @throws WeiboException
     */
    protected String checkPostTextAndReturn(String text) throws WeiboException {
        if (CheckUtils.isEmpty(text)) {
            return text;
        } else if (text.length() > 140) {
            throw new WeiboException("文本内容不能超过140个汉字");
        }

        try {
            // return URLEncoder.encode(text, CharsetUtils.UTF_8); // 官网上说需要做URLEncode，但实际做了返回会乱码，所以去掉(应该是新版有改动)
            return text;
        } catch (Exception e) {
            throw new WeiboException(e);
        }

    }

    /**
     * 构造具体业务接口类(应该只有OAuth接口类会用到)
     * 
     * @param apiClass
     *            业务接口类
     * @return
     */
    public static <T extends Weibo<T>> T of(Class<? extends T> apiClass) {
        return of(apiClass, null);
    }

    /**
     * 构造具体业务接口类
     * 
     * @param apiClass
     *            业务接口类
     * @param accessToken
     *            授权后的token
     * @param needClientId
     * 
     * @return
     */
    public static <T extends Weibo<T>> T of(Class<? extends T> apiClass, String accessToken) {
        try {
            T obj = apiClass.newInstance();
            String checkAccessTokenName = obj.checkAccessToken();
            if (CheckUtils.isNotEmpty(checkAccessTokenName) && CheckUtils.isEmpty(accessToken)) {
                throw WeiboException.ofParamCanNotNull(checkAccessTokenName);
            }

            String clientId = WeiboConfigs.getClientId(); // 默认把应用client_id/source/appkey值设到接口类中，实际是否使用和检查根据接口类决定
            String checkClientIdName = obj.checkClientId();
            if (CheckUtils.isNotEmpty(checkClientIdName) && CheckUtils.isEmpty(clientId)) {
                throw WeiboException.ofParamCanNotNull(checkClientIdName);
            }

            if (CheckUtils.isNotEmpty(accessToken)) {
                // 初始化接口对象时检查accessToken是否已失效
                cacheHandler.checkAccessTokenExists(accessToken);
            }

            obj.accessToken(accessToken);
            obj.clientId(clientId);
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
}