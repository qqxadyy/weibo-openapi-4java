package pjq.weibo.openapi.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.WeiboApiAnnos.WeiboApi;
import pjq.weibo.openapi.support.WeiboApiAnnos.WeiboPropName;
import pjq.weibo.openapi.utils.CheckUtils;
import pjq.weibo.openapi.utils.collection.CollectionUtils;
import pjq.weibo.openapi.utils.collection.CollectionUtils.Continue;
import weibo4j.util.WeiboConfig;

/**
 * 微博开放平台配置常量<br/>
 * 这些定义值可以通过在weibo-openapi-config.properties中重新定义进行覆盖(配置名看{@link WeiboPropName}的定义)，以防以后微博接口等有变更而需要重新编译的情况
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeiboConfigs {
    /**
     * properties配置文件中的配置名前缀
     */
    public static final String CONFIG_PREFIX = "weibo.";

    /**
     * properties配置文件中的api配置名前缀
     */
    public static final String CONFIG_API_PREFIX = CONFIG_PREFIX + "api.";

    /**
     * client_id(创建应用时分配的AppKey)配置
     */
    public static final String CONFIG_CLIENT_ID = CONFIG_PREFIX + "client_id";

    /**
     * client_secret(创建应用时分配的AppSecret)配置
     */
    public static final String CONFIG_CLIENT_SECRET = CONFIG_PREFIX + "client_secret";

    /**
     * redirect_uri(应用配置的授权回调域名)配置
     */
    public static final String CONFIG_REDIRECT_URI = CONFIG_PREFIX + "redirect_uri";

    /**
     * safe_domains(安全域名)配置
     */
    public static final String CONFIG_SAFE_DOMAINS = CONFIG_PREFIX + "safe_domains";

    /**
     * debug_mode(调试模式)配置
     */
    public static final String CONFIG_DEBUG_MODE = CONFIG_PREFIX + "debug_mode";

    /**
     * URL中的后缀
     */
    @WeiboPropName("suffix_json")
    public static final String URL_SUFFIX_JSON = ".json";

    /**
     * 带版本2的接口URL前段(主要使用)
     */
    @WeiboPropName("base_url")
    public static final String BASE_URL = "https://api.weibo.com/2/";

    /**
     * 普通的接口URL前段(不带版本2)
     */
    @WeiboPropName("base_url_common")
    public static final String BASE_URL_COMMON = "https://api.weibo.com/";

    /**
     * 商业接口URL前段
     */
    @WeiboPropName("base_url_c_api")
    public static final String BASE_URL_COMMERCE = "https://c.api.weibo.com/2/";

    /**
     * 粉丝服务平台-菜单、用户管理接口前段
     */
    @WeiboPropName("base_url_m_api")
    public static final String BASE_URL_MENU = "https://m.api.weibo.com/2/";

    /**
     * 提醒接口前段
     */
    @WeiboPropName("base_url_remind_api")
    public static final String BASE_URL_REMIND = "https://rm.api.weibo.com/2/";

    // ----------------------------begin:URL二级前缀------------------------------------
    /**
     * 接口URL中二级前缀-oauth2
     */
    @WeiboPropName("second_prefix_oauth2")
    public static final String SECOND_PREFIX_OAUTH2 = "oauth2/";

    /**
     * 接口URL中二级前缀-account
     */
    @WeiboPropName("second_prefix_account")
    public static final String SECOND_PREFIX_ACCOUNT = "account/";

    /**
     * 接口URL中二级前缀-remind
     */
    @WeiboPropName("second_prefix_remind")
    public static final String SECOND_PREFIX_REMIND = "remind/";

    /**
     * 接口URL中二级前缀-users
     */
    @WeiboPropName("second_prefix_users")
    public static final String SECOND_PREFIX_USERS = "users/";

    /**
     * 接口URL中二级前缀-statuses
     */
    @WeiboPropName("second_prefix_statuses")
    public static final String SECOND_PREFIX_STATUSES = "statuses/";

    /**
     * 接口URL中二级前缀-comments
     */
    @WeiboPropName("second_prefix_comments")
    public static final String SECOND_PREFIX_COMMENTS = "comments/";

    /**
     * 接口URL中二级前缀-common
     */
    @WeiboPropName("second_prefix_common")
    public static final String SECOND_PREFIX_COMMON = "common/";

    /**
     * 接口URL中二级前缀-proxy
     */
    @WeiboPropName("second_prefix_proxy")
    public static final String SECOND_PREFIX_PROXY = "proxy/";

    /**
     * 接口URL中二级前缀-friendships
     */
    @WeiboPropName("third_prefix_friendships")
    public static final String SECOND_PREFIX_FRIENDSHIPS = "friendships/";

    /**
     * 接口URL中三级前缀-profile
     */
    @WeiboPropName("third_prefix_profile")
    public static final String THIRD_PREFIX_PROFILE = "profile/";

    /**
     * 接口URL中三级前缀-article
     */
    @WeiboPropName("third_prefix_article")
    public static final String THIRD_PREFIX_ARTICLE = "article/";

    /**
     * 接口URL中三级前缀-live
     */
    @WeiboPropName("third_prefix_live")
    public static final String THIRD_PREFIX_LIVE = "live/";

    /**
     * 接口URL中三级前缀-friends_timeline
     */
    @WeiboPropName("third_prefix_friends_timeline")
    public static final String THIRD_PREFIX_FRIENDS_TIMELINE = "friends_timeline/";

    /**
     * 接口URL中三级前缀-user_timeline
     */
    @WeiboPropName("third_prefix_user_timeline")
    public static final String THIRD_PREFIX_USER_TIMELINE = "user_timeline/";

    /**
     * 接口URL中三级前缀-repost_timeline
     */
    @WeiboPropName("third_prefix_repost_timeline")
    public static final String THIRD_PREFIX_REPOST_TIMELINE = "repost_timeline/";

    /**
     * 接口URL中三级前缀-mentions
     */
    @WeiboPropName("third_prefix_mentions")
    public static final String THIRD_PREFIX_MENTIONS = "mentions/";

    /**
     * 接口URL中三级前缀-friends
     */
    @WeiboPropName("third_prefix_friends")
    public static final String THIRD_PREFIX_FRIENDS = "friends/";

    /**
     * 接口URL中三级前缀-followers
     */
    @WeiboPropName("third_prefix_followers")
    public static final String THIRD_PREFIX_FOLLOWERS = "followers/";
    // ----------------------------end:URL二级前缀------------------------------------

    // ----------------------------begin:oauth2接口------------------------------------
    /**
     * OAuth2-请求用户授权Token
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_COMMON, prefixes = SECOND_PREFIX_OAUTH2)
    public static final String OAUTH2_AUTHORIZE = "authorize";

    /**
     * OAuth2-获取授权过的Access Token
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_COMMON, prefixes = SECOND_PREFIX_OAUTH2)
    public static final String OAUTH2_ACCESS_TOKEN = "access_token";

    /**
     * OAuth2-查询用户access_token的授权相关信息
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_COMMON, prefixes = SECOND_PREFIX_OAUTH2)
    public static final String OAUTH2_GET_TOKEN_INFO = "get_token_info";

    /**
     * OAuth2-授权回收接口，帮助开发者主动取消用户的授权
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_COMMON, prefixes = SECOND_PREFIX_OAUTH2)
    public static final String OAUTH2_REVOKE_OAUTH2 = "revokeoauth2";
    // ----------------------------end:oauth2接口------------------------------------

    // ----------------------------begin:account接口------------------------------------
    /**
     * account-获取当前授权用户API访问频率限制
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_ACCOUNT, suffix = URL_SUFFIX_JSON)
    public static final String ACCOUNT_RATE_LIMIT_STATUS = "rate_limit_status";

    /**
     * account-授权之后获取用户的UID
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_ACCOUNT, suffix = URL_SUFFIX_JSON)
    public static final String ACCOUNT_GET_UID = "get_uid";

    /**
     * account-授权之后获取用户的联系邮箱
     */
    @WeiboPropName("emial")
    @WeiboApi(prefixes = {SECOND_PREFIX_ACCOUNT, THIRD_PREFIX_PROFILE}, suffix = URL_SUFFIX_JSON)
    public static final String ACCOUNT_PROFILE_EMAIL = "email";
    // ----------------------------end:account接口------------------------------------

    // ----------------------------begin:remind接口------------------------------------
    /**
     * remind-获取某个用户的各种消息未读数
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_REMIND, prefixes = SECOND_PREFIX_REMIND, suffix = URL_SUFFIX_JSON)
    public static final String REMIND_UNREAD_COUNT = "unread_count";
    // ----------------------------end:remind接口------------------------------------

    // ----------------------------begin:users接口------------------------------------
    /**
     * users-授权之后获取用户信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_USERS, suffix = URL_SUFFIX_JSON)
    public static final String USERS_SHOW = "users_show"; // 因为有其它后缀为show的接口，所以在配置文件中配置，其它类似情况的接口做相同处理

    /**
     * users-授权之后通过个性域名获取用户信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_USERS, suffix = URL_SUFFIX_JSON)
    public static final String USERS_DOMAIN_SHOW = "domain_show";

    /**
     * users-批量获取用户的粉丝数、关注数、微博数
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_USERS, suffix = URL_SUFFIX_JSON)
    public static final String USERS_COUNTS = "counts";

    /**
     * users-获取用户等级信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_USERS, suffix = URL_SUFFIX_JSON)
    public static final String USERS_SHOW_RANK = "show_rank";
    // ----------------------------end:users接口------------------------------------

    // ----------------------------begin:statuses接口------------------------------------
    /**
     * statuses-获取当前授权用户及其所关注用户的最新微博<br/>
     * 实际和friends_timeline接口的作用、参数、返回完全一致，所以friends_timeline接口不再定义
     * 
     * @see https://open.weibo.com/wiki/2/statuses/home_timeline
     * @see https://open.weibo.com/wiki/2/statuses/friends_timeline
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_HOME_TIMELINE = "home_timeline";

    /**
     * statuses-获取当前授权用户及其所关注用户的最新微博的ID列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_STATUSES, THIRD_PREFIX_FRIENDS_TIMELINE}, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_FRIENDS_TIMELINE_IDS = "statuses_friends_timeline_ids";

    /**
     * statuses-获取授权用户发布的最新微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_USER_TIMELINE = "user_timeline";

    /**
     * statuses-获取授权用户发布的最新微博的ID列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_STATUSES, THIRD_PREFIX_USER_TIMELINE}, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_USER_TIMELINE_IDS = "statuses_user_timeline_ids";

    /**
     * statuses-获取转发过指定微博的微博列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_REPOST_TIMELINE = "repost_timeline";

    /**
     * statuses-获取指定微博的最新转发微博的ID列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_STATUSES, THIRD_PREFIX_REPOST_TIMELINE}, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_REPOST_TIMELINE_IDS = "statuses_repost_timeline_ids";

    /**
     * statuses-获取当前授权用户及与其双向关注用户的微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_BILATERAL_TIMELINE = "bilateral_timeline";

    /**
     * statuses-获取@当前授权用户的最新微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_MENTIONS = "statuses_mentions";

    /**
     * statuses-获取@当前授权用户的最新微博的ID列表<br/>
     * 官网没有注明是废弃，但是调用后total_number有数量而id列表没返回，可能实际已废弃
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_STATUSES, THIRD_PREFIX_MENTIONS}, suffix = URL_SUFFIX_JSON)
    @Deprecated
    public static final String STATUSES_MENTIONS_IDS = "statuses_mentions_ids";

    /**
     * statuses-根据微博ID获取单条微博信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_SHOW = "statuses_show";

    /**
     * statuses-批量获取指定微博的转发数评论数
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_COUNT = "count";

    /**
     * statuses-根据ID跳转到单条微博页
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES)
    public static final String STATUSES_GO = "go"; // 官网接口就是go，没有后缀

    /**
     * statuses-第三方分享链接到微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_SHARE = "share";

    /**
     * statuses-通过微博/评论/私信/MID获取其ID
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_QUERY_ID = "queryid";

    /**
     * statuses-通过微博/评论/私信/ID获取其MID
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_QUERY_MID = "querymid";

    /**
     * statuses-(官网注明已废弃)获取当前用户最新转发的微博列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    @Deprecated
    public static final String STATUSES_REPOST_BY_ME = "repost_by_me";

    /**
     * statuses-转发一条微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_REPOST = "repost";

    /**
     * statuses-删除一条微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_STATUSES, suffix = URL_SUFFIX_JSON)
    public static final String STATUSES_DESTROY = "statuses_destroy";
    // ----------------------------end:statuses接口------------------------------------

    // ----------------------------begin:emotions接口------------------------------------
    /**
     * emotions-获取官方表情
     */
    @WeiboPropName
    @WeiboApi(suffix = URL_SUFFIX_JSON)
    public static final String EMOTIONS = "emotions";
    // ----------------------------end:emotions接口------------------------------------

    // ----------------------------begin:comments接口------------------------------------
    /**
     * comments-获取某条微博的评论列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_SHOW = "comments_show";

    /**
     * comments-获取@当前用户的评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_MENTIONS = "comments_mentions";

    /**
     * comments-获取当前用户发出的评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_BY_ME = "by_me";

    /**
     * comments-获取当前用户收到的评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_TO_ME = "to_me";

    /**
     * comments-获取当前用户的最新评论,包括接收到的与发出的
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_TIMELINE = "timeline";

    /**
     * comments-根据评论ID批量返回评论信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_SHOW_BATCH = "show_batch";

    /**
     * comments-评论一条微博
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_CREATE = "comments_create";

    /**
     * comments-回复一条当前用户收到的评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_REPLY = "reply";

    /**
     * comments-删除一条评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_DESTROY = "comments_destroy";

    /**
     * comments-批量删除评论
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMENTS, suffix = URL_SUFFIX_JSON)
    public static final String COMMENTS_DESTROY_BATCH = "destroy_batch";
    // ----------------------------end:comments接口------------------------------------

    // ----------------------------begin:common接口------------------------------------
    /**
     * common-通过地址编码获取地址名称
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMON, suffix = URL_SUFFIX_JSON)
    public static final String COMMON_CODE_TO_LOCATION = "code_to_location";

    /**
     * common-获取城市列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMON, suffix = URL_SUFFIX_JSON)
    public static final String COMMON_GET_CITY = "get_city";

    /**
     * common-获取省份列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMON, suffix = URL_SUFFIX_JSON)
    public static final String COMMON_GET_PROVINCE = "get_province";

    /**
     * common-获取国家列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMON, suffix = URL_SUFFIX_JSON)
    public static final String COMMON_GET_COUNTRY = "get_country";

    /**
     * common-获取时区配置表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_COMMON, suffix = URL_SUFFIX_JSON)
    public static final String COMMON_GET_TIMEZONE = "get_timezone";
    // ----------------------------end:common接口------------------------------------

    // ----------------------------begin:friendships接口------------------------------------
    /**
     * friendships-获取当前用户的关注用户列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_FRIENDSHIPS, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_FRIENDS = "friends";

    /**
     * friendships-获取当前用户的关注用户的ID列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_FRIENDSHIPS, THIRD_PREFIX_FRIENDS}, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_FRIENDS_IDS = "friendships_friends_ids";

    /**
     * friendships-获取当前用户的粉丝列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_FRIENDSHIPS, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_FOLLOWERS = "followers";

    /**
     * friendships-获取当前用户的粉丝的ID列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_FRIENDSHIPS, THIRD_PREFIX_FOLLOWERS}, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_FOLLOWERS_IDS = "friendships_followers_ids";

    /**
     * friendships-获取用户的活跃粉丝列表
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_FRIENDSHIPS, THIRD_PREFIX_FOLLOWERS}, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_FOLLOWERS_ACTIVE = "active";

    /**
     * friendships-获取两个用户之间的详细关注关系情况
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_FRIENDSHIPS, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_SHOW = "friendships_show";

    /**
     * friendships-关注一个用户
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_FRIENDSHIPS, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_CREATE = "friendships_create";

    /**
     * friendships-取关一个用户
     */
    @WeiboPropName
    @WeiboApi(prefixes = SECOND_PREFIX_FRIENDSHIPS, suffix = URL_SUFFIX_JSON)
    public static final String FRIENDSHIPS_DESTROY = "friendships_destroy";
    // ----------------------------end:friendships接口------------------------------------

    // ----------------------------begin:proxy接口------------------------------------
    /**
     * proxy-发布头条文章
     */
    @WeiboPropName
    @WeiboApi(baseUrl = BASE_URL_COMMON, prefixes = {SECOND_PREFIX_PROXY, THIRD_PREFIX_ARTICLE},
        suffix = URL_SUFFIX_JSON)
    public static final String PROXY_ARTICLE_PUBLISH = "publish";

    /**
     * proxy-创建直播
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_PROXY, THIRD_PREFIX_LIVE})
    public static final String PROXY_LIVE_CREATE = "proxy_live_create";

    /**
     * proxy-更新直播
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_PROXY, THIRD_PREFIX_LIVE})
    public static final String PROXY_LIVE_UPDATE = "update";

    /**
     * proxy-删除直播
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_PROXY, THIRD_PREFIX_LIVE})
    public static final String PROXY_LIVE_DELETE = "delete";

    /**
     * proxy-获取直播信息
     */
    @WeiboPropName
    @WeiboApi(prefixes = {SECOND_PREFIX_PROXY, THIRD_PREFIX_LIVE})
    public static final String PROXY_LIVE_SHOW = "proxy_live_show";
    // ----------------------------end:proxy接口------------------------------------

    private static Map<String, String> apiMapInfos = new HashMap<>();
    static {
        // 初始化微博API的Map
        Class<WeiboConfigs> clazz = WeiboConfigs.class;
        Field[] fields = clazz.getDeclaredFields();

        // 先从配置文件中获取配置值，没有配置则用默认值
        CollectionUtils.forEach(fields, field -> {
            if (!field.isAnnotationPresent(WeiboPropName.class)) {
                throw new Continue();
            }
            try {
                field.setAccessible(true);
                String fieldValue = null;
                String oriFieldValue = (String)field.get(clazz);

                WeiboPropName propNameAnno = field.getAnnotation(WeiboPropName.class);
                String propName = propNameAnno.value();
                if (CheckUtils.isEmpty(propName)) {
                    propName = oriFieldValue;
                }
                fieldValue = CheckUtils.getValue(oriFieldValue, WeiboConfig.getValue(CONFIG_API_PREFIX + propName));
                apiMapInfos.put(oriFieldValue, fieldValue);
            } catch (Exception e) {
                throw new Continue(e);
            }
        });

        // 拼接api的URL
        CollectionUtils.forEach(fields, field -> {
            if (!field.isAnnotationPresent(WeiboApi.class)) {
                throw new Continue();
            }
            try {
                field.setAccessible(true);
                String oriFieldValue = (String)field.get(clazz);
                StringBuilder url = new StringBuilder("");

                WeiboApi apiAnno = field.getAnnotation(WeiboApi.class);
                String baseUrl = apiAnno.baseUrl();
                if (CheckUtils.isEmpty(baseUrl)) {
                    baseUrl = BASE_URL;
                }
                url.append(apiMapInfos.get(baseUrl));

                // 拼接二级开始的前缀
                String[] prefixes = apiAnno.prefixes();
                CollectionUtils.forEach(prefixes, prefix -> {
                    if (CheckUtils.isNotEmpty(prefix)) {
                        url.append(apiMapInfos.get(prefix));
                    }
                });
                url.append(apiMapInfos.get(oriFieldValue));

                // 拼接后缀
                String apiSuffix = apiAnno.suffix();
                if (CheckUtils.isNotEmpty(apiSuffix)) {
                    url.append(apiMapInfos.get(apiSuffix));
                }

                apiMapInfos.put(oriFieldValue, url.toString());
            } catch (Exception e) {
                throw new Continue(e);
            }
        });
    }

    /**
     * 根据接口名获取完整的url
     * 
     * @param apiName
     * @return
     */
    public static String getApiUrl(String apiName) {
        if (CheckUtils.isEmpty(apiName) || !apiMapInfos.containsKey(apiName)) {
            return null;
        }
        return apiMapInfos.get(apiName);
    }

    /**
     * 获取weibo-openapi-config.properties的配置值
     * 
     * @param proName
     * @return
     */
    public static String getPropConfig(String proName) {
        return WeiboConfig.getValue(proName);
    }

    public static String getOpenAPIBaseURL() {
        return getApiUrl(BASE_URL);
    }

    public static String getAuthorizeURL() {
        return getApiUrl(OAUTH2_AUTHORIZE);
    }

    public static String getAccessTokenURL() {
        return getApiUrl(OAUTH2_ACCESS_TOKEN);
    }

    public static String getRemindBaseURL() {
        return getApiUrl(BASE_URL_REMIND);
    }

    public static String getClientId() {
        return getPropConfig(CONFIG_CLIENT_ID);
    }

    public static String getClientSecret() {
        return getPropConfig(CONFIG_CLIENT_SECRET);
    }

    public static String getRedirectURI() {
        return getPropConfig(CONFIG_REDIRECT_URI);
    }

    public static List<String> getSafeDomains() {
        return Arrays.asList(getPropConfig(CONFIG_SAFE_DOMAINS).split(","));
    }

    public static boolean isDebugMode() {
        return "true".equalsIgnoreCase(getPropConfig(CONFIG_DEBUG_MODE));
    }
}