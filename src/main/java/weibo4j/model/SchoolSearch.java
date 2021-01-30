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
 * 搜学校搜索建议
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class SchoolSearch extends WeiboResponse {

    private static final long serialVersionUID = 4059782919675941016L;

    private String schoolName;
    private String location;
    private long id;
    private long type;

    public SchoolSearch(Response res) throws WeiboException {
        super(res);
        JSONObject json = res.asJSONObject();
        try {
            id = json.getInt("id");
            location = json.getString("location");
            type = json.getLong("type");
            schoolName = json.getString("school_name");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public SchoolSearch(JSONObject json) throws WeiboException {
        try {
            id = json.getInt("id");
            location = json.getString("location");
            type = json.getLong("type");
            schoolName = json.getString("school_name");
        } catch (JSONException je) {
            throw new WeiboException(je.getMessage() + ":" + json.toString(), je);
        }
    }

    public static List<SchoolSearch> constructSchoolSearch(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONArray();
            int size = list.length();
            List<SchoolSearch> schools = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                schools.add(new SchoolSearch(list.getJSONObject(i)));
            }
            return schools;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        } catch (WeiboException te) {
            throw te;
        }
    }

    @Override
    public String toString() {
        return "SchoolSearch [id=" + id + ",school_name=" + schoolName + ",location" + location + ", type=" + type
            + "]";
    }
}
