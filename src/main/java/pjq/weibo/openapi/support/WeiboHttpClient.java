package pjq.weibo.openapi.support;

import java.util.Map;

import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.http.HttpException;
import pjq.weibo.openapi.utils.http.OKHttpSender;
import pjq.weibo.openapi.utils.http.OKHttpSender4Async;
import pjq.weibo.openapi.utils.http.OKHttpSenderBase;
import pjq.weibo.openapi.utils.http.OKHttpSenderBase.ParamDataType;
import pjq.weibo.openapi.utils.http.SimpleAsyncCallback;

/**
 * 发送微博相关请求的父类<br>
 * 已有用okhttp实现的默认类，如果需要自行实现发http请求的部分，需要继承该父类，且实际发请求的参数编码必须为UTF-8
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
public abstract class WeiboHttpClient {
    public static enum MethodType {
        POST, GET, DELETE;
    }

    private static WeiboHttpClient customInstance;

    private static class InstanceHolder {
        private static WeiboHttpClient INSTANCE =
            (null != customInstance ? customInstance : new DefaultWeiboHttpClient());
    }

    /**
     * 返回微博http请求的处理实例<br>
     * 如果有调用{@link WeiboImplRegister#registWeiboClient(WeiboHttpClient)}方法，则返回其参数对象；否则返回SDK默认的OKHttp实现
     * 
     * @return
     */
    public static WeiboHttpClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 实际发请求的方法
     * 
     * @param methodType
     *            微博接口只需GET、POST、DELETE
     * @param url
     *            请求地址
     * @param paramMap
     *            参数集合
     * @param extraHeaders
     *            headers集合
     * @throws HttpException
     *             http状态码不是200时要抛出这个异常
     * @param callback
     *            异步请求时的回调对象，为空时发同步请求
     * @throws Exception
     * @return
     */
    public abstract String httpExecute(MethodType methodType, String url, Map<String, String> paramMap,
        Map<String, String> extraHeaders, SimpleAsyncCallback callback) throws HttpException, Exception;

    /**
     * 
     * @param url
     *            请求地址
     * @param paramMap
     *            参数集合
     * @param extraHeaders
     *            headers集合
     * @param fileParamName
     *            接收文件的参数名，不传时默认为"file"
     * @param callback
     *            异步请求时的回调对象，为空时发同步请求
     * @param filePaths
     *            要上传的文件绝对路径数组
     * @return
     * @throws HttpException
     * @throws Exception
     */
    public abstract String httpPostMultiPartForm(String url, Map<String, String> paramMap,
        Map<String, String> extraHeaders, String fileParamName, SimpleAsyncCallback callback, String... filePaths)
        throws HttpException, Exception;

    /**
     * 发送请求获取文件流,并写入到filePath的文件中(用于必须严格按照第三方提供的url进行请求的情况)
     * 
     * @param url
     *            文件的请求地址，实现该方法时不能变更该地址的内容
     * @param filePath
     *            保存文件的路径
     * @throws HttpException
     * @throws Exception
     */
    public abstract void httpGetOriginalUrlFile(String url, String filePath) throws HttpException, Exception;

    private static class DefaultWeiboHttpClient extends WeiboHttpClient {
        @Override
        public String httpExecute(MethodType methodType, String url, Map<String, String> paramMap,
            Map<String, String> extraHeaders, SimpleAsyncCallback callback) throws HttpException, Exception {
            String responseStr = null;
            OKHttpSenderBase sender =
                CheckUtils.isNull(callback) ? OKHttpSender.getInstance() : OKHttpSender4Async.getInstance(callback);
            if (MethodType.POST.equals(methodType)) {
                responseStr = sender.httpExecute(sender.createCommonClient(url),
                    sender.createHttpPost(url, paramMap, ParamDataType.KEY_VALUE_MAP, extraHeaders));
            } else if (MethodType.GET.equals(methodType)) {
                responseStr = sender.httpExecute(sender.createCommonClient(url),
                    sender.createHttpGet(url + OKHttpSenderBase.getKeyValueStrFromMap(url, paramMap), extraHeaders));
            } else {
                responseStr = sender.httpExecute(sender.createCommonClient(url),
                    sender.createHttpGet(url + OKHttpSenderBase.getKeyValueStrFromMap(url, paramMap), extraHeaders)
                        .newBuilder().delete().build());
            }
            return responseStr;
        }

        @Override
        public String httpPostMultiPartForm(String url, Map<String, String> paramMap, Map<String, String> extraHeaders,
            String fileParamName, SimpleAsyncCallback callback, String... filePaths) throws HttpException, Exception {
            OKHttpSenderBase sender =
                CheckUtils.isNull(callback) ? OKHttpSender.getInstance() : OKHttpSender4Async.getInstance(callback);
            String responseStr =
                sender.httpExecute(sender.createCommonClient(url), sender.createHttpPostMultiPartForm(url, paramMap,
                    ParamDataType.KEY_VALUE_MAP, extraHeaders, fileParamName, filePaths));
            return responseStr;
        }

        @Override
        public void httpGetOriginalUrlFile(String url, String filePath) throws HttpException, Exception {
            OKHttpSender.getInstance().httpGetOriginalUrlFile(url, filePath);
        }
    }
}