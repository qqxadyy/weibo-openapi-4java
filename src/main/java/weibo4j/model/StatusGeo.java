package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 微博status下的geo
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class StatusGeo extends WeiboResponse {
    private String type;
    private List<String> coordinates; // 经纬度

    public StatusGeo(Response res) {
        super(res);
    }

    public StatusGeo(JSONObject json) {
        super(json);
    }
}