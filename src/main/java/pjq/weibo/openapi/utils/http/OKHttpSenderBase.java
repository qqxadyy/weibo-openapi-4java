package pjq.weibo.openapi.utils.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.tika.Tika;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pjq.weibo.openapi.constant.HttpStatus;
import pjq.weibo.openapi.utils.CharsetUtils;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.collection.CollectionUtils;
import pjq.weibo.openapi.utils.collection.CollectionUtils.Continue;

/**
 * OKHttp3工具类(暂时只提供一般常用的请求方式，根据实际用到的情况做扩展)
 * 
 * @author pengjianqiang
 * @date 2021年1月18日
 */
@Slf4j
@NoArgsConstructor
public abstract class OKHttpSenderBase {
    private static final int READ_TIMEOUT = 120000; // 传输超时时间，默认120秒
    private static final int CONNECTION_TIMEOUT = 60000; // 连接超时时间，默认60秒
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_REDIRECT_URL = "location";
    public static final String SSL_PROTOCOL_TLS = "TLS"; // 默认
    public static final String SSL_PROTOCOL_SSL = "SSL";

    public static enum ParamDataType {
        XML, JSON, RAW, KEY_VALUE_STR, KEY_VALUE_MAP;
    }

    private static class InstanceHoler {
        private static OkHttpClient HTTP_INSTANCE;
        private static OkHttpClient HTTPS_INSTANCE;

        static {
            try {
                // callTimeout是整个网络过程的超时时间，一般不用，用具体的timeout设置
                // 不重试，避免链接本身就是不通时浪费资源
                HTTP_INSTANCE = new Builder().connectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                    .writeTimeout(Duration.ofMillis(READ_TIMEOUT)).readTimeout(Duration.ofMillis(READ_TIMEOUT))
                    .retryOnConnectionFailure(false).followRedirects(false).build();

                HTTPS_INSTANCE = HTTP_INSTANCE.newBuilder()
                    .sslSocketFactory(getSSLContext().getSocketFactory(), new AnyTrustManager())
                    .hostnameVerifier(new AnyHostnameVerifier()).build();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    private static OkHttpClient getClient() {
        return InstanceHoler.HTTP_INSTANCE;
    }

    private static OkHttpClient getHttpsClient(String... sslProtocol) {
        if (CheckUtils.isEmpty(sslProtocol)) {
            return InstanceHoler.HTTPS_INSTANCE;
        } else {
            try {
                return InstanceHoler.HTTPS_INSTANCE.newBuilder()
                    .sslSocketFactory(getSSLContext(sslProtocol).getSocketFactory(), new AnyTrustManager()).build();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    public static SSLContext getSSLContext(String... sslProtocol) throws Exception {
        SSLContext sslContext = SSLContext.getInstance(CheckUtils.getValue(SSL_PROTOCOL_TLS, sslProtocol));
        sslContext.init(null, new TrustManager[] {new AnyTrustManager()}, new java.security.SecureRandom());
        return sslContext;
    }

    public static SSLContext getSSLContextWithCA(String cafilepath, String capwd, String keyStoreType,
        String keyManagerFactoryAlgorithm, String... sslProtocol) throws Exception {
        SSLContext sslContext = SSLContext.getInstance(CheckUtils.getValue(SSL_PROTOCOL_TLS, sslProtocol));
        sslContext.init(HttpCertFileUtils
            .getKeyManagerFactory(cafilepath, capwd, keyStoreType, keyManagerFactoryAlgorithm).getKeyManagers(),
            new TrustManager[] {new AnyTrustManager()}, new java.security.SecureRandom());
        return sslContext;
    }

    /**
     * 请求不带参数的url(当然url后面可跟参数)
     * 
     * @param url
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpGet(String url, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpGet(url, null), responseCharset);
    }

    /**
     * 用于必须严格按照第三方提供的url进行请求的情况
     * 
     * @param url
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpGetOriginalUrl(String url, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpGet4OriginalUrl(url, null), responseCharset);
    }

    /**
     * 请求url，且可用paramMap传参
     * 
     * @param url
     * @param paramMap
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpGetMap(String url, Map<String, String> paramMap, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpGet(url + getKeyValueStrFromMap(url, paramMap), null),
            responseCharset);
    }

    /**
     * 发送请求获取文件流,并写入到filePath的文件中
     * 
     * @param url
     * @param filePath
     * @throws Exception
     */
    public void httpGetFile(String url, String filePath) throws Exception {
        httpGetToGetFile(createCommonClient(url), createHttpGet(url, null), filePath);
    }

    /**
     * 发送请求获取文件流,并写入到filePath的文件中(用于必须严格按照第三方提供的url进行请求的情况)
     * 
     * @param url
     * @param filePath
     * @throws Exception
     */
    public void httpGetOriginalUrlFile(String url, String filePath) throws Exception {
        httpGetToGetFile(createCommonClient(url), createHttpGet4OriginalUrl(url, null), filePath);
    }

    /**
     * -请求不带参数的url(当然url后面可跟参数)
     * 
     * @param url
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPost(String url, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, null, ParamDataType.KEY_VALUE_STR, null),
            responseCharset);
    }

    /**
     * 请求url
     * 
     * @param url
     * @param paramData
     *            key1=value1&key2=value2形式的参数字符串
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPostKeyValueParam(String url, String paramData, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, paramData, ParamDataType.KEY_VALUE_STR, null),
            responseCharset);
    }

    /**
     * 请求url
     * 
     * @param url
     * @param paramMap
     *            参数map
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPostMap(String url, Map<String, String> paramMap, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, paramMap, ParamDataType.KEY_VALUE_MAP, null),
            responseCharset);
    }

    /**
     * post一个XML串
     * 
     * @param url
     * @param paramDataXML
     *            XML格式的字符串
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPostXml(String url, String paramDataXML, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, paramDataXML, ParamDataType.XML, null),
            responseCharset);
    }

    /**
     * post一个JSON串
     * 
     * @param url
     * @param paramDataJSON
     *            JSON格式的字符串
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPostJson(String url, String paramDataJSON, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, paramDataJSON, ParamDataType.JSON, null),
            responseCharset);
    }

    /**
     * post一个纯文本字符串串
     * 
     * @param url
     * @param paramDataRAW
     *            纯文本字符串
     * @param responseCharset
     * @return
     * @throws Exception
     */
    public String httpPostRaw(String url, String paramDataRAW, String... responseCharset) throws Exception {
        return httpExecute(createCommonClient(url), createHttpPost(url, paramDataRAW, ParamDataType.RAW, null),
            responseCharset);
    }

    public OkHttpClient createCommonClient(String url, String... sslProtocol) throws Exception {
        String[] urlInfos = checkUrl(url);
        if (!Boolean.valueOf(urlInfos[0])) {
            return getClient();
        } else {
            return getHttpsClient(sslProtocol);
        }
    }

    private String[] checkUrl(String url) {
        CheckUtils.checkNotEmpty(url, "请求地址不可为空");
        Boolean isHttps = false;
        if (url.startsWith("http:")) {
        } else if (url.startsWith("https:")) {
            isHttps = true;
        } else {
            throw new RuntimeException("请求地址没有http/https协议头");
        }

        int tmpIndex = url.indexOf("?"); // 用问号做分隔要用split("\\?")，否则报错，indexOf问号则不用
        String[] urlInfos = {};
        if (tmpIndex < 0) {
            // url后没有带参数
            urlInfos = new String[] {isHttps.toString(), url};
        } else if (tmpIndex == 0) {
            // url第一个字符为问号
            throw new RuntimeException("请求地址不规范");
        } else {
            urlInfos = new String[] {isHttps.toString(), url.substring(0, tmpIndex), url.substring(tmpIndex + 1)};
        }
        return urlInfos;
    }

    /**
     * 创建GET请求的okhttpRequest
     * 
     * @param url
     * @param extraHeaders
     *            额外传的headers
     * @param paramCharset
     * @return
     * @throws Exception
     */
    public Request createHttpGet(String url, Map<String, String> extraHeaders, String... paramCharset)
        throws Exception {
        String[] urlInfos = checkUrl(url);
        String trueUrl = urlInfos[1];
        String paramAfterUrl = "";
        String truePararmCharset = CheckUtils.getValue(CharsetUtils.UTF_8, paramCharset);

        if (urlInfos.length > 2 && CheckUtils.isNotEmpty(urlInfos[2])) {
            // GET请求要对参数进行编码
            Map<String, String> paramMap = getMapFromKeyValueStr(urlInfos[2]);
            for (Entry<String, String> enrtySet : paramMap.entrySet()) {
                String paramKey = enrtySet.getKey();
                String paramValue = enrtySet.getValue();
                if (CheckUtils.areNotEmpty(paramKey, paramValue)) {
                    paramAfterUrl += URLEncoder.encode(paramKey, truePararmCharset) + "="
                        + URLEncoder.encode(paramValue, truePararmCharset) + "&";
                }
            }
        }
        if (CheckUtils.isNotEmpty(paramAfterUrl)) {
            trueUrl += "?" + paramAfterUrl.substring(0, paramAfterUrl.length() - 1); // 去掉最后的&号
        }
        log.info("requestURL=========>{}", trueUrl);
        return addExtraHeaders(new Request.Builder().url(trueUrl), extraHeaders).build();
    }

    /**
     * 创建GET请求的okhttpRequest(用于必须严格按照第三方提供的url进行请求的情况)
     * 
     * @param url
     * @param extraHeaders
     *            额外传的headers
     * @param paramCharset
     * @return
     * @throws Exception
     */
    public Request createHttpGet4OriginalUrl(String url, Map<String, String> extraHeaders, String... paramCharset)
        throws Exception {
        checkUrl(url);
        log.info("requestURL=========>{}", url);
        return addExtraHeaders(new Request.Builder().url(url), extraHeaders).build();
    }

    /**
     * 创建POST请求的okhttpRequest
     * 
     * @param url
     * @param paramDataObj
     *            参数对象，根据paramDataType参数决定对象内容
     * @param paramDataType
     *            {@link ParamDataType}
     * @param extraHeaders
     *            额外传的headers
     * @param paramCharset
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Request createHttpPost(String url, Object paramDataObj, ParamDataType paramDataType,
        Map<String, String> extraHeaders, String... paramCharset) throws Exception {
        String[] urlInfos = checkUrl(url);
        String trueUrl = urlInfos[1];
        String paramAfterUrl = "";
        String truePararmCharset = CheckUtils.getValue(CharsetUtils.UTF_8, paramCharset);
        log.info("requestURL=========>{}", trueUrl);

        if (urlInfos.length > 2 && CheckUtils.isNotEmpty(urlInfos[2])) {
            paramAfterUrl = urlInfos[2];
        }
        Request.Builder builder = addExtraHeaders(new Request.Builder().url(trueUrl), extraHeaders);

        // 设置参数
        if (ParamDataType.XML.equals(paramDataType) || ParamDataType.JSON.equals(paramDataType)) {
            String paramData = (String)paramDataObj;

            if (CheckUtils.isEmpty(paramData)) {
                switch (paramDataType) {
                    case XML:
                        paramData = "";
                        break;
                    case JSON:
                        paramData = "{}"; // 空JSON
                        break;
                    case RAW:
                        paramData = "";
                        break;
                    default:
                        break;
                }
            }

            okhttp3.MediaType meidaType =
                okhttp3.MediaType.parse((ParamDataType.XML.equals(paramDataType) ? MediaType.APPLICATION_XML
                    : (ParamDataType.JSON.equals(paramDataType) ? MediaType.APPLICATION_JSON : MediaType.TEXT_PLAIN))
                    + "; charset=" + truePararmCharset);
            RequestBody requestBody = RequestBody.create(paramData, meidaType);
            log.info("request{}========>{}", paramDataType, paramData);
            builder.post(requestBody);
        } else if (ParamDataType.KEY_VALUE_STR.equals(paramDataType)) {
            String paramData = (String)paramDataObj;

            if (CheckUtils.isNotEmpty(paramAfterUrl)) {
                if (CheckUtils.isNotEmpty(paramData)) {
                    paramData = paramAfterUrl + "&" + paramData;
                } else {
                    paramData = paramAfterUrl;
                }
            }

            Map<String, String> paramMap = new HashMap<>();
            if (CheckUtils.isNotEmpty(paramData)) {
                paramMap.putAll(getMapFromKeyValueStr(paramData));
            }
            postForm(builder, paramMap, truePararmCharset);
        } else {
            // KEY_VALUE_MAP形式
            Map<String, String> paramMap = (Map<String, String>)paramDataObj;

            if (null == paramMap) {
                paramMap = new HashMap<>();
            }

            if (CheckUtils.isNotEmpty(paramAfterUrl)) {
                paramMap.putAll(getMapFromKeyValueStr(paramAfterUrl));
            }
            postForm(builder, paramMap, truePararmCharset);
        }
        return builder.build();
    }

    private void postForm(Request.Builder builder, Map<String, String> paramMap, String truePararmCharset) {
        FormBody requestBody = null;
        FormBody.Builder bodyBuilder = new FormBody.Builder(CharsetUtils.forName(truePararmCharset));
        if (CheckUtils.isNotEmpty(paramMap)) {
            requestBody = addPostParams(bodyBuilder, paramMap).build();
        } else {
            requestBody = bodyBuilder.build();
        }

        builder.removeHeader(HEADER_CONTENT_TYPE); // 保证只有一个该header值
        builder.addHeader(HEADER_CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);// 必须加这句，否有些服务器获取不了参数
        builder.post(requestBody);
    }

    private FormBody.Builder addPostParams(FormBody.Builder builder, Map<String, String> paramMap) {
        if (CheckUtils.isNotEmpty(paramMap)) {
            StringBuilder paramData = new StringBuilder("");
            paramMap.forEach((paramKey, paramValue) -> {
                if (CheckUtils.areNotEmpty(paramKey, paramValue)) {
                    paramData.append(paramKey + "=" + paramValue + "&");
                    builder.add(paramKey, paramValue);
                }
            });
            log.info("requestParam=======>{}", paramData);
        }
        return builder;
    }

    /**
     * 创建POST MultiPart表单的okhttpRequest
     * 
     * @param url
     * @param paramDataObj
     *            参数对象，根据paramDataType参数决定对象内容
     * @param paramDataType
     *            {@link ParamDataType}
     * @param extraHeaders
     *            额外传的headers
     * @param fileParamName
     *            接收文件的参数名，不传时默认为"file"
     * @param filePaths
     *            要上传的文件绝对路径数组
     * @param paramCharset
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Request createHttpPostMultiPartForm(String url, Object paramDataObj, ParamDataType paramDataType,
        Map<String, String> extraHeaders, String fileParamName, String[] filePaths, String... paramCharset)
        throws Exception {
        String[] urlInfos = checkUrl(url);
        String trueUrl = urlInfos[1];
        String paramAfterUrl = "";
        String truePararmCharset = CheckUtils.getValue(CharsetUtils.UTF_8, paramCharset);
        log.info("requestURL=========>{}", trueUrl);

        if (urlInfos.length > 2 && CheckUtils.isNotEmpty(urlInfos[2])) {
            paramAfterUrl = urlInfos[2];
        }
        Request.Builder builder = addExtraHeaders(new Request.Builder().url(trueUrl), extraHeaders);

        // 设置参数
        if (ParamDataType.XML.equals(paramDataType) || ParamDataType.JSON.equals(paramDataType)) {
            throw new Exception("该方法不支持XML或JSON类型的字符串参数");
        } else if (ParamDataType.KEY_VALUE_STR.equals(paramDataType)) {
            String paramData = (String)paramDataObj;

            if (CheckUtils.isNotEmpty(paramAfterUrl)) {
                if (CheckUtils.isNotEmpty(paramData)) {
                    paramData = paramAfterUrl + "&" + paramData;
                } else {
                    paramData = paramAfterUrl;
                }
            }

            Map<String, String> paramMap = new HashMap<>();
            if (CheckUtils.isNotEmpty(paramData)) {
                paramMap.putAll(getMapFromKeyValueStr(paramData));
            }
            postMultiPartForm(builder, paramMap, truePararmCharset, fileParamName, filePaths);
        } else {
            // KEY_VALUE_MAP形式
            Map<String, String> paramMap = (Map<String, String>)paramDataObj;

            if (null == paramMap) {
                paramMap = new HashMap<>();
            }

            if (CheckUtils.isNotEmpty(paramAfterUrl)) {
                paramMap.putAll(getMapFromKeyValueStr(paramAfterUrl));
            }
            postMultiPartForm(builder, paramMap, truePararmCharset, fileParamName, filePaths);
        }
        return builder.build();
    }

    private void postMultiPartForm(Request.Builder builder, Map<String, String> paramMap, String truePararmCharset,
        String fileParamName, String[] filePaths) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (CheckUtils.isNotEmpty(paramMap)) {
            StringBuilder paramData = new StringBuilder("");
            paramMap.forEach((paramKey, paramValue) -> {
                if (CheckUtils.areNotEmpty(paramKey, paramValue)) {
                    paramData.append(paramKey + "=" + paramValue + "&");
                    try {
                        bodyBuilder.addFormDataPart(paramKey, new String(paramValue.getBytes(), truePararmCharset));
                    } catch (Exception e) {
                        bodyBuilder.addFormDataPart(paramKey, paramValue);
                    }
                }
            });
            log.info("requestParam=======>{}", paramData);
        }
        if (CheckUtils.isNotEmpty(filePaths)) {
            CollectionUtils.forEach(filePaths, filePath -> {
                try {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new RuntimeException(filePath + "不存在");
                    } else if (file.isDirectory()) {
                        throw new RuntimeException(filePath + "不是文件");
                    }

                    okhttp3.MediaType meidaType = okhttp3.MediaType.parse(new Tika().detect(file));
                    bodyBuilder.addFormDataPart(CheckUtils.getValue("file", fileParamName), file.getName(),
                        RequestBody.create(file, meidaType));
                } catch (Exception e) {
                    throw new Continue(e);
                }
            });
        }

        builder.post(bodyBuilder.build());
    }

    /**
     * 设置额外header信息
     * 
     * @param builder
     * @param extraHeaders
     */
    private Request.Builder addExtraHeaders(Request.Builder builder, Map<String, String> extraHeaders) {
        if (CheckUtils.isNotEmpty(extraHeaders)) {
            log.info("extraHeaders=======>{}", extraHeaders);
            extraHeaders.forEach((headerName, headerValue) -> {
                if (CheckUtils.areNotEmpty(headerName, headerValue)) {
                    builder.addHeader(headerName, headerValue);
                }
            });
        }
        return builder;
    }

    public static Map<String, String> getMapFromKeyValueStr(String paramData) {
        Map<String, String> map = new HashMap<>();
        if (CheckUtils.isEmpty(paramData)) {
            return map;
        }

        String[] params = paramData.split("&"); // 这里如果参数中有带&号，则可能导致出错
        for (int i = 0; i < params.length; i++) {
            int tmpIndex = params[i].indexOf("=");
            if (tmpIndex > 0) {
                String key = params[i].substring(0, tmpIndex);
                String value = params[i].substring(tmpIndex + 1);
                if (CheckUtils.areNotEmpty(key, value)) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    public static String getKeyValueStrFromMap(String url, Map<String, String> map) {
        String str = "";
        if (null == map || map.size() <= 0) {
            return str;
        }

        for (Entry<String, String> enrtySet : map.entrySet()) {
            String key = enrtySet.getKey();
            String value = enrtySet.getValue();
            if (CheckUtils.areNotEmpty(key, value)) {
                str += (key + "=" + value + "&");
            }
        }
        return (url.indexOf("?") > -1 ? "&" : "?") + str.substring(0, str.length() - 1);
    }

    public String httpExecute(OkHttpClient client, Request request, String... responseCharset) throws Exception {
        String responseStr = "";
        long beginTime = System.currentTimeMillis();
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            log.info("statusCode=========>{}", statusCode);
            log.info("cost===============>{}ms", System.currentTimeMillis() - beginTime);
            if (statusCode == HttpStatus.SC_OK) {
                // 读取内容
                responseStr = new String(
                    response.body().string().getBytes(CheckUtils.getValue(CharsetUtils.UTF_8, responseCharset)));
                log.info("responseStr========>{}", responseStr);
            } else {
                responseStr = handleError(response, statusCode, true);
            }
        }
        return responseStr;
    }

    /**
     * 自定义httpClient和httpPost去发送请求获取文件流并写入到filePath对应的文件
     * 
     * @param httpClient
     * @param httpGet
     * @param filePath
     * @param responseCharset
     * @throws Exception
     */
    public void httpGetToGetFile(OkHttpClient client, Request request, String filePath, String... responseCharset)
        throws Exception {
        long beginTime = System.currentTimeMillis();
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            log.info("statusCode=========>{}", statusCode);
            log.info("cost===============>{}ms", System.currentTimeMillis() - beginTime);
            if (statusCode == HttpStatus.SC_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
                    String tempLine = br.readLine();
                    while (CheckUtils.isNotEmpty(tempLine)) {
                        bw.write(tempLine);
                        tempLine = br.readLine();
                    }
                    log.info("=========>写入文件{}", filePath);
                }
            } else {
                handleError(response, statusCode, false);
            }
        }
    }

    protected String handleError(Response response, int statusCode, boolean handleRedirect) throws Exception {
        String responseStr = "";
        switch (statusCode) {
            case HttpStatus.SC_MOVED_PERMANENTLY:
            case HttpStatus.SC_MOVED_TEMPORARILY:
                if (handleRedirect) {
                    try {
                        List<String> headers = response.headers(HEADER_REDIRECT_URL); // 重定向的地址是在header中
                        if (CheckUtils.isNotEmpty(headers)) {
                            responseStr = headers.get(0);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("获取重定向地址失败", e);
                    }
                    log.info("redirectURL========>{}", responseStr);
                }
                break;
            case HttpStatus.SC_NOT_FOUND:
                throw new HttpException(statusCode, "请求地址不存在", parseErrorMsg(response));
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                throw new HttpException(statusCode, "请求调用失败", parseErrorMsg(response));
            default:
                throw new HttpException(statusCode, "其它错误", parseErrorMsg(response));
        }
        return responseStr;
    }

    private String parseErrorMsg(Response response) {
        try {
            String errMsg = response.body().byteString().utf8();
            try {
                // json字符串时直接返回
                return JSON.parseObject(errMsg).toJSONString();
            } catch (Exception e) {
                // 转换json报错时
                String firstLine = errMsg.split("\\\\r\\\\n")[0];
                if (CheckUtils.isNotEmpty(firstLine)) {
                    return firstLine.substring(firstLine.lastIndexOf(":") + 1);
                } else {
                    return "";
                }
            }
        } catch (Exception e) {
            return "";
        }

    }

    private static class AnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // return hostname.equals(session.getPeerHost());
            return true;
        }
    }

    private static class AnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // return null;
            return new X509Certificate[] {}; // okhttp3之后要返回这个
        }
    }

    /**
     * 默认的okhttp异步请求回调类
     * 
     * @author pengjianqiang
     * @date 2021年1月19日
     */
    @Data
    @AllArgsConstructor
    protected static class DefaultOkHttpAsyncCallback implements Callback {
        private OKHttpSenderBase thisSender;
        private Long beginTime;
        private SimpleAsyncCallback simpleAsyncCallback;
        private String responseCharset;

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            int statusCode = response.code();
            try {
                String responseStr = "";
                log.info("statusCode=========>{}", statusCode);
                log.info("cost===============>{}ms", System.currentTimeMillis() - beginTime);
                if (statusCode == HttpStatus.SC_OK) {
                    // 读取内容
                    responseStr = new String(
                        response.body().string().getBytes(CheckUtils.getValue(CharsetUtils.UTF_8, responseCharset)));
                    log.info("responseStr========>{}", responseStr);
                } else {
                    responseStr = thisSender.handleError(response, statusCode, true);
                }
                if (CheckUtils.isNotNull(simpleAsyncCallback)) {
                    simpleAsyncCallback.onResponse(true, statusCode, responseStr);
                }
            } catch (Exception e) {
                log.info("异步请求失败=========>{}", e);
                if (CheckUtils.isNotNull(simpleAsyncCallback)) {
                    simpleAsyncCallback.onResponse(false, statusCode, e.getMessage());
                }
            } finally {
                if (CheckUtils.isNotNull(response)) {
                    response.close();
                }
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            log.info("异步请求失败=========>{}", e);
        }
    }
}