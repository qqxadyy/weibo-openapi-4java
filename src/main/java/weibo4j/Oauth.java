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
 * src/main/java/weibo4j下的文件是从weibo4j-oauth2-beta3.1.1.zip中复制出来的
 * 本项目对这个目录下的部分源码做了重新改造
 * 但是许可信息和"https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1"或源码中已存在的保持一致
 */
package weibo4j;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import weibo4j.http.BASE64Encoder;
import weibo4j.model.AccessToken;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

public class Oauth extends Weibo {
    /**
     * 
     */
    private static final long serialVersionUID = 7003420545330439247L;
    // ----------------------------针对站内应用处理SignedRequest获取accesstoken----------------------------------------
    public String user_id;

    public String getToken() {
        return accessToken;
    }

    /*
     * 解析站内应用post的SignedRequest split为part1和part2两部分
     */
    public String parseSignedRequest(String signed_request)
        throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String[] t = signed_request.split("\\.", 2);
        // 为了和 url encode/decode 不冲突，base64url 编码方式会将
        // '+'，'/'转换成'-'，'_'，并且去掉结尾的'='。 因此解码之前需要还原到默认的base64编码，结尾的'='可以用以下算法还原
        int padding = (4 - t[0].length() % 4);
        for (int i = 0; i < padding; i++) {
            t[0] += "=";
        }
        String part1 = t[0].replace("-", "+").replace("_", "/");

        SecretKey key = new SecretKeySpec(clientSecret().getBytes(), "hmacSHA256");
        Mac m;
        m = Mac.getInstance("hmacSHA256");
        m.init(key);
        m.update(t[1].getBytes());
        String part1Expect = BASE64Encoder.encode(m.doFinal());

        sun.misc.BASE64Decoder decode = new sun.misc.BASE64Decoder();
        String s = new String(decode.decodeBuffer(t[1]));
        if (part1.equals(part1Expect)) {
            return ts(s);
        } else {
            return null;
        }
    }

    /*
     * 处理解析后的json解析
     */
    public String ts(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            accessToken = jsonObject.getString("oauth_token");
            user_id = jsonObject.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accessToken;

    }

    /*----------------------------Oauth接口--------------------------------------*/

    public AccessToken getAccessTokenByCode(String code) throws WeiboException {
        return new AccessToken(client.post(WeiboConfigs.getAccessTokenURL(),
            new PostParameter[] {new PostParameter(MoreUseParamNames.CLIENT_ID, clientId()),
                new PostParameter(MoreUseParamNames.CLIENT_SECRET, clientSecret()),
                new PostParameter("grant_type", "authorization_code"), new PostParameter(MoreUseParamNames.CODE, code),
                new PostParameter(MoreUseParamNames.REDIRECT_URI, redirectURI())},
            false, null));
    }

    public String authorize(String response_type) throws WeiboException {
        return WeiboConfigs.getAuthorizeURL() + "?client_id=" + clientId() + "&redirect_uri=" + redirectURI()
            + "&response_type=" + response_type;
    }

    public String authorize(String response_type, String state) throws WeiboException {
        return WeiboConfigs.getAuthorizeURL() + "?client_id=" + clientId() + "&redirect_uri=" + redirectURI()
            + "&response_type=" + response_type + "&state=" + state;
    }

    public String authorize(String response_type, String state, String scope) throws WeiboException {
        return WeiboConfigs.getAuthorizeURL() + "?client_id=" + clientId() + "&redirect_uri=" + redirectURI()
            + "&response_type=" + response_type + "&state=" + state + "&scope=" + scope;
    }
}
