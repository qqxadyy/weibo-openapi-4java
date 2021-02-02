package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 用户user下的verified_detail
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserVerifiedDetail extends WeiboResponse {
    private @WeiboJsonName(isNewAndNoDesc = true) Integer custom;
    private @WeiboJsonName(isNewAndNoDesc = true) List<JSONObject> data;

    public UserVerifiedDetail(JSONObject json) {
        super(json);
    }
}