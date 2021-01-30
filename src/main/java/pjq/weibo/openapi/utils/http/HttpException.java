package pjq.weibo.openapi.utils.http;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class HttpException extends RuntimeException {
    private @Getter @Setter int statusCode;

    /**
     * 原始报错字符串
     */
    private @Getter @Setter String oriErrMsg;

    /**
     * 
     * @param statusCode
     *            http状态码
     * @param msg
     *            异常信息
     * @param oriErrMsg
     *            请求报错的原始报文串
     * @param msg
     */
    public HttpException(int statusCode, String msg, String oriErrMsg) {
        super(msg);
        this.statusCode = statusCode;
        this.oriErrMsg = oriErrMsg;
    }

    public String getConcatedMsg() {
        return getMessage() + "[" + oriErrMsg + "]";
    }
}