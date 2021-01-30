package pjq.weibo.openapi.utils.http;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pjq.weibo.openapi.utils.CharsetUtils;
import pjq.weibo.openapi.utils.CheckUtils;

/**
 * 异步方式发送请求
 * 
 * @author pengjianqiang
 * @date 2021年1月18日
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
public final class OKHttpSender4Async extends OKHttpSenderBase {
    private SimpleAsyncCallback callback;

    private static class InstanceHoler {
        private static OKHttpSender4Async INSTANCE = new OKHttpSender4Async();
    }

    public static OKHttpSender4Async getInstance() {
        return InstanceHoler.INSTANCE;
    }

    /**
     * 需要定义异步请求后的业务处理方法
     * 
     * @param callback
     * @return
     */
    public static OKHttpSender4Async getInstance(SimpleAsyncCallback callback) {
        return new OKHttpSender4Async(callback); // 该方法需要每次都new一个对象
    }

    @Override
    public String httpExecute(OkHttpClient client, Request request, String... responseCharset) throws Exception {
        client.newCall(request).enqueue(new DefaultOkHttpAsyncCallback(this, System.currentTimeMillis(), callback,
            CheckUtils.getValue(CharsetUtils.UTF_8, responseCharset)));
        return "已发送异步请求";
    }
}