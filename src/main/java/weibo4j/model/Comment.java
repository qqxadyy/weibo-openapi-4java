package weibo4j.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月25日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Comment extends WeiboResponse {
    private String id; // 评论id
    private @WeiboJsonName(value = "like_count", isNewAndNoDesc = true) Long likeCount;
    private @WeiboJsonName(isNewAndNoDesc = true) String gid;
    private @WeiboJsonName("created_at") Date createdAt; // 评论时间
    private String rootid;
    private @WeiboJsonName(value = "floor_number", isNewAndNoDesc = true) Integer floorNumber;
    private String text; // 评论内容
    private @WeiboJsonName(value = "disable_reply", isNewAndNoDesc = true) Integer disableReply;
    private String mid; // 评论MID
    private String source; // 内容来源
    private @WeiboJsonName(value = "source_type", isNewAndNoDesc = true) Integer sourceType;
    private @WeiboJsonName(value = "source_allowclick", isNewAndNoDesc = true) Integer sourceAllowclick;
    private @WeiboJsonName(value = "reply_count", isNewAndNoDesc = true) Long replyCount;
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean liked;
    private @WeiboJsonName(value = "feature_type", isNewAndNoDesc = true) Integer featureType;
    private @WeiboJsonName(value = "url_objects", isNewAndNoDesc = true) List<JSONObject> urlObjects;
    private @WeiboJsonName(value = "cut_tail", isNewAndNoDesc = true) Boolean cutTail;
    private @WeiboJsonName("reply_comment") Comment replyComment; // 评论来源评论，当本评论属于对另一评论的回复时返回此字段
    private User user; // User对象
    private Status status; // Status对象
    private @WeiboJsonName(isNewAndNoDesc = true) String readtimetype;
    private @WeiboJsonName(value = "reply_original_text", isNewAndNoDesc = true) String replyOriginalText;
    private @WeiboJsonName(isNewAndNoDesc = true) String appKey;

    public Comment(Response res) {
        super(res);
    }

    public Comment(JSONObject json) {
        super(json);
    }

    public Comment(String str) {
        super(str);
    }
}