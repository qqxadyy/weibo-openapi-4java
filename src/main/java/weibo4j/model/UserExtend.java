package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 用户User下的extend
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class UserExtend extends WeiboResponse {
    private String mbprivilege;
    private Privacy privacy;

    public UserExtend(JSONObject json) {
        super(json);
    }
}