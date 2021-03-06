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
import pjq.commons.utils.CheckUtils;
import pjq.weibo.openapi.constant.ParamConstant.CommonLanguage;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboApiParamScope;
import weibo4j.Weibo;
import weibo4j.model.CodeAndNameObject;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;

/**
 * Common相关接口<br>
 * 使用<code>Weibo.of({@link WeiboApiCommon}.class,accessToken)</code>生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiCommon extends Weibo<WeiboApiCommon> {
    /**
     * 国家的首字母，a-z或A-Z，可为空代表返回全部，默认为全部，只能传一个
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMON_LOCATION)})
    private String capital;

    /**
     * 返回的语言版本，默认为简体中文
     */
    @Setter(onMethod_ = {@WeiboApiParamScope(WeiboApiParamScope.COMMON_COMMON)})
    private CommonLanguage language;

    /**
     * 获取国家列表
     * 
     * @return
     * @throws WeiboException
     */
    public List<CodeAndNameObject> apiGetCountry() throws WeiboException {
        List<PostParameter> paramList = commonParam();
        return CodeAndNameObject.builList(client
            .get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_COUNTRY), paramListToArray(paramList), accessToken())
            .asJSONArray());
    }

    /**
     * 获取省份列表
     * 
     * @param country
     *            国家的代码值
     * @return
     * @throws WeiboException
     */
    public List<CodeAndNameObject> apiGetProvince(String country) throws WeiboException {
        if (CheckUtils.isEmpty(country)) {
            throw WeiboException.ofParamCanNotNull("country");
        }
        List<PostParameter> paramList = commonParam();
        paramList.add(new PostParameter("country", country));
        return CodeAndNameObject.builList(client
            .get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_PROVINCE), paramListToArray(paramList), accessToken())
            .asJSONArray());
    }

    /**
     * 获取城市列表
     * 
     * @param province
     *            省份的代码值
     * @return
     * @throws WeiboException
     */
    public List<CodeAndNameObject> apiGetCity(String province) throws WeiboException {
        if (CheckUtils.isEmpty(province)) {
            throw WeiboException.ofParamCanNotNull("province");
        }
        List<PostParameter> paramList = commonParam();
        paramList.add(new PostParameter("province", province));
        return CodeAndNameObject.builList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_CITY), paramListToArray(paramList), accessToken())
                .asJSONArray());
    }

    /**
     * 通过地址编码获取地址名称<br>
     * 注：该接口新版可传language参数，官网上没写
     * 
     * @param codes
     * @return
     * @throws WeiboException
     */
    public List<CodeAndNameObject> apiGetLocationByCode(String... codes) throws WeiboException {
        if (CheckUtils.isEmpty(codes)) {
            throw WeiboException.ofParamCanNotNull("codes");
        }
        List<PostParameter> paramList = commonParam();
        paramList.add(new PostParameter("codes", joinArrayParam(codes)));
        return CodeAndNameObject.builList(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_CODE_TO_LOCATION),
            paramListToArray(paramList), accessToken()).asJSONArray());
    }

    /**
     * 获取时区配置表
     * 
     * @return
     * @throws WeiboException
     */
    public List<CodeAndNameObject> apiGetTimeZone() throws WeiboException {
        List<PostParameter> paramList = commonParam();
        return CodeAndNameObject.builListForTimeZone(client
            .get(WeiboConfigs.getApiUrl(WeiboConfigs.COMMON_GET_TIMEZONE), paramListToArray(paramList), accessToken())
            .asJSONObject());
    }

    private List<PostParameter> commonParam() {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNotEmpty(capital)) {
            paramList.add(new PostParameter("capital", capital));
        }
        if (CheckUtils.isNotNull(language)) {
            paramList.add(new PostParameter(MoreUseParamNames.LANGUAGE, language.value()));
        }
        return paramList;
    }
}