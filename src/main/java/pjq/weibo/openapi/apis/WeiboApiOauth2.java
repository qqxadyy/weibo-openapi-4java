package pjq.weibo.openapi.apis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.BizConstant.TrueOrFalse;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Display;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Language;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Scope;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboCacher;
import pjq.weibo.openapi.utils.CharsetUtils;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.DateTimeUtils;
import weibo4j.Oauth;
import weibo4j.Weibo;
import weibo4j.model.AccessToken;
import weibo4j.model.AccessTokenInfo;
import weibo4j.model.PostParameter;
import weibo4j.model.RevokeOAuth2;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

/**
 * oauth2相关接口<br/>
 * 使用{@code Weibo.of(WeiboApiOauth2.class)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiOauth2 extends Weibo<WeiboApiOauth2> {
    private static Oauth apiOld = new Oauth();

    /**
     * 申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔
     */
    private OAuth2Scope[] scopes;

    /**
     * 授权页面的终端类型
     */
    private OAuth2Display display;

    /**
     * 是否强制用户重新登录，true：是，false：否。默认false
     */
    private TrueOrFalse forcelogin;

    /**
     * 授权页语言，缺省为中文简体版，en为英文版<br/>
     * 英文版好像有问题，账号密码正确对会提示用扫码登录，建议不传该参数，默认用中文版
     */
    private OAuth2Language language;

    @Override
    protected String checkAccessToken() {
        return null; // 该接口不用检查access_token
    }

    /**
     * 申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔
     * 
     * @param scopes
     * @return
     */
    public WeiboApiOauth2 scopes(OAuth2Scope... scopes) {
        this.scopes = scopes;
        return this;
    }

    /*----------------------------Oauth接口--------------------------------------*/

    /**
     * 获取oahtu2-authorize接口的URL<br/>
     * 为保证安全，获取授权URL时强制传state值
     * 
     * @return
     * @throws WeiboException
     */
    public String apiBuildAuthorizeURL() throws WeiboException {
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        WeiboCacher.cacheStateOfAuthorize(state);

        StringBuilder url = new StringBuilder();
        url.append(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_AUTHORIZE));
        url.append("?").append(MoreUseParamNames.CLIENT_ID).append("=").append(WeiboConfigs.getClientId());

        url.append("&").append(MoreUseParamNames.REDIRECT_URI).append("=");
        try {
            url.append(URLEncoder.encode(WeiboConfigs.getRedirectURI(), CharsetUtils.UTF_8));
        } catch (UnsupportedEncodingException e) {
            url.append(WeiboConfigs.getRedirectURI());
        }

        if (CheckUtils.isNotEmpty(scopes)) {
            String scopesStr = Arrays.stream(scopes)
                .collect(StringBuilder::new, (str, scope) -> str.append(scope.value()).append(","), (s1, s2) -> {
                }).toString();
            url.append("&scope=").append(scopesStr.substring(0, scopesStr.length() - 1));
        }
        url.append("&state=").append(state);
        if (CheckUtils.isNotNull(display)) {
            url.append("&display=").append(display.value());
        }
        if (CheckUtils.isNotNull(forcelogin)) {
            url.append("&forcelogin=").append(forcelogin.value());
        }
        if (OAuth2Language.ENGLISH.equals(language)) {
            url.append("&language=").append(language.value()); // 只有指定英文时才传值
        }
        return url.toString();
    }

    /**
     * 获取授权过的AccessToken
     * 
     * @param code
     *            授权回调后获取的code
     * @param state
     *            授权回调地址返回的state(该参数不是调接口需要的参数，而是用于安全验证)
     * @return
     * @throws WeiboException
     */
    public static AccessToken apiGetAccessTokenByCode(String code, String state) throws WeiboException {
        if (CheckUtils.isEmpty(code)) {
            throw WeiboException.ofParamCanNotNull("code");
        }
        if (CheckUtils.isEmpty(state)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.STATE);
        }
        if (!WeiboCacher.existsStateOfAuthorize(state)) {
            throw new WeiboException("不存在" + MoreUseParamNames.STATE + "信息，该授权回调可能不安全");
        }
        AccessToken tokenInfo = apiOld.getAccessTokenByCode(code);
        tokenInfo.setCreateAt(DateTimeUtils.currentDateObj());
        return WeiboCacher.cacheAccessToken(tokenInfo);
    }

    /**
     * 用code换取到accessToken并通过该token获取到User信息
     * 
     * @param code
     *            授权回调后获取的code
     * @param state
     *            授权回调地址返回的state(该参数不是调接口需要的参数，而是用于安全验证)
     * @return
     * @throws WeiboException
     */
    public static User apiGetUserByCode(String code, String state) throws WeiboException {
        AccessToken accessToken = apiGetAccessTokenByCode(code, state);
        User user = Weibo.of(WeiboApiUsers.class, accessToken.getAccessToken()).apiShowUserById(accessToken.getUid());
        user.setAccessToken(accessToken);
        return WeiboCacher.cacheUser(user);
    }

    /**
     * 查询用户access_token的授权相关信息
     * 
     * @param accessToken
     *            授权后的token
     * @return
     * @throws WeiboException
     */
    public static AccessTokenInfo apiGetTokenInfo(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }
        WeiboCacher.checkAccessTokenExists(accessToken);
        return new AccessTokenInfo(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_GET_TOKEN_INFO),
            new PostParameter[] {new PostParameter(MoreUseParamNames.ACCESS_TOKEN, accessToken)}, false, null));
    }

    /**
     * 授权回收接口，帮助开发者主动取消用户的授权<br/>
     * 注意：取消授权后再调用其它接口，会报错[21321:Applications over the unaudited use restrictions:未审核的应用使用人数超过限制]，本地测试时尤其需要注意
     * 
     * @param accessToken
     *            授权后的token
     * @return
     * @throws WeiboException
     */
    public static boolean apiRevokeOAuth2(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }
        WeiboCacher.checkAccessTokenExists(accessToken);
        RevokeOAuth2 result = new RevokeOAuth2(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_REVOKE_OAUTH2),
            new PostParameter[] {new PostParameter(MoreUseParamNames.ACCESS_TOKEN, accessToken)}, false, null));
        if ("true".equalsIgnoreCase(result.getResult())) {
            // 成功取消授权，则缓存中的相关信息
            WeiboCacher.removeCachesByTokenWhenRevokeOAuth(accessToken);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消授权回调<br/>
     * 这个方法必须在收到取消授权回调请求的时候才能调用
     * 
     * @param clientId
     *            应用clientid/appkey
     * @param uid
     *            取消授权的用户
     * @param authEnd
     *            取消授权的时间
     * @param verification
     *            应该是用于验证该请求是否微博发出参数，但是在官网没找到相关说明
     */
    public static void apiRevokeOAuth2Callback(String clientId, String uid, String authEnd, String verification) {
        if (!WeiboConfigs.getClientId().equals(clientId)) {
            throw new WeiboException("clientId与配置值不一致，该取消授权回调请求不安全");
        }
        WeiboCacher.removeCachesByUidWhenRevokeOAuth(uid); // 收到取消授权的回调时再清除一次相关缓存，确保相关信息被移除
    }
}