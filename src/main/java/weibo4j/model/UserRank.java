package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 用户等级
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class UserRank extends WeiboResponse {
    private String uid;
    private Integer rank;

    public UserRank(Response res) {
        super(res);
    }

    public UserRank(JSONObject json) {
        super(json);
    }
}