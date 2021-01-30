package pjq.weibo.openapi.examplesnew;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pjq.weibo.openapi.apis.WeiboApiOauth2;
import pjq.weibo.openapi.constant.ParamConstant.OAuth2Scope;
import weibo4j.Weibo;
import weibo4j.model.AccessToken;
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
        try {
            // 获取access_token
            AccessToken token = WeiboApiOauth2.apiGetAccessTokenByCode(code);
            String accessToken = token.getAccessToken();
            System.out.println("access_token:" + accessToken);
            System.out.println("uid:" + token.getUid());

            System.out.println(WeiboApiOauth2.apiGetTokenInfo(accessToken));
            // System.out.println(WeiboApiOauth2.apiRevokeoauth2(accessToken));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}