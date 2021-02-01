package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 两个用户之间的关注关系详情
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class FriendShipFollowerDetail extends WeiboResponse {
    private String id; // 用户id
    private @WeiboJsonName("screen_name") String screenName; // 微博昵称
    private @WeiboJsonName(value = "followed_by", isNewAndNoDesc = true) Boolean followedBy; // 是否被另一个用户关注
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean following; // 是否已关注另一个用户
    private @WeiboJsonName(value = "notifications_enabled", isNewAndNoDesc = true) Boolean notificationsEnabled;

    public FriendShipFollowerDetail(Response res) {
        super(res);
    }

    public FriendShipFollowerDetail(JSONObject json) {
        super(json);
    }
}