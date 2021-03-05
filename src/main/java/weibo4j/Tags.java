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

import weibo4j.model.Paging;
import weibo4j.model.PostParameter;
import weibo4j.model.Tag;
import weibo4j.model.TagWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.WeiboConfig;

public class Tags extends Weibo {

    private static final long serialVersionUID = 7047254100483792467L;

    public Tags(String access_token) {
        this.accessToken = access_token;
    }

    /*----------------------------标签接口----------------------------------------*/
    /**
     * 返回指定用户的标签列表
     * 
     * @param uid
     *            要获取的标签列表所属的用户ID
     * @return list of the tags
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags
     * @since JDK 1.5
     */
    public List<Tag> getTags(String uid) throws WeiboException {
        return Tag.constructTags(client.get(WeiboConfig.getOpenAPIBaseURL() + "tags.json",
            new PostParameter[] {new PostParameter("uid", uid)}, accessToken));
    }

    /**
     * 返回指定用户的标签列表
     * 
     * @param uid
     *            要获取的标签列表所属的用户ID
     * @param page
     *            返回结果的页码，默认为1
     * @return list of the tags
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags
     * @since JDK 1.5
     */
    public List<Tag> getTags(String uid, int count, Paging page) throws WeiboException {
        return Tag.constructTags(client.get(WeiboConfig.getOpenAPIBaseURL() + "tags.json",
            new PostParameter[] {new PostParameter("uid", uid), new PostParameter("count", count)}, page,
            accessToken));
    }

    /**
     * 批量获取用户的标签列表
     * 
     * @param uids
     *            要获取标签的用户ID。最大20，逗号分隔
     * @return list of the tags
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags/tags_batch
     * @since JDK 1.5
     */
    public TagWapper getTagsBatch(String uids) throws WeiboException {
        return Tag.constructTagWapper(client.get(WeiboConfig.getOpenAPIBaseURL() + "tags/tags_batch.json",
            new PostParameter[] {new PostParameter("uids", uids)}, accessToken));
    }

    /**
     * 获取系统推荐的标签列表
     * 
     * @return list of the tags
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags/suggestions
     * @since JDK 1.5
     */

    public List<Tag> getTagsSuggestions() throws WeiboException {
        return Tag.constructTags(client.get(WeiboConfig.getOpenAPIBaseURL() + "tags/suggestions.json", accessToken));
    }

    /**
     * 获取系统推荐的标签列表
     * 
     * @param count
     *            返回记录数，默认10，最大10
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/tags/suggestions
     * @since JDK 1.5
     */
    public List<Tag> getTagsSuggestions(int count) throws WeiboException {
        return Tag.constructTags(client.get(WeiboConfig.getOpenAPIBaseURL() + "tags/suggestions.json",
            new PostParameter[] {new PostParameter("count", count)}, accessToken));
    }

    /**
     * 为当前登录用户添加新的用户标签
     * 
     * @param tags
     *            要创建的一组标签，用半角逗号隔开，每个标签的长度不可超过7个汉字，14个半角字符
     * @return tag_id
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags/create
     * @since JDK 1.5
     */
    public JSONArray createTags(String tags) throws WeiboException {
        return client.post(WeiboConfig.getOpenAPIBaseURL() + "tags/create.json",
            new PostParameter[] {new PostParameter("tags", tags)}, accessToken).asJSONArray();
    }

    /**
     * 删除一个用户标签
     * 
     * @param tag_id
     *            要删除的标签ID
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @throws JSONException
     * @see http://open.weibo.com/wiki/2/tags/destroy
     * @since JDK 1.5
     */
    public JSONObject destoryTag(Integer tag_id) throws WeiboException {
        return client.post(WeiboConfig.getOpenAPIBaseURL() + "tags/destroy.json",
            new PostParameter[] {new PostParameter("tag_id", tag_id.toString())}, accessToken).asJSONObject();
    }

    /**
     * 批量删除一组标签
     * 
     * @param ids
     *            要删除的一组标签ID，以半角逗号隔开，一次最多提交10个ID
     * @return tag_id
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.1
     * @see http://open.weibo.com/wiki/2/tags/destroy_batch
     * @since JDK 1.5
     */
    public List<Tag> destroyTagsBatch(String ids) throws WeiboException {
        return Tag.constructTags(client.post(WeiboConfig.getOpenAPIBaseURL() + "tags/destroy_batch.json",
            new PostParameter[] {new PostParameter("ids", ids)}, accessToken));
    }
}
