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

import java.util.List;
import java.util.Map;

import weibo4j.model.Favorites;
import weibo4j.model.FavoritesIds;
import weibo4j.model.FavoritesTag;
import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.Tag;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Favorite extends Weibo {

    private static final long serialVersionUID = 2298934944028795652L;

    public Favorite(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------收藏接口----------------------------------------*/
    /**
     * 获取当前登录用户的收藏列表
     * 
     * @return list of the Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites
     * @since JDK 1.5
     */
    public List<Favorites> getFavorites() throws WeiboException {
        return Favorites
            .constructFavorites(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites.json", accessToken));
    }

    /**
     * 获取当前登录用户的收藏列表
     * 
     * @param page
     * @return list of the Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites
     * @since JDK 1.5
     */
    public List<Favorites> getFavorites(Paging page) throws WeiboException {
        return Favorites.constructFavorites(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites.json", null, page, accessToken));
    }

    /**
     * 获取当前登录用户的收藏列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites
     * @since JDK 1.5
     */
    public List<Favorites> getFavorites(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return Favorites
            .constructFavorites(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites.json", parList, accessToken));
    }

    /**
     * 获取当前登录用户的收藏列表ID
     * 
     * @return list of the Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites
     * @since JDK 1.5
     */
    public List<FavoritesIds> getFavoritesIds() throws WeiboException {
        return FavoritesIds
            .constructFavoritesIds(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/ids.json", accessToken));
    }

    /**
     * 获取当前登录用户的收藏列表ID
     * 
     * @return list of the Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites
     * @since JDK 1.5
     */
    public JSONObject getFavoritesIds(Paging page) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/ids.json", null, page, accessToken)
            .asJSONObject();
    }

    /**
     * 获取当前登录用户的收藏列表ID
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites/ids
     * @since JDK 1.5
     */
    public JSONObject getFavoritesIds(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/ids.json", parList, accessToken).asJSONObject();
    }

    /**
     * 根据收藏ID获取指定的收藏信息
     * 
     * @param id
     * @return Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites/show
     * @since JDK 1.5
     */
    public Favorites showFavorites(String id) throws WeiboException {
        return new Favorites(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/show.json",
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 根据标签获取当前登录用户该标签下的收藏列表
     * 
     * @param tid
     * @return list of the favorite Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites/by_tags
     * @since JDK 1.5
     */
    public List<Favorites> getFavoritesByTags(String tid) throws WeiboException {
        return Favorites.constructFavorites(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/by_tags.json",
            new PostParameter[] {new PostParameter("tid", tid)}, accessToken));
    }

    /**
     * 根据标签获取当前登录用户该标签下的收藏列表
     * 
     * @param tid
     * @param page
     * @return list of the favorite Status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/show
     * @since JDK 1.5
     */
    public List<Favorites> getFavoritesByTags(String tid, Paging page) throws WeiboException {
        return Favorites.constructFavorites(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/by_tags.json",
            new PostParameter[] {new PostParameter("tid", tid)}, page, accessToken));
    }

    /**
     * 根据标签获取当前登录用户该标签下的收藏列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites/by_tags
     * @since JDK 1.5
     */
    public List<Favorites> getFavoritesByTags(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return Favorites.constructFavorites(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/by_tags.json", parList, accessToken));
    }

    /**
     * 获取当前登录用户的收藏标签列表
     * 
     * @param page
     * @return list of the favorite tags
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/favorites/tags
     * @since JDK 1.5
     */
    public List<FavoritesTag> getFavoritesTags() throws WeiboException {
        return Tag.constructTag(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/tags.json", accessToken));

    }

    /**
     * 获取当前登录用户的收藏标签列表
     * 
     * @param map
     *            参数列表
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites/tags
     * @since JDK 1.5
     */
    public List<FavoritesTag> getFavoritesTags(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return Tag
            .constructTag(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/tags.json", parList, accessToken));

    }

    /**
     * 获取当前用户某个标签下的收藏列表的ID
     * 
     * @param tid
     *            需要查询的标签ID
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites/by_tags/ids
     * @since JDK 1.5
     */
    public List<FavoritesIds> getFavoritesIdsByTags(String tid) throws WeiboException {
        return FavoritesIds
            .constructFavoritesIds(client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/by_tags/ids.json",
                new PostParameter[] {new PostParameter("tid", tid)}, accessToken));
    }

    /**
     * 获取当前用户某个标签下的收藏列表的ID
     * 
     * @param map
     *            参数
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/favorites/by_tags/ids
     * @since JDK 1.5
     */
    public List<FavoritesIds> getFavoritesIdsByTags(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return FavoritesIds.constructFavoritesIds(
            client.get(WeiboConfig.getOpenAPIBaseURL() + "favorites/by_tags/ids.json", parList, accessToken));
    }

    /**
     * 添加一条微博到收藏里
     * 
     * @param 要收藏的微博ID
     * @return Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/create
     * @since JDK 1.5
     */
    public Favorites createFavorites(String id) throws WeiboException {
        return new Favorites(client.post(WeiboConfig.getOpenAPIBaseURL() + "favorites/create.json",
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 取消收藏一条微博
     * 
     * @param 要取消收藏的微博ID
     * @return Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/destroy
     * @since JDK 1.5
     */
    public Favorites destroyFavorites(String id) throws WeiboException {
        return new Favorites(client.post(WeiboConfig.getOpenAPIBaseURL() + "favorites/destroy.json",
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 批量删除收藏
     * 
     * @param ids
     *            要取消收藏的收藏ID，用半角逗号分隔，最多不超过10个。
     * @return destroy list of Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/destroy_batch
     * @since JDK 1.5
     */
    public Boolean destroyFavoritesBatch(String ids) throws WeiboException {
        try {
            return client
                .post(WeiboConfig.getOpenAPIBaseURL() + "favorites/destroy_batch.json",
                    new PostParameter[] {new PostParameter("ids", ids)}, accessToken)
                .asJSONObject().getBoolean("result");
        } catch (JSONException e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 更新一条收藏的收藏标签
     * 
     * @param id
     *            要需要更新的收藏ID
     * @return update tag of Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/tags/update
     * @since JDK 1.5
     */
    public Favorites updateFavoritesTags(String id) throws WeiboException {
        return new Favorites(client.post(WeiboConfig.getOpenAPIBaseURL() + "favorites/tags/update.json",
            new PostParameter[] {new PostParameter("id", id)}, accessToken));
    }

    /**
     * 更新一条收藏的收藏标签
     * 
     * @param id
     *            要需要更新的收藏ID
     * @param tags
     *            需要更新的标签内容，必须做URLencode，用半角逗号分隔，最多不超过2条。
     * @return update tag of Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/tags/update
     * @since JDK 1.5
     */
    public Favorites updateFavoritesTags(String id, String tags) throws WeiboException {
        return new Favorites(client.post(WeiboConfig.getOpenAPIBaseURL() + "favorites/tags/update.json",
            new PostParameter[] {new PostParameter("id", id), new PostParameter("tags", tags)}, accessToken));
    }

    /**
     * 更新当前登录用户所有收藏下的指定标签
     * 
     * @param tid
     *            需要更新的标签ID
     * @param tag
     *            需要更新的标签内容，必须做URLencode
     * @return update tags of Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/tags/update_batch
     * @since JDK 1.5
     */
    public JSONObject updateFavoritesTagsBatch(String tid, String tag) throws WeiboException {
        return client
            .post(WeiboConfig.getOpenAPIBaseURL() + "favorites/tags/update_batch.json",
                new PostParameter[] {new PostParameter("tid", tid), new PostParameter("tag", tag)}, accessToken)
            .asJSONObject();
    }

    /**
     * 删除当前登录用户所有收藏下的指定标签
     * 
     * @param id
     *            需要删除的标签ID。。
     * @return destroy tags of Favorites status
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.0
     * @see http://open.weibo.com/wiki/2/favorites/tags/destroy_batch
     * @since JDK 1.5
     */
    public Boolean destroyFavoritesTagsBatch(String ids) throws WeiboException {
        try {
            return client
                .post(WeiboConfig.getOpenAPIBaseURL() + "favorites/destroy_batch.json",
                    new PostParameter[] {new PostParameter("ids", ids)}, accessToken)
                .asJSONObject().getBoolean("result");
        } catch (JSONException e) {
            throw new WeiboException(e);
        }
    }
}
