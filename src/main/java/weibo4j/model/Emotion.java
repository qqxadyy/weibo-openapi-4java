package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsType;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * @author SinaWeibo
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class Emotion extends WeiboResponse {
    private String phrase; // 表情使用的替代文字
    private EmotionsType type; // 表情类型
    private String url; // 表情图片存放的位置
    private Boolean hot; // 是否为热门表情
    private Boolean common; // 是否是常用表情
    private String value;
    private String category; // 表情分类
    private String picid;
    private String icon;

    public Emotion(Response res) {
        super(res);
    }

    public Emotion(JSONObject json) {
        super(json);
    }
}
