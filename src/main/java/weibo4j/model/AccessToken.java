package weibo4j.model;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import lombok.*;
import lombok.EqualsAndHashCode.Exclude;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.support.WeiboJsonName;
import pjq.weibo.openapi.utils.DateTimeUtils;
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
    private @WeiboJsonName("expires_in") @Exclude String expiresIn; // token有效期，单位秒
    private @WeiboJsonName(value = "refresh_token") @Exclude String refreshToken; // 好像是安卓的SDK才会返回
    private @Exclude String uid;
    private @WeiboJsonName("remind_in") @Exclude String remindIn; // 官网说明该参数即将废弃，用expires_in
    private @WeiboJsonName(isNewAndNoDesc = true) @Exclude String isRealName;
    private @WeiboJsonName(fromJson = false) @Exclude Date createAt; // 用于记录授权时间
    private @WeiboJsonName(fromJson = false) @Exclude Date authEnd; // 用于记录取消授权时间
    private @WeiboJsonName(fromJson = false) @Exclude String clientId; // 用于记录申请授权码的clientId

    public AccessToken(Response res) {
        super(res);
    }

    public AccessToken(JSONObject json) {
        super(json);
    }

    /**
     * 获取授权码的有效天数
     * 
     * @return
     */
    public int expiresInDays() {
        try {
            int expiresInDays = Integer.parseInt(this.expiresIn) / (60 * 60 * 24);
            return Double.valueOf(Math.floor(Math.min(300, expiresInDays))).intValue(); // 最长不超过300天(开发者获取到的授权有效期是5年)
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean active() {
        return DateTimeUtils.currentDate()
            .isBefore(DateTimeUtils.plus(DateTimeUtils.dateToLocalDate(createAt), expiresInDays(), ChronoUnit.DAYS));
    }
}