package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiFriendShips;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiFriendShipsExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUEadcfc3fe8b0fdTHu";
            String uid = "2187883302";
            String screenName = "qqxadyy";

            // String accessToken = "2.00bqKnvBHIqjUEb1a1a269fcNCa72B";
            // String uid = "1770103757";
            // String screenName = "";

            WeiboApiFriendShips apiObj = Weibo.of(WeiboApiFriendShips.class, accessToken);

            // System.out.println(apiObj.apiGetMyFriendsByScreenName(screenName));
            // System.out.println(apiObj.apiGetMyFriendsIdsByScreenName(screenName));
            // System.out.println(apiObj.apiGetMyFansById(uid));
            // System.out.println(apiObj.apiGetMyFansIdsById(uid));
            // System.out.println(apiObj.apiShowFriendShipBetweenUsersById("", ""));
            // System.out.println(apiObj.apiGetActiveFollowers(uid));
            System.out.println(apiObj.apiFollowerUserById("1770103757"));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}