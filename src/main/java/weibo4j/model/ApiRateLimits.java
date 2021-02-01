package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 具体某个接口的频次限制信息
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class ApiRateLimits extends WeiboResponse {
    private String api; // 接口
    private Integer limit; // 接口限制
    private @WeiboJsonName("limit_time_unit") String limitTimeUnit; // 限制单位
    private @WeiboJsonName("remaining_hits") Integer remainingHits; // 剩余调用次数

    public ApiRateLimits(JSONObject json) {
        super(json);
    }
}