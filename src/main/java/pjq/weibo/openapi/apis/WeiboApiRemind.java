package pjq.weibo.openapi.apis;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.BizConstant.StatusType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.model.PostParameter;
import weibo4j.model.UnreadCount;
import weibo4j.model.WeiboException;

/**
 * Remind相关接口<br/>
 * 使用{@code Weibo.of(WeiboApiRemind.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiRemind extends Weibo<WeiboApiRemind> {
    /**
     * JSONP回调函数，用于前端调用返回JS格式的信息
     */
    private String callback;

    /**
     * 未读数版本。0：原版未读数，1：新版未读数。默认为0
     */
    private StatusType unreadMessage;

    /**
     * 获取某个用户的各种消息未读数
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public UnreadCount apiGetUnreadCount(String uid) throws WeiboException {
        if (CheckUtils.isEmpty(uid)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.UID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.UID, uid));
        if (CheckUtils.isNotEmpty(callback)) {
            paramList.add(new PostParameter("callback", callback));
        }
        if (CheckUtils.isNotNull(unreadMessage)) {
            paramList.add(new PostParameter("unread_message", StatusType.VALID.value()));
        }
        return new UnreadCount(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.REMIND_UNREAD_COUNT),
            paramListToArray(paramList), accessToken));
    }
}