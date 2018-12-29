package com.peiport.podcruisedatasystem.common.util;


//封装工具类，截取中间时间的视频截图

import com.peiport.podcruisedatasystem.entity.util.VideoInfo;
import com.peiport.podcruisedatasystem.entity.util.VideoThumbTaker;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;

/**
 * 视频、图片工具类
 * @author lvyangfan
 *
 */
public class VideoUtils {
    @Value("${web.ffmpeg-path}")
    private String ffmpegPath;

    private VideoUtils(){

    }
//    public static void main(String[] args) {
//
//
//        try {
//            getCenterImg("E:\\java_developing\\ffmpeg-win64-static\\bin\\ffmpeg.exe","C:\\01.mp4","D:\\img\\b.png");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 获取视频中间时间点的截图
     * @param ffmpegPath 本地ffmpeg工具位置
     * @param videoPath 视频位置
     * @param imgPath 生成图片位置
     * @throws
     * @throws InterruptedException
     */
    public static void getCenterImg(String ffmpegPath,String videoPath,String imgPath) throws IOException, InterruptedException {

        VideoInfo videoInfo = new VideoInfo(ffmpegPath);
        videoInfo.getInfo(videoPath);
        System.out.println(videoInfo);
        int hours = videoInfo.getHours();
        int minutes = videoInfo.getMinutes();
        float seconds = videoInfo.getSeconds();
        float total = hours * 3600 + minutes * 60 + seconds;
        total = total / 2;

        hours = (int) (total / 3600);
        minutes = (int) ((total % 3600) / 60);
        seconds = total - hours * 3600 - minutes * 60;
        VideoThumbTaker videoThumbTaker = new VideoThumbTaker();
       // videoThumbTaker.getThumb(ffmpegPath,videoPath, imgPath, 800, 600, hours, minutes, seconds);
        System.out.println("over");

    }

    public static void exportImg(String sourceDirectory,String targetDirectory) throws IOException {
        // 新建文件输出流并对它进行缓冲
        FileInputStream input = null;
        FileOutputStream output = null;
        BufferedInputStream inBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            input = new FileInputStream(sourceDirectory);
            inBuff=new BufferedInputStream(input);
            output = new FileOutputStream(targetDirectory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream outBuff=new BufferedOutputStream(output);
        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len =inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }
}
