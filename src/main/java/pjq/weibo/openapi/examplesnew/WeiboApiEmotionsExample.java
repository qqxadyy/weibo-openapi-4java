package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiEmotions;
import pjq.weibo.openapi.constant.ParamConstant.EmotionsType;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiEmotionsExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUEd04398b008E4KvmB";

            WeiboApiEmotions apiObj = Weibo.of(WeiboApiEmotions.class, accessToken);

            System.out.println(apiObj.type(EmotionsType.CARTOON).apiGetEmotions());
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}