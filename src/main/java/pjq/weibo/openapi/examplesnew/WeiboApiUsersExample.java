package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiUsers;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiUsersExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUEadcfc3fe8b0fdTHu";
            String uid = "";
            String screenName = ""; // 昵称
            String domain = ""; // 个性化域名

            WeiboApiUsers apiObj = Weibo.of(WeiboApiUsers.class, accessToken);

            // System.out.println(apiObj.apiShowUserById(uid));
            System.out.println(apiObj.apiShowUserByScreenName(screenName));
            // System.out.println(apiObj.apiShowUserByDomain(domain));
            // System.out.println(apiObj.apiGetUserCounts(uid));
            // System.out.println(apiObj.apiShowUserRank(uid));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}