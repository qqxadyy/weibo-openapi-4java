package weibo4j.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * 获取@当前用户的最新微博的ID
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class MentionsIds extends WeiboResponse {

    private static final long serialVersionUID = -7507725013094889640L;

    private long nextCursor;
    private long previousCursor;
    private long totalNumber;
    private long interval;
    private List<String> statusesIds; // ID列表
    private boolean hasvisible;

    public MentionsIds(Response res) throws WeiboException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            nextCursor = json.getLong("next_cursor");
            previousCursor = json.getLong("previous_cursor");
            totalNumber = json.getLong("total_number");
            hasvisible = json.getBoolean("hasvisible");
            interval = json.getLong("interval");
            JSONArray list = json.getJSONArray("statuses");
            int size = list.length();
            statusesIds = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                statusesIds.add(list.getString(i));
            }
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }

    }

    public MentionsIds(JSONObject json) throws WeiboException {
        try {
            nextCursor = json.getLong("next_cursor");
            previousCursor = json.getLong("previous_cursor");
            totalNumber = json.getLong("total_number");
            hasvisible = json.getBoolean("hasvisible");
            interval = json.getLong("interval");
            JSONArray list = json.getJSONArray("statuses");
            int size = list.length();
            statusesIds = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                statusesIds.add(list.getString(i));
            }
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    @Override
    public String toString() {
        return "MentionsIds [" + "next_cursor=" + nextCursor + ", previous_cursor=" + previousCursor + ", interval="
            + interval + ", hasvisible=" + hasvisible + ", statusesIds=" + statusesIds + ", total_number = "
            + totalNumber + "]";
    }

}
