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
/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package weibo4j.model;

import java.util.Arrays;

import weibo4j.Weibo;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * A data class representing array of numeric IDs.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @editor SinaWeibo
 */
public class IDs extends WeiboResponse {
    private long[] ids;           //ids
    private long previousCursor;  //当前cursor
    private long nextCursor;      //下一个cursor
    private static final long serialVersionUID = -6585026560164704953L;
    /*package*/ IDs(Response res,Weibo w) throws WeiboException {
        super(res);
        if("[]\n".equals(res.asString())){
        	previousCursor=0;
        	nextCursor=0;
        	ids= new long[0];
        	return;
        }
        JSONObject json=  res.asJSONObject();
        try {
        	previousCursor = json.getLong("previous_cursor");
            nextCursor = json.getLong("next_cursor");
        	
            if(!json.isNull("ids")){
        		JSONArray jsona= json.getJSONArray("ids");
        		int size=jsona.length();
        		ids =new long[ size];
        		for (int i = 0; i < size; i++) {
        			ids[i] =jsona.getLong(i);
				}
        	}
        	
         } catch (JSONException jsone) {
             throw new WeiboException(jsone);
         } 
        
    }

    public long[] getIDs() {
        return ids;
    }

    /**
     *
     * @since weibo4j-V2 1.0.0
     */
    public boolean hasPrevious(){
        return 0 != previousCursor;
    }

    /**
     *
     * @since weibo4j-V2 1.0.0
     */
    public long getPreviousCursor() {
        return previousCursor;
    }

    /**
     *
     * @since weibo4j-V2 1.0.0
     */
    public boolean hasNext(){
        return 0 != nextCursor;
    }

    /**
     *
     * @since weibo4j-V2 1.0.0
     */
    public long getNextCursor() {
        return nextCursor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IDs)) return false;

        IDs iDs = (IDs) o;

        if (!Arrays.equals(ids, iDs.ids)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ids != null ? Arrays.hashCode(ids) : 0;
    }

    @Override
    public String toString() {
        return "IDs{" +
                "ids=" + ids +
                ", previousCursor=" + previousCursor +
                ", nextCursor=" + nextCursor +
                '}';
    }
}