package weibo4j.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;

/**
 * revokeoauth2的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@SuppressWarnings("serial")
@ToString
@Getter
@EqualsAndHashCode(callSuper = false)
public class RevokeOAuth2 extends WeiboResponse implements Serializable {
    private @WeiboJsonName("result") String result;

    public RevokeOAuth2(Response res) {
        super(res);
    }

    public RevokeOAuth2(String res) {
        super(res);
    }
}