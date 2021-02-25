package pjq.weibo.openapi.apis;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.Account;
import weibo4j.Weibo;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.UserEmails;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;

/**
 * Account相关接口<br>
 * 使用{@code Weibo.of(WeiboApiAccount.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiAccount extends Weibo<WeiboApiAccount> {
    private Account apiOld;

    @Override
    protected void afterOfInit(String accessToken, String clientId) {
        apiOld = new Account(accessToken);
    }

    /**
     * 获取当前登录用户的API访问频率限制情况
     * 
     * @return
     * @throws WeiboException
     */
    public RateLimitStatus apiGetAccountRateLimitStatus() throws WeiboException {
        return apiOld.getAccountRateLimitStatus();
    }

    /**
     * OAuth授权之后，获取授权用户的UID
     * 
     * @return
     * @throws WeiboException
     */
    public String apiGetUid() throws WeiboException {
        try {
            return apiOld.getUid().getString(MoreUseParamNames.UID);
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 获取用户的联系邮箱
     * 
     * @return
     * @throws WeiboException
     */
    public List<UserEmails> apiGetUserEmails() throws WeiboException {
        return WeiboResponse.buildList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.ACCOUNT_PROFILE_EMAIL), accessToken), UserEmails.class);
    }
}