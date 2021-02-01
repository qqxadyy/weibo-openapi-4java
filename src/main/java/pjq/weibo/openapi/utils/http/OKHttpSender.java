package pjq.weibo.openapi.utils.http;

import lombok.NoArgsConstructor;

/**
 * 同步方式发送请求
 * 
 * @author pengjianqiang
 * @date 2021年1月18日
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class OKHttpSender extends OKHttpSenderBase {
    private static class InstanceHolder {
        private static OKHttpSender INSTANCE = new OKHttpSender();
    }

    public static OKHttpSender getInstance() {
        return InstanceHolder.INSTANCE;
    }
}