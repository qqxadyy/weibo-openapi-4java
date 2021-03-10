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
package pjq.weibo.openapi.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.commons.constant.CommonEnumConstant.StatusType;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.DefaultValueGetter;
import pjq.weibo.openapi.constant.ParamConstant.AuthorType;
import pjq.weibo.openapi.constant.ParamConstant.FilterType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.QueryIdInbox;
import pjq.weibo.openapi.constant.ParamConstant.QueryIdType;
import pjq.weibo.openapi.constant.ParamConstant.SourceType;
import pjq.weibo.openapi.constant.ParamConstant.StatusesFeature;
import pjq.weibo.openapi.constant.ParamConstant.TrimUser;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.WeiboContentChecker;
import pjq.weibo.openapi.utils.WeiboContentChecker.PicCheckResult;
import pjq.weibo.openapi.utils.http.SimpleAsyncCallback;
import weibo4j.Timeline;
import weibo4j.WeiboParamPager;
import weibo4j.http.Response;
import weibo4j.model.PostParameter;
import weibo4j.model.Status;
import weibo4j.model.StatusIdsPager;
import weibo4j.model.StatusPager;
import weibo4j.model.StatusesCounts;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

/**
 * Statuses相关接口<br>
 * 使用{@code Weibo.of(WeiboApiStatuses.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiStatuses extends WeiboParamPager<WeiboApiStatuses> {
    private Timeline apiOld;

    /**
     * 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    private StatusType baseApp;

    /**
     * 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     */
    private StatusesFeature feature;

    /**
     * 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    private TrimUser trimUser;

    /**
     * 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0
     */
    private AuthorType filterByAuthor;

    /**
     * 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0
     */
    private SourceType filterBySource;

    /**
     * 原创筛选类型，0：全部微博、1：原创的微博，默认为0
     */
    private FilterType filterByType;

    /**
     * 仅对私信有效，当MID类型为私信时用此参数，0：发件箱、1：收件箱，默认为0
     */
    private QueryIdInbox inbox;

    /**
     * MID是否是base62编码，0：否、1：是，默认为0(形如"3z4efAo4lk"的MID即为经过base62转换的MID)
     */
    private StatusType isBase62;

    /**
     * 开发者上报的操作用户真实IP
     */
    private String rip;

    @Override
    protected String checkClientId() {
        return MoreUseParamNames.CLIENT_ID_USE_SOURCE;
    }

    @Override
    protected void afterOfInit(String accessToken, String clientId) {
        apiOld = new Timeline(accessToken);
    }

    /**
     * 获取当前授权用户及其所关注用户的最新微博
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetFriendsLatestStatuses() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_HOME_TIMELINE),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取当前授权用户及其所关注用户的最新微博的ID列表
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public StatusIdsPager apiGetFriendsLatestStatusIds() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusIdsPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_FRIENDS_TIMELINE_IDS),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取授权用户发布的最新微博<br>
     * 官网中已说明新版下，该接口只能获取授权用户的微博，uid和access_token都可以不传<br>
     * 此接口最多只返回最新的5条数据，官方移动SDK调用可返回10条<br>
     * 因为只查询授权用户的最新微博，该方法默认不返回该用户的详细字段，要返回该用户的详细字段需要调用{@link #apiGetMyLatestStatusesWithTrimUserParam()}
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetMyLatestStatuses() throws WeiboException {
        trimUser(TrimUser.ONLY_USER_ID);
        return apiGetMyLatestStatusesWithTrimUserParam();
    }

    /**
     * 获取授权用户发布的最新微博<br>
     * 官网中已说明新版下，该接口只能获取授权用户的微博，uid和access_token都可以不传<br>
     * 此接口最多只返回最新的5条数据，官方移动SDK调用可返回10条<br>
     * 该方法根据传入的trim_user参数判断是否返回用户的详细字段
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetMyLatestStatusesWithTrimUserParam() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取授权用户发布的最新微博的ID列表<br>
     * 官网中已说明新版下，该接口只能获取授权用户的微博，uid和access_token都可以不传<br>
     * 
     * @return
     * @throws WeiboException
     */
    public StatusIdsPager apiGetMyLatestStatusIds() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusIdsPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_USER_TIMELINE_IDS),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取当前授权用户及与其双向关注用户的微博
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetFriendsEachOtherStatuses() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_BILATERAL_TIMELINE),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取授权用户关注人转发过指定微博的微博列表
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetFriendsStatusesWhichRepostedThis(String statusId) throws WeiboException {
        filterByAuthor(AuthorType.FRIEND);
        return apiGetStatusesWhichRepostedThis(statusId);
    }

    /**
     * 获取转发过指定微博的微博列表
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetStatusesWhichRepostedThis(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = pageParam();
        paramList.addAll(filterCommonParam());
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取指定微博的最新转发微博的ID列表
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public StatusIdsPager apiGetStatusIdsWhichRepostedThis(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = pageParam();
        paramList.addAll(filterCommonParam());
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return new StatusIdsPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST_TIMELINE_IDS),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取@当前授权用户的最新微博
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetStatusesAtMe() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(filterCommonParam());
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 获取@当前授权用户的最新微博的ID列表<br>
     * 官网没有注明是废弃，但是调用后total_number有数量而id列表没返回，可能实际已废弃
     * 
     * @return
     * @throws WeiboException
     */
    @SuppressWarnings("deprecation")
    public StatusIdsPager apiGetStatusIdsAtMe() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(filterCommonParam());
        return new StatusIdsPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_MENTIONS_IDS),
            paramListToArray(paramList), accessToken));
    }

    /**
     * 根据微博ID获取单条微博信息
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public Status apiGetStatus(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return new Status(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_SHOW), paramListToArray(paramList), accessToken));
    }

    /**
     * 批量获取指定微博的转发数评论数
     * 
     * @param statusIds
     *            微博ID数组
     * @return
     * @throws WeiboException
     */
    public List<StatusesCounts> apiGetStatusesCounts(String... statusIds) throws WeiboException {
        if (CheckUtils.isEmpty(statusIds)) {
            throw WeiboException.ofParamCanNotNull("ids");
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter("ids", joinArrayParam(statusIds)));
        return WeiboResponse.buildList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_COUNT), paramListToArray(paramList), accessToken),
            StatusesCounts.class);
    }

    /**
     * 根据ID跳转到单条微博页(使用access_token参数)
     * 
     * @param uid
     *            要跳转的用户ID
     * @param statusId
     *            微博ID
     * @return 返回目标微博的地址url地址
     * @throws WeiboException
     */
    public String apiStatusGo(String uid, String statusId) throws WeiboException {
        return statusGo(uid, statusId, false);
    }

    /**
     * 根据ID跳转到单条微博页(使用source参数)
     * 
     * @param uid
     *            要跳转的用户ID
     * @param statusId
     *            微博ID
     * @return 返回目标微博的地址url地址
     * @throws WeiboException
     */
    public String apiStatusGoWithSource(String uid, String statusId) throws WeiboException {
        return statusGo(uid, statusId, true);
    }

    private String statusGo(String uid, String statusId, boolean useClientId) throws WeiboException {
        if (CheckUtils.isEmpty(uid)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.UID);
        }
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.UID, uid));
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        if (useClientId) {
            // statusgo接口的source参数和access_token参数互斥，所以传入source参数时不传access_token参数
            paramList.add(new PostParameter(MoreUseParamNames.CLIENT_ID_USE_SOURCE, clientId()));
            return client
                .get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_GO), paramListToArray(paramList), false, accessToken)
                .asString();
        } else {
            return client
                .get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_GO), paramListToArray(paramList), accessToken)
                .asString();
        }
    }

    /**
     * 通过微博/评论/私信ID获取其MID
     * 
     * @param ids
     *            ID数组
     * @return
     * @throws WeiboException
     */
    public Map<String, String> apiQueryMidById(QueryIdType type, String... ids) throws WeiboException {
        return queryIdOrMid(false, type, ids);
    }

    /**
     * 通过微博/评论/私信MID获取其ID
     * 
     * @param mids
     *            MID数组
     * @return
     * @throws WeiboException
     */
    public Map<String, String> apiQueryIdByMid(QueryIdType type, String... mids) throws WeiboException {
        return queryIdOrMid(true, type, mids);
    }

    /**
     * 调用queryid/querymid接口
     * 
     * @param queryId
     *            是否调用queryid接口，否则调用querymid接口
     * @param queryIdType
     * @param ids
     * @return
     * @throws WeiboException
     */
    private Map<String, String> queryIdOrMid(boolean queryId, QueryIdType queryIdType, String... ids)
        throws WeiboException {
        String idParamName = !queryId ? MoreUseParamNames.ID : "mid";
        if (CheckUtils.isEmpty(ids)) {
            throw WeiboException.ofParamCanNotNull(idParamName);
        } else if (ids.length > 20) {
            throw new WeiboException(idParamName + "的数量不能超过20个");
        }
        String apiName = !queryId ? WeiboConfigs.STATUSES_QUERY_MID : WeiboConfigs.STATUSES_QUERY_ID;

        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(idParamName, joinArrayParam(ids)));
        paramList.add(new PostParameter(MoreUseParamNames.TYPE, queryIdType.value()));
        paramList.add(new PostParameter("is_batch", StatusType.VALID.value())); // 默认使用批量模式

        if (queryId) {
            // queryid接口特有参数
            if (CheckUtils.isNotNull(inbox)) {
                paramList.add(new PostParameter("inbox", inbox.value()));
            }
            if (CheckUtils.isNotNull(isBase62)) {
                paramList.add(new PostParameter("isBase62", isBase62.value()));
            }
        }

        JSONArray ja =
            client.get(WeiboConfigs.getApiUrl(apiName), paramListToArray(paramList), accessToken).asJSONArray();

        Map<String, String> midMap = new HashMap<>();
        if (CheckUtils.isNull(ja)) {
            return midMap;
        }
        for (int i = 0, size = ja.length(); i < size; i++) {
            try {
                JSONObject eachJo = ja.getJSONObject(i);
                String key = (String)eachJo.keys().next();
                midMap.put(key, DefaultValueGetter.getValue("", eachJo.getString(key)));
            } catch (Exception e) {
                continue;
            }
        }
        return midMap;
    }

    /**
     * 转发一条微博<br>
     * 默认按不评论处理，不提供is_comment参数的传入
     * 
     * @param statusIds
     *            微博ID
     * @param repostText
     *            转发文本，内容不超过140个汉字，不填则默认为"转发微博"
     * @return
     * @throws WeiboException
     */
    public Status repostStatus(String statusId, String... repostText) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        if (CheckUtils.isNotEmpty(repostText)) {
            paramList.add(new PostParameter("status", WeiboContentChecker.checkPostTextAndReturn(repostText[0])));
        }
        if (CheckUtils.isNotEmpty(rip)) {
            paramList.add(new PostParameter(MoreUseParamNames.REAL_IP, rip));
        }
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_REPOST), paramListToArray(paramList),
            accessToken));
    }

    /**
     * 删除一条微博
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public Status destroy(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        return apiOld.destroy(statusId);
    }

    /**
     * timeline类公共参数
     * 
     * @return
     */
    private List<PostParameter> timelineCommonParam() {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNotNull(baseApp)) {
            paramList.add(new PostParameter("base_app", baseApp.value()));
        }
        if (CheckUtils.isNotNull(feature)) {
            paramList.add(new PostParameter("feature", feature.value()));
        }
        if (CheckUtils.isNotNull(trimUser)) {
            paramList.add(new PostParameter("trim_user", trimUser.value()));
        }
        return paramList;
    }

    /**
     * filter类公共参数
     * 
     * @return
     */
    private List<PostParameter> filterCommonParam() {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNotNull(filterByAuthor)) {
            paramList.add(new PostParameter("filter_by_author", filterByAuthor.value()));
        }
        if (CheckUtils.isNotNull(filterBySource)) {
            paramList.add(new PostParameter("filter_by_source", filterBySource.value()));
        }
        if (CheckUtils.isNotNull(filterByType)) {
            paramList.add(new PostParameter("filter_by_type", filterByType.value()));
        }
        return paramList;
    }

    /**
     * (同步)第三方分享一条链接到微博
     * 
     * @param statusText
     *            用户分享到微博的文本内容，内容不超过140个汉字(可包含表情转义符，但是好像只有"普通表情"类才可以正确显示)<br>
     *            文本中不能包含"#话题词#"(经测试加了也不报错，只是会被微博自动过滤掉)<br>
     *            同时文本中必须包含至少一个第三方分享到微博的网页URL，且该URL只能是该第三方（调用方）绑定域下的URL链接<br>
     *            绑定域在"我的应用-应用信息-基本应用信息编辑-安全域名"里设置<br>
     *            链接地址中如含有中文等字符，需要做URLEncode<br>
     *            正文例子："这是一个测试微博http://安全域名{详细地址}"，里面的第三方地址是必须的，可以只到域名那段，例如http://www.myapp.com
     * @param picPath
     *            用户想要分享到微博的图片地址，仅支持JPEG、GIF、PNG图片，上传图片大小限制为<5M(只能传一个图片)
     * @return
     * @throws WeiboException
     */
    public Status apiShareStatus(String statusText, String picPath) throws WeiboException {
        return new Status(apiShareStatusAsync(statusText, picPath, null));
    }

    /**
     * (异步)第三方分享一条链接到微博<br>
     * callback为空时会转成同步请求，且不会返回具体的业务对象，所以如果不需要异步请求，直接使用{@link #apiShareStatus(String, String)}
     * 
     * @param statusText
     * @param picPath
     * @param callback
     *            异步请求时的回调对象
     * @return
     * @throws WeiboException
     */
    public Response apiShareStatusAsync(String statusText, String picPath, SimpleAsyncCallback callback)
        throws WeiboException {
        if (CheckUtils.isEmpty(statusText)) {
            throw WeiboException.ofParamCanNotNull("status");
        }
        statusText = WeiboContentChecker.checkPostTextAndReturn(statusText);
        WeiboContentChecker.checkIfHasSafeLink(statusText, safeDomains());
        PicCheckResult picCheckResult = WeiboContentChecker.checkIfPicValid(picPath);
        boolean needUploadPic = picCheckResult.isValid();

        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter("status", statusText));
        if (CheckUtils.isNotEmpty(rip)) {
            paramList.add(new PostParameter(MoreUseParamNames.REAL_IP, rip));
        }
        Response response = null;
        if (!needUploadPic) {
            response = client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_SHARE), paramListToArray(paramList),
                accessToken, callback);
        } else {
            SimpleAsyncCallback actualCallback = null;
            if (CheckUtils.isNotNull(callback)) {
                actualCallback = (isSuccess, statusCode, responseStr) -> {
                    // 上传完先删除临时文件
                    picCheckResult.deleteTempFile();
                    callback.onResponse(isSuccess, statusCode, responseStr);
                };
            }

            // 开始调发微博的请求(pic为上传图片的参数名)
            response = client.postMultipartForm(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_SHARE),
                paramListToArray(paramList), accessToken, "pic", actualCallback, picCheckResult.getFilePath());

            if (CheckUtils.isNull(callback)) {
                // 同步上传时在这里删除临时文件
                picCheckResult.deleteTempFile();
            }
        }
        return response;
    }
}