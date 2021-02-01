package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusPager extends WeiboResponsePager {
    private List<Status> statuses; // 微博列表
    private List<Status> reposts; // 转发微博列表
    private @WeiboJsonName(isNewAndNoDesc = true) List<JSONObject> advertises;
    private @WeiboJsonName(isNewAndNoDesc = true) List<JSONObject> ad;
    private @WeiboJsonName(value = "uve_blank", isNewAndNoDesc = true) Integer uveBlank;
    private @WeiboJsonName(value = "has_unread", isNewAndNoDesc = true) Integer hasUnread;

    public StatusPager(JSONObject json) {
        super(json);
    }

    public StatusPager(Response res) {
        super(res);
    }
}