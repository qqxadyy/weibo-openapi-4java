package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 微博评论数等
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusesCounts extends WeiboResponse {
    private String id; // 微博ID
    private Integer comments; // 评论数
    private Integer reposts; // 转发数
    private Integer attitudes; // 表态数
    private @WeiboJsonName(value = "number_display_strategy",
        isNewAndNoDesc = true) StatusNumberDisplayStrategy statusNumberDisplayStrategy;

    public StatusesCounts(JSONObject json) {
        super(json);
    }
}