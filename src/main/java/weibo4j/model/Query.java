/*
 * Copyright (c) 2007-2009 All rights reserved.
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 搜索条件
 * 
 * @author SinaWeibo
 *
 */
@Getter
@Setter
public class Query {
    private String q; // 搜索的关键字。
    private Boolean snick = null;// 搜索范围是否包含昵称
    private int rpp = 20;
    private Boolean sdomain = null;// 搜索范围是否包含个性域名
    private Boolean sintro = null;// 搜索范围是否包含简介
    private Integer province = null;// 省份ID，参考省份城市编码表
    private Integer city = null;// 城市ID，参考省份城市编码表
    private Gender gender = null;// 性别
    private String comorsch = null;
    private int sort = 1;// 排序方式，1为按更新时间，2为按粉丝数。
    private Integer page = null;
    private Integer count = null;// 默认每页10条
    private boolean base_app = true;// 是否不基于当前应用来获取数据
    private int filter_ori = 0;// 过滤器，是否为原创，0为全部，5为原创，4为转发。默认为0。
    private int filter_pic;// 过滤器。是否包含图片。0为全部，1为包含，2为不包含。
    private long fuid;// 微博作者的用户ID。
    private Date starttime;// 开始时间，Unix时间戳
    private Date endtime;// 结束时间，Unix时间戳
    private boolean needcount = false;// 返回结果中是否包含返回记录数。true则返回搜索结果记录数。
    private String geocode = null;// 返回指定经纬度附近的信息。经纬度参数格式是“纬度，经度，半径”，半径支持km（公里），m（米），mi（英里）。格式需要URL Encode编码

    public PostParameter[] getParameters() throws WeiboException {
        List<PostParameter> list = new ArrayList<>();
        Class<Query> clz = Query.class;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            Method getMethod;
            try {
                getMethod = clz.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(this, new Object[] {});
                if (value != null) {
                    list.add(getParameterValue(fieldName, value));
                }
            } catch (Exception e) {
                throw new WeiboException(e);
            }
        }
        return list.toArray(new PostParameter[list.size()]);

    }

    private PostParameter getParameterValue(String name, Object value) {
        if (value instanceof Boolean) {
            return new PostParameter(name, (Boolean)value ? "0" : "1");
        } else if (value instanceof String) {
            return new PostParameter(name, value.toString());
        } else if (value instanceof Integer) {
            return new PostParameter(name, Integer.toString((Integer)value));
        } else if (value instanceof Long) {
            return new PostParameter(name, Long.toString((Long)value));
        } else if (value instanceof Gender) {
            return new PostParameter(name, Gender.valueOf((Gender)value));
        }
        return null;
    }

}
