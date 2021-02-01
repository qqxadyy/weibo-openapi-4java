package weibo4j.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * revokeoauth2的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class RevokeOAuth2 extends WeiboResponse implements Serializable {
    private String result;

    public RevokeOAuth2(Response res) {
        super(res);
    }

    public RevokeOAuth2(JSONObject json) {
        super(json);
    }
}