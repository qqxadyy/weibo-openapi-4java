/*
 * Copyright (c) 2007-2009, Yusuke Yamamoto All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. Neither the name of the Yusuke Yamamoto nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package weibo4j.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pjq.weibo.openapi.constant.ParamConstant.Gender;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * 新版本改造
 * 
 * @author pengjianqiang
 * @date 2021年1月25日
 */
@Data
@EqualsAndHashCode(callSuper = false)
@WeiboJsonName
@SuppressWarnings("serial")
public class User extends WeiboResponse {
    private String id; // 用户UID
    private @WeiboJsonName(value = "class", isNewAndNoDesc = true) String clazz;
    private @WeiboJsonName("screen_name") String screenName; // 微博昵称
    private String name; // 友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
    private String province; // 省份编码（参考省份编码表）
    private String city; // 城市编码（参考城市编码表）
    private String location; // 地址
    private String description; // 个人描述
    private String url; // 用户博客地址
    private @WeiboJsonName("profile_image_url") String profileImageUrl; // 用户头像地址（中图），50×50像素
    private @WeiboJsonName(value = "profile_url", isNew = true) String profileUrl; // 用户的微博统一URL地址
    private String domain; // 用户个性化域名
    private Gender gender; // 性别,m--男，f--女,n--未知
    private @WeiboJsonName("followers_count") Long followersCount; // 粉丝数
    private @WeiboJsonName("friends_count") Long friendsCount; // 关注数
    private @WeiboJsonName(value = "pagefriends_count", isNewAndNoDesc = true) Long pageFriendsCount;
    private @WeiboJsonName("statuses_count") Long statusesCount; // 微博数
    private @WeiboJsonName("video_status_count") Long videoStatusCount;
    private @WeiboJsonName("video_play_count") Long videoPlayCount;
    private @WeiboJsonName("favourites_count") Long favouritesCount; // 收藏数
    private @WeiboJsonName("created_at") Date createdAt; // 用户创建（注册）时间
    private Boolean following; // 保留字段,是否已关注(此特性暂不支持)
    private Boolean verified; // 加V标示，是否微博认证用户
    private @WeiboJsonName("verified_type") Integer verifiedType; // 认证类型
    private @WeiboJsonName(value = "user_limit", isNewAndNoDesc = true) Integer userLimit;
    private @WeiboJsonName("allow_all_act_msg") Boolean allowAllActMsg; // 是否允许所有人给我发私信
    private @WeiboJsonName("geo_enabled") Boolean geoEnabled; // 是否允许标识用户的地理位置
    private @WeiboJsonName(isNewAndNoDesc = true) String ptype;
    private @WeiboJsonName("allow_all_comment") Boolean allowAllComment; // 是否允许所有人对我的微博进行评论
    private @WeiboJsonName("avatar_large") String avatarLarge; // 用户头像地址（大图），180×180像素
    private @WeiboJsonName(value = "avatar_hd", isNew = true) String avatarHd; // 用户头像地址（高清），高清头像原图
    private @WeiboJsonName("verified_reason") String verifiedReason; // 认证原因
    private @WeiboJsonName("verified_reason_modified") String verifiedReasonModified;
    private @WeiboJsonName(value = "verified_trade", isNewAndNoDesc = true) String verifiedTrade;
    private @WeiboJsonName(value = "verified_reason_url", isNewAndNoDesc = true) String verifiedReasonUrl;
    private @WeiboJsonName(value = "verified_source", isNewAndNoDesc = true) String verifiedSrouce;
    private @WeiboJsonName(value = "verified_source_url", isNewAndNoDesc = true) String verifiedSourceUrl;
    private @WeiboJsonName(value = "verified_state", isNewAndNoDesc = true) Integer verifiedState;
    private @WeiboJsonName(value = "verified_level", isNewAndNoDesc = true) Integer verifiedLevel;
    private @WeiboJsonName(value = "verified_type_ext", isNewAndNoDesc = true) Integer verifiedTypeExt;
    private @WeiboJsonName(value = "verified_contact_name", isNewAndNoDesc = true) String verifiedContactName;
    private @WeiboJsonName(value = "verified_contact_email", isNewAndNoDesc = true) String verifiedContactEmail;
    private @WeiboJsonName(value = "verified_contact_mobile", isNewAndNoDesc = true) String verifiedContactMobile;
    private @WeiboJsonName(value = "verified_detail", isNewAndNoDesc = true) UserVerifiedDetail userVerifiedDetail;
    private @WeiboJsonName(value = "has_service_tel", isNewAndNoDesc = true) Boolean hasServiceTel;
    private @WeiboJsonName(value = "type", isNewAndNoDesc = true) String type;
    private @WeiboJsonName("follow_me") Boolean followMe; // 该用户是否关注当前登录用户
    private @WeiboJsonName(isNewAndNoDesc = true) Boolean like;
    private @WeiboJsonName(value = "like_me", isNewAndNoDesc = true) Boolean likeMe;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer level;
    private @WeiboJsonName("online_status") Integer onlineStatus; // 用户在线状态
    private @WeiboJsonName("bi_followers_count") Long biFollowersCount; // 互粉数
    private String remark; // 用户备注信息，只有在查询用户关系时才返回此字段
    private @WeiboJsonName(isNewAndNoDesc = true) UserInsecurity userInsecurity;
    private String lang; // 用户语言版本
    private String weihao; // 用户的微号
    private @WeiboJsonName(isNewAndNoDesc = true) Integer star;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer mbtype;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer mbrank;
    private @WeiboJsonName(value = "block_word", isNewAndNoDesc = true) Integer blockWord;
    private @WeiboJsonName(value = "block_app", isNewAndNoDesc = true) Integer blockApp;
    private @WeiboJsonName(value = "credit_score", isNewAndNoDesc = true) Integer creditScore;
    private @WeiboJsonName(value = "user_ability", isNewAndNoDesc = true) Long userAbility;
    private @WeiboJsonName(value = "avatargj_id", isNewAndNoDesc = true) String avatargjId;
    private @WeiboJsonName(isNewAndNoDesc = true) Integer urank;
    private @WeiboJsonName(value = "story_read_state", isNewAndNoDesc = true) Integer storyReadState;
    private @WeiboJsonName(value = "vclub_member", isNewAndNoDesc = true) Integer vclubMember;
    private @WeiboJsonName(value = "is_teenager", isNewAndNoDesc = true) Integer isTeenager;
    private @WeiboJsonName(value = "is_guardian", isNewAndNoDesc = true) Integer isGuardian;
    private @WeiboJsonName(value = "is_teenager_list", isNewAndNoDesc = true) Integer isTeenagerList;
    private @WeiboJsonName(value = "pc_new", isNewAndNoDesc = true) Integer pcNew;
    private @WeiboJsonName(value = "special_follow", isNewAndNoDesc = true) Boolean specialFollow;
    private @WeiboJsonName(value = "planet_video", isNewAndNoDesc = true) Integer planetVideo;
    private @WeiboJsonName(value = "video_mark", isNewAndNoDesc = true) Integer videoMark;
    private @WeiboJsonName(value = "live_status", isNewAndNoDesc = true) Integer liveStatus;
    private @WeiboJsonName(value = "user_ability_extend", isNewAndNoDesc = true) Integer userAbilityExtend;
    private @WeiboJsonName(value = "tab_manage", isNewAndNoDesc = true) String tabManage;
    private @WeiboJsonName(value = "cover_image_phone", isNewAndNoDesc = true) String coverImagePhone;
    private @WeiboJsonName(isNewAndNoDesc = true) UserExtend userExtend;
    private @WeiboJsonName(isNewAndNoDesc = true) JSONObject badge; // 这个返回是一大段key的json对象,而且可能key不十分固定,直接用JSONObject
    private @WeiboJsonName(value = "badge_top", isNewAndNoDesc = true) String badgeTop;
    private @WeiboJsonName(value = "has_ability_tag", isNewAndNoDesc = true) Integer hasAbilityTag;
    private @WeiboJsonName(isNewAndNoDesc = true) String ulevel;
    private Status status; // 用户最新一条微博
    private @WeiboJsonName(value = "vplususer_name", isNewAndNoDesc = true) String vplususerName;
    private @WeiboJsonName(isDeleted = true) String statusId; // 新版本接口已不返回

    public User(JSONObject json) {
        super(json);
    }

    public User(Response res) {
        super(res);
    }

    public static String[] constructIds(Response res) throws WeiboException {
        try {
            JSONArray list = res.asJSONObject().getJSONArray("ids");
            String temp = list.toString().substring(1, list.toString().length() - 1);
            String[] ids = temp.split(",");
            return ids;
        } catch (JSONException jsone) {
            throw new WeiboException(jsone.getMessage() + ":" + jsone.toString(), jsone);
        }
    }
}