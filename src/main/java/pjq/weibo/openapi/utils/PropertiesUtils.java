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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;

import pjq.weibo.openapi.utils.collection.CollectionUtils;
import weibo4j.model.WeiboException;

/**
 * <p>
 * -Properties工具类
 * <p>
 * Create at 2019年1月15日
 * 
 * @author pengjianqiang
 */
public final class PropertiesUtils {
    private PropertiesUtils() {}

    /**
     * 更新资源文件
     * 
     * @param fileName
     *            properties文件名
     * @param propsToAddOrUpdate
     *            要新增/更新的配置项
     * @param propsToDelete
     *            要删除的配置项
     * @throws Exception
     */
    public static void updateProperties(String fileName, Map<String, String> propsToAddOrUpdate,
        Set<String> propsToDelete) throws Exception {
        try {
            StringBuilder newContent = new StringBuilder();
            String filePath = Thread.currentThread().getContextClassLoader().getResource(fileName).toURI().getPath();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                String deleteSymbol = "#";
                String equalSymbol = "=";

                String newLine = System.lineSeparator();
                String line = br.readLine();
                while (null != line) {
                    if (CheckUtils.isEmpty(line)) {
                        // 空行
                        newContent.append(newLine);
                        line = br.readLine();
                        continue;
                    }

                    String tempLine = line.trim();
                    if (line.startsWith(deleteSymbol)) {
                        // 注释行
                        newContent.append(tempLine).append(newLine);
                        line = br.readLine();
                        continue;
                    } else if (tempLine.indexOf(equalSymbol) < 0) {
                        // 没有=号，行有问题，注释掉
                        newContent.append(deleteSymbol).append(tempLine).append(newLine);
                        line = br.readLine();
                        continue;
                    }

                    String[] infos = tempLine.split(equalSymbol);
                    String key = infos[0].trim();
                    if (CheckUtils.isNotEmpty(propsToDelete) && propsToDelete.contains(key)) {
                        // 删除key
                        newContent.append(deleteSymbol).append("deleted auto by program").append(newLine);
                        newContent.append(deleteSymbol).append(tempLine).append(newLine);
                    } else if (CheckUtils.isNotEmpty(propsToAddOrUpdate) && propsToAddOrUpdate.containsKey(key)) {
                        // 更新key
                        newContent.append(key).append(equalSymbol)
                            .append(CheckUtils.getValue("", propsToAddOrUpdate.get(key))).append(newLine);
                        propsToAddOrUpdate.remove(key);
                    } else {
                        // 不处理
                        newContent.append(tempLine).append(newLine);
                    }
                    line = br.readLine();
                }

                // 新增key
                if (CheckUtils.isNotEmpty(propsToAddOrUpdate)) {
                    newContent.append(newLine);
                    CollectionUtils.forEach(propsToAddOrUpdate, entry -> {
                        String keyToAdd = entry.getKey();
                        String value = entry.getValue();
                        if (CheckUtils.isNotEmpty(keyToAdd)) {
                            newContent.append(deleteSymbol).append("added auto by program").append(newLine);
                            newContent.append(keyToAdd).append(equalSymbol).append(CheckUtils.getValue("", value))
                                .append(newLine);
                        }
                    });
                }
            }

            if (CheckUtils.isNotEmpty(newContent.toString())) {
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
                    bw.write(newContent.toString());
                }
            }
        } catch (Exception e) {
            throw new WeiboException("资源文件更新失败," + ExceptionUtils.getRootCauseMessage(e));
        }
    }
}