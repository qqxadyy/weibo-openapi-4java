package weibo4j.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * get_token_info的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class AccessTokenInfo extends WeiboResponse implements Serializable {
    private String uid;
    private String appkey;
    private String scope;
    private @WeiboJsonName(value = "create_at", isDeleted = true) String createAt; // 新版接口中已不返回
    private @WeiboJsonName("expire_in") String expireIn; // 官网说明该参数即将废弃，用expires_in

    public AccessTokenInfo(Response res) {
        super(res);
    }

    public AccessTokenInfo(JSONObject json) {
        super(json);
    }

    public AccessTokenInfo(String res) {
        super(res);
    }
}