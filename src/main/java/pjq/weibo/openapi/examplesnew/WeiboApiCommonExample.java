package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiCommon;
import pjq.weibo.openapi.constant.ParamConstant.CommonLanguage;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiCommonExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUE9c4dc96e7d0LlkqG";

            WeiboApiCommon apiObj = Weibo.of(WeiboApiCommon.class, accessToken);

            // System.out.println(apiObj.capital("c").language(CommonLanguage.TRADITIONAL_CHINESE).apiGetCountry());
            // System.out.println(apiObj.capital("g").apiGetProvince("001"));
            // System.out.println(apiObj.capital("g").apiGetCity("001044"));
            System.out
                .println(apiObj.language(CommonLanguage.TRADITIONAL_CHINESE).apiGetLocationByCode("001", "001044001"));
            // System.out.println(apiObj.apiGetTimeZone());
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}