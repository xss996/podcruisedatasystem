package com.peiport.podcruisedatasystem.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: joeshaw
 * @Date: 2018/12/11 09:42
 * @Description: 全局捕获异常和自定义全局捕获异常
 */
@ControllerAdvice
public class MyControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyControllerAdvice.class);
    /**
     * 全局异常处理，反正异常返回统一格式的map
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandler(Exception ex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("提示","系统发生错误或操作有误，请重试！");
        map.put("code", 1001);
        map.put("msg", ex.getMessage());
        ex.printStackTrace();
        LOGGER.error("全局异常："+ex);
        return map;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param myex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value =MyException.class)
    public Map<String,Object> myExceptionHandler(MyException myex){

        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("code",myex.getCode());
        map.put("message",myex.getMessage());
        map.put("method",myex.getMethod());
        map.put("descinfo",myex.getDescinfo());
        myex.printStackTrace();
        LOGGER.error("自定义异常："+myex);
        return map;
    }
}

