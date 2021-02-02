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
        String authorizeURL =
            Weibo.of(WeiboApiOauth2.class).scopes(OAuth2Scope.ALL, OAuth2Scope.EMAIL).apiBuildAuthorizeURL();
        BareBonesBrowserLaunch.openURL(authorizeURL);
        System.out.println("在浏览器中输入以下地址：" + authorizeURL);
        System.out.println("授权成功后输入获取到的code");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();
        System.out.println("code: " + code);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String state = br2.readLine();
        System.out.println("state: " + state);
        try {
            // 获取access_token
            User user = WeiboApiOauth2.apiGetUserByCode(code, state);
            AccessToken tokenInfo = user.getAccessToken();
            String accessToken = tokenInfo.getAccessToken();
            System.out.println("access_token:" + accessToken);
            System.out.println("uid:" + user.getId());
            System.out.println("createAt:" + tokenInfo.getCreateAt());

            System.out.println(WeiboApiOauth2.apiGetTokenInfo(accessToken));
            // System.out.println(WeiboApiOauth2.apiRevokeOAuth2(accessToken));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}