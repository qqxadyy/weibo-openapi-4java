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
package pjq.weibo.openapi.examplesnew.capis;

import java.io.IOException;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;

import pjq.commons.constant.CommonEnumConstant.YesOrNoInt;
import pjq.weibo.openapi.apis.capis.WeiboCApiStatuses;
import pjq.weibo.openapi.constant.ParamConstant.QueryIdType;
import weibo4j.Weibo;
import weibo4j.model.SimpleGeo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboCApiStatusesExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CBBIyKD5d5233a353GSQCmD";

            WeiboCApiStatuses apiObj = Weibo.of(WeiboCApiStatuses.class, accessToken);

            // System.out.println(apiObj.apiGetFriendsLatestStatuses());
            // System.out.println(apiObj.isGetLongText(YesOrNoInt.YES).count(1).apiGetMyLatestStatuses());
            // System.out.println(apiObj.apiGetStatusesWhichRepostedThis(""));
            // System.out.println(apiObj.apiGetStatusesAtMe());
            // System.out.println(apiObj.apiGetStatusesByIds());
            // System.out.println(apiObj.apiGetStatusesCounts());
            // System.out.println(apiObj.apiQueryMidById(QueryIdType.STATUS, ""));
            // System.out.println(apiObj.isBase62(YesOrNoInt.YES).apiQueryIdByMid(QueryIdType.STATUS, ""));
            // System.out.println(apiObj.apiRepostStatus("", ""));
            // System.out.println(apiObj.apiDestroy(""));
            // System.out.println(apiObj.apiFilterOneStatus(""));
            // System.out.println(apiObj.apiFilterMentionsAndShield(""));

            // System.out.println(apiObj.isLongtext(YesOrNoInt.YES).simpleGeo(new SimpleGeo("113.27324", "23.15792"))
            // .annotations(Arrays.asList(JSON.parseObject("{\"testAnno\":\"sss\"}")))
            // .apiShareStatus("是是是", new String[] {"d://work/2.png"}));

            // System.out.println(apiObj.apiShareStatusAsync("ss", new String[] {"d://work/2.png"},
            // (isSuccess, statusCode, responseStr) -> {
            // Response res = new Response();
            // res.setResponseAsString(responseStr);
            // System.out.println(new Status(res));
            // }));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}