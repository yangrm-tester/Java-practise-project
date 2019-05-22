package com.yrm.so2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ImagUtil
 * @createTime 2019年05月21日 17:41:00
 */
public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 返回的地址就是项目的地址  例如本项目的地址： /E:/IdeaWorkSpace/so2o/target/classes/
     */
    private static final String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private static final String WATER_MARK_JPG_NAME = "watermark.jpg";

    private static final String PATTERN = "yyyyMMddHHmmss";

    private static final Random random = new Random();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN);

    private static final int RANDOM_BOUND_VALUE = 89999;

    private static final int RANDOM_START_VALUE = 10000;

    public static String generateThumbnail(CommonsMultipartFile file, String targetAddr) {
        /**获得自己生成的文件名字*/
        String realFileName = getRandomFileName();
        /**拿到文件的后缀*/
        String ext = getFileExt(file);
        /**创建目标路径不存在则创建*/
        mikDir(targetAddr);
        String dest = targetAddr + realFileName + ext;
        logger.info("Method [generateThumbnail] call start .Input params===>[realFileName={},ext={},dest={}]", realFileName, ext, dest);
        try {
            Thumbnails.of(file.getInputStream()).size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + WATER_MARK_JPG_NAME)), 0.25f).outputQuality(0.8f).toFile(dest);
        } catch (Exception e) {
            logger.error("file thumbnail fail. Error===[{}]", e.getMessage());
            return null;
        }
        return dest;
    }


    private static void mikDir(String targetAddr) {
        String realPath = PathUtil.getImageBasePath() + targetAddr;
        File realPathFile = new File(realPath);
        if (!realPathFile.exists()) {
            /**创建所有的目录 如果包含多个层级的话*/
            realPathFile.mkdirs();
        }

    }

    private static String getFileExt(CommonsMultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return null;
    }

    /**
     * 生成随机文件名 当前日期yyyymmddHHmmss +5位随机数
     */
    private static String getRandomFileName() {
        String dateStr = DATE_FORMAT.format(new Date());
        int randomNum = random.nextInt(RANDOM_BOUND_VALUE) + RANDOM_START_VALUE;
        return dateStr + randomNum;
    }
}
