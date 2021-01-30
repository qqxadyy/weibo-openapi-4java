package pjq.weibo.openapi.apis;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsLanguage;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsType;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.model.Emotion;
import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.model.WeiboResponse;

/**
 * Emotions相关接口<br/>
 * 使用{@code Weibo.of(WeiboApiEmotions.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
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
        List<PostParameter> paramList = Lists.newArrayList();
        if (CheckUtils.isNotNull(type)) {
            paramList.add(new PostParameter(MoreUseParamNames.TYPE, type.value()));
        }
        if (CheckUtils.isNotNull(language)) {
            paramList.add(new PostParameter(MoreUseParamNames.LANGUAGE, language.value()));
        }
        paramList.add(new PostParameter(MoreUseParamNames.CLIENT_ID_USE_APPKEY, clientId));
        return WeiboResponse.buildList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.EMOTIONS), paramListToArray(paramList), accessToken),
            Emotion.class);
    }
}