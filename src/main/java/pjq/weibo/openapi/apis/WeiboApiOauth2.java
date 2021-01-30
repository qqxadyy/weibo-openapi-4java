package pjq.weibo.openapi.apis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.BizConstant.TrueOrFalse;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Display;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Language;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Scope;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CharsetUtils;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Oauth;
import weibo4j.Weibo;
import weibo4j.model.AccessToken;
import weibo4j.model.AccessTokenInfo;
import weibo4j.model.PostParameter;
import weibo4j.model.RevokeOAuth2;
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

    /**
     * 浏览器端的授权登录成功跳转地址，display为default、mobile或wap时才生效<br/>
     * 这个不是微博官网的接口参数
     */
    private String authSuccRedirectUri;

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
        if (CheckUtils.isNull(display) || OAuth2Display.DEFAULT.equals(display) || OAuth2Display.MOBILE.equals(display)
            || OAuth2Display.WAP.equals(display)) {
            // 处理authSuccRedirectUri
        }
        return url.toString();
    }

    /**
     * 获取授权过的AccessToken
     * 
     * @param code
     *            授权回调后获取的code
     * @return
     * @throws WeiboException
     */
    public static AccessToken apiGetAccessTokenByCode(String code) throws WeiboException {
        if (CheckUtils.isEmpty(code)) {
            throw WeiboException.ofParamCanNotNull("code");
        }
        return apiOld.getAccessTokenByCode(code);
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
    public static RevokeOAuth2 apiRevokeoauth2(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }
        return new RevokeOAuth2(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_REVOKE_OAUTH2),
            new PostParameter[] {new PostParameter(MoreUseParamNames.ACCESS_TOKEN, accessToken)}, false, null));
    }
}