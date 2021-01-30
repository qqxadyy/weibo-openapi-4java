package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiComments;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiCommentsExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUEadcfc3fe8b0fdTHu";

            WeiboApiComments apiObj = Weibo.of(WeiboApiComments.class, accessToken);

            // System.out.println(apiObj.count(2).apiShowStatusComments(""));
            // System.out.println(apiObj.apiShowCommentsAtMe());
            // System.out.println(apiObj.apiCreateComment("", ""));
            // System.out.println(apiObj.apiReplyComment("", "", ""));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}