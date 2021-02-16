package weibo4j.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用于保存state和clientId(不是接口返回的对象)
 * 
 * @author pengjianqiang
 * @date 2021年2月8日
 */
@Data
@AllArgsConstructor
public class StateClientId {
    private String state;
    private String clientId;
}