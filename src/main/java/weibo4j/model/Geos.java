package weibo4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 地理信息
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class Geos extends WeiboResponse {
    private String longitude; // 经度坐标
    private String latitude; // 维度坐标
    private String city; // 所在城市的城市代码
    private String province; // 所在省份的省份代码
    private @WeiboJsonName("city_name") String cityName; // 所在城市的城市名称
    private @WeiboJsonName("province_name") String provinceName;// 所在省份的省份名称
    private String address; // 所在的实际地址，可以为空
    private String pinyin; // 地址的汉语拼音，不是所有情况都会返回该字段
    private String more; // 更多信息，不是所有情况都会返回该字段

    public Geos(Response res) {
        super(res);
    }

    public Geos(JSONObject json) {
        super(json);
    }
}