package weibo4j.model;

import lombok.*;
import pjq.weibo.openapi.support.WeiboJsonName;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 获取用户的粉丝数、关注数、微博数、悄悄关注数
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserCounts extends WeiboResponse {
    private String id;
    private @WeiboJsonName("followers_count") Long followersCount;// 粉丝数
    private @WeiboJsonName("friends_count") Long friendsCount;// 关注数
    private @WeiboJsonName("statuses_count") Long statusesCount;// 微博数
    private @WeiboJsonName("private_friends_count") Long privateFriendsCount;// 悄悄关注数
    private @WeiboJsonName(value = "pagefriends_count", isNewAndNoDesc = true) Long pageFriendsCount;

    public UserCounts(Response res) {
        super(res);
    }

    public UserCounts(JSONObject json) {
        super(json);
    }

    public long toLongValue(Long value) {
        return CheckUtils.isNull(value) ? 0 : value;
    }
}