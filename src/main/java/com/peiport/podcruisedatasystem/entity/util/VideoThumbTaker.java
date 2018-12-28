package com.peiport.podcruisedatasystem.entity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//截取视频几时几分几秒的图片
public class VideoThumbTaker {
    private static  final Logger LOGGER = LoggerFactory.getLogger(VideoThumbTaker.class);
    public VideoThumbTaker() {

    }

    /****
         * 获取指定时间内的图片
         * @param videoFilename:视频路径
         * @param thumbFilename:图片保存路径
         * @param width:图片长
         * @param height:图片宽
         * @param hour:指定时
         * @param min:指定分
         * @param sec:指定秒
         * @throws IOException
         * @throws InterruptedException
         */
//    public void getThumb(String ffmpegApp,String videoFilename, String thumbFilename, int width, int height, int hour, int min, float sec)  {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y", "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
//                    "-an", thumbFilename);
//            Process process  = processBuilder.start();
//            InputStream stderr = process.getErrorStream();
//            InputStreamReader isr = new InputStreamReader(stderr);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null)
//                process.waitFor();
//                br.close();
//                isr.close();
//                stderr.close();
//                process.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}

    public void getOneFrame(String videoFilename, String thumbFilename, int width, int height, String time)  {
        StringBuilder sb = new StringBuilder();
        //sb.append("cmd/c  ");
        sb.append("ffmpeg -y -i  ");
        sb.append(videoFilename);
        sb.append("  -vframes 1 -ss  ");
        sb.append(time);
        sb.append("  -f mjpeg -s  ");
        sb.append(width);
        sb.append("*");
        sb.append(height);
        sb.append("  -an   ");
        sb.append(thumbFilename);
        Process process = null;
        BufferedReader br = null;
        try {
            process = Runtime.getRuntime().exec(sb.toString());
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                process.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("ffmpeg执行的cmd命令为：" + sb);
    }

//    public static void main(String[] args) {
//	VideoThumbTaker videoThumbTaker = new VideoThumbTaker("E:\\java_developing\\ffmpeg-win64-static\\bin\\ffmpeg.exe");
//	try {
//	    videoThumbTaker.getThumb("D:\\video\\环保小视频.mp4", "D:\\img\\vbhdu.png", 800, 600, 0, 0, 5);
//	System.out.println("over");
//	} catch (Exception e) {
//	e.printStackTrace();
//	}
//    }
}

