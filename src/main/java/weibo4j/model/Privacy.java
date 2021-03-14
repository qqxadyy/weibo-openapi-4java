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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pjq.commons.constant.CommonEnumConstant.YesOrNoInt;
import pjq.weibo.openapi.constant.ParamConstant.PrivacyUserType;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONObject;

/**
 * 获取隐私设置信息
 * 
 * @author xiaoV
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@WeiboJsonName
@SuppressWarnings("serial")
public class Privacy extends WeiboResponse {
    private YesOrNoInt badge; // 勋章是否可见，0：不可见、1：可见
    private PrivacyUserType comment; // 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
    private YesOrNoInt geo; // 是否开启地理信息，0：不开启、1：开启
    private PrivacyUserType message; // 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
    private YesOrNoInt mobile; // 是否可以通过手机号码搜索到我，0：不可以、1：可以
    private YesOrNoInt realname; // 是否可以通过真名搜索到我，0：不可以、1：可以
    private @WeiboJsonName(value = "profile_url_type", isNewAndNoDesc = true) YesOrNoInt profileUrlType; // 暂时没从返回中见过，可能错
    private YesOrNoInt webim; // 是否开启webim， 0：不开启、1：开启

    public Privacy(Response res) {
        super(res);
    }

    public Privacy(JSONObject json) {
        super(json);
    }
}