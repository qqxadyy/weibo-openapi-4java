/*
 * Copyright © 2021 pengjianqiang
 * All rights reserved.
 * 项目名称：微博开放平台API-JAVA SDK
 * 项目描述：基于微博开放平台官网的weibo4j-oauth2-beta3.1.1包及新版接口做二次开发
 * 项目地址：https://github.com/qqxadyy/weibo-openapi-4java
 * 许可证信息：见下文
 *
 * ======================================================================
 *
 * src/main/java/weibo4j下的文件是从weibo4j-oauth2-beta3.1.1.zip中复制出来的
 * 本项目对这个目录下的部分源码做了重新改造
 * 但是许可信息和"https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1"或源码中已存在的保持一致
 */
package weibo4j.model;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;
import pjq.commons.utils.DateTimeUtils;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * access_token的返回(直接在原始类中改造)
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class AccessToken extends WeiboResponse implements Serializable {
    private @WeiboJsonName(MoreUseParamNames.ACCESS_TOKEN) String accessToken;
    private @WeiboJsonName("expires_in") @Exclude String expiresIn; // token有效期，单位秒
    private @WeiboJsonName(value = "refresh_token") @Exclude String refreshToken; // 好像是安卓的SDK才会返回
    private @Exclude String uid;
    private @WeiboJsonName("remind_in") @Exclude String remindIn; // 官网说明该参数即将废弃，用expires_in
    private @WeiboJsonName(isNewAndNoDesc = true) @Exclude String isRealName;
    private @WeiboJsonName(fromJson = false) @Exclude Date createAt; // 用于记录授权时间
    private @WeiboJsonName(fromJson = false) @Exclude Date authEnd; // 用于记录取消授权时间
    private @WeiboJsonName(fromJson = false) @Exclude String clientId; // 用于记录申请授权码的clientId

    public AccessToken(Response res) {
        super(res);
    }

    public AccessToken(JSONObject json) {
        super(json);
    }

    /**
     * 获取授权码的有效天数
     * 
     * @return
     */
    public int expiresInDays() {
        try {
            // expiresIn-(token创建时间到当前时间)即为token实际的剩余有效天数
            long createdToNowSeconds = DateTimeUtils.durationSeconds(DateTimeUtils.dateToLocalDateTime(this.createAt),
                DateTimeUtils.currentDateTime());
            int expiresInDays =
                (Integer.parseInt(this.expiresIn) - Long.valueOf(createdToNowSeconds).intValue()) / (60 * 60 * 24);
            return Double.valueOf(Math.floor(expiresInDays)).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean active() {
        return DateTimeUtils.currentDate()
            .isBefore(DateTimeUtils.plus(DateTimeUtils.dateToLocalDate(createAt), expiresInDays(), ChronoUnit.DAYS));
    }
}