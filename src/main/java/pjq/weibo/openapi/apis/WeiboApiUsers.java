package pjq.weibo.openapi.apis;

import java.util.List;

import lombok.*;
import lombok.experimental.Accessors;
import pjq.weibo.openapi.constant.ParamConstant.MoreUseParamNames;
import pjq.weibo.openapi.constant.ParamConstant.TrimStatus;
import pjq.weibo.openapi.constant.WeiboConfigs;
import pjq.weibo.openapi.utils.CheckUtils;
import weibo4j.Weibo;
import weibo4j.model.*;

/**
 * Users相关接口<br/>
 * 使用{@code Weibo.of(WeiboApiUsers.class,accessToken)}生成对象
 * 
 * @author pengjianqiang
 * @date 2021年1月21日
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WeiboApiUsers extends Weibo<WeiboApiUsers> {
    /**
     * 返回值中user字段中的status字段开关，0：返回完整status字段、1：status字段仅返回status_id，默认为1<br/>
     * 官网接口中没有写该参数
     */
    private TrimStatus trimStatus;

    @Override
    protected String checkClientId() {
        // 有出现过show接口返回source参数不能为空的情况，但是之后又没有出现，以防万一还是检查该参数，接口传参也传该参数
        return MoreUseParamNames.CLIENT_ID_USE_SOURCE;
    }

    /**
     * 根据用户ID获取用户信息(只返回最新的一条微博id，不返回其对应内容)
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public User apiShowUserById(String uid) throws WeiboException {
        trimStatus(TrimStatus.ONLY_STATUS_ID);
        return apiShowUserWithTrimStatusParam(MoreUseParamNames.UID, uid);
    }

    /**
     * 根据用户昵称获取用户信息(只返回最新的一条微博id，不返回其对应内容)
     * 
     * @param screenName
     *            用户昵称
     * @return
     * @throws WeiboException
     */
    public User apiShowUserByScreenName(String screenName) throws WeiboException {
        trimStatus(TrimStatus.ONLY_STATUS_ID);
        return apiShowUserWithTrimStatusParam(MoreUseParamNames.SCREEN_NAME, screenName);
    }

    /**
     * 根据用户ID/昵称获取用户信息(默认会返回最新一条微博的信息，也可以用trimStatus参数控制不返回)
     * 
     * @param uidOrScreenName
     *            MoreUseParamNames.UID或MoreUseParamNames.SCREEN_NAME
     * @param val
     *            对应的参数值
     * @return
     * @throws WeiboException
     */
    public User apiShowUserWithTrimStatusParam(String uidOrScreenName, String val) throws WeiboException {
        if (CheckUtils.isEmpty(val)) {
            throw WeiboException.ofParamCanNotNull(uidOrScreenName);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.CLIENT_ID_USE_SOURCE, clientId()));
        paramList.add(new PostParameter(uidOrScreenName, val));
        if (CheckUtils.isNotNull(trimStatus)) {
            paramList.add(new PostParameter("trim_status", trimStatus.value()));
        }
        return new User(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_SHOW), paramListToArray(paramList), accessToken));
    }

    /**
     * 通过个性化域名获取用户资料(只返回最新的一条微博id，不返回其对应内容)
     * 
     * @param domain
     *            个性化域名
     * @return
     * @throws WeiboException
     */
    public User apiShowUserByDomain(String domain) throws WeiboException {
        trimStatus(TrimStatus.ONLY_STATUS_ID);
        return apiShowUserByDomainWithTrimStatusParam(domain);
    }

    /**
     * 通过个性化域名获取用户资料(默认会返回最新一条微博的信息，也可以用trimStatus参数控制不返回)
     * 
     * @param domain
     *            个性化域名
     * @return
     * @throws WeiboException
     */
    public User apiShowUserByDomainWithTrimStatusParam(String domain) throws WeiboException {
        if (CheckUtils.isEmpty(domain)) {
            throw WeiboException.ofParamCanNotNull("domain");
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.CLIENT_ID_USE_SOURCE, clientId()));
        paramList.add(new PostParameter("domain", domain));
        if (CheckUtils.isNotNull(trimStatus)) {
            paramList.add(new PostParameter("trim_status", trimStatus.value()));
        }
        return new User(client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_DOMAIN_SHOW), paramListToArray(paramList),
            accessToken));
    }

    /**
     * 批量获取用户的粉丝数、关注数、微博数
     * 
     * @param uids
     *            用户UID数组
     * @return
     * @throws WeiboException
     */
    public List<UserCounts> apiGetUserCounts(String... uids) throws WeiboException {
        if (CheckUtils.isEmpty(uids)) {
            throw WeiboException.ofParamCanNotNull("uids");
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter("uids", joinArrayParam(uids)));
        return WeiboResponse.buildList(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_COUNTS), paramListToArray(paramList), accessToken),
            UserCounts.class);
    }

    /**
     * 获取用户等级信息<br/>
     * 实际会报错没有这个api
     * 
     * @param uid
     *            用户ID
     * @return
     * @throws WeiboException
     */
    public UserRank apiShowUserRank(String uid) throws WeiboException {
        if (CheckUtils.isEmpty(uid)) {
            throw WeiboException.ofParamCanNotNull(MoreUseParamNames.UID);
        }
        List<PostParameter> paramList = newParamList();
        paramList.add(new PostParameter(MoreUseParamNames.UID, uid));
        return new UserRank(
            client.get(WeiboConfigs.getApiUrl(WeiboConfigs.USERS_SHOW_RANK), paramListToArray(paramList), accessToken));
    }
}