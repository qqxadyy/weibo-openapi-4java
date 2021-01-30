package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
@WeiboJsonName
public class CommentPager extends WeiboResponsePager {
    private List<Comment> comments;
    private @WeiboJsonName(isNewAndNoDesc = true) List<JSONObject> marks;
    private @WeiboJsonName(isNewAndNoDesc = true) Status status;

    public CommentPager(JSONObject json) {
        super(json);
    }

    public CommentPager(Response res) {
        super(res);
    }
}