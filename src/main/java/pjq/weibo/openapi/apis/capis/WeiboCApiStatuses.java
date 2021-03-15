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
package pjq.weibo.openapi.apis.capis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.commons.constant.CommonEnumConstant.YesOrNoInt;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.DefaultValueGetter;
import pjq.weibo.openapi.apis.WeiboApiStatuses;
import pjq.weibo.openapi.apis.base.WeiboParamPager;
import pjq.weibo.openapi.constant.ParamConstant.AuthorType;
import pjq.weibo.openapi.constant.ParamConstant.FilterType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.QueryIdInbox;
import pjq.weibo.openapi.constant.ParamConstant.QueryIdType;
import pjq.weibo.openapi.constant.ParamConstant.SourceType;
import pjq.weibo.openapi.constant.ParamConstant.StatusesFeature;
import pjq.weibo.openapi.constant.ParamConstant.StatusesPopularizeFlag;
import pjq.weibo.openapi.constant.ParamConstant.StatusesVisible;
import pjq.weibo.openapi.constant.ParamConstant.TrimUser;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboApiParamScope;
import pjq.weibo.openapi.utils.WeiboContentChecker;
import pjq.weibo.openapi.utils.WeiboContentChecker.PicCheckResults;
import pjq.weibo.openapi.utils.http.SimpleAsyncCallback;
import weibo4j.http.Response;
import weibo4j.model.PostParameter;
import weibo4j.model.SimpleGeo;
import weibo4j.model.Status;
import weibo4j.model.StatusPager;
import weibo4j.model.StatusesCounts;
import weibo4j.model.UploadedPic;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;
import weibo4j.org.json.JSONArray;

/**
 * Statuses相关商业接口<br>
 * 使用<code>Weibo.of({@link WeiboCApiStatuses}.class,accessToken)</code>生成对象<br>
 * 部分方法是直接从{@link WeiboApiStatuses}复制然后只改了url部分的，要调整的话要同时调整
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboCApiStatuses extends WeiboParamPager<WeiboCApiStatuses> {
    /**
     * 用于筛选推广微博。0：返回推广微博和普通微博；1：返回推广微博；2：返回普通微博；默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope("仅'获取授权用户发布的最新微博接口'可用")})
    private StatusesPopularizeFlag flag;

    /**
     * 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE)})
    private YesOrNoInt baseApp;

    /**
     * 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE)})
    private StatusesFeature feature;

    /**
     * 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE)})
    private TrimUser trimUser;

    /**
     * 是否排除评论赞计数，0:表示不排除，1:表示排除，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope("仅'获取授权用户发布的最新微博接口'及'获取微博的评论数接口'可用")})
    private YesOrNoInt excludeCommentLike;

    /**
     * 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE_OTHER)})
    private AuthorType filterByAuthor;

    /**
     * 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE_OTHER)})
    private SourceType filterBySource;

    /**
     * 原创筛选类型，0：全部微博、1：原创的微博，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_TIMELINE_OTHER)})
    private FilterType filterByType;

    /**
     * 仅对私信有效，当MID类型为私信时用此参数，0：发件箱、1：收件箱，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_QUERYID)})
    private QueryIdInbox inbox;

    /**
     * MID是否是base62编码，0：否、1：是，默认为0(形如"3z4efAo4lk"的MID即为经过base62转换的MID)
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_QUERYID)})
    private YesOrNoInt isBase62;

    /**
     * 是否仅屏蔽当前微博。0：仅屏蔽当前@提到我的微博；1：屏蔽当前@提到我的微博，以及后续对其转发而引起的@提到我的微博。默认1
     */
    @Setter(onMethod_ = {@WeiboApiParamScope("仅'屏蔽某个@我的微博及后续由其转发引起的@提及接口'可用")})
    private YesOrNoInt followUp;

    /**
     * 针对字数大于140字微博，是否返回完整内容的开关，0：只返回140以内的微博内容、1：返回值中增加longText字段返回完整微博内容，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope("仅'根据微博ID批量获取微博信息接口'可用")})
    private YesOrNoInt isGetLongText;

    /**
     * 微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_PUBLISH)})
    private StatusesVisible visible;

    /**
     * 微博的保护投递指定分组ID，只有当visible参数为3时生效且必选
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_PUBLISH)})
    private String listId;

    /**
     * 经纬度信息
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_PUBLISH)})
    private SimpleGeo simpleGeo;

    /**
     * 元数据，主要是为了方便第三方应用记录一些适合于自己使用的信息，每条微博可以包含一个或者多个元数据，必须以json字串的形式提交，字串长度不超过512个字符
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_PUBLISH)})
    private List<JSONObject> annotations;

    /**
     * 是否发送超过140字微博，0：不超过140字，1：超过140字但在2000字以内，默认为0。操作用户需满足发送超过140字微博资格
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.STATUSES_PUBLISH)})
    private YesOrNoInt isLongtext;

    @Override
    protected String checkClientId() {
        return MoreUseParamNames.CLIENT_ID_USE_SOURCE;
    }

    @Override
    protected void afterOfInit(String accessToken, String clientId) {}

    /**
     * 获取当前授权用户及其所关注用户的最新微博
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetFriendsLatestStatuses() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_FRIENDS_TIMELINE),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 获取授权用户发布的最新微博<br>
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
     * 该方法根据传入的trim_user参数判断是否返回用户的详细字段
     * 
     * @return
     * @throws WeiboException
     */
    public StatusPager apiGetMyLatestStatusesWithTrimUserParam() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(timelineCommonParam());
        if (CheckUtils.isNotNull(flag)) {
            paramList.add(new PostParameter("flag", flag.value()));
        }
        if (CheckUtils.isNotNull(excludeCommentLike)) {
            paramList.add(new PostParameter("exclude_comment_like", excludeCommentLike.value()));
        }
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_USER_TIMELINE),
            paramListToArray(paramList), accessToken()));
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
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_REPOST_TIMELINE),
            paramListToArray(paramList), accessToken()));
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
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_MENTIONS),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 根据微博ID批量获取微博信息
     * 
     * @param statusIds
     *            微博ID数组
     * @return
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月14日
     */
    public StatusPager apiGetStatusesByIds(String... statusIds) throws WeiboException {
        if (CheckUtils.isEmpty(statusIds)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.IDS);
        } else if (statusIds.length > 50) {
            throw WeiboException.ofParamIdsOutOfLimit(MoreUseParamNames.ID, 50);
        }
        List<PostParameter> paramList = newParamList();
        paramList.addAll(timelineCommonParam());
        if (CheckUtils.isNotEmpty(statusIds)) {
            paramList.add(new PostParameter(MoreUseParamNames.IDS, joinArrayParam(statusIds)));
        }
        if (CheckUtils.isNotNull(isGetLongText)) {
            paramList.add(new PostParameter("isGetLongText", isGetLongText.value()));
        }
        return new StatusPager(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_SHOW_BATCH),
            paramListToArray(paramList), accessToken()));
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
     * 批量获取指定微博的转发数评论数
     * 
     * @param statusIds
     *            微博ID数组
     * @return
     * @throws WeiboException
     */
    public List<StatusesCounts> apiGetStatusesCounts(String... statusIds) throws WeiboException {
        if (CheckUtils.isEmpty(statusIds)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.IDS);
        } else if (statusIds.length > 100) {
            WeiboException.ofParamIdsOutOfLimit(MoreUseParamNames.ID, 100);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.IDS, joinArrayParam(statusIds)));
        if (CheckUtils.isNotNull(excludeCommentLike)) {
            paramList.add(new PostParameter("exclude_comment_like", excludeCommentLike.value()));
        }
        return WeiboResponse.buildList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_COUNT),
            paramListToArray(paramList), accessToken()), StatusesCounts.class);
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
            throw WeiboException.ofParamIdsOutOfLimit(idParamName, 20);
        }
        String apiName = !queryId ? WeiboConfigs.STATUSES_CAPI_QUERY_MID : WeiboConfigs.STATUSES_CAPI_QUERY_ID;

        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(idParamName, joinArrayParam(ids)));
        paramList.add(new PostParameter(MoreUseParamNames.TYPE, queryIdType.value()));
        paramList.add(new PostParameter("is_batch", YesOrNoInt.YES.value())); // 默认使用批量模式

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
            client.get(WeiboConfigs.getApiUrl(apiName), paramListToArray(paramList), accessToken()).asJSONArray();

        Map<String, String> midMap = new HashMap<>();
        if (CheckUtils.isNull(ja)) {
            return midMap;
        }
        for (int i = 0, size = ja.length(); i < size; i++) {
            try {
                weibo4j.org.json.JSONObject eachJo = ja.getJSONObject(i);
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
     * @param statusId
     *            微博ID
     * @param repostText
     *            转发文本，内容不超过140个汉字，不填则默认为"转发微博"
     * @return
     * @throws WeiboException
     */
    public Status apiRepostStatus(String statusId, String... repostText) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        if (CheckUtils.isNotEmpty(repostText)) {
            paramList.add(new PostParameter("status", WeiboContentChecker.checkPostTextAndReturn(repostText[0])));
        }
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_REPOST),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 删除一条微博
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public Status apiDestroy(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_DESTROY),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 过滤某条微博
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月14日
     */
    public Status apiFilterOneStatus(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_FILTER_CREATE),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 屏蔽某个@我的微博及后续由其转发引起的@提及
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月14日
     */
    public Status apiFilterMentionsAndShield(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        if (CheckUtils.isNotNull(followUp)) {
            paramList.add(new PostParameter("follow_up", followUp.value()));
        }
        return new Status(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_MENTIONS_SHIELD),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * (同步)第三方分享一条链接到微博
     * 
     * @param statusText
     *            用户分享到微博的文本内容，内容不超过130或1900个汉字(可包含表情转义符，所有类别的表情可以正确显示)<br>
     *            如果要上传图片，则文本内容不超过130个汉字<br>
     *            文本中可以包含"#话题词#"<br>
     * @param picPath
     *            用户想要分享到微博的图片地址，仅支持JPEG、GIF、PNG图片，上传图片大小限制为<5M
     * @return
     * @throws WeiboException
     */
    public Status apiShareStatus(String statusText, String picPath) throws WeiboException {
        return new Status(apiShareStatusAsync(statusText, Arrays.asList(picPath).toArray(new String[] {}), null));
    }

    /**
     * (同步)第三方分享一条链接到微博
     * 
     * @param statusText
     *            用户分享到微博的文本内容，内容不超过130或1900个汉字(可包含表情转义符，所有类别的表情可以正确显示)<br>
     *            如果要上传图片，则文本内容不超过130个汉字<br>
     *            文本中可以包含"#话题词#"<br>
     * @param picPaths
     *            用户想要分享到微博的图片地址，仅支持JPEG、GIF、PNG图片，上传图片大小限制为<5M
     * @return
     * @throws WeiboException
     */
    public Status apiShareStatus(String statusText, String[] picPaths) throws WeiboException {
        return new Status(apiShareStatusAsync(statusText, picPaths, null));
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
        return apiShareStatusAsync(statusText, Arrays.asList(picPath).toArray(new String[] {}), callback);
    }

    /**
     * (异步)第三方分享一条链接到微博<br>
     * callback为空时会转成同步请求，且不会返回具体的业务对象，所以如果不需要异步请求，直接使用{@link #apiShareStatus(String, String)}
     * 
     * @param statusText
     * @param picPaths
     * @param callback
     *            异步请求时的回调对象
     * @return
     * @throws WeiboException
     */
    public Response apiShareStatusAsync(String statusText, String[] picPaths, SimpleAsyncCallback callback)
        throws WeiboException {
        if (CheckUtils.isEmpty(statusText)) {
            // 网上接口显示发多图时文本内容可为空，实际是不能为空
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.STATUS_TEXT);
        }
        if (StatusesVisible.SPECIFY_GROUP.equals(visible) && CheckUtils.isEmpty(listId)) {
            throw WeiboException.ofParamCanNotNull("list_id");
        }
        if (CheckUtils.isNotNull(simpleGeo)) {
            simpleGeo.check();
        }
        WeiboContentChecker.checkAnnotationsLength(annotations);

        statusText = WeiboContentChecker.checkPostTextAndReturn4CApi(statusText,
            CheckUtils.isEmpty(picPaths) ? 0 : picPaths.length, isLongtext);

        PicCheckResults picCheckResults = WeiboContentChecker.checkIfPicsValid(picPaths);
        boolean needUploadPic = picCheckResults.isValid();
        int picNums = picCheckResults.picNums();

        String picids = null;
        if (needUploadPic && picNums > 1) {
            // 如果是上传多图，则先使用上传图片的接口上传图片后获取图片id，再用pic_id参数，而不使用url参数
            if (picNums > 12) {
                // 微博实际可以发超过15张，最多可发数没测过，但是避免过程中网络等因素影响导致出错，限定只能发12张
                throw new WeiboException("最多只能发布12张图片");
            }
            try {
                List<UploadedPic> uploadedPics = apiGetStatusesUploadPic(picCheckResults.picPaths());
                picids = UploadedPic.toPicIds(uploadedPics);
            } catch (Exception e) {
                // 如果报错则先删除临时文件
                picCheckResults.deleteTempFiles();

                if (e instanceof WeiboException) {
                    throw e;
                } else {
                    throw new WeiboException(e);
                }
            }
        }

        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.STATUS_TEXT, statusText));
        if (CheckUtils.isNotNull(simpleGeo)) {
            paramList.add(new PostParameter("long", simpleGeo.getLongitude()));
            paramList.add(new PostParameter("lat", simpleGeo.getLatitude()));
        }
        if (CheckUtils.isNotEmpty(annotations)) {
            paramList.add(new PostParameter("annotations", JSON.toJSONString(annotations)));
        }
        if (CheckUtils.isNotNull(isLongtext)) {
            paramList.add(new PostParameter("is_longtext", isLongtext.value()));
        }
        if (CheckUtils.isNotEmpty(picids)) {
            paramList.add(new PostParameter("pic_id", picids));
        }

        // 根据不同情况选择接口
        String url = WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_UPDATE);
        if (needUploadPic) {
            // 根据发送的图片数选择接口
            if (picNums <= 1) {
                url = WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_UPLOAD);
            } else {
                url = WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_UPLOAD_URL_TEXT);
            }
        }

        Response response = null;
        if (!needUploadPic) {
            response = client.post(url, paramListToArray(paramList), accessToken(), callback);
        } else {
            SimpleAsyncCallback actualCallback = null;
            if (CheckUtils.isNotNull(callback)) {
                actualCallback = (isSuccess, statusCode, responseStr) -> {
                    // 上传完先删除临时文件
                    picCheckResults.deleteTempFiles();
                    callback.onResponse(isSuccess, statusCode, responseStr);
                };
            }

            if (picNums <= 1) {
                // 开始调发微博的请求(pic为上传图片的参数名)
                response = client.postMultipartForm(url, paramListToArray(paramList), accessToken(),
                    MoreUseParamNames.PIC, actualCallback, picCheckResults.getPicCheckResults().get(0).getFilePath());
            } else {
                // 上传多个图片的接口按一般post调用即可
                response = client.post(url, paramListToArray(paramList), accessToken(), callback);
            }

            if (CheckUtils.isNull(callback)) {
                // 同步上传时在这里删除临时文件
                picCheckResults.deleteTempFiles();
            }
        }
        return response;
    }

    /**
     * 上传图片并获取其上传后的信息
     * 
     * @param picPaths
     * @return
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月12日
     */
    private List<UploadedPic> apiGetStatusesUploadPic(List<String> picPaths) throws WeiboException {
        if (CheckUtils.isEmpty(picPaths)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.PIC);
        }

        List<UploadedPic> uploadedPics = new ArrayList<>();
        for (String picPath : picPaths) {
            List<PostParameter> paramList = newParamList();
            uploadedPics.add(
                new UploadedPic(client.postMultipartForm(WeiboConfigs.getApiUrl(WeiboConfigs.STATUSES_CAPI_UPLOAD_PIC),
                    paramListToArray(paramList), accessToken(), MoreUseParamNames.PIC, null, picPath)));
        }
        return uploadedPics;
    }
}