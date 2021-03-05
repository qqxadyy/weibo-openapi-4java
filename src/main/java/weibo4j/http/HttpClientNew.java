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
package weibo4j.http;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.support.WeiboHttpClient;
import pjq.weibo.openapi.support.WeiboHttpClient.MethodType;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.http.HttpException;
import pjq.weibo.openapi.utils.http.SimpleAsyncCallback;
import weibo4j.model.Configuration;
import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * 2021年1月19日-二次开发by pengjianqiang<br/>
 * 原始类{@link weibo4j.http.HttpClient}<br/>
 * {@link weibo4j.model.MySSLSocketFactory}也废弃
 * 
 * @author sinaWeibo
 * 
 */
@Slf4j
@NoArgsConstructor
public class HttpClientNew implements java.io.Serializable {

    private static final long serialVersionUID = -176092625883595547L;
    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;// Not Modified: There was no new data to return.
    private static final int BAD_REQUEST = 400;// Bad Request: The request was invalid. An accompanying error message
                                               // will explain why. This is the status code will be returned during rate
                                               // limiting.
    private static final int NOT_AUTHORIZED = 401;// Not Authorized: Authentication credentials were missing or
                                                  // incorrect.
    private static final int FORBIDDEN = 403;// Forbidden: The request is understood, but it has been refused. An
                                             // accompanying error message will explain why.
    private static final int NOT_FOUND = 404;// Not Found: The URI requested is invalid or the resource requested, such
                                             // as a user, does not exists.
    private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by the Search API when an invalid format
                                                  // is specified in the request.
    private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error: Something is broken. Please post to
                                                         // the group so the Weibo team can investigate.
    private static final int BAD_GATEWAY = 502;// Bad Gateway: Weibo is down or being upgraded.
    private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable: The Weibo servers are up, but overloaded
                                                       // with requests. Try again later. The search and trend methods
                                                       // use this to indicate when you are being rate limited.

    private static WeiboHttpClient weiboClient = WeiboHttpClient.getInstance();

    private String proxyHost = Configuration.getProxyHost();
    private int proxyPort = Configuration.getProxyPort();
    private String proxyAuthUser = Configuration.getProxyUser();
    private String proxyAuthPassword = Configuration.getProxyPassword();

    public String getProxyHost() {
        return proxyHost;
    }

    /**
     * Sets proxy host. System property -Dsinat4j.http.proxyHost or http.proxyHost overrides this attribute.
     * 
     * @param proxyHost
     */
    public void setProxyHost(String proxyHost) {
        this.proxyHost = Configuration.getProxyHost(proxyHost);
    }

    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * Sets proxy port. System property -Dsinat4j.http.proxyPort or -Dhttp.proxyPort overrides this attribute.
     * 
     * @param proxyPort
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = Configuration.getProxyPort(proxyPort);
    }

    public String getProxyAuthUser() {
        return proxyAuthUser;
    }

    /**
     * Sets proxy authentication user. System property -Dsinat4j.http.proxyUser overrides this attribute.
     * 
     * @param proxyAuthUser
     */
    public void setProxyAuthUser(String proxyAuthUser) {
        this.proxyAuthUser = Configuration.getProxyUser(proxyAuthUser);
    }

    public String getProxyAuthPassword() {
        return proxyAuthPassword;
    }

    /**
     * Sets proxy authentication password. System property -Dsinat4j.http.proxyPassword overrides this attribute.
     * 
     * @param proxyAuthPassword
     */
    public void setProxyAuthPassword(String proxyAuthPassword) {
        this.proxyAuthPassword = Configuration.getProxyPassword(proxyAuthPassword);
    }

    /**
     * 处理http getmethod 请求
     * 
     */

    public Response get(String url, String token) throws WeiboException {
        return get(url, new PostParameter[0], token);
    }

    public Response get(String url, PostParameter[] params, String token) throws WeiboException {
        return get(url, params, true, token);
    }

    public Response get(String url, PostParameter[] params, Boolean WithTokenHeader, String token)
        throws WeiboException {
        return httpRequest(url, encodeParametersNew(params), MethodType.GET, WithTokenHeader, token, null, null);
    }

    public Response get(String url, PostParameter[] params, Paging paging, String token) throws WeiboException {
        if (null != paging) {
            List<PostParameter> pagingParams = new ArrayList<>(4);
            if (-1 != paging.getMaxId()) {
                pagingParams.add(new PostParameter("max_id", String.valueOf(paging.getMaxId())));
            }
            if (-1 != paging.getSinceId()) {
                pagingParams.add(new PostParameter("since_id", String.valueOf(paging.getSinceId())));
            }
            if (-1 != paging.getPage()) {
                pagingParams.add(new PostParameter("page", String.valueOf(paging.getPage())));
            }
            if (-1 != paging.getCount()) {
                if (-1 != url.indexOf("search")) {
                    // search api takes "rpp"
                    pagingParams.add(new PostParameter("rpp", String.valueOf(paging.getCount())));
                } else {
                    pagingParams.add(new PostParameter("count", String.valueOf(paging.getCount())));
                }
            }
            PostParameter[] newparams = null;
            PostParameter[] arrayPagingParams = pagingParams.toArray(new PostParameter[pagingParams.size()]);
            if (null != params) {
                newparams = new PostParameter[params.length + pagingParams.size()];
                System.arraycopy(params, 0, newparams, 0, params.length);
                System.arraycopy(arrayPagingParams, 0, newparams, params.length, pagingParams.size());
            } else {
                if (0 != arrayPagingParams.length) {
                    String encodedParams = HttpClientNew.encodeParameters(arrayPagingParams);
                    if (-1 != url.indexOf("?")) {
                        url += "&" + encodedParams;
                    } else {
                        url += "?" + encodedParams;
                    }
                }
            }
            return get(url, newparams, token);
        } else {
            return get(url, params, token);
        }
    }

    /**
     * 处理http deletemethod请求
     */
    public Response delete(String url, PostParameter[] params, String token) throws WeiboException {
        return httpRequest(url, encodeParametersNew(params), MethodType.DELETE, true, token, null, null);
    }

    /**
     * 处理http post请求
     * 
     */
    public Response post(String url, PostParameter[] params, String token) throws WeiboException {
        return post(url, params, true, token);
    }

    public Response post(String url, PostParameter[] params, String token, SimpleAsyncCallback callback)
        throws WeiboException {
        return post(url, params, true, token, callback);
    }

    public Response post(String url, PostParameter[] params, Boolean WithTokenHeader, String token)
        throws WeiboException {
        return post(url, params, WithTokenHeader, token, null);
    }

    public Response post(String url, PostParameter[] params, Boolean WithTokenHeader, String token,
        SimpleAsyncCallback callback) throws WeiboException {
        return httpRequest(url, encodeParametersNew(params), MethodType.POST, WithTokenHeader, token, null, callback);
    }

    /**
     * 处理http post multipart请求
     * 
     * @param url
     * @param params
     * @param token
     * @param fileParamName
     *            接收文件的参数名
     * @param callback
     *            异步请求后的业务处理器，为空时会发同步请求
     * @param filePaths
     *            要提交文件的文件路径
     * @return
     * @throws WeiboException
     */
    public Response postMultipartForm(String url, PostParameter[] params, String token, String fileParamName,
        SimpleAsyncCallback callback, String... filePaths) throws WeiboException {
        return httpRequest(url, encodeParametersNew(params), MethodType.POST, true, token, fileParamName, callback,
            filePaths);
    }

    /**
     * 
     * @param url
     * @param paramMap
     * @param methodType
     * @param WithTokenHeader
     * @param token
     * @param fileParamName
     *            接收文件的参数名
     * @param callback
     *            异步请求后的业务处理器，为空时发同步请求
     * @param filePaths
     *            要提交文件的文件路径
     * @return
     * @throws WeiboException
     */
    public Response httpRequest(String url, Map<String, String> paramMap, MethodType methodType,
        Boolean WithTokenHeader, String token, String fileParamName, SimpleAsyncCallback callback, String... filePaths)
        throws WeiboException {
        try {
            Map<String, String> extraHeaders = new HashMap<>();
            if (WithTokenHeader) {
                // 这里应该是旧版接口的处理，保留
                if (CheckUtils.isEmpty(token)) {
                    throw new IllegalStateException("Oauth2 token is not set!");
                }
                extraHeaders.put("Authorization", "OAuth2 " + token);
                extraHeaders.put("API-RemoteIP", InetAddress.getLocalHost().getHostAddress());

                // 2021-01-22增加处理
                if (CheckUtils.isEmpty(paramMap)) {
                    paramMap = new HashMap<>();
                }
                paramMap.put(MoreUseParamNames.ACCESS_TOKEN, token);
            }

            String responseStr = null;
            if (CheckUtils.isEmpty(filePaths)) {
                responseStr = weiboClient.httpExecute(methodType, url, paramMap, extraHeaders, callback);
            } else {
                responseStr =
                    weiboClient.httpPostMultiPartForm(url, paramMap, extraHeaders, fileParamName, callback, filePaths);
            }
            Response response = new Response();
            response.setResponseAsString(responseStr);
            return response;
        } catch (HttpException e) {
            log.error(e.getConcatedMsg());
            try {
                throw new WeiboException(getCause(e.getStatusCode()), new JSONObject(e.getOriErrMsg()),
                    e.getStatusCode());
            } catch (JSONException e1) {
                throw new WeiboException(e1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                throw new WeiboException(e.getMessage(), new JSONObject(), -1);
            } catch (JSONException e1) {
                throw new WeiboException(e1);
            }
        }
    }

    /**
     * 发异步请求
     * 
     * @param url
     * @param paramMap
     * @param methodType
     * @param WithTokenHeader
     * @param token
     * @param callback
     *            异步请求后的业务处理器，为空时发同步请求
     * @return
     * @throws WeiboException
     */
    public Response httpRequestAsync(String url, Map<String, String> paramMap, MethodType methodType,
        Boolean WithTokenHeader, String token, SimpleAsyncCallback callback) throws WeiboException {
        return httpRequest(url, paramMap, methodType, WithTokenHeader, token, null, callback);
    }

    public static Map<String, String> encodeParametersNew(PostParameter[] postParams) {
        Map<String, String> paramMap = new HashMap<>();
        if (CheckUtils.isNotEmpty(postParams)) {
            for (int j = 0; j < postParams.length; j++) {
                paramMap.put(postParams[j].getName(), postParams[j].getValue());
            }
        }
        return paramMap;
    }

    /*
     * 对parameters进行encode处理
     */
    public static String encodeParameters(PostParameter[] postParams) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < postParams.length; j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(postParams[j].getName(), "UTF-8")).append("=")
                    .append(URLEncoder.encode(postParams[j].getValue(), "UTF-8"));
            } catch (java.io.UnsupportedEncodingException neverHappen) {
            }
        }
        return buf.toString();
    }

    // private static class ByteArrayPart extends PartBase {
    // private byte[] mData;
    // private String mName;
    //
    // public ByteArrayPart(byte[] data, String name, String type) throws IOException {
    // super(name, type, "UTF-8", "binary");
    // mName = name;
    // mData = data;
    // }
    //
    // protected void sendData(OutputStream out) throws IOException {
    // out.write(mData);
    // }
    //
    // protected long lengthOfData() throws IOException {
    // return mData.length;
    // }
    //
    // protected void sendDispositionHeader(OutputStream out) throws IOException {
    // super.sendDispositionHeader(out);
    // StringBuilder buf = new StringBuilder();
    // buf.append("; filename=\"").append(mName).append("\"");
    // out.write(buf.toString().getBytes());
    // }
    // }

    private static String getCause(int statusCode) {
        String cause = null;
        switch (statusCode) {
            case NOT_MODIFIED:
                break;
            case BAD_REQUEST:
                cause =
                    "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
                break;
            case NOT_AUTHORIZED:
                cause = "Authentication credentials were missing or incorrect.";
                break;
            case FORBIDDEN:
                cause =
                    "The request is understood, but it has been refused.  An accompanying error message will explain why.";
                break;
            case NOT_FOUND:
                cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
                break;
            case NOT_ACCEPTABLE:
                cause = "Returned by the Search API when an invalid format is specified in the request.";
                break;
            case INTERNAL_SERVER_ERROR:
                cause = "Something is broken.  Please post to the group so the Weibo team can investigate.";
                break;
            case BAD_GATEWAY:
                cause = "Weibo is down or being upgraded.";
                break;
            case SERVICE_UNAVAILABLE:
                cause =
                    "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
                break;
            default:
                cause = "";
        }
        return statusCode + ":" + cause;
    }
}
