package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.common.Constant;
import com.peiport.podcruisedatasystem.entity.OperateSummary;
import com.peiport.podcruisedatasystem.entity.ftp.FTPObject;
import com.peiport.podcruisedatasystem.entity.util.LayuiReplay;
import com.peiport.podcruisedatasystem.util.TimeConvertUtil;
import com.peiport.podcruisedatasystem.util.ftp.FTPUtil;
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

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/summary")
public class OperateSummaryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperateSummaryController.class);


    @RequestMapping("/list")
    public String list(String routeName, String date, Model model) {
        model.addAttribute("routeName", routeName);
        model.addAttribute("date", date);
        return "operateSummary/list";
    }

    @RequestMapping("/find")
    @ResponseBody
    public LayuiReplay<OperateSummary> findOperateSummary(String routeName, String date, int page, int limit, HttpSession session) throws IOException, FtpProtocolException, ParseException {
        List<OperateSummary> operateSummaryList = new ArrayList<>();
        int type = Integer.parseInt((String) session.getAttribute("type"));
        FTPObject ftpObject = (FTPObject) session.getAttribute("ftpObject");
        if (type == Constant.FILE_TYPE_LOCAL) {  //本地

        } else if (type == Constant.FILE_TYPE_FTP) {     //ftp
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObject);
            List<FTPFile> ftpFileList = (List<FTPFile>) session.getAttribute("ftpFileList");
            if (routeName != null && !routeName.equals("") && date != null && !date.equals("")) {
                InputStream in = null;
                for (FTPFile file : ftpFileList) {
                    if (file.getName().contains(routeName) && file.getName().contains(date)) {
                        //查找操作纪要文件
                        String path = Constant.FTP_DIRCTORY_ROOT + file.getName()+ "/";
                        List<FTPFile> ftpFiles  = FTPUtil.list(ftpClient, new String((path ).getBytes("GBK"), "iso-8859-1"));
                        if (ftpFiles.size() != 0) {
                            for (FTPFile f : ftpFiles) {
                                if (f.isFile() && f.getName().split("\\.")[1].toLowerCase().equals("csv")) {
                                    in = ftpClient.retrieveFileStream(new String((path + "/" + f.getName()).getBytes("GBK"), "iso-8859-1"));
                                    InputStreamReader reader = new InputStreamReader(in);//转为字符流
                                    BufferedReader bufferedReader = new BufferedReader(reader);
                                    bufferedReader.readLine();//忽略第一行
                                    String line = null;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        String[] contents = line.split(",");
                                        OperateSummary summary = new OperateSummary();
                                        summary.setId(Integer.parseInt(contents[0]) + 1);
                                        summary.setDate(TimeConvertUtil.StringToDate(contents[1]));
                                        summary.setTowerNum(contents[2]);
                                        summary.setPollingNum(Integer.parseInt(contents[3]));
                                        summary.setEventClass(contents[4]);
                                        summary.setEventContent(contents[5]);
                                        operateSummaryList.add(summary);
                                    }
                                    bufferedReader.close();
                                    reader.close();
                                    in.close();
                                }

                            }
                        }
                        session.setAttribute("path", path);
                    }
                }
                FTPUtil.closeConnection(ftpClient);
            }
        }
        int fromIndex = (page - 1) * limit;
        int toIndex = fromIndex + limit;
        if (toIndex > operateSummaryList.size()) {
            toIndex = operateSummaryList.size();
        }
        return new LayuiReplay<OperateSummary>(0, "OK", operateSummaryList.size(), operateSummaryList.subList(fromIndex, toIndex));
    }

}
