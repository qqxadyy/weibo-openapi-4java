package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiAccount;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiAccountExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUE9c4dc96e7d0LlkqG";

            WeiboApiAccount apiObj = Weibo.of(WeiboApiAccount.class, accessToken);

            // System.out.println(apiObj.apiGetAccountRateLimitStatus());
            // System.out.println(apiObj.apiGetUid());
            // System.out.println(apiObj.apiGetUserEmails());
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}