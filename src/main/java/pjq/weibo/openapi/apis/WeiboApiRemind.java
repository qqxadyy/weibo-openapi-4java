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
import pjq.weibo.openapi.constant.BizConstant.StatusType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.model.PostParameter;
import weibo4j.model.UnreadCount;
import weibo4j.model.WeiboException;

/**
 * Remind相关接口<br>
 * 使用{@code Weibo.of(WeiboApiRemind.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiRemind extends Weibo<WeiboApiRemind> {
    /**
     * JSONP回调函数，用于前端调用返回JS格式的信息
     */
    private String callback;

    /**
     * 未读数版本。0：原版未读数，1：新版未读数。默认为0
     */
    private StatusType unreadMessage;

    /**
     * 获取某个用户的各种消息未读数
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public UnreadCount apiGetUnreadCount(String uid) throws WeiboException {
        if (CheckUtils.isEmpty(uid)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.UID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.UID, uid));
        if (CheckUtils.isNotEmpty(callback)) {
            paramList.add(new PostParameter("callback", callback));
        }
        if (CheckUtils.isNotNull(unreadMessage)) {
            paramList.add(new PostParameter("unread_message", StatusType.VALID.value()));
        }
        return new UnreadCount(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.REMIND_UNREAD_COUNT),
            paramListToArray(paramList), accessToken));
    }
}