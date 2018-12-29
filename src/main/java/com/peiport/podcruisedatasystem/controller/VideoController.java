package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.common.Constant;
import com.peiport.podcruisedatasystem.entity.Tower;
import com.peiport.podcruisedatasystem.entity.VideoSynchronizedCsv;
import com.peiport.podcruisedatasystem.entity.ftp.FTPObject;
import com.peiport.podcruisedatasystem.entity.util.LayuiReplay;
import com.peiport.podcruisedatasystem.entity.util.VideoThumbTaker;
import com.peiport.podcruisedatasystem.common.util.TimeConvertUtil;
import com.peiport.podcruisedatasystem.common.util.ftp.FTPUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.ftp.FtpProtocolException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/video")
public class VideoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    @Value("${web.video-path}")
    private String videoSavePath;

    @Value("${web.image-path}")
    private String imageSavePath;

    @Value("${web.ffmpeg-path}")
    private String ffmpegPath;

//    @Value("${web.exportImg-path}")
//    private String exportImgPath;

    @RequestMapping("/listMenu")
    public String list(Model model, HttpSession session) throws IOException, FtpProtocolException {
        String type = (String) session.getAttribute("type");
        String path = (String) session.getAttribute("path");
        FTPObject ftpObject = (FTPObject) session.getAttribute("ftpObject");

        //左侧菜单
        Set<Tower> towerSet = new HashSet<>();
        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {  //本地文件

        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {  //ftp文件
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            String videoPath = path + Constant.FTP_VIDEO_PATH_NAME;
            Set<String> towerNumSet = new HashSet<>();
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(videoPath.getBytes("GBK"), "iso-8859-1"));
            for (FTPFile file : ftpFileList) {
                if (file.isFile() && file.getName().split("\\.")[1].toLowerCase().equals("mp4")) {
                    towerNumSet.add(file.getName().split("_")[4]);
                }
            }
            for (String number : towerNumSet) {
                Tower tower = new Tower();
                tower.setTowerNum(number);
                towerSet.add(tower);
            }
            FTPUtil.closeConnection(ftpClient);
        }
        model.addAttribute("towerMenu", towerSet);
        return "video/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public LayuiReplay<Tower> getImageList(String towerNum, int page, int limit, HttpServletRequest request) throws IOException, FtpProtocolException, ParseException {
        String type = (String) request.getSession().getAttribute("type");
        String path = (String) request.getSession().getAttribute("path");
        FTPObject ftpObject = (FTPObject) request.getSession().getAttribute("ftpObject");
        Set<Tower> towerSet = new HashSet<>();
        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {  //本地文件

        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {  //ftp文件
            String videoPath = path + Constant.FTP_VIDEO_PATH_NAME;
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(videoPath.getBytes("GBK"), "iso-8859-1"));
            Set<String> towerNumSet = new HashSet<>();
            for (FTPFile file : ftpFileList) {
                if (file.getName().split("\\.")[1].toLowerCase().equals("mp4")&& file.getName().contains(towerNum)) {
                    towerNumSet.add(file.getName().split("_")[4]);
                }
            }
            for (String number : towerNumSet) {
                for (FTPFile file : ftpFileList) {
                    int i = 1;
                    //
                    if (file.getName().split("\\.")[1].toLowerCase().equals("mp4") && file.getName().contains(number)&& file.getName().contains("红外视频") ) {
                        Tower tower = new Tower();
                        tower.setId(i++);
                        tower.setTowerNum(number);
                        tower.setPhotoTime(TimeConvertUtil.StringToDate(file.getName().split("_")[5].split("\\.")[0]));
                        towerSet.add(tower);
                    }
                }
            }
            FTPUtil.closeConnection(ftpClient);
        }
        List<Tower> towerList = new ArrayList<>(towerSet);
        int fromIndex = (page - 1) * limit;
        int toIndex = fromIndex + limit;
        if (toIndex > towerList.size()) {
            toIndex = towerList.size();
        }
        return new LayuiReplay<Tower>(0, "OK", towerList.size(), towerList.subList(fromIndex, toIndex));
    }

    @RequestMapping("/check")
    public String checkVideoPage(String towerNum, Model model, HttpServletRequest request) throws IOException, FtpProtocolException, ParseException {
        String type = (String) request.getSession().getAttribute("type");
        String path = (String) request.getSession().getAttribute("path");
        FTPObject ftpObject = (FTPObject) request.getSession().getAttribute("ftpObject");
        model.addAttribute("towerNum", towerNum);
        model.addAttribute("title", "查看视频");
        FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
        String videoPath = path + Constant.FTP_VIDEO_PATH_NAME;  //视频文件夹路径
        List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(videoPath.getBytes("GBK"), "iso-8859-1"));
        //查找视频同步CSV文件并将数据保存
        List<VideoSynchronizedCsv> csvList = new ArrayList<>();
        boolean isExist = false;
        for (FTPFile file : ftpFileList) {
            if (file.getName().split("\\.")[1].toLowerCase().equals("csv")) {
                isExist = true;
                LOGGER.info("视频同步CSV文件已存在，正在读取.....");
                FTPClient client = FTPUtil.loginFTP(ftpObject);
                String videoCsvPath = videoPath + file.getName();
                InputStream in = client.retrieveFileStream(new String(videoCsvPath.getBytes("GBK"), "iso-8859-1"));
                InputStreamReader reader = new InputStreamReader(in);//转为字符流
                BufferedReader bufferedReader = new BufferedReader(reader);
                bufferedReader.readLine();//忽略第一行
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] contents = line.split/**/(",");
                    VideoSynchronizedCsv csv = new VideoSynchronizedCsv();
                    csv.setId(Integer.parseInt(contents[0]));
                    csv.setDate(TimeConvertUtil.StringToDate(contents[1]));
                    csv.setPollingNum(Integer.parseInt(contents[2]));
                    csv.setTowerNum(contents[3]);
                    csv.setPodBearing(Double.parseDouble(contents[4]));
                    csv.setPodPitching(Double.parseDouble(contents[5]));
                    csv.setPodRolling(Double.parseDouble(contents[6]));
                    csv.setVisibleLightAngle(Double.parseDouble(contents[7]));
                    csv.setIRAngle(Double.parseDouble(contents[8]));
                    if (contents[9].equals("TV")) {
                        csv.setCurrentChannel(Constant.CHANNEL_TV);
                    } else if (contents[9].equals("IR")) {
                        csv.setCurrentChannel(Constant.CHANNEL_IR);
                    }
                    csv.setGPSLng(Double.parseDouble(contents[10]));
                    csv.setGPSLat(Double.parseDouble(contents[11]));
                    csv.setGPSHeight(Double.parseDouble(contents[12]));
                    csv.setPlaneDirection(Double.parseDouble(contents[13]));
                    csv.setPlanePitching(Double.parseDouble(contents[14]));
                    csv.setPlaneRolling(Double.parseDouble(contents[15]));
                    csv.setBoresightDirection(Double.parseDouble(contents[16]));
                    csv.setBoresightPitching(Double.parseDouble(contents[17]));
                    csv.setBoresightRolling(Double.parseDouble(contents[18]));
                    csv.setTargetLng(Double.parseDouble(contents[19]));
                    csv.setTargetLat(Double.parseDouble(contents[20]));
                    csv.setGroundDistance(Double.parseDouble(contents[21]));
                    csv.setLaserDistance(Double.parseDouble(contents[22]));
                    csv.setSurfaceHeight(Double.parseDouble(contents[23]));
                    csvList.add(csv);
                }

              //  LOGGER.info("csvlist的size：" + csvList.size());
                bufferedReader.close();
                reader.close();
                in.close();
            }
        }
        if (!isExist ){
            LOGGER.info("视频同步CSV文件不存在....");
            csvList = null;
            model.addAttribute("csvObject", null);
        }
        if (null != csvList && !csvList.isEmpty()){
            request.getSession().setAttribute("csvList", csvList);
            model.addAttribute("csvObject", csvList.get(0));
        }

        return "video/checkVideo";
    }



    @RequestMapping("/videoList")
    @ResponseBody
    public List<String> checkVideo(String towerNum, HttpServletRequest request) throws IOException, FtpProtocolException {
        String type = (String) request.getSession().getAttribute("type");
        String path = (String) request.getSession().getAttribute("path");
        FTPObject ftpObject = (FTPObject) request.getSession().getAttribute("ftpObject");
        OutputStream out = null;
        List<String> videoPathList = new ArrayList<>();
        String HWVideoName = "HWVideo.mp4"; //红外视频名称
        String KJGVideoName = "KJGVideo.mp4"; //可见光视频名称
        String HWVideoPath = videoSavePath + HWVideoName;  //红外视频路径
        String KJGVideoPath = videoSavePath + KJGVideoName;  //可见光视频路径

        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {  //本地文件

        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {  //ftp文件
            String videoPath = path + Constant.FTP_VIDEO_PATH_NAME;
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            //判断视频保存路径是否存在，不存在就创建,存在就删除里面的文件，防止磁盘爆满
            File targetDirectory = new File(videoSavePath);
            long size = FileUtils.sizeOfDirectory(targetDirectory);
            LOGGER.info("视频保存目录大小为："+size);
            if (targetDirectory.exists()) {
                File[] files = targetDirectory.listFiles();
                if (files != null && files.length != 0) {
                    for (File file : files) {
                        file.delete();
                    }
                    LOGGER.info("删除视频保存目录" + targetDirectory + "下的所有文件");
                }
            }

            File f = new File(videoSavePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(videoPath.getBytes("GBK"), "iso-8859-1"));
            long startTime = System.currentTimeMillis();
            for (FTPFile file : ftpFileList) {
                if (file.getName().split("\\.")[1].toLowerCase().equals("mp4") && file.getName().contains(towerNum)) {
                    //红外视频下载到本地
                    if (file.getName().contains("红外视频")) {
                        FTPClient ftpClient1 = FTPUtil.loginFTP(ftpObject);
                        out = new BufferedOutputStream(new FileOutputStream(HWVideoPath));
                        //ftp视频保存到本地
                        boolean result = ftpClient1.retrieveFile(new String((videoPath + file.getName()).getBytes("GBK"), "iso-8859-1"), out);
                        if (result) {
                            videoPathList.add(HWVideoName);
                            LOGGER.info("红外视频下载到本地成功...");
                        }
                        out.close();
                    }
                    //可见光视频下载到本地
                    if (file.getName().contains("可见光视频")) {
                        FTPClient ftpClient1 = FTPUtil.loginFTP(ftpObject);
                        out = new BufferedOutputStream(new FileOutputStream(KJGVideoPath));
                        //ftp视频保存到本地
                        boolean result = ftpClient1.retrieveFile(new String((videoPath + file.getName()).getBytes("GBK"), "iso-8859-1"), out);
                        if (result) {
                            videoPathList.add(KJGVideoName);
                            LOGGER.info("可见光视频下载到本地成功....");
                        }
                        out.close();
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            FTPUtil.closeConnection(ftpClient);
            LOGGER.info("视频下载所需时间：" + (endTime - startTime));
        }
        return videoPathList;
    }


    @RequestMapping("/screenshot")
    @ResponseBody
    public List<String> screenshot(String IRCurrentTime, String VRCurrentTime, String IRVideoSrc, String VRVideoSrc) {
        String IRTime = TimeConvertUtil.convertHMS(Double.parseDouble(IRCurrentTime));
        String VRTime = TimeConvertUtil.convertHMS(Double.parseDouble(VRCurrentTime));
        LOGGER.info("IRTime="+IRTime+",VRTime="+VRTime);
        String IRImgName = "JT_HW" + TimeConvertUtil.dateToString(new Date()) + ".jpg";
        String VRImgName = "JT_KJG" + TimeConvertUtil.dateToString(new Date()) + ".jpg";
        String IRImgPath = imageSavePath + IRImgName;
        String VRImgPath = imageSavePath + VRImgName;
        // LOGGER.info("split:"+IRVideoSrc.substring(IRVideoSrc.lastIndexOf("/"),IRVideoSrc.length()));
        String IRVideoPath = videoSavePath + IRVideoSrc.substring(IRVideoSrc.lastIndexOf("/")+1, IRVideoSrc.length());
        String VRVideoPath = videoSavePath + VRVideoSrc.substring(VRVideoSrc.lastIndexOf("/")+1, VRVideoSrc.length());   //  http://localhost:8089/KJGVideo.mp4
        File file = new File(imageSavePath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
        } else {
            file.mkdirs();
        }
        List<String> list = new ArrayList<>();
        LOGGER.info("视频截图保存中......");
        //初始化ffmpeg程序
        // Runtime.getRuntime().exec(ffmpegPath);
        //红外视频截图保存
        VideoThumbTaker videoThumbTaker = new VideoThumbTaker();
            videoThumbTaker.getOneFrame( IRVideoPath, IRImgPath, 400, 225,IRTime );
            videoThumbTaker.getOneFrame( VRVideoPath, VRImgPath, 400, 300,VRTime );
//        videoThumbTaker.getThumb(ffmpegPath, IRVideoPath, IRImgPath, 400, 225, IRTimes[0], IRTimes[1], IRTimes[2]);
//        //可见光截图保存
//        videoThumbTaker.getThumb(ffmpegPath, VRVideoPath, VRImgPath, 400, 300, VRTimes[0], VRTimes[1], VRTimes[2]);
        LOGGER.info("视频截图已保存......");
        list.add(IRImgName);
        list.add(VRImgName);
        return list;
    }

    @RequestMapping("/map")
    @ResponseBody
    public VideoSynchronizedCsv getLatLng(String second,HttpServletRequest request) {
        List<VideoSynchronizedCsv> csvList = (List<VideoSynchronizedCsv>) request.getSession().getAttribute("csvList");
        VideoSynchronizedCsv csvObject = null;
        if (null != csvList && !csvList.isEmpty()){
            if (Integer.parseInt(second)<csvList.size()){
                csvObject = csvList.get(Integer.parseInt(second));
            }else if (Integer.parseInt(second) >= csvList.size()){
                csvObject = csvList.get(csvList.size()-1);
            }
        }
        return csvObject;
    }


}
