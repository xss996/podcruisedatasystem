package com.peiport.podcruisedatasystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
      // model.addAttribute("title","地图实时位置更新");
        return "test/test2";
    }

}
