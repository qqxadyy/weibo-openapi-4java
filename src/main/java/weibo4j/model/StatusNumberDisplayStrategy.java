package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的number_display_strategy
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusNumberDisplayStrategy extends WeiboResponse {
    private @WeiboJsonName(value = "apply_scenario_flag", isNewAndNoDesc = true) Integer commentPermissionType;
    private @WeiboJsonName(value = "display_text_min_number", isNewAndNoDesc = true) Long approvalCommentType;
    private @WeiboJsonName(value = "display_text", isNewAndNoDesc = true) String displayText;

    public StatusNumberDisplayStrategy(JSONObject json) {
        super(json);
    }
}