package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.common.Constant;
import com.peiport.podcruisedatasystem.entity.ftp.FTPObject;
import com.peiport.podcruisedatasystem.entity.menu.Child;
import com.peiport.podcruisedatasystem.entity.menu.Parent;
import com.peiport.podcruisedatasystem.common.util.ftp.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.net.ftp.FtpProtocolException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/route")
public class RouteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);
    @RequestMapping("/index")
    public String index(Model model, @RequestParam(defaultValue = "1") String type, HttpServletRequest request) throws IOException, FtpProtocolException {
        List<Parent> parentList = new ArrayList<>();
        request.getSession().setAttribute("type", type);
        if (Integer.parseInt(type) == Constant.FILE_TYPE_LOCAL) {   //本地文件
            model.addAttribute("title", "本地文件");

        } else if (Integer.parseInt(type) == Constant.FILE_TYPE_FTP) {    //ftp文件
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String ip = request.getParameter("ip");
            String port = request.getParameter("port");
            FTPObject ftpObj = new FTPObject(ip, Integer.parseInt(port), username, password);
            request.getSession().setAttribute("ftpObject",ftpObj);
            LOGGER.info("ftp登录信息："+ftpObj);
            //FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码。
            FTPClient ftpClient = FTPUtil.loginFTP(ftpObj);
            String rootPath = new String(Constant.FTP_DIRCTORY_ROOT.getBytes("GBK"), "iso-8859-1");
            request.getSession().setAttribute("path", rootPath);
            Set<String> titleSet = new HashSet<>();
            List<FTPFile> ftpFileList = FTPUtil.list(ftpClient, rootPath); //查看data目录下的文件夹
            //一级菜单
            for (FTPFile file : ftpFileList) {
                titleSet.add(file.getName().split("_")[3]);
            }
            //一级菜单下的二级菜单
            for (String title : titleSet) {
                Parent parent = new Parent();
                parent.setTitle(title);
                List<Child> childList = new ArrayList<>();
                for (FTPFile file : ftpFileList) {
                    if (file.getName().split("_")[3].equals(title)) {
                        Child child = new Child();
                        child.setName(file.getName().split("_")[4]);
                        childList.add(child);
                    }
                }
                parent.setChildList(childList);
                parentList.add(parent);
            }
            FTPUtil.closeConnection(ftpClient);
            model.addAttribute("title", "ftp文件");
            request.getSession().setAttribute("ftpFileList", ftpFileList);
            model.addAttribute("parentList", parentList);
        }
        return "route/index";
    }

    @RequestMapping("/report")
    public String report() {
        return "route/report";
    }

}
