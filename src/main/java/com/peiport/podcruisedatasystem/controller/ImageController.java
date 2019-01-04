package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.common.Constant;
import com.peiport.podcruisedatasystem.entity.Image;
import com.peiport.podcruisedatasystem.entity.Report;
import com.peiport.podcruisedatasystem.entity.Tower;
import com.peiport.podcruisedatasystem.entity.ftp.FTPObject;
import com.peiport.podcruisedatasystem.entity.pdf.GeneratePDF;
import com.peiport.podcruisedatasystem.entity.util.LayuiReplay;
import com.peiport.podcruisedatasystem.common.util.TimeConvertUtil;
import com.peiport.podcruisedatasystem.common.util.ftp.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.net.ftp.FtpProtocolException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/image")
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);


    @Value("${web.zip-path}")
    private String zipSavePath;   //压缩文件保存目录

    @Value("${web.temp}")     //临时文件保存目录
    private String tempPath;

    @Value("${web.pdfPath}")
    private String pdfPath;


    @RequestMapping("/listMenu")
    public String list(Model model, HttpSession session) throws IOException, FtpProtocolException {
        String type = (String) session.getAttribute("type");
        String path = (String) session.getAttribute("path");
        FTPObject ftpObject = (FTPObject) session.getAttribute("ftpObject");
        //左侧菜单
        Set<Tower> towerSet = new HashSet<>();
        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {  //本地文件
        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {  //ftp文件
            String imagePath = path + Constant.FTP_IMG_PATH_NAME;
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            Set<String> towerNumSet = new HashSet<>();
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(imagePath.getBytes("GBK"), "iso-8859-1"));
            int i = 1;
            for (FTPFile file : ftpFileList) {
                if (file.getName().split("\\.")[1].toLowerCase().equals("jpg")) {
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
        return "image/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public LayuiReplay<Tower> getImageList(String towerNum, int page, int limit, HttpServletRequest request) throws IOException, FtpProtocolException, ParseException {
        String type = (String) request.getSession().getAttribute("type");
        String path = (String) request.getSession().getAttribute("path");
        FTPObject ftpObject = (FTPObject) request.getSession().getAttribute("ftpObject");
        List<Tower> towerList = new ArrayList<>();
        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {  //本地文件

        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {  //ftp文件
            String imagePath = path + Constant.FTP_IMG_PATH_NAME;
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(imagePath.getBytes("GBK"), "iso-8859-1"));
            int i = 1;
            for (FTPFile file : ftpFileList) {
                if (towerNum != null && file.getName().contains(towerNum) && file.getName().split("\\.")[1].toLowerCase().equals("jpg")) {
                    Tower tower = new Tower();
                    tower.setId(i++);
                    tower.setAlarmType(file.getName().split("_")[8]);
                    tower.setTowerName(file.getName().split("_")[5]);
                    tower.setTemperature(Double.parseDouble(file.getName().split("_")[7]));
                    tower.setLocaltion(file.getName().split("_")[6]);
                    tower.setTowerNum(file.getName().split("_")[4]);
                    tower.setPhotoTime(TimeConvertUtil.StringToDate(file.getName().split("_")[9]));
                    towerList.add(tower);
                }
            }
            FTPUtil.closeConnection(ftpClient);
        }
        int fromIndex = (page - 1) * limit;
        int toIndex = fromIndex + limit;
        if (toIndex > towerList.size()) {
            toIndex = towerList.size();
        }
        return new LayuiReplay<Tower>(0, "OK", towerList.size(), towerList.subList(fromIndex, toIndex));

    }

    @RequestMapping("/check")
    public String checkImages(String towerNum, String date, String towerName, String localtion, Double temperature, Model model, HttpSession session) throws Exception {
        model.addAttribute("towerNum", towerNum);
        model.addAttribute("date", date);
        model.addAttribute("towerName", towerName);
        model.addAttribute("localtion", localtion);
        model.addAttribute("temperature", temperature);
        model.addAttribute("title", "查看图片");
        String path = (String) session.getAttribute("path");
        String routeName = path.split("_")[3];
        model.addAttribute("routeName", routeName);
        String imagePath = path + Constant.FTP_IMG_PATH_NAME;
        session.setAttribute("imagePath", imagePath);   //导出文件需要用到
        FTPObject ftpObject = (FTPObject) session.getAttribute("ftpObject");
        FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
        FTPFile[] images = new FTPFile[3];
        List<String> imagePathList = new ArrayList<>();
        List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, new String(imagePath.getBytes("GBK"), "iso-8859-1"));
        String dt = date.replace("-", "");
        dt = dt.replace(" ", "");
        dt = dt.replace(":", "");
        File f = new File(tempPath);
        if (!f.exists()) {   //没有文件夹就先创建再保存文件
            f.mkdirs();
        }else{
            File[] files = f.listFiles();
            if (files != null)
            for (File file: files){
                file.delete();
            }
        }
        String zipFileName_prefix ="";
        for (FTPFile file : ftpFileList) {
            if (file.getName().contains(towerNum) && file.getName().contains(dt)
                    && file.getName().contains(temperature.intValue() + "")) {
                String[] split = (file.getName().split("\\.")[0]).split("_", 2);
                zipFileName_prefix = split[1];
                FTPClient client = FTPUtil.loginFTP(ftpObject);
                InputStream in = client.retrieveFileStream(new String((imagePath + file.getName()).getBytes("GBK"), "iso-8859-1"));
                String fileSavePath = tempPath + "/" + file.getName();
                OutputStream out = new FileOutputStream(fileSavePath);
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                if (!file.getName().contains("csv")) {
                    imagePathList.add(file.getName());
                }
                FTPUtil.closeIO(out, in);
                FTPUtil.closeConnection(client);
            }
        }
        FTPUtil.closeConnection(ftpClient);
        File file = new File(tempPath);
        // File zipFile = new File("D:/dataSave/视频相关.zip");
        File zipFile = new File(zipSavePath);
        if (!zipFile.exists()) {
            zipFile.mkdirs();
        }else{
            File[] files = zipFile.listFiles();
            if (files!=null){
                LOGGER.info("zip文件保存目录正在删除文件.....");
                for(File fl:files){
                    fl.delete();
                }
            }
        }
        LOGGER.info("zipfilename=:"+zipFileName_prefix);
        String zipFileName = zipFileName_prefix + ".zip";
        FTPUtil.zipCompress(zipSavePath +"/"+ zipFileName, file);  //压缩成zip文件保存
        session.setAttribute("zipFileName",zipFileName);  //保存到session导出文件时用到
        session.setAttribute("images", images);
        FTPUtil.closeConnection(ftpClient);
        session.setAttribute("imagePathList", imagePathList);
        return "image/checkImages";
    }

    @RequestMapping("/getImages")
    @ResponseBody
    public List<String> getImages(HttpSession session) {
        List<String> imagePaths = (List<String>) session.getAttribute("imagePathList");
        return imagePaths;
    }


    @RequestMapping("/exportImg")
    @ResponseBody
    public void exportImg(HttpServletRequest request, HttpServletResponse response) throws IOException, FtpProtocolException {
        response.setContentType("application/x-msdownload;charset=utf-8");
        String fileName = (String)request.getSession().getAttribute("zipFileName");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        File file = new File(zipSavePath + fileName);
        InputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = in.read(buf)) != -1) {
            out.write(buf, 0, length);
        }
        FTPUtil.closeIO(out, in);
    }

    @RequestMapping("/report")
    public String reportPage(HttpServletRequest request,Model model) throws ParseException {
        List<String> imagePathList = (List<String>) request.getSession().getAttribute("imagePathList");
        LOGGER.info("object="+imagePathList);
        if (imagePathList.size()>0){
            String KJGPath = imagePathList.get(0);    //可见光路径名称
            Image KJGJTImage = new Image();
            KJGJTImage.setOffice(KJGPath.split("_")[1]);
            KJGJTImage.setVoltage(KJGPath.split("_")[2]);
            KJGJTImage.setRouteName(KJGPath.split("_")[3]);
            KJGJTImage.setTowerNum(KJGPath.split("_")[4]);
            KJGJTImage.setDeviceName(KJGPath.split("_")[5]);
            KJGJTImage.setTime(TimeConvertUtil.StringToDate(KJGPath.split("_")[9].split("\\.")[0]));
            KJGJTImage.setPath("../"+KJGPath);
            KJGJTImage.setImageName(KJGPath);
            String HWJTPath = imagePathList.get(1);
            Image HWJTImage = new Image();
            HWJTImage.setImageName(HWJTPath);
            HWJTImage.setTime(TimeConvertUtil.StringToDate(HWJTPath.split("_")[9].split("\\.")[0]));
            HWJTImage.setPath("../"+HWJTPath);
            model.addAttribute("kjgImage",KJGJTImage);
            model.addAttribute("hwjtImage",HWJTImage);
        }

        model.addAttribute("title","巡航检测报告");
        return "image/report";
    }

    @RequestMapping("/report/create")
    @ResponseBody
    public int createReport(@ModelAttribute Report report, HttpSession session) throws Exception {
        LOGGER.info("report=" + report);
        //将项目中的logo图片下载到本地
        ClassPathResource classPathResource = new ClassPathResource("static/img/logo/国家电网lr.jpg");
        InputStream in = null;
        OutputStream out = null;
        OutputStream os = null;
        in = classPathResource.getInputStream();
        String path = pdfPath + "pdfLogo.jpg";
        File file = new File(pdfPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        out = new FileOutputStream(path);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        Date date = new Date();
        String fileName = "巡航检测报告" + TimeConvertUtil.dateToString(date) + ".pdf";
        session.setAttribute("pdfFileName",fileName);
        out = new FileOutputStream(pdfPath + fileName);
        GeneratePDF.createPDF(out, report, path);
        FTPUtil.closeIO(out, in);
        return Constant.SUCCESS;
    }

    @RequestMapping("/report/download")
    @ResponseBody
    public void downloadReport(HttpSession session,HttpServletResponse response) throws IOException {
        String pdfFileName = (String) session.getAttribute("pdfFileName");
        File file = new File(pdfPath + pdfFileName);
        if(file.exists()){
            LOGGER.info("报告下载文件名为："+file.getName());
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(pdfFileName.getBytes("gb2312"), "iso8859-1"));
            InputStream in = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            FTPUtil.closeIO(out, in);
        }

    }
}
