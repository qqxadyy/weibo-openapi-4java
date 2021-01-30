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
 * 发送微博相关请求的接口类<br/>
 * 已有用okhttp实现的默认类，如果需要自行实现发请求的部分，需要实现该接口，且实际发请求的参数编码必须为UTF-8
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
public interface WeiboHttpClient {
    public static enum MethodType {
        POST, GET, DELETE;
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
     * @throws Exception
     * @return
     */
    public String httpExecute(MethodType methodType, String url, Map<String, String> paramMap,
        Map<String, String> extraHeaders) throws HttpException, Exception;

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
     *            异步请求时的回调对象
     * @param filePaths
     *            要上传的文件绝对路径数组
     * @return
     * @throws HttpException
     * @throws Exception
     */
    public String httpPostMultiPartForm(String url, Map<String, String> paramMap, Map<String, String> extraHeaders,
        String fileParamName, SimpleAsyncCallback callback, String... filePaths) throws HttpException, Exception;

    public static class DefaultWeiboHttpClient implements WeiboHttpClient {
        @Override
        public String httpExecute(MethodType methodType, String url, Map<String, String> paramMap,
            Map<String, String> extraHeaders) throws HttpException, Exception {
            String responseStr = null;
            OKHttpSender sender = OKHttpSender.getInstance();
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
    }
}