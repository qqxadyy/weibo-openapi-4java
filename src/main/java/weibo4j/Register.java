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

import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.WeiboConfig;

public class Register extends Weibo {

    private static final long serialVersionUID = -6809545704064413209L;

    public Register(String access_token) {
        this.accessToken = access_token;
    }

    /**
     * 验证昵称是否可用，并给予建议昵称
     * 
     * @param nickname
     *            需要验证的昵称。4-20个字符，支持中英文、数字、"_"或减号。必须做URLEncode，采用UTF-8编码
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/register/verify_nickname
     * @since JDK 1.5
     */
    public JSONObject verifyNickname(String nickname) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "register/verify_nickname.json",
            new PostParameter[] {new PostParameter("nickname", nickname)}, accessToken).asJSONObject();
    }
}
