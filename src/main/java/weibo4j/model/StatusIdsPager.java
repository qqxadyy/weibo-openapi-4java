package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 微博ID集合的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
@WeiboJsonName
public class StatusIdsPager extends WeiboResponsePager {
    private List<String> statuses; // 微博ID列表

    public StatusIdsPager(JSONObject json) {
        super(json);
    }

    public StatusIdsPager(Response res) {
        super(res);
    }
}