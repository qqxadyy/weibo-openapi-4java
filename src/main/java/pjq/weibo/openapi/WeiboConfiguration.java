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
package pjq.weibo.openapi;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.model.WeiboException;

/**
 * 微博配置对象
 * 
 * @author pengjianqiang
 * @date 2021年2月5日
 */
@Data
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboConfiguration {
    /**
     * 创建应用时分配的AppKey
     */
    private String clientId;

    /**
     * 创建应用时分配的AppSecret
     */
    private String clientSecret;

    /**
     * 应用配置的授权回调域名
     */
    private String redirectUri;

    /**
     * 应用配置的安全域名，多个用","分隔
     */
    private String safeDomains;

    /**
     * 根据配置值生成微博配置对象
     * 
     * @param clientId
     * @param clientSecret
     * @param redirectUri
     * @param safeDomains
     *            应用配置的安全域名(如果应用没有发微博的需求，就不用传这个参数)
     * @return
     */
    public static WeiboConfiguration of(String clientId, String clientSecret, String redirectUri, String safeDomains) {
        if (!CheckUtils.areNotEmpty(clientId, clientSecret, redirectUri)) {
            throw new WeiboException("微博应用的clientId、clientSecret、redirectUri配置都不能为空");
        }

        return new WeiboConfiguration().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri)
            .safeDomains(safeDomains);
    }

    /**
     * 获取安全域名的list对象
     * 
     * @return
     * @creator pengjianqiang@2021年3月4日
     */
    public List<String> safeDomainList() {
        return CheckUtils.isNotEmpty(safeDomains) ? Arrays.asList(safeDomains.split(",")) : null;
    }
}