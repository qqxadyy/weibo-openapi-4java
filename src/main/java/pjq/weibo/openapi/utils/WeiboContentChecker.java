package pjq.weibo.openapi.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import pjq.weibo.openapi.utils.http.OKHttpSender;
import weibo4j.model.WeiboException;

/**
 * 微博内容检查器
 * 
 * @author pengjianqiang
 * @date 2021年2月26日
 */

public final class WeiboContentChecker {
    private WeiboContentChecker() {}

    /**
     * 检查推送的微博/评论文本内容是否超过字数限制
     * 
     * @param text
     * @return
     * @throws WeiboException
     */
    public static String checkPostTextAndReturn(String text) throws WeiboException {
        if (CheckUtils.isEmpty(text)) {
            return text;
        }

        // 用GBK编码判断中英文、特殊符号混合的字符串长度(每个中文的UTF-8比GBK多一位，要用GBK)
        String textErrMsg = "文本内容长度不能超过130(包括空格、换行等，纯英文不超过260)";
        boolean isContainChinese = isContainChinese(text);
        int textLength = text.getBytes(CharsetUtils.gbk()).length;
        if (isContainChinese && textLength > 130) {
            // 微博接口要求是长度不大于140，这里限制为130，避免这里长度检查通过而微博接口不通过的情况
            throw new WeiboException(textErrMsg);
        } else if (!isContainChinese && textLength > (130 * 2)) {
            // 无中文的字符串实际长度限为140*2，这里限制位130*2
            throw new WeiboException(textErrMsg);
        }

        try {
            // return URLEncoder.encode(text, CharsetUtils.UTF_8); // 官网上说需要做URLEncode，但实际做了返回会乱码，所以去掉(应该是新版有改动)
            return text;
        } catch (Exception e) {
            throw new WeiboException(e);
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
            if (Pattern.matches("^[\\s\\S]*http(s)?://" + safeDomain + "[\\s\\S]*$", statusText)) {
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
                OKHttpSender.getInstance().httpGetOriginalUrlFile(picPath, filePath);
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
}