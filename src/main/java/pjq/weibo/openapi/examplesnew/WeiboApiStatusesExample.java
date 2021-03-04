package pjq.weibo.openapi.examplesnew;

import java.io.IOException;

import pjq.weibo.openapi.apis.WeiboApiStatuses;
import weibo4j.Weibo;
import weibo4j.http.Response;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

public class WeiboApiStatusesExample {
    public static void main(String[] args) throws WeiboException, IOException, JSONException {
        try {
            String accessToken = "2.0035IE5CHIqjUE13813114a4fklWmC";

            WeiboApiStatuses apiObj = Weibo.of(WeiboApiStatuses.class, accessToken);

            // System.out.println(apiObj.count(5).apiGetFriendsLatestStatuses());
            // System.out.println(apiObj.apiGetFriendsLatestStatusIds());
            // System.out.println(apiObj.count(1).apiGetMyLatestStatuses());
            // System.out.println(apiObj.apiGetMyLatestStatusIds());
            // System.out.println(apiObj.apiGetFriendsEachOtherStatuses());
            // System.out.println(apiObj.apiGetStatusesWhichRepostedThis(""));
            // System.out.println(apiObj.apiGetStatusIdsWhichRepostedThis(""));
            // System.out.println(apiObj.apiGetStatusesAtMe());
            // System.out.println(apiObj.apiGetStatusIdsAtMe());
            // System.out.println(apiObj.apiStatusGo("", ""));
            // System.out.println(apiObj.apiGetStatus(""));
            // System.out.println(apiObj.apiGetStatusesCounts("", ""));
            // System.out.println(apiObj.apiQueryMidById(QueryIdType.STATUS, ""));
            // System.out.println(apiObj.isBase62(StatusType.VALID).apiQueryIdByMid(QueryIdType.STATUS, ""));
            // System.out.println(apiObj.apiShareStatus("http://pjq.mynatapp.cc/ ss", ""));
            System.out.println(apiObj.apiShareStatusAsync("http://pjq.mynatapp.cc/ ,sss#", "",
                (isSuccess, statusCode, responseStr) -> {
                    Response res = new Response();
                    res.setResponseAsString(responseStr);
                    System.out.println(new Status(res));
                }));
        } catch (WeiboException e) {
            if (401 == e.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                e.printStackTrace();
            }
        }
    }
}