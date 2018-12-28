package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.common.Constant;
import com.peiport.podcruisedatasystem.entity.Tower;
import com.peiport.podcruisedatasystem.util.ftp.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tower")
public class TowerController {

    @RequestMapping("/list")
    public String list(String towerNum, HttpSession session,Model model) throws Exception {
        int type =Integer.parseInt((String)session.getAttribute("type")) ;
        List<Tower> towerList = new ArrayList<>();
        if (type == Constant.FILE_TYPE_LOCAL){
            Tower tower = new Tower();
            tower.setTowerNum(towerNum);
            tower.setTowerName("未设定");
            tower.setAlarmType("");
            tower.setLocaltion("未设定");
            tower.setTemperature(335);
            towerList.add(tower);
        }else if (type == Constant.FILE_TYPE_FTP){
            //根据所在目录以及塔号寻找对应的图片
            String imgPath = session.getAttribute("path")+"图片/";
            FTPClient ftpClient =(FTPClient) session.getAttribute("ftpClient");
            System.out.println(imgPath);
            if (ftpClient != null){
                List<FTPFile> ftpFiles = FTPUtil.list(ftpClient, new String(imgPath.getBytes("GBK"), "iso-8859-1"));
            }
        }
        model.addAttribute("towerList",towerList.subList(0,10));
        return "image/list";
    }
}
