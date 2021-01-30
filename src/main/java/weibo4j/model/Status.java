package weibo4j.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月25日
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Status extends WeiboResponse {
    private @WeiboJsonName("created_at") Date createdAt; // 微博创建时间
    private String id; // 微博id
    private String mid; // 微博MID
    private @WeiboJsonName(value = "can_edit", isNewAndNoDesc = true) String canEdit;
    private @WeiboJsonName(value = "show_additional_indication",
        isNewAndNoDesc = true) Integer showAdditionalIndication;
    private @WeiboJsonName(isNewAndNoDesc = true) String appid;
    private @WeiboJsonName(value = "url_objects", isNewAndNoDesc = true) List<StatusUrlObject> urlOjects;
    private String text; // 微博内容
    private Integer textLength;
    private @WeiboJsonName(fromJson = false) Source sourceObj; // 微博来源(暂时用source属性代替)
    private @WeiboJsonName(value = "source_allowclick", isNewAndNoDesc = true) Integer sourceAllowclick;
    private @WeiboJsonName(value = "source_type", isNewAndNoDesc = true) Integer sourceType;
    private String source; // 微博来源(新版本接口好像改成字符串类型，所以把原Source类型的变量改名保留，以防万一)
    private Boolean favorited; // 是否已收藏
    private Boolean truncated; // 是否被截断
    private @WeiboJsonName("in_reply_to_status_id") String inReplyToStatusId; // 回复ID
    private @WeiboJsonName("in_reply_to_user_id") String inReplyToUserId; // 回复人ID
    private @WeiboJsonName("in_reply_to_screen_name") String inReplyToScreenName; // 回复人昵称
    private @WeiboJsonName(value = "pic_urls", isNewAndNoDesc = true) List<StatusPicUrl> statusPicUrls;
    private @WeiboJsonName("thumbnail_pic") String thumbnailPic; // 微博内容中的图片的缩略地址
    private @WeiboJsonName("bmiddle_pic") String bmiddlePic; // 中型图片
    private @WeiboJsonName("original_pic") String originalPic; // 原始图片
    private StatusGeo statusGeo; // 地理信息，保存经纬度，没有时不返回此字段
    private User user; // 作者信息
    private @WeiboJsonName(value = "retweeted_status") Status retweetedStatus; // 转发的博文，内容为status，如果不是转发，则没有此字段
    private @WeiboJsonName(value = "is_paid", isNewAndNoDesc = true) Boolean isPaid;
    private @WeiboJsonName(value = "mblog_vip_type", isNewAndNoDesc = true) Integer mblogVipType;
    private List<JSONObject> annotations; // 元数据，该字段返回的内容不固定，用JSONObject
    private @WeiboJsonName("reposts_count") Long repostsCount; // 转发数
    private @WeiboJsonName("comments_count") Long commentsCount; // 评论数
    private @WeiboJsonName(value = "attitudes_count", isNew = true) Long attitudesCount; // 表态数
    private @WeiboJsonName(value = "pending_approval_count", isNewAndNoDesc = true) Long pendingApprovalCount;
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean isLongText;
    private @WeiboJsonName(value = "reward_exhibition_type", isNewAndNoDesc = true) Integer rewardExhibitionType;
    private @WeiboJsonName(value = "reward_scheme", isNewAndNoDesc = true) String rewardScheme;
    private @WeiboJsonName(value = "hide_flag", isNewAndNoDesc = true) Integer hideFlag;
    private Integer mlevel;
    private Visible visible;
    private @WeiboJsonName(value = "biz_feature", isNewAndNoDesc = true) Integer bizFeature;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer hasActionTypeCard;
    private @WeiboJsonName(value = "darwin_tags", isNewAndNoDesc = true) List<JSONObject> darwinTags;
    private @WeiboJsonName(value = "hot_weibo_tags", isNewAndNoDesc = true) List<JSONObject> hotWeiboTags;
    private @WeiboJsonName(value = "text_tag_tips", isNewAndNoDesc = true) List<JSONObject> textTagTips;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer mblogtype;
    private @WeiboJsonName(isNewAndNoDesc = true) String rid;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer userType;
    private @WeiboJsonName(value = "more_info_type", isNewAndNoDesc = true) Integer moreUnfoType;
    private @WeiboJsonName(value = "number_display_strategy",
        isNewAndNoDesc = true) StatusNumberDisplayStrategy statusNumberDisplayStrategy;
    private @WeiboJsonName(value = "positive_recom_flag", isNewAndNoDesc = true) Integer positiveRecomFlag;
    private @WeiboJsonName(value = "content_auth", isNewAndNoDesc = true) Integer contentAuth;
    private @WeiboJsonName(value = "gif_ids", isNewAndNoDesc = true) String gifIds;
    private @WeiboJsonName(value = "is_show_bulletin", isNewAndNoDesc = true) Integer isShowBulletin;
    private @WeiboJsonName(value = "comment_manage_info",
        isNewAndNoDesc = true) StatusCommentManageInfo statusCommentManageInfo;
    private @WeiboJsonName(value = "pic_num", isNewAndNoDesc = true) Integer picNum;
    private @WeiboJsonName(value = "alchemy_params", isNewAndNoDesc = true) StatusAlchemyParams statusAlchemyParams;
    private @WeiboJsonName(value = "pic_ids") List<JSONObject> picIds; // 微博配图ID
    private @WeiboJsonName(value = "pic_types") String picTypes;
    private @WeiboJsonName List<JSONObject> ad; // 微博流内的推广微博ID
    private @WeiboJsonName("ad") String adOld; // 旧版本是String类型，新版本好像用对象数组代替了，这里先做保留
    private @WeiboJsonName(isNewAndNoDesc = true) String version;
    private @WeiboJsonName(value = "biz_ids", isNewAndNoDesc = true) List<String> bizIds;
    private @WeiboJsonName(value = "page_type", isNewAndNoDesc = true) Integer pageType;
    private @WeiboJsonName(isNewAndNoDesc = true) String mark;
    private @WeiboJsonName(value = "enable_comment_guide", isNewAndNoDesc = true) Boolean enableCommentGuide;
    private @WeiboJsonName(isNewAndNoDesc = true) String fid;
    private @WeiboJsonName(value = "extend_info", isNewAndNoDesc = true) JSONObject extendInfo;
    private @WeiboJsonName(value = "expire_time", isNewAndNoDesc = true) String expireTime;
    private @WeiboJsonName(isNewAndNoDesc = true) String picStatus;
    private @WeiboJsonName(isNewAndNoDesc = true) String uid;
    private @WeiboJsonName(isNewAndNoDesc = true) String pid;
    private @WeiboJsonName(value = "repost_type", isNewAndNoDesc = true) Integer repostType;

    public Status(Response res) {
        super(res);
    }

    public Status(JSONObject json) {
        super(json);
    }

    public Status(String str) {
        super(str);
    }
}