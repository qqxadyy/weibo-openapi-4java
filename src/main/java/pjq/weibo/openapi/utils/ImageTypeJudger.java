package pjq.weibo.openapi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <p>
 * 图片文件类型判断工具<br/>
 * 读取文件的前几个字节，文件后缀不存在也可以解析
 * <p>
 * Create at 2019年1月15日
 * 
 * @author pengjianqiang
 */
public final class ImageTypeJudger {
    private ImageTypeJudger() {}

    public static final String NOT_IMAGE = "notImage";

    /**
     * 判断文件是否图片类型
     * 
     * @param filePath
     *            文件绝对路径
     * @return
     */
    public static boolean isImage(String filePath) {
        return isImage(new File(filePath));
    }

    /**
     * 判断文件是否图片类型
     * 
     * @param file
     *            文件对象
     * @return
     */
    public static boolean isImage(File file) {
        try {
            return !NOT_IMAGE.equals(getImageType(file));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取图片类型(jpg/png/gif/bmp)
     * 
     * @param filePath
     *            文件绝对路径
     * @return
     */
    public static String getImageType(String filePath) {
        return getImageType(new File(filePath));
    }

    /**
     * 获取图片类型(jpg/png/gif/bmp)
     * 
     * @param file
     *            文件对象
     * @return
     */
    public static String getImageType(File file) {
        String filePath = file.getAbsolutePath();
        if (!file.exists()) {
            throw new RuntimeException(filePath + "不存在");
        } else if (file.isDirectory()) {
            throw new RuntimeException(filePath + "不是文件");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] b = new byte[4]; // 读取文件的前几个字节来判断图片格式
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                return "jpg";
            } else if (type.contains("89504E47")) {
                return "png";
            } else if (type.contains("47494638")) {
                return "gif";
            } else if (type.contains("424D")) {
                return "bmp";
            } else {
                return NOT_IMAGE;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(filePath + "不存在");
        } catch (IOException e) {
            throw new RuntimeException(filePath + "读取失败");
        }
    }

    /**
     * byte数组转换成16进制字符串
     * 
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}