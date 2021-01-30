package pjq.weibo.openapi.utils.http;

/**
 * 简单的异步http返回报文处理接口
 * 
 * @author pengjianqiang
 * @date 2021年1月30日
 */
@FunctionalInterface
public interface SimpleAsyncCallback {
    /**
     * 请求后的业务处理方法
     * 
     * @param isSuccess
     *            true或false
     * @param statusCode
     *            如果isSuccess为true但statusCode不为200，表示请求已被重定向，重定向的URL值在responseStr
     * @param responseStr
     *            返回报文
     */
    public void onResponse(boolean isSuccess, int statusCode, String responseStr);
}