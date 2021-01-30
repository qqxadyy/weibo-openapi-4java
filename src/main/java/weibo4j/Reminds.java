package weibo4j;

import java.util.Map;

import weibo4j.model.PostParameter;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.ArrayUtils;
import weibo4j.util.WeiboConfig;

public class Reminds extends Weibo {

    private static final long serialVersionUID = 5042162449339969435L;

    public Reminds(String access_token) {
        this.accessToken = access_token;
    }

    /**
     * 获取某个用户的各种消息未读数
     * 
     * 
     * @return
     * @throws WeiboException
     *             when Weibo service or network is unavailable
     * @version weibo4j-V2 1.0.2
     * @see http://open.weibo.com/wiki/2/remind/unread_count
     * @since JDK 1.5
     */
    public JSONObject getUnreadCountOfRemind() throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "remind/unread_count.json", accessToken).asJSONObject();
    }

    public JSONObject getUnreadCountOfRemind(String callback) throws WeiboException {
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "remind/unread_count.json",
            new PostParameter[] {new PostParameter("callback", callback)}, accessToken).asJSONObject();
    }

    public JSONObject getUnreadCountOfRemind(Map<String, String> map) throws WeiboException {
        PostParameter[] parList = ArrayUtils.mapToArray(map);
        return client.get(WeiboConfig.getOpenAPIBaseURL() + "remind/unread_count.json", parList, accessToken)
            .asJSONObject();
    }

}
