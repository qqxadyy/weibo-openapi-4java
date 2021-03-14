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
package pjq.weibo.openapi.examplesnew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pjq.weibo.openapi.apis.WeiboApiOauth2;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Scope;
import weibo4j.Weibo;
import weibo4j.model.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

public class WeiboApiOAuth2Example {
    public static void main(String[] args) throws WeiboException, IOException {
        WeiboApiOauth2 apiObj = Weibo.of(WeiboApiOauth2.class);// 从配置文件获取配置
        // apiObj = Weibo.of(WeiboApiOauth2.class, WeiboConfiguration.of("", "", "", "")); // 从配置读取获取配置

        String authorizeURL = apiObj.scopes(OAuth2Scope.ALL, OAuth2Scope.EMAIL).apiBuildAuthorizeURL();
        BareBonesBrowserLaunch.openURL(authorizeURL);
        System.out.println("在浏览器中输入以下地址：" + authorizeURL);
        System.out.println("授权成功后输入获取到的state:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String state = br.readLine();
        System.out.println("state: " + state);
        System.out.println("授权成功后输入获取到的code:");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String code = br2.readLine();
        System.out.println("code: " + code);
        try {
            // 获取access_token
            User user = apiObj.apiGetUserByCode(code, state);
            AccessToken tokenInfo = user.getLatestAccessToken();
            String accessToken = tokenInfo.getAccessToken();
            System.out.println("access_token:" + accessToken);
            System.out.println("uid:" + user.getId());
            System.out.println("createAt:" + tokenInfo.getCreateAt());
            System.out.println("expiresInDays:" + tokenInfo.expiresInDays());

            System.out.println(apiObj.apiGetTokenInfo(accessToken));
            // apiObj.apiRevokeOAuth2(accessToken);
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}