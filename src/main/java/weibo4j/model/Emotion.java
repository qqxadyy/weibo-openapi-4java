package weibo4j.model;

import com.alibaba.fastjson.annotation.JSONField;

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
    private @JSONField(serialize = false) EmotionsType type; // 表情类型
    private String url; // 表情图片存放的位置
    private @JSONField(serialize = false) Boolean hot; // 是否为热门表情
    private @JSONField(serialize = false) Boolean common; // 是否是常用表情
    private @JSONField(serialize = false) String value; // 同phrase
    private String category; // 表情分类
    private @JSONField(serialize = false) String picid;
    private @JSONField(serialize = false) String icon;// 路径跟url的有点差别，但表情图实际的内容没什么差别

    public Emotion(Response res) {
        super(res);
    }

    public Emotion(JSONObject json) {
        super(json);
    }
}
