package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * User下的Insecurity
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class UserInsecurity extends WeiboResponse {
    private @WeiboJsonName(value = "sexual_content", isNewAndNoDesc = true) Boolean sexualContent;

    public UserInsecurity(JSONObject json) {
        super(json);
    }
}