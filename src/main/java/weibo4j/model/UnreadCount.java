package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 消息未读数<br/>
 * 因为消息未读数有太多不确定字段且官网没说明，只定义较常用的字段，剩下有需要的则直接从srcJson中解析
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UnreadCount extends WeiboResponse {
    private Integer status; // 新微博未读数
    private Integer follower; // 新粉丝数
    private Integer cmt; // 新评论数
    private Integer dm; // 新私信数
    private @WeiboJsonName("mention_status") Integer mentionStatus; // 新@当前用户的微博数
    private @WeiboJsonName("mention_cmt") Integer mentionCmt; // 新@当前用户的评论数
    private Integer group; // 微群消息未读数
    private Integer notice; // 新通知未读数
    private Integer invite; // 新邀请未读数
    private Integer badge; // 新勋章数
    private Integer photo; // 相册消息未读数

    public UnreadCount(Response res) {
        super(res);
    }

    public UnreadCount(JSONObject json) {
        super(json);
    }
}