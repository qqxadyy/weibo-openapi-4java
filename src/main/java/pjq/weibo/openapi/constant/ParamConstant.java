package pjq.weibo.openapi.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.ValuableEnum;

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
        public static final String REDIRECT_URI = "redirect_uri";
        public static final String CLIENT_SECRET = "client_secret";
        public static final String UID = "uid";
        public static final String SCREEN_NAME = "screen_name";
        public static final String ID = "id";
        public static final String LANGUAGE = "language";
        public static final String TYPE = "type";
        public static final String CLIENT_ID = "client_id";
        public static final String REAL_IP = "rip";

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
    public static enum OAuth2Scope implements ValuableEnum {
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

        OAuth2Scope(String value) {
            this.value = value;
        }
    }

    /**
     * OAuth2接口-display参数枚举
     */
    public static enum OAuth2Display implements ValuableEnum {
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

        OAuth2Display(String value) {
            this.value = value;
        }
    }

    /**
     * OAuth2接口-language参数枚举
     */
    public static enum OAuth2Language implements ValuableEnum {
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

        OAuth2Language(String value) {
            this.value = value;
        }
    }

    /**
     * 返回的gender枚举
     */
    public static enum Gender implements ValuableEnum {
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

        Gender(String value) {
            this.value = value;
        }
    }

    /**
     * common接口-language参数枚举
     */
    public static enum CommonLanguage implements ValuableEnum {
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

        CommonLanguage(String value) {
            this.value = value;
        }
    }

    /**
     * filter_by_author参数枚举(评论作者类型)
     */
    public static enum AuthorType implements ValuableEnum {
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
    public static enum SourceType implements ValuableEnum {
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
    public static enum TrimUser implements ValuableEnum {
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
    public static enum TrimStatus implements ValuableEnum {
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
    public static enum FilterType implements ValuableEnum {
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
    public static enum PrivacyUserType implements ValuableEnum {
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
    public static enum EmotionsType implements ValuableEnum {
        /**
         * 普通表情
         */
        FACE("face"),

        /**
         * 魔法表情
         */
        MAGIC("ani"),

        /**
         * 动漫表情
         */
        CARTOON("cartoon");

        @SuppressWarnings("unused")
        private String value;

        EmotionsType(String value) {
            this.value = value;
        }
    }

    /**
     * emotions接口-language参数枚举(语言类别)
     */
    public static enum EmotionsLanguage implements ValuableEnum {
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

        EmotionsLanguage(String value) {
            this.value = value;
        }
    }

    /**
     * statuses接口-feature参数枚举(过滤类型)
     */
    public static enum StatusesFeature implements ValuableEnum {
        /**
         * 全部
         */
        ALL,

        /**
         * 原创
         */
        ORIGNAL,

        /**
         * 图片
         */
        PICTURE,

        /**
         * 视频
         */
        VIDEO,

        /**
         * 音乐
         */
        MUSIC;
    }

    /**
     * statuses-querymid/queryid接口-type参数枚举(获取类型)
     */
    public static enum QueryIdType implements ValuableEnum {
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

        QueryIdType(String value) {
            this.value = value;
        }
    }

    /**
     * statuses-queryid接口-inbox参数枚举(信箱类型)
     */
    public static enum QueryIdInbox implements ValuableEnum {
        /**
         * 发件箱
         */
        SEND_BOX,

        /**
         * 收件箱
         */
        RECEIVE_BOX;
    }
}