package com.peiport.podcruisedatasystem.controller;

import com.peiport.podcruisedatasystem.exception.MyException;
import com.peiport.podcruisedatasystem.util.ftp.FTPUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class IndexController {

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/test/{id}")
    public String test(@PathVariable Integer id, Model model){
        if(true){
            id = 1/id;
        }
     return "test/test2";
    }
    @RequestMapping("/test2")
    public String test2(Model model){
       model.addAttribute("title","仪表测试");
        return "test/test2";
    }

}
