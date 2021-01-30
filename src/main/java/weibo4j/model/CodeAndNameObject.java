package weibo4j.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

/**
 * code_to_location的返回
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@SuppressWarnings("serial")
public class CodeAndNameObject extends WeiboResponse implements Serializable {
    private String code;
    private String name;

    /**
     * 和父类的builList不通用
     * 
     * @param jsonArray
     * @return
     */
    public static List<CodeAndNameObject> builList(JSONArray jsonArray) {
        List<CodeAndNameObject> list = new ArrayList<>();
        if (CheckUtils.isNull(jsonArray)) {
            return list;
        }

        try {
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                String code = (String)jo.keys().next();
                String name = jo.getString(code);
                list.add(new CodeAndNameObject(code, name));
            }
            return list;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<CodeAndNameObject> builListForTimeZone(JSONObject jsonObject) {
        List<CodeAndNameObject> list = new ArrayList<>();
        if (CheckUtils.isNull(jsonObject)) {
            return list;
        }

        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String code = keys.next();
                String name = jsonObject.getString(code);
                list.add(new CodeAndNameObject(code, name));
            }
            return list;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }
}