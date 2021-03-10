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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pjq.commons.annos.EnhanceEnum.DefualtEnhanceEnum;
import pjq.commons.constant.DateTimePattern;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.CommonTypeJudger;
import pjq.commons.utils.DateTimeUtils;
import pjq.commons.utils.DefaultValueGetter;
import pjq.commons.utils.collection.CollectionUtils;
import pjq.commons.utils.collection.CollectionUtils.Continue;
import pjq.weibo.openapi.support.WeiboJsonName;
import weibo4j.http.HTMLEntity;
import weibo4j.http.Response;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

/**
 * 2021-01-25：改造<br/>
 * 实现类至少实现{@link Response}和{@link JSONObject}这两个构造方法，且方法实现为super(param)即可，可参考{@link User}<br/>
 * 如果是返回对象中的嵌套对象(非{@link User}、{@link Status}等顶层返回对象)，则一般只需要实现{@link JSONObject}方法即可<br/>
 * 都必须显式定义无参构造方法，因为从缓存中获取该对象时是用alibaba的json做转换，而该组件会调用类的构造方法，没有空参数构造方法的话会调用其它构造方法并导致报错
 * 
 * @see weibo4j.DirectMessage
 * @see weibo4j.model.Status
 * @see weibo4j.model.User
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
@NoArgsConstructor
@Getter
public abstract class WeiboResponse implements java.io.Serializable {
    private static Map<String, SimpleDateFormat> formatMap = new HashMap<>();
    private static final long serialVersionUID = 3519962197957449562L;
    private @Getter transient int rateLimitLimit = -1;
    private @Getter transient int rateLimitRemaining = -1;
    private @Getter transient long rateLimitReset = -1;
    @SuppressWarnings("unused")
    private static final boolean IS_DALVIK = Configuration.isDalvik();

    /**
     * 生成对象的原始json对象，用于定义的对象不能完全正确解析json时能从源json中做解析
     */
    private @JSONField(serialize = false) JSONObject srcJson;

    public WeiboResponse(Response res) {
        String limit = res.getResponseHeader("X-RateLimit-Limit");
        if (null != limit) {
            rateLimitLimit = Integer.parseInt(limit);
        }
        String remaining = res.getResponseHeader("X-RateLimit-Remaining");
        if (null != remaining) {
            rateLimitRemaining = Integer.parseInt(remaining);
        }
        String reset = res.getResponseHeader("X-RateLimit-Reset");
        if (null != reset) {
            rateLimitReset = Long.parseLong(reset);
        }

        try {
            initInstanceFields(res.asJSONObject());
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    public WeiboResponse(String res) {
        try {
            initInstanceFields(new JSONObject(res));
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    public WeiboResponse(JSONObject json) {
        initInstanceFields(json);
    }

    /**
     * 用于处理一些特殊的转换
     */
    protected void afterJsonInit(JSONObject json) {}

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initInstanceFields(JSONObject json) {
        this.srcJson = json;
        try {
            Class<?> thisClass = this.getClass();
            if (!thisClass.isAnnotationPresent(WeiboJsonName.class)) {
                return;
            }
            if (CheckUtils.isNull(json)) {
                return;
            }

            WeiboResponse _this = this;
            Field[] fields = thisClass.getDeclaredFields();
            CollectionUtils.forEach(fields, field -> {
                int md = field.getModifiers();
                if (!Modifier.isPrivate(md) || Modifier.isStatic(md) || Modifier.isFinal(md)) {
                    throw new Continue(); // 只处理private的属性
                }

                String fieldName = field.getName();
                Class<?> type = field.getType();
                String jsonName = null;
                try {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(WeiboJsonName.class)) {
                        WeiboJsonName jsonAnno = field.getAnnotation(WeiboJsonName.class);
                        if (!jsonAnno.fromJson()) {
                            throw new Continue(); // 表示不用从json串中获取值
                        }

                        jsonName = jsonAnno.value();
                        if (CheckUtils.isEmpty(jsonName)) {
                            jsonName = fieldName;
                        }
                    } else {
                        jsonName = fieldName;
                    }
                    if (json.isNull(jsonName)) {
                        throw new Continue();
                    }

                    if (CommonTypeJudger.isStringType(type)) {
                        field.set(_this, DefaultValueGetter.getValue(null, json.getString(jsonName)));
                    } else if (CommonTypeJudger.isBooleanType(type)) {
                        field.set(_this, json.getBoolean(jsonName));
                    } else if (CommonTypeJudger.isBigIntegerType(type)) {
                        field.set(_this, new BigInteger(DefaultValueGetter.getValue("0", json.getString(jsonName))));
                    } else if (CommonTypeJudger.isIntType(type)) {
                        field.set(_this, json.getInt(jsonName));
                    } else if (CommonTypeJudger.isDoubleType(type)) {
                        field.set(_this, json.getDouble(jsonName));
                    } else if (CommonTypeJudger.isLongType(type)) {
                        field.set(_this, json.getLong(jsonName));
                    } else if (type.isEnum()) {
                        field.set(_this, DefualtEnhanceEnum.valueOrNameOf(type.getName(),
                            DefaultValueGetter.getValue(null, json.getString(jsonName))));
                    } else if (JSONObject.class.isAssignableFrom(type)) {
                        field.set(_this, json.getJSONObject(jsonName));
                    } else if (WeiboResponse.class.isAssignableFrom(type)) {
                        field.set(_this,
                            type.getConstructor(JSONObject.class).newInstance(json.getJSONObject(jsonName)));
                    } else if (CommonTypeJudger.isDateType(type)) {
                        String dateStr = json.getString(jsonName);
                        if (CheckUtils.isNotEmpty(dateStr)) {
                            try {
                                // 尝试用这种格式转换日期(账号创建时间、微博创建时间等是这种格式)
                                field.set(_this,
                                    DateTimeUtils.commonParse(dateStr,
                                        DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH),
                                        Date.class));
                            } catch (Exception e) {
                                // 失败时再尝试用一般格式转换，再失败则抛异常
                                field.set(_this,
                                    DateTimeUtils.parseDate(dateStr, DateTimePattern.PATTERN_DATETIME.value()));
                            }
                        }
                    } else if (CommonTypeJudger.isListType(type)) {
                        Class<?> objClass =
                            (Class<?>)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                        List list = new ArrayList<>();
                        JSONArray ja = json.getJSONArray(jsonName);
                        for (int i = 0, size = ja.length(); i < size; i++) {
                            if (CommonTypeJudger.isStringType(objClass)) {
                                list.add(DefaultValueGetter.getValue(null, ja.getString(i)));
                            } else if (WeiboResponse.class.isAssignableFrom(objClass)) {
                                list.add(objClass.getConstructor(JSONObject.class).newInstance(ja.getJSONObject(i)));
                            } else if (JSONObject.class.isAssignableFrom(objClass)) {
                                list.add(ja.getJSONObject(i));
                            }
                        }
                        field.set(_this, list);
                    } else {
                        throw new Exception("暂不支持" + type.getName() + "类型的转换");
                    }
                } catch (Exception e) {
                    if (e instanceof Continue && CheckUtils.isEmpty(e.getMessage())) {
                        throw (Continue)e;
                    }
                    throw new Continue(fieldName + "从json获取值时报错[" + e.getMessage() + "]");
                }
            });

            // 如果是Pager类的实现，则追加处理，把分页相关属性值设置到实现类中
            Class<WeiboResponsePager> pagerClass = WeiboResponsePager.class;
            if (pagerClass.isAssignableFrom(thisClass) && !pagerClass.equals(thisClass)) {
                // 因为也是构造方法也是JSONObject，会调用父类的构造方法，所以必须排除WeiboResponsePager类本身，否则会死循环
                WeiboResponsePager srcPager = new WeiboResponsePager(json);
                Method[] methods = pagerClass.getMethods();
                CollectionUtils.forEach(methods, method -> {
                    String methodName = method.getName();
                    if (!methodName.startsWith("set")) {
                        throw new Continue(); // 只处理set方法
                    }

                    try {
                        String getterMethodName = "get" + methodName.substring(3);
                        Class<?> paramType = method.getParameters()[0].getType();
                        thisClass.getMethod(methodName, paramType).invoke(_this,
                            pagerClass.getMethod(getterMethodName).invoke(srcPager));
                    } catch (Exception e) {
                        throw new Continue("方法" + methodName + "从json获取值时报错[" + e.getMessage() + "]");
                    }
                });
            }
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 对象本身就是json数组时，使用该方法
     * 
     * @param res
     * @param clazz
     * @return
     * @throws WeiboException
     */
    public static <T extends WeiboResponse> List<T> buildList(Response res, Class<T> clazz) throws WeiboException {
        return constructList(res, null, clazz);
    }

    /**
     * 如果json数组需要先从一个jsonKey中获取时，使用该方法
     * 
     * @param res
     * @param arrayKeyName
     * @param clazz
     * @return
     * @throws WeiboException
     */
    public static <T extends WeiboResponse> List<T> buildList(Response res, String arrayKeyName, Class<T> clazz)
        throws WeiboException {
        return constructList(res, arrayKeyName, clazz);
    }

    public static <T extends WeiboResponse> List<T> constructList(Response res, String arrayKeyName, Class<T> clazz)
        throws WeiboException {
        try {
            JSONArray list =
                CheckUtils.isEmpty(arrayKeyName) ? res.asJSONArray() : res.asJSONObject().getJSONArray(arrayKeyName);
            int size = list.length();
            List<T> tList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                tList.add(clazz.getConstructor(JSONObject.class).newInstance(list.getJSONObject(i)));
            }
            return tList;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    protected static void ensureRootNodeNameIs(String rootName, Element elem) throws WeiboException {
        if (!rootName.equals(elem.getNodeName())) {
            throw new WeiboException("Unexpected root node name:" + elem.getNodeName() + ". Expected:" + rootName
                + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
        }
    }

    protected static void ensureRootNodeNameIs(String[] rootNames, Element elem) throws WeiboException {
        String actualRootName = elem.getNodeName();
        for (String rootName : rootNames) {
            if (rootName.equals(actualRootName)) {
                return;
            }
        }
        String expected = "";
        for (int i = 0; i < rootNames.length; i++) {
            if (i != 0) {
                expected += " or ";
            }
            expected += rootNames[i];
        }
        throw new WeiboException("Unexpected root node name:" + elem.getNodeName() + ". Expected:" + expected
            + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
    }

    protected static void ensureRootNodeNameIs(String rootName, Document doc) throws WeiboException {
        Element elem = doc.getDocumentElement();
        if (!rootName.equals(elem.getNodeName())) {
            throw new WeiboException("Unexpected root node name:" + elem.getNodeName() + ". Expected:" + rootName
                + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/");
        }
    }

    protected static boolean isRootNodeNilClasses(Document doc) {
        String root = doc.getDocumentElement().getNodeName();
        return "nil-classes".equals(root) || "nilclasses".equals(root);
    }

    protected static String getChildText(String str, Element elem) {
        return HTMLEntity.unescape(getTextContent(str, elem));
    }

    protected static String getTextContent(String str, Element elem) {
        NodeList nodelist = elem.getElementsByTagName(str);
        if (nodelist.getLength() > 0) {
            Node node = nodelist.item(0).getFirstChild();
            if (null != node) {
                String nodeValue = node.getNodeValue();
                return null != nodeValue ? nodeValue : "";
            }
        }
        return "";
    }

    /*modify by sycheng  add "".equals(str) */
    protected static int getChildInt(String str, Element elem) {
        String str2 = getTextContent(str, elem);
        if (null == str2 || "".equals(str2) || "null".equals(str)) {
            return -1;
        } else {
            return Integer.valueOf(str2);
        }
    }

    protected static long getChildLong(String str, Element elem) {
        String str2 = getTextContent(str, elem);
        if (null == str2 || "".equals(str2) || "null".equals(str)) {
            return -1;
        } else {
            return Long.valueOf(str2);
        }
    }

    protected static String getString(String name, JSONObject json, boolean decode) {
        String returnValue = null;
        try {
            returnValue = json.getString(name);
            if (decode) {
                try {
                    returnValue = URLDecoder.decode(returnValue, "UTF-8");
                } catch (UnsupportedEncodingException ignore) {
                }
            }
        } catch (JSONException ignore) {
            // refresh_url could be missing
        }
        return returnValue;
    }

    protected static boolean getChildBoolean(String str, Element elem) {
        String value = getTextContent(str, elem);
        return Boolean.valueOf(value);
    }

    protected static Date getChildDate(String str, Element elem) throws WeiboException {
        return getChildDate(str, elem, "EEE MMM d HH:mm:ss z yyyy");
    }

    protected static Date getChildDate(String str, Element elem, String format) throws WeiboException {
        return parseDate(getChildText(str, elem), format);
    }

    protected static Date parseDate(String str, String format) throws WeiboException {
        if (str == null || "".equals(str)) {
            return null;
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (null == sdf) {
            sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            formatMap.put(format, sdf);
        }
        try {
            synchronized (sdf) {
                // SimpleDateFormat is not thread safe
                return sdf.parse(str);
            }
        } catch (ParseException pe) {
            throw new WeiboException("Unexpected format(" + str + ") returned from sina.com.cn");
        }
    }

    protected static int getInt(String key, JSONObject json) throws JSONException {
        String str = json.getString(key);
        if (null == str || "".equals(str) || "null".equals(str)) {
            return -1;
        }
        return Integer.parseInt(str);
    }

    protected static long getLong(String key, JSONObject json) throws JSONException {
        String str = json.getString(key);
        if (null == str || "".equals(str) || "null".equals(str)) {
            return -1;
        }
        return Long.parseLong(str);
    }

    protected static boolean getBoolean(String key, JSONObject json) throws JSONException {
        String str = json.getString(key);
        if (null == str || "".equals(str) || "null".equals(str)) {
            return false;
        }
        return Boolean.valueOf(str);
    }

}
