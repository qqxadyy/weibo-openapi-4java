package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 用户邮箱的返回不够通用，直接从json处理
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserEmails extends WeiboResponse {
    private String email;

    public UserEmails(JSONObject json) {
        super(json);
    }

    public UserEmails(Response res) {
        super(res);
    }
}