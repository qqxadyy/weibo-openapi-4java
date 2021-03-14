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

import java.util.Map;

import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class ShortUrl extends Weibo {

    private static final long serialVersionUID = -1312280626965611916L;

    public ShortUrl(String access_token) {
        this.accessToken = access_token;
    }

    /**
     * 将一个或多个长链接转换成短链接
     * 
     * @param url_long
     *            需要转换的长链接，需要URLencoded，最多不超过20个
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/shorten
     * @since JDK 1.5
     */
    public JSONObject longToShortUrl(String url_long) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/shorten.json",
            new PostParameter[] {new PostParameter("url_long", url_long)}, accessToken).asJSONObject();
    }

    /**
     * 将一个或多个短链接还原成原始的长链接
     * 
     * @param url_short
     *            需要还原的短链接，需要URLencoded，最多不超过20个
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/expand
     * @since JDK 1.5
     */
    public JSONObject shortToLongUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/expand.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取短链接的总点击数
     * 
     * @param url_short
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/clicks
     * @since JDK 1.5
     */
    public JSONObject clicksOfUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/clicks.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取一个短链接点击的referer来源和数量
     * 
     * @param url_short
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/referers
     * @since JDK 1.5
     */
    public JSONObject referersOfUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/referers.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取一个短链接点击的地区来源和数量
     * 
     * @param url_short
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/locations
     * @since JDK 1.5
     */
    public JSONObject locationsOfUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/locations.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取短链接在微博上的微博分享数
     * 
     * @param url_short
     *            需要取得分享数的短链接，需要URLencoded，最多不超过20个
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/share/counts
     * @since JDK 1.5
     */
    public JSONObject shareCountsOfUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/share/counts.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取包含指定单个短链接的最新微博内容
     * 
     * @param url_short
     *            需要取得关联微博内容的短链接，需要URLencoded
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/share/statuses
     * @since JDK 1.5
     */
    public JSONObject statusesContentUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/share/statuses.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取包含指定单个短链接的最新微博内容
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/share/statuses
     * @since JDK 1.5
     */
    public JSONObject statusesContentUrl(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/share/statuses.json", parList, accessToken)
            .asJSONObject();
    }

    /**
     * 获取短链接在微博上的微博评论数
     * 
     * @param url_short
     *            需要取得分享数的短链接，需要URLencoded，最多不超过20个
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/comment/counts
     * @since JDK 1.5
     */
    public JSONObject commentCountOfUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/comment/counts.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取包含指定单个短链接的最新微博评论
     * 
     * @param url_short
     *            需要取得关联微博评论内容的短链接，需要URLencoded
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/comment/comments
     * @since JDK 1.5
     */
    public JSONObject commentsContentUrl(String url_short) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/comment/comments.json",
            new PostParameter[] {new PostParameter("url_short", url_short)}, accessToken).asJSONObject();
    }

    /**
     * 获取包含指定单个短链接的最新微博评论
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/short_url/comment/comments
     * @since JDK 1.5
     */
    public JSONObject commentsContentUrl(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "short_url/comment/comments.json", parList, accessToken)
            .asJSONObject();
    }

}
