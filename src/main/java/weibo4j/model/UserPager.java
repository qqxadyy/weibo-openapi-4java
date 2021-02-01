/*
 * UserObjectWapper.java created on 2010-7-28 上午08:48:35 by bwl (Liu Daoru)
 */

package weibo4j.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月28日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class UserPager extends WeiboResponsePager {
    private List<User> users;
    private @WeiboJsonName(value = "use_sink_stragety", isNewAndNoDesc = true) Boolean useSinkStragety;
    private @WeiboJsonName(value = "has_filtered_fans", isNewAndNoDesc = true) Boolean hasFilteredFans;

    public UserPager(JSONObject json) {
        super(json);
    }

    public UserPager(Response res) {
        super(res);
    }
}