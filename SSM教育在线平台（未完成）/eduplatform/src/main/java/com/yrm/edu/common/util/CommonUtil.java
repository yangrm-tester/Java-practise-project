package com.yrm.edu.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className CommonUtil
 * @createTime 2019年05月29日 11:08:00
 * 公用的工具类
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * byte数组集合转换byte二维数组
     *
     * @param: [bytes]
     * @return: byte[][]
     * @Description:
     */

    public static byte[][] toArray(List<byte[]> bytes) {
        if (!CollectionUtils.isEmpty(bytes)) {
            byte[][] byteTarry = new byte[bytes.size()][];
            for (int i = 0; i < bytes.size(); i++) {
                byteTarry[i] = bytes.get(i);
            }
            return byteTarry;
        }
        return null;
    }

    /**
     * 将文件转换成字节数组
     *
     * @param: [file]
     * @return: byte[]
     * @Description:
     */

    public static byte[] getFileBytes(File file) throws IOException {
        if (file != null) {
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream baout = new ByteArrayOutputStream(4096);
            byte[] readBytes = new byte[4096];
            int n;
            byte[] retByte;
            try {
                while ((n = fin.read(readBytes)) != -1) {
                    baout.write(readBytes, 0, n);
                }
                baout.flush();
                retByte = baout.toByteArray();
                return retByte;
            } catch (IOException e) {
                logger.error("get File bytes error.Error===>[{}]", e.getMessage());
                return null;
            } finally {
                if (fin != null) {
                    fin.close();
                }
                if (baout != null) {
                    baout.close();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\rumingyang\\Desktop\\httpUtil.java");
        System.out.println(getFileBytes(file).length);;
    }
}
