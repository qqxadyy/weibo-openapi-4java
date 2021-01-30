package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的comment_manage_info
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusCommentManageInfo extends WeiboResponse {
    private @WeiboJsonName(value = "comment_permission_type", isNewAndNoDesc = true) Integer commentPermissionType;
    private @WeiboJsonName(value = "approval_comment_type", isNewAndNoDesc = true) Integer approvalCommentType;

    public StatusCommentManageInfo(JSONObject json) {
        super(json);
    }
}