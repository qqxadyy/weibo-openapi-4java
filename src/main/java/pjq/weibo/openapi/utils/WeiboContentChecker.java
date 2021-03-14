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
package pjq.weibo.openapi.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pjq.commons.utils.CharsetUtils;
import pjq.commons.utils.CheckUtils;
import pjq.commons.utils.collection.CollectionUtils;
import pjq.weibo.openapi.support.WeiboHttpClient;
import weibo4j.model.WeiboException;

/**
 * 微博内容检查器
 * 
 * @author pengjianqiang
 * @date 2021年2月26日
 */

public final class WeiboContentChecker {
    private WeiboContentChecker() {}

    private static final String ANY_CHAR = "[\\s\\S]*";

    /**
     * 检查文本长度
     * 
     * @param text
     * @param length
     *            限制长度
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月12日
     */
    public static void checkPostTextLength(String text, int length) throws WeiboException {
        // 用GBK编码判断中英文、特殊符号混合的字符串长度(每个中文的UTF-8比GBK多一位，要用GBK)
        String textErrMsg = "文本内容长度不能超过" + length + "(包括空格、换行等，纯英文不超过" + (length * 2) + ")";
        boolean isContainChinese = isContainChinese(text);
        int textLength = text.getBytes(CharsetUtils.gbk()).length;
        if (isContainChinese && textLength > length) {
            // 微博接口要求是长度不大于length+10，这里限制为length，避免这里长度检查通过而微博接口不通过的情况
            throw new WeiboException(textErrMsg);
        } else if (!isContainChinese && textLength > (length * 2)) {
            // 无中文的字符串实际长度限为length*2
            throw new WeiboException(textErrMsg);
        }
    }

    /**
     * 判断字符串中是否包含中文
     * 
     * @param str
     *            待校验字符串
     * @return
     * @warn 不能校验是否为中文标点符号
     */
    private static boolean isContainChinese(String str) {
        if (Pattern.compile("[\u4e00-\u9fa5]").matcher(str).find()) {
            return true;
        }
        return false;
    }

    /**
     * 检查推送的微博/评论文本内容是否超过字数限制，及是否包含话题词
     * 
     * @param text
     * @return
     * @throws WeiboException
     */
    public static String checkPostTextAndReturn(String text) throws WeiboException {
        if (CheckUtils.isEmpty(text)) {
            return text;
        }
        checkPostTextLength(text, 130);
        if (Pattern.matches("^" + ANY_CHAR + "#" + ANY_CHAR + "#" + ANY_CHAR + "$", text)) {
            throw new WeiboException("文本内容不能包含 #话题词#");
        }
        try {
            // return URLEncoder.encode(text, CharsetUtils.UTF_8); // 官网上说需要做URLEncode，但实际做了返回会乱码，所以去掉(应该是新版有改动)
            return text;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 检查推送的微博/评论文本内容是否超过字数限制(商业接口用)
     * 
     * @param text
     * @param length
     * @return
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月12日
     */
    public static String checkPostTextAndReturn4CApi(String text, int length) throws WeiboException {
        if (CheckUtils.isEmpty(text)) {
            return text;
        }
        checkPostTextLength(text, length - 10); // 比微博限制的少10
        try {
            // return URLEncoder.encode(text, CharsetUtils.UTF_8); // 官网上说需要做URLEncode，但实际做了返回会乱码，所以去掉(应该是新版有改动)
            return text;
        } catch (Exception e) {
            throw new WeiboException(e);
        }
    }

    /**
     * 检查元数据
     * 
     * @param annotations
     * @throws WeiboException
     * @creator pengjianqiang@2021年3月12日
     */
    public static void checkAnnotationsLength(List<JSONObject> annotations) throws WeiboException {
        if (CheckUtils.isEmpty(annotations)) {
            return;
        }
        checkPostTextLength(JSON.toJSONString(annotations), 512 - 10); // 比微博限制的少10
    }

    /**
     * 检查文本内容是否含有安全域名的链接
     * 
     * @param statusText
     * @param safeDomains
     *            微博应用安全域名，多个用","分隔
     * @throws WeiboException
     */
    public static void checkIfHasSafeLink(String statusText, String safeDomains) throws WeiboException {
        checkIfHasSafeLink(statusText, Arrays.asList(safeDomains.split(",")));
    }

    /**
     * 检查文本内容是否含有安全域名的链接
     * 
     * @param statusText
     * @param safeDomains
     * @throws WeiboException
     */
    public static void checkIfHasSafeLink(String statusText, List<String> safeDomains) throws WeiboException {
        if (CheckUtils.isEmpty(safeDomains)) {
            throw new WeiboException("应用的安全域名配置不能为空");
        }

        for (String safeDomain : safeDomains) {
            if (Pattern.matches("^" + ANY_CHAR + "http(s)?://" + safeDomain + ANY_CHAR + "$", statusText)) {
                return;
            }
        }
        throw new WeiboException(
            "文本内容必须包含至少一个以下安全域名的链接地址" + safeDomains + ",且链接地址的域名参数部分已做正确的URLEncode,链接地址后建议加空格，防止微博解析错误");
    }

    /**
     * 检查图片是否可上传，可上传时返回true
     * 
     * @param picPath
     *            本地图片路径或http资源路径
     * @return
     */
    public static PicCheckResult checkIfPicValid(String picPath) {
        boolean isTempFile = false;
        String filePath = null;
        if (CheckUtils.isEmpty(picPath)) {
            return new PicCheckResult(false, picPath, isTempFile);
        }

        if (picPath.startsWith("http")) {
            try {
                isTempFile = true;
                filePath = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString().replaceAll("-", "")
                    + DigestUtils.md5Hex(picPath);
                WeiboHttpClient.getInstance().httpGetOriginalUrlFile(picPath, filePath);
            } catch (Exception e) {
                throw new WeiboException("获取图片资源失败[" + picPath + "]");
            }
        } else {
            filePath = new String(picPath);
        }

        File file = new File(filePath);
        String imageType = ImageTypeJudger.getImageType(file);
        if (ImageTypeJudger.NOT_IMAGE.equals(imageType)) {
            throw new WeiboException("文件不是图片类型[" + picPath + "]");
        } else if ("bmp".equals(imageType)) {
            throw new WeiboException("暂不支持bmp类型[" + picPath + "]");
        }
        BigDecimal fileSizeMB = new BigDecimal(file.length() + "").divide(new BigDecimal(String.valueOf(1024 * 1024)));
        if (fileSizeMB.compareTo(new BigDecimal("5")) >= 0) {
            throw new WeiboException("大小超过5MB，不能上传[" + picPath + "]");
        }
        return new PicCheckResult(true, filePath, isTempFile);
    }

    /**
     * 检查图片是否可上传，可上传时返回true
     * 
     * @param picPaths
     *            本地图片路径或http资源路径
     * @return
     */
    public static PicCheckResults checkIfPicsValid(String[] picPaths) {
        PicCheckResults picCheckResults = new PicCheckResults();
        if (CheckUtils.isEmpty(picPaths)) {
            picCheckResults.addResult(new PicCheckResult(false, "", false));
            return picCheckResults;
        }

        for (String picPath : picPaths) {
            picCheckResults.addResult(checkIfPicValid(picPath));
        }
        return picCheckResults;
    }

    @Data
    @AllArgsConstructor
    public static class PicCheckResult {
        private boolean isValid;
        private String filePath;
        private boolean isTempFile;

        public void deleteTempFile() {
            if (isTempFile()) {
                try {
                    new File(getFilePath()).delete();
                } catch (Exception e) {
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class PicCheckResults {
        private List<PicCheckResult> picCheckResults;

        public void addResult(PicCheckResult picCheckResult) {
            if (CheckUtils.isEmpty(picCheckResults)) {
                picCheckResults = new ArrayList<>();
            }
            picCheckResults.add(picCheckResult);
        }

        public boolean isValid() {
            if (CheckUtils.isEmpty(picCheckResults)) {
                return false;
            }
            for (PicCheckResult picCheckResult : picCheckResults) {
                if (!picCheckResult.isValid()) {
                    return false;
                }
            }
            return true;
        }

        public void deleteTempFiles() {
            if (CheckUtils.isEmpty(picCheckResults)) {
                return;
            }
            for (PicCheckResult picCheckResult : picCheckResults) {
                picCheckResult.deleteTempFile();
            }
        }

        public int picNums() {
            return CheckUtils.isEmpty(picCheckResults) ? 0 : picCheckResults.size();
        }

        public List<String> picPaths() {
            return CheckUtils.isEmpty(picCheckResults) ? new ArrayList<>()
                : CollectionUtils.transformList(picCheckResults, picCheckResult -> picCheckResult.getFilePath());
        }
    }
}