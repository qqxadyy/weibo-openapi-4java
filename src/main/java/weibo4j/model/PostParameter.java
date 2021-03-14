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

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A data class representing HTTP Post parameter
 * 
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PostParameter implements java.io.Serializable {
    String name;
    String value;
    private File file = null;

    private static final long serialVersionUID = -8708108746980739212L;

    public PostParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public PostParameter(String name, long value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public PostParameter(String name, double value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public PostParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public PostParameter(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public File getFile() {
        return file;
    }

    public boolean isFile() {
        return null != file;
    }

    private static final String JPEG = "image/jpeg";
    private static final String GIF = "image/gif";
    private static final String PNG = "image/png";
    private static final String OCTET = "application/octet-stream";

    /**
     * 
     * @return content-type
     */
    public String getContentType() {
        if (!isFile()) {
            throw new IllegalStateException("not a file");
        }
        String contentType;
        String extensions = file.getName();
        int index = extensions.lastIndexOf(".");
        if (-1 == index) {
            // no extension
            contentType = OCTET;
        } else {
            extensions = extensions.substring(extensions.lastIndexOf(".") + 1).toLowerCase();
            if (extensions.length() == 3) {
                if ("gif".equals(extensions)) {
                    contentType = GIF;
                } else if ("png".equals(extensions)) {
                    contentType = PNG;
                } else if ("jpg".equals(extensions)) {
                    contentType = JPEG;
                } else {
                    contentType = OCTET;
                }
            } else if (extensions.length() == 4) {
                if ("jpeg".equals(extensions)) {
                    contentType = JPEG;
                } else {
                    contentType = OCTET;
                }
            } else {
                contentType = OCTET;
            }
        }
        return contentType;
    }

    public static boolean containsFile(PostParameter[] params) {
        boolean containsFile = false;
        if (null == params) {
            return false;
        }
        for (PostParameter param : params) {
            if (param.isFile()) {
                containsFile = true;
                break;
            }
        }
        return containsFile;
    }

    /*package*/ static boolean containsFile(List<PostParameter> params) {
        boolean containsFile = false;
        for (PostParameter param : params) {
            if (param.isFile()) {
                containsFile = true;
                break;
            }
        }
        return containsFile;
    }

    public static PostParameter[] getParameterArray(String name, String value) {
        return new PostParameter[] {new PostParameter(name, value)};
    }

    public static PostParameter[] getParameterArray(String name, int value) {
        return getParameterArray(name, String.valueOf(value));
    }

    public static PostParameter[] getParameterArray(String name1, String value1, String name2, String value2) {
        return new PostParameter[] {new PostParameter(name1, value1), new PostParameter(name2, value2)};
    }

    public static PostParameter[] getParameterArray(String name1, int value1, String name2, int value2) {
        return getParameterArray(name1, String.valueOf(value1), name2, String.valueOf(value2));
    }

    public int compareTo(Object o) {
        int compared;
        PostParameter that = (PostParameter)o;
        compared = name.compareTo(that.name);
        if (0 == compared) {
            compared = value.compareTo(that.value);
        }
        return compared;
    }

    public static String encodeParameters(PostParameter[] httpParams) {
        if (null == httpParams) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < httpParams.length; j++) {
            if (httpParams[j].isFile()) {
                throw new IllegalArgumentException("parameter [" + httpParams[j].name + "]should be text");
            }
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(httpParams[j].name, "UTF-8")).append("=")
                    .append(URLEncoder.encode(httpParams[j].value, "UTF-8"));
            } catch (java.io.UnsupportedEncodingException neverHappen) {
            }
        }
        return buf.toString();

    }

}
