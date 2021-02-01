package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的alchemy_params
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusAlchemyParams extends WeiboResponse {
    private @WeiboJsonName(value = "comment_guide_type", isNewAndNoDesc = true) Integer commentGuideType;
    private @WeiboJsonName(value = "ug_red_envelope", isNewAndNoDesc = true) Boolean ugRedEnvelope;

    public StatusAlchemyParams(JSONObject json) {
        super(json);
    }
}