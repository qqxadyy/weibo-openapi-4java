package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 微博ID集合的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusIdsPager extends WeiboResponsePager {
    private List<String> statuses; // 微博ID列表

    public StatusIdsPager(JSONObject json) {
        super(json);
    }

    public StatusIdsPager(Response res) {
        super(res);
    }
}