package weibo4j.model;

import java.util.List;

import lombok.*;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 用户ID集合的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserIdsPager extends WeiboResponsePager {
    private List<String> ids; // 用户ID列表

    public UserIdsPager(JSONObject json) {
        super(json);
    }

    public UserIdsPager(Response res) {
        super(res);
    }
}