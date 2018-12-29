package com.peiport.podcruisedatasystem.common.Timer;

import com.peiport.podcruisedatasystem.common.util.TimeConvertUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Component
public class Timer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);

    @Value("${web.pdfPath}")
    private String pdfPath;

    /**
     * 定时查看PDF目录，超过一定大小删除目录文件
     * @throws IOException
     */
    @Scheduled(fixedRate = 1000*60*60*2)   //单位毫秒
    public void deleteFile() throws IOException {
        Date date = new Date();
        File file = new File(pdfPath);
        if (file.exists()){
            long sizeOfDirectory = FileUtils.sizeOfDirectory(file);
            LOGGER.info("pdf文件储存目录大小为："+sizeOfDirectory);
            if (sizeOfDirectory> (long) 1024 * 1024 * 1024 * 5){
                LOGGER.info("pdf文件储存目录大小已超过5G，正在清除文件，清除时间："+TimeConvertUtil.dateFormat2(date));
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            }
        }

    }
}
