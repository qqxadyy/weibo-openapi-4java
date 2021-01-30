package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 两个用户之间的关注关系
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class FriendShipEachOther extends WeiboResponse {
    private FriendShipFollowerDetail source;
    private FriendShipFollowerDetail target;

    public FriendShipEachOther(Response res) {
        super(res);
    }

    public FriendShipEachOther(JSONObject json) {
        super(json);
    }
}