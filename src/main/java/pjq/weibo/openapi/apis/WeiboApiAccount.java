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