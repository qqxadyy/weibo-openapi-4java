package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的url_objects
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusUrlObject extends WeiboResponse {
    private @WeiboJsonName(value = "object_id", isNewAndNoDesc = true) String objectId;
    private @WeiboJsonName(value = "like_count", isNewAndNoDesc = true) Long likeCount;
    private @WeiboJsonName(value = "follower_count", isNewAndNoDesc = true) Long followerCount;
    private @WeiboJsonName(value = "search_topic_read_count", isNewAndNoDesc = true) Long searchTopicReadCount;
    private @WeiboJsonName(value = "super_topic_photo_count", isNewAndNoDesc = true) Long superTopicPhotoCount;
    private @WeiboJsonName(value = "search_topic_count", isNewAndNoDesc = true) Long searchTopicCount;
    private @WeiboJsonName(value = "super_topic_status_count", isNewAndNoDesc = true) Long superTopicStatusCount;
    private @WeiboJsonName(value = "asso_like_count", isNewAndNoDesc = true) Long assoLikeCount;
    private @WeiboJsonName(value = "url_ori", isNewAndNoDesc = true) String urlOri;
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean isActionType;
    private @WeiboJsonName(value = "card_info_un_integrity", isNewAndNoDesc = true) Boolean cardInfoUnIntegrity;
    private @WeiboJsonName(isNewAndNoDesc = true) JSONObject info;
    private @WeiboJsonName(isNewAndNoDesc = true) JSONObject object;

    public StatusUrlObject(Response res) {
        super(res);
    }

    public StatusUrlObject(JSONObject json) {
        super(json);
    }
}