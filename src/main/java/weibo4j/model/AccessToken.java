package weibo4j.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class AccessToken extends WeiboResponse implements Serializable {
    private @WeiboJsonName(MoreUseParamNames.ACCESS_TOKEN) String accessToken;
    private @WeiboJsonName("expires_in") String expiresIn; // token有效期，单位秒
    private @WeiboJsonName(value = "refresh_token", isDeleted = true) String refreshToken; // 新版接口中已不返回
    private String uid;
    private @WeiboJsonName("remind_in") String remindIn; // 官网说明该参数即将废弃，用expires_in
    private @WeiboJsonName(isNewAndNoDesc = true) String isRealName;
    private @WeiboJsonName(fromJson = false) Date createAt; // 用于记录授权时间

    public AccessToken(Response res) {
        super(res);
    }

    public AccessToken(JSONObject json) {
        super(json);
    }
}