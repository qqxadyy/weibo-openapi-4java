package pjq.weibo.openapi;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.model.WeiboException;

/**
 * 微博配置对象
 * 
 * @author pengjianqiang
 * @date 2021年2月5日
 */
@Data
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboConfiguration {
    /**
     * 创建应用时分配的AppKey
     */
    private String clientId;

    /**
     * 创建应用时分配的AppSecret
     */
    private String clientSecret;

    /**
     * 应用配置的授权回调域名
     */
    private String redirectUri;

    /**
     * 应用配置的安全域名，多个用","分隔
     */
    private String safeDomains;

    /**
     * 根据配置值生成微博配置对象
     * 
     * @param clientId
     * @param clientSecret
     * @param redirectUri
     * @param safeDomains
     *            应用配置的安全域名(如果应用没有发微博的需求，就不用传这个参数)
     * @return
     */
    public static WeiboConfiguration of(String clientId, String clientSecret, String redirectUri, String safeDomains) {
        if (!CheckUtils.areNotEmpty(clientId, clientSecret, redirectUri)) {
            throw new WeiboException("微博应用的clientId、clientSecret、redirectUri配置都不能为空");
        }

        return new WeiboConfiguration().clientId(clientId).clientSecret(clientSecret).redirectUri(redirectUri)
            .safeDomains(safeDomains);
    }

    /**
     * 获取安全域名的list对象
     * 
     * @return
     * @creator pengjianqiang@2021年3月4日
     */
    public List<String> safeDomainList() {
        return CheckUtils.isNotEmpty(safeDomains) ? Arrays.asList(safeDomains.split(",")) : null;
    }
}