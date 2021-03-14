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
 * The MIT License
 * Copyright © 2021 pengjianqiang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pjq.weibo.openapi.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pjq.commons.annos.EnhanceEnum;
import pjq.commons.annos.EnhanceEnum.EnhanceEnumFieldDefine;

/**
 * 接口相关参数的枚举值常量类
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParamConstant {
    /**
     * 使用较多的参数名用常量定义
     * 
     * @author pengjianqiang
     * @date 2021年1月21日
     */
    public interface MoreUseParamNames {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String STATE = "state";
        public static final String CODE = "code";
        public static final String REDIRECT_URI = "redirect_uri";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String UID = "uid";
        public static final String SCREEN_NAME = "screen_name";
        public static final String ID = "id";
        public static final String IDS = "ids";
        public static final String LANGUAGE = "language";
        public static final String TYPE = "type";
        public static final String CLIENT_ID = "client_id";
        public static final String REAL_IP = "rip";
        public static final String STATUS_TEXT = "status";
        public static final String PIC = "pic";

        /**
         * 部分接口要传的source参数，实际上也是要传client_id值
         */
        public static final String CLIENT_ID_USE_SOURCE = "source";

        /**
         * 部分接口要传的appkey参数，实际上也是要传client_id值
         */
        public static final String CLIENT_ID_USE_APPKEY = "appkey";
    }

    /**
     * OAuth2接口-scope参数枚举
     */
    @AllArgsConstructor
    public static enum OAuth2Scope implements EnhanceEnum {
        /**
         * 请求所有scope权限
         */
        ALL("all"),

        /**
         * 用户的联系邮箱
         */
        EMAIL("email"),

        /**
         * 私信发送接口
         */
        DIRECT_MESSAGES_WRITE("direct_messages_write"),

        /**
         * 私信读取接口
         */
        DIRECT_MESSAGES_READ("direct_messages_read"),

        /**
         * 邀请发送接口
         */
        INVITATION_WRITE("invitation_write"),

        /**
         * 好友分组读取接口组
         */
        FRIENDSHIPS_GROUP_READ("friendships_groups_read"),

        /**
         * 好友分组写入接口组
         */
        FRIENDSHIPS_GROUP_WRITE("friendships_groups_write"),

        /**
         * 定向微博读取接口组
         */
        STATUSES_TO_ME_READ("statuses_to_me_read"),

        /**
         * 关注应用官方微博
         */
        FOLLOW_APP_OFFICAIAL_MICROBLOG("follow_app_official_microblog");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * OAuth2接口-display参数枚举
     */
    @AllArgsConstructor
    public static enum OAuth2Display implements EnhanceEnum {
        /**
         * 默认的授权页面，适用于web浏览器
         */
        DEFAULT("default"),

        /**
         * 移动终端的授权页面，适用于支持html5的手机。注：使用此版授权页请用 https://open.weibo.cn/oauth2/authorize 授权接口
         */
        MOBILE("mobile"),

        /**
         * wap版授权页面，适用于非智能手机
         */
        WAP("wap"),

        /**
         * 客户端版本授权页面，适用于PC桌面应用
         */
        CLIENT("client"),

        /**
         * 默认的站内应用授权页，授权后不返回access_token，只刷新站内应用父框架
         */
        APP_ON_WEIBO("apponweibo");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * OAuth2接口-language参数枚举
     */
    @AllArgsConstructor
    public static enum OAuth2Language implements EnhanceEnum {
        /**
         * 简体中文(官网没给出具体的参数值)
         */
        SIMPLIFIED_CHINESE(""),

        /**
         * 英文
         */
        ENGLISH("en");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * 返回的gender枚举
     */
    @AllArgsConstructor
    public static enum Gender implements EnhanceEnum {
        /**
         * 男
         */
        MALE("m"),

        /**
         * 女
         */
        FEMALE("f"),

        /**
         * 未知
         */
        UNKNOWN("n");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * common接口-language参数枚举
     */
    @AllArgsConstructor
    public static enum CommonLanguage implements EnhanceEnum {
        /**
         * 简体中文
         */
        SIMPLIFIED_CHINESE("zh-cn"),

        /**
         * 繁体中文
         */
        TRADITIONAL_CHINESE("zh-tw"),

        /**
         * 英文
         */
        ENGLISH("english");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * filter_by_author参数枚举(评论作者类型)
     */
    public static enum AuthorType implements EnhanceEnum {
        /**
         * 全部
         */
        ALL,

        /**
         * 我关注的人
         */
        FRIEND,

        /**
         * 陌生人
         */
        STRANGER;
    }

    /**
     * filter_by_source参数枚举(评论来源类型)
     */
    public static enum SourceType implements EnhanceEnum {
        /**
         * 全部
         */
        ALL,

        /**
         * 来自微博的评论
         */
        FROM_WEIBO,

        /**
         * 来自微群的评论
         */
        FROM_WEIQUN;
    }

    /**
     * trim_user参数枚举(返回值中user字段过滤开关)
     */
    public static enum TrimUser implements EnhanceEnum {
        /**
         * 返回完整User字段
         */
        FULL,

        /**
         * 仅返回User.userid
         */
        ONLY_USER_ID;
    }

    /**
     * trim_status参数枚举(返回值中status字段过滤开关)
     */
    public static enum TrimStatus implements EnhanceEnum {
        /**
         * 返回完整Status字段
         */
        FULL,

        /**
         * 仅返回Status.id
         */
        ONLY_STATUS_ID;
    }

    /**
     * filter_by_type参数枚举(筛选类型)
     */
    public static enum FilterType implements EnhanceEnum {
        /**
         * 全部微博
         */
        ALL,

        /**
         * 原创的微博
         */
        ORIGNAL;
    }

    /**
     * 隐私设置对象中的用户类型
     */
    public static enum PrivacyUserType implements EnhanceEnum {
        /**
         * 全部
         */
        ALL,

        /**
         * 关注的人
         */
        FRIEND,

        /**
         * 可信用户
         */
        TRUSTED;
    }

    /**
     * emotions接口-type参数枚举(表情类别)
     */
    @AllArgsConstructor
    @EnhanceEnumFieldDefine
    public static enum EmotionsType implements EnhanceEnum {
        /**
         * 普通表情
         */
        FACE("face", "普通表情"),

        /**
         * 魔法表情
         */
        MAGIC("ani", "魔法表情"),

        /**
         * 动漫表情
         */
        CARTOON("cartoon", "动漫表情");

        @SuppressWarnings("unused")
        private String value;

        @SuppressWarnings("unused")
        private String desc;
    }

    /**
     * emotions接口-language参数枚举(语言类别)
     */
    @AllArgsConstructor
    public static enum EmotionsLanguage implements EnhanceEnum {
        /**
         * 简体
         */
        SIMPLIFIED_CHINESE("cnname"),

        /**
         * 繁体
         */
        TRADITIONAL_CHINESE("twname");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * statuses接口-feature参数枚举(过滤类型)
     */
    @AllArgsConstructor
    public static enum StatusesFeature implements EnhanceEnum {
        /**
         * 全部
         */
        ALL("0"),

        /**
         * 原创
         */
        ORIGNAL("1"),

        /**
         * 图片
         */
        PICTURE("2"),

        /**
         * 视频
         */
        VIDEO("3"),

        /**
         * 音乐
         */
        MUSIC("4"),

        /**
         * 纯文本
         */
        PLAIN_TEXT("7"),

        /**
         * 转发
         */
        REPOST("8");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * statuses-querymid/queryid接口-type参数枚举(获取类型)
     */
    @AllArgsConstructor
    public static enum QueryIdType implements EnhanceEnum {
        /**
         * 微博
         */
        STATUS("1"),

        /**
         * 评论
         */
        COMMENT("2"),

        /**
         * 私信
         */
        SECRET_MSG("3");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * statuses-queryid接口-inbox参数枚举(信箱类型)
     */
    public static enum QueryIdInbox implements EnhanceEnum {
        /**
         * 发件箱
         */
        SEND_BOX,

        /**
         * 收件箱
         */
        RECEIVE_BOX;
    }

    /**
     * statuses-商业API-update接口-visible参数枚举(微博可见性)
     */
    public static enum StatusesVisible implements EnhanceEnum {
        /**
         * 所有人可见
         */
        ALL,

        /**
         * 仅自己可见
         */
        ONLY_SELF,

        /**
         * 密友可见
         */
        CLOSE_FRIEND,

        /**
         * 指定分组
         */
        SPECIFY_GROUP;
    }

    /**
     * statuses-商业API-flag参数枚举(用于筛选推广微博)
     */
    public static enum StatusesPopularizeFlag implements EnhanceEnum {
        /**
         * 返回推广微博和普通微博
         */
        ALL,

        /**
         * 返回推广微博
         */
        ONLY_POPULAR,

        /**
         * 返回普通微博
         */
        ONLY_COMMON,;
    }
}