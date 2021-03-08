/*
 * Copyright © 2021 pengjianqiang
 * All rights reserved.
 * 项目名称：微博开放平台API-JAVA SDK
 * 项目描述：基于微博开放平台官网的weibo4j-oauth2-beta3.1.1包及新版接口做二次开发
 * 项目地址：https://github.com/qqxadyy/weibo-openapi-4java
 * 许可证信息：见下文
 *
 * ======================================================================
 *
 * The MIT License
 * Copyright © 2021 pengjianqiang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pjq.weibo.openapi.apis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import pjq.weibo.openapi.constant.BizConstant.TrueOrFalse;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Display;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Language;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Scope;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboCacher;
import pjq.weibo.openapi.support.WeiboHttpClient.MethodType;
import pjq.weibo.openapi.utils.CharsetUtils;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.DateTimeUtils;
import weibo4j.Oauth;
import weibo4j.Weibo;
import weibo4j.model.AccessToken;
import weibo4j.model.AccessTokenInfo;
import weibo4j.model.PostParameter;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

/**
 * oauth2相关接口<br>
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
@Slf4j
public class WeiboApiOauth2 extends Weibo<WeiboApiOauth2> {
    private static Oauth apiOld = new Oauth();

    /**
     * 申请scope权限所需参数，可一次申请多个scope权限，用逗号分隔
     */
    private OAuth2Scope[] scopes;

    /**
     * 是否使用state参数，true：是，false：否。默认true，并生成随机state值
     */
    private TrueOrFalse useState;

    /**
     * 授权页面的终端类型
     */
    private OAuth2Display display;

    /**
     * 是否强制用户重新登录，true：是，false：否。默认false
     */
    private TrueOrFalse forcelogin;

    /**
     * 授权页语言，缺省为中文简体版，en为英文版<br>
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
     * 获取oahtu2-authorize接口的URL<br>
     * 为保证安全，获取授权URL时强制传state值
     * 
     * @return
     * @throws WeiboException
     */
    public String apiBuildAuthorizeURL() throws WeiboException {
        StringBuilder url = new StringBuilder();
        url.append(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_AUTHORIZE));
        url.append("?").append(MoreUseParamNames.CLIENT_ID).append("=").append(clientId());

        url.append("&").append(MoreUseParamNames.REDIRECT_URI).append("=");
        try {
            url.append(URLEncoder.encode(redirectURI(), CharsetUtils.UTF_8));
        } catch (UnsupportedEncodingException e) {
            url.append(redirectURI());
        }

        if (CheckUtils.isNotEmpty(scopes)) {
            String scopesStr = Arrays.stream(scopes)
                .collect(StringBuilder::new, (str, scope) -> str.append(scope.value()).append(","), (s1, s2) -> {
                }).toString();
            url.append("&scope=").append(scopesStr.substring(0, scopesStr.length() - 1));
        }

        // useState默认为true
        if (CheckUtils.isNull(useState)) {
            useState = TrueOrFalse.TRUE;
        }
        if (TrueOrFalse.TRUE.equals(useState)) {
            String state = UUID.randomUUID().toString().replaceAll("-", "");
            WeiboCacher.cacheStateInfoOfAuthorize(state, clientId());
            url.append("&state=").append(state);
        }

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
    public AccessToken apiGetAccessTokenByCode(String code, String state) throws WeiboException {
        if (CheckUtils.isEmpty(code)) {
            throw WeiboException.ofParamCanNotNull("code");
        }
        if (CheckUtils.isNotEmpty(state)) {
            // 如果state不为空，则检查安全性
            WeiboCacher.existsStateOfAuthorize(state);
        }
        AccessToken tokenInfo = ((Oauth)apiOld.weiboConfiguration(weiboConfiguration())).getAccessTokenByCode(code);
        tokenInfo.setClientId(clientId());
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
    public User apiGetUserByCode(String code, String state) throws WeiboException {
        AccessToken accessToken = apiGetAccessTokenByCode(code, state);
        User user = null;
        try {
            user = Weibo.of(WeiboApiUsers.class, accessToken.getAccessToken(), weiboConfiguration())
                .apiShowUserById(accessToken.getUid());
            user.addAccessToken(accessToken);
            return WeiboCacher.cacheUser(user);
        } catch (Exception e) {
            // 如果已换取到token信息，但是查询授权用户信息失败，则先返回只带token信息的User对象，但不缓存(用于后续通过授权token重新查询用户信息)
            user = new User();
            user.addAccessToken(accessToken);
            return user;
        }
    }

    /**
     * 查询用户access_token的授权相关信息
     * 
     * @param accessToken
     *            授权后的token
     * @return
     * @throws WeiboException
     */
    public AccessToken apiGetTokenInfo(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }
        WeiboCacher.checkAccessTokenExists(accessToken);
        AccessTokenInfo tokenInfo =
            new AccessTokenInfo(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_GET_TOKEN_INFO),
                new PostParameter[] {new PostParameter(MoreUseParamNames.ACCESS_TOKEN, accessToken)}, false, null));
        return tokenInfo.toAccessTokenObj(clientId(), accessToken);
    }

    /**
     * 查询用户access_token的授权相关信息并缓存<br>
     * 用于系统缓存中没有token详细信息而需要根据token值查询出并缓存的情况
     * 
     * @param accessToken
     *            授权后的token
     * @return
     * @throws WeiboException
     */
    public AccessToken apiGetTokenInfoAndCache(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }
        AccessTokenInfo tokenInfo =
            new AccessTokenInfo(client.post(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_GET_TOKEN_INFO),
                new PostParameter[] {new PostParameter(MoreUseParamNames.ACCESS_TOKEN, accessToken)}, false, null));
        return WeiboCacher.cacheAccessToken(tokenInfo.toAccessTokenObj(clientId(), accessToken));
    }

    /**
     * 授权回收接口，帮助开发者主动取消用户的授权<br>
     * 注意：取消授权后再调用其它接口，会报错[21321:Applications over the unaudited use restrictions:未审核的应用使用人数超过限制]，本地测试时尤其需要注意
     * 
     * @param accessToken
     *            授权后的token
     * @throws WeiboException
     */
    public void apiRevokeOAuth2(String accessToken) throws WeiboException {
        if (CheckUtils.isEmpty(accessToken)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.ACCESS_TOKEN);
        }

        try {
            // 实际是否成功取消授权都先移除缓存中的相关信息(移除前会检查缓存中有没有token信息)
            WeiboCacher.removeCachesByTokenWhenRevokeOAuth(accessToken);
        } catch (Exception e) {
            // 移除缓存时报错不影响后续发取消授权的请求
        }

        // 异步发送取消授权的请求，不影响后续的业务流程
        try {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put(MoreUseParamNames.ACCESS_TOKEN, accessToken);
            client.httpRequestAsync(WeiboConfigs.getApiUrl(WeiboConfigs.OAUTH2_REVOKE_OAUTH2), paramMap,
                MethodType.POST, false, null, (isSuccess, statusCode, responseStr) -> {
                    log.info("取消微博授权结果[accessToken={},response={}]", accessToken, responseStr);
                });
        } catch (Exception e) {
        }
    }

    /**
     * 取消授权回调<br>
     * 这个方法必须在收到取消授权回调请求的时候才能调用<br>
     * 考虑到安全问题，该方法不做实质处理
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
        // WeiboCacher.removeCachesByUidWhenRevokeOAuth(uid); // 收到取消授权的回调时再清除一次相关缓存，确保相关信息被移除
    }
}