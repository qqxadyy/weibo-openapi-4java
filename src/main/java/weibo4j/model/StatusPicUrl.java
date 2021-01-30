package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的pic_urls
 * 
 * @author pengjianqiang
 * @date 2021年1月26日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusPicUrl extends WeiboResponse {
    private @WeiboJsonName(value = "thumbnail_pic", isNewAndNoDesc = true) String thumbnailPic;

    public StatusPicUrl(JSONObject json) {
        super(json);
    }
}