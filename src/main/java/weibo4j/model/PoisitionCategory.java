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
 * 获取地点分类
 * 
 * @author xiaoV
 * 
 */
@Getter
@Setter
public class PoisitionCategory extends WeiboResponse {

    private static final long serialVersionUID = 6795534455304308918L;

    private long id;
    private String name;
    private long pid;

    public PoisitionCategory(Response res) throws WeiboException {
        super(res);
        JSONObject json = null;
        try {
            json = res.asJSONObject();
            id = json.getLong("id");
            pid = json.getLong("pid");
            name = json.getString("name");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public PoisitionCategory(JSONObject json) throws WeiboException {
        try {
            id = json.getLong("id");
            pid = json.getLong("pid");
            name = json.getString("name");
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    public static List<PoisitionCategory> constructPoisCategory(Response res) throws WeiboException {
        try {
            JSONArray json = res.asJSONArray();
            int size = json.length();
            List<PoisitionCategory> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(new PoisitionCategory(json.getJSONObject(i)));
            }
            return list;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone);
        }
    }

    @Override
    public String toString() {
        return "PoisitionCategory [" + "id=" + id + ", name=" + name + ", pid=" + pid + "]";
    }
}
