package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Visible extends WeiboResponse {
    private int type; // 微博的可见性及指定可见分组信息。0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号
    private @WeiboJsonName("list_id") int listid;

    public Visible(JSONObject json) {
        super(json);
    }
}