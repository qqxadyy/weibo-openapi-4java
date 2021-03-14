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

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.commons.constant.CommonEnumConstant.YesOrNoInt;
import pjq.commons.utils.CheckUtils;
import pjq.weibo.openapi.apis.base.WeiboParamPager;
import pjq.weibo.openapi.constant.ParamConstant.AuthorType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.SourceType;
import pjq.weibo.openapi.constant.ParamConstant.TrimUser;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboApiParamScope;
import pjq.weibo.openapi.utils.WeiboContentChecker;
import weibo4j.Comments;
import weibo4j.model.Comment;
import weibo4j.model.CommentPager;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;

/**
 * Comments相关接口<br>
 * 使用<code>Weibo.of({@link WeiboApiComments}.class,accessToken)</code>生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiComments extends WeiboParamPager<WeiboApiComments> {
    private Comments apiOld;

    /**
     * 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_QUERY)})
    private AuthorType filterByAuthor;

    /**
     * 来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_QUERY)})
    private SourceType filterBySource;

    /**
     * 返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_QUERY)})
    private TrimUser trimUser;

    /**
     * 回复中是否自动加入"回复@用户名"，0：是、1：否，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_WRITE)})
    private Integer withoutMention;

    /**
     * 当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_WRITE)})
    private YesOrNoInt commentOri;

    /**
     * 开发者上报的操作用户真实IP
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMENTS_WRITE)})
    private String rip;

    @Override
    protected void afterOfInit(String accessToken, String clientId) {
        apiOld = new Comments(accessToken);
    }

    /**
     * 根据微博ID返回某条微博的评论列表
     * 
     * @param statusId
     *            微博ID
     * @return
     * @throws WeiboException
     */
    public CommentPager apiShowStatusComments(String statusId) throws WeiboException {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        List<PostParameter> paramList = pageParam();
        paramList.addAll(readCommonParam());
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        return commonCommentPager(paramList, WeiboConfigs.COMMENTS_SHOW);
    }

    /**
     * 获取最新的提到当前登录用户的评论，即@当前用户的评论
     * 
     * @return
     * @throws WeiboException
     */
    public CommentPager apiShowCommentsAtMe() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(readCommonParam());
        return commonCommentPager(paramList, WeiboConfigs.COMMENTS_MENTIONS);
    }

    /**
     * 获取当前用户所发出的评论列表
     * 
     * @return
     * @throws WeiboException
     */
    public CommentPager apiShowMySendComments() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(readCommonParam());
        return commonCommentPager(paramList, WeiboConfigs.COMMENTS_BY_ME);
    }

    /**
     * 获取当前用户所接收到的评论列表
     * 
     * @return
     * @throws WeiboException
     */
    public CommentPager apiShowMyReceiveComments() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(readCommonParam());
        return commonCommentPager(paramList, WeiboConfigs.COMMENTS_TO_ME);
    }

    /**
     * 获取当前用户的最新评论,包括接收到的与发出的
     * 
     * @return
     * @throws WeiboException
     */
    public CommentPager apiShowMyLatestComments() throws WeiboException {
        List<PostParameter> paramList = pageParam();
        paramList.addAll(readCommonParam());
        return commonCommentPager(paramList, WeiboConfigs.COMMENTS_TIMELINE);
    }

    /**
     * 根据评论ID批量返回评论信息
     * 
     * @param commentIds
     *            评论ID数组
     * @return
     * @throws WeiboException
     */
    public List<Comment> apiShowCommentsBatch(String... commentIds) throws WeiboException {
        if (CheckUtils.isEmpty(commentIds)) {
            throw WeiboException.ofParamCanNotNull("cids");
        } else if (commentIds.length > 20) {
            WeiboException.ofParamIdsOutOfLimit("cid", 20);
        }
        return apiOld.getCommentShowBatch(joinArrayParam(commentIds));
    }

    /**
     * 对一条微博进行评论
     * 
     * @param statusId
     *            需要评论的微博ID
     * @param commentText
     *            评论内容，内容不超过130个汉字(可以带表情，例如[微笑]、[吃瓜]，不过需要先知道表情对应的转义符)
     * @return
     * @throws WeiboException
     */
    public Comment apiCreateComment(String statusId, String commentText) throws WeiboException {
        String trueCommentText = commonWriteCheck(statusId, commentText);
        List<PostParameter> paramList = writeCommonParam();
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        paramList.add(new PostParameter("comment", trueCommentText));
        return new Comment(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.COMMENTS_CREATE),
            paramListToArray(paramList), accessToken()));
    }

    /**
     * 回复一条当前用户收到的评论
     * 
     * @param commentId
     *            需要回复的评论ID
     * @param statusId
     *            需要评论的微博ID
     * @param commentText
     *            评论内容，内容不超过130个汉字(可以带表情，例如[微笑]、[吃瓜]，不过需要先知道表情对应的转义符)
     * @return
     * @throws WeiboException
     */
    public Comment apiReplyComment(String commentId, String statusId, String commentText) throws WeiboException {
        if (CheckUtils.isEmpty(commentId)) {
            throw WeiboException.ofParamCanNotNull("cid");
        }
        String trueCommentText = commonWriteCheck(statusId, commentText);
        List<PostParameter> paramList = writeCommonParam();
        paramList.add(new PostParameter("cid", commentId));
        paramList.add(new PostParameter(MoreUseParamNames.ID, statusId));
        paramList.add(new PostParameter("comment", trueCommentText));
        return new Comment(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.COMMENTS_REPLY), paramListToArray(paramList),
            accessToken()));
    }

    /**
     * 删除一条评论
     * 
     * @param commentId
     *            评论ID
     * @return
     * @throws WeiboException
     */
    public Comment apiDeleteComment(String commentId) throws WeiboException {
        if (CheckUtils.isEmpty(commentId)) {
            throw WeiboException.ofParamCanNotNull("cid");
        }
        return apiOld.destroyComment(commentId);
    }

    /**
     * 批量删除评论
     * 
     * @param commentIds
     *            评论ID数组
     * @return
     * @throws WeiboException
     */
    public List<Comment> apiDeleteCommentBatch(String... commentIds) throws WeiboException {
        if (CheckUtils.isEmpty(commentIds)) {
            throw WeiboException.ofParamCanNotNull("cids");
        } else if (commentIds.length > 20) {
            WeiboException.ofParamIdsOutOfLimit("cid", 20);
        }
        return apiOld.destoryCommentBatch(joinArrayParam(commentIds));
    }

    private String commonWriteCheck(String statusId, String commentText) {
        if (CheckUtils.isEmpty(statusId)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ID);
        }
        if (CheckUtils.isEmpty(commentText)) {
            throw WeiboException.ofParamCanNotNull("comment");
        }
        return WeiboContentChecker.checkPostTextAndReturn(commentText);
    }

    private CommentPager commonCommentPager(List<PostParameter> paramList, String apiName) throws WeiboException {
        return new CommentPager(
            client.get(WeiboConfigs.getApiUrl(apiName), paramListToArray(paramList), accessToken()));
    }

    /**
     * 查询类接口公共参数
     * 
     * @return
     */
    private List<PostParameter> readCommonParam() {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNotNull(filterByAuthor)) {
            paramList.add(new PostParameter("filter_by_author", filterByAuthor.value()));
        }
        if (CheckUtils.isNotNull(filterBySource)) {
            paramList.add(new PostParameter("filter_by_source", filterBySource.value()));
        }
        if (CheckUtils.isNotNull(trimUser)) {
            paramList.add(new PostParameter("trim_user", trimUser.value()));
        }
        return paramList;
    }

    /**
     * 写入类接口公共参数
     * 
     * @return
     */
    private List<PostParameter> writeCommonParam() {
        List<PostParameter> paramList = newParamList();
        if (new Integer("1").equals(withoutMention)) {
            // 默认为0，所以只要当为1时传1即可
            paramList.add(new PostParameter("without_mention", withoutMention));
        }
        if (CheckUtils.isNotNull(commentOri)) {
            paramList.add(new PostParameter("comment_ori", commentOri.value()));
        }
        if (CheckUtils.isNotEmpty(rip)) {
            paramList.add(new PostParameter(MoreUseParamNames.REAL_IP, rip));
        }
        return paramList;
    }
}