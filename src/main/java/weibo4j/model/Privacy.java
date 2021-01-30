package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.constant.BizConstant.StatusType;
import pjq.weibo.openapi.constant.ParamConstant.PrivacyUserType;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 获取隐私设置信息
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Privacy extends WeiboResponse {
    private StatusType badge; // 勋章是否可见，0：不可见、1：可见
    private PrivacyUserType comment; // 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
    private StatusType geo; // 是否开启地理信息，0：不开启、1：开启
    private PrivacyUserType message; // 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
    private StatusType mobile; // 是否可以通过手机号码搜索到我，0：不可以、1：可以
    private StatusType realname; // 是否可以通过真名搜索到我，0：不可以、1：可以
    private @WeiboJsonName(value = "profile_url_type", isNewAndNoDesc = true) StatusType profileUrlType; // 暂时没从返回中见过，可能错
    private StatusType webim; // 是否开启webim， 0：不开启、1：开启

    public Privacy(Response res) {
        super(res);
    }

    public Privacy(JSONObject json) {
        super(json);
    }
}