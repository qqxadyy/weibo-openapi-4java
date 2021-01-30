package weibo4j.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 返回对象的分页包装类(一般不要实例化,不用abstract修饰是有其它作用)
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class WeiboResponsePager extends WeiboResponse {
    private @WeiboJsonName("previous_cursor") BigInteger previousCursor; // 有的接口没有previous_cursor_str,直接用previous_cursor,下同
    private @WeiboJsonName("next_cursor") BigInteger nextCursor;
    private @WeiboJsonName("total_number") Long totalNumber;
    private @WeiboJsonName("display_total_number") Long displayTotalNumber;
    private Boolean hasvisible;
    private @WeiboJsonName(value = "since_id", isNewAndNoDesc = true) String sinceId;
    private @WeiboJsonName(value = "max_id", isNewAndNoDesc = true) String maxId;
    private @WeiboJsonName(value = "tips_show", isNewAndNoDesc = true) Integer tipsShow;
    private @WeiboJsonName(value = "miss_ids", isNewAndNoDesc = true) List<JSONObject> missIds;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer interval;
    private @WeiboJsonName(value = "request_id", isNewAndNoDesc = true) String requestId;
    private @WeiboJsonName(value = "extend_card", isNewAndNoDesc = true) Integer extendCard;

    public WeiboResponsePager(JSONObject json) {
        super(json);
    }

    public WeiboResponsePager(Response res) {
        super(res);
    }
}