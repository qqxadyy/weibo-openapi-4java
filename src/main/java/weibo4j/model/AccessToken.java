package weibo4j.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * access_token的返回(直接在原始类中改造)
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class AccessToken extends WeiboResponse implements Serializable {
    private @WeiboJsonName(MoreUseParamNames.ACCESS_TOKEN) String accessToken;
    private @WeiboJsonName("expires_in") String expiresIn;
    private @WeiboJsonName(value = "refresh_token", isDeleted = true) String refreshToken; // 新版接口中已不返回
    private String uid;
    private @WeiboJsonName("remind_in") String remindIn; // 官网说明该参数即将废弃，用expires_in
    private @WeiboJsonName(isNewAndNoDesc = true) String isRealName;

    public AccessToken(Response res) {
        super(res);
    }

    public AccessToken(JSONObject json) {
        super(json);
    }

    public AccessToken(String res) {
        super(res);
    }
}