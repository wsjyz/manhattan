package com.manhattan.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dam on 14-6-19.
 */
public class BaseController {
    /**
     * 设置错误消息
     * @param msg
     * @param response
     * 获取方式：new String(conn.getHeaderField("ErrorMsg").getBytes("ISO-8859-1"), "UTF-8")
     */
    public void setResponse(String msg,HttpServletResponse response) {
        response.addHeader("ErrorMsg" ,msg);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
