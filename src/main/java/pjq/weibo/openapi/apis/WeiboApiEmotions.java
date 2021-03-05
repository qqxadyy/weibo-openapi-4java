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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsLanguage;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.support.WeiboCacher;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.model.Emotion;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;

/**
 * Emotions相关接口<br>
 * 使用{@code Weibo.of(WeiboApiEmotions.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiEmotions extends Weibo<WeiboApiEmotions> {
    /**
     * 表情类别，face：普通表情、ani：魔法表情、cartoon：动漫表情，默认为face
     */
    private EmotionsType type;

    /**
     * 语言类别，cnname：简体、twname：繁体，默认为cnname
     */
    private EmotionsLanguage language;

    @Override
    protected String checkClientId() {
        return MoreUseParamNames.CLIENT_ID_USE_APPKEY;
    }

    /**
     * 获取微博官方表情的详细信息
     * 
     * @return
     * @throws WeiboException
     */
    public List<Emotion> apiGetEmotions() throws WeiboException {
        List<PostParameter> paramList = newParamList();
        if (CheckUtils.isNull(type)) {
            type(EmotionsType.FACE); // 默认为face类型
        }

        // 先从缓存获取
        List<Emotion> emotions = WeiboCacher.getEmotions(type);
        if (CheckUtils.isNotEmpty(emotions)) {
            return emotions;
        }

        if (CheckUtils.isNotNull(type)) {
            paramList.add(new PostParameter(MoreUseParamNames.TYPE, type.value()));
        }
        if (CheckUtils.isNotNull(language)) {
            paramList.add(new PostParameter(MoreUseParamNames.LANGUAGE, language.value()));
        }
        paramList.add(new PostParameter(MoreUseParamNames.CLIENT_ID_USE_APPKEY, clientId()));
        emotions = WeiboResponse.buildList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.EMOTIONS), paramListToArray(paramList), accessToken),
            Emotion.class);
        WeiboCacher.cacheEmotions(type, emotions);
        return emotions;
    }

    /**
     * 获取微博所有类型的表情
     * 
     * @return
     * @creator pengjianqiang@2021年3月4日
     */
    public Map<String, List<Emotion>> apiGetAllTypeEmotions() {
        Map<String, List<Emotion>> allList = new HashMap<>();
        try {
            allList.put(EmotionsType.FACE.desc(), type(EmotionsType.FACE).apiGetEmotions());
        } catch (Exception e) {
        }
        try {
            allList.put(EmotionsType.MAGIC.desc(), type(EmotionsType.MAGIC).apiGetEmotions());
        } catch (Exception e) {
        }
        try {
            allList.put(EmotionsType.CARTOON.desc(), type(EmotionsType.CARTOON).apiGetEmotions());
        } catch (Exception e) {
        }
        return allList;
    }
}