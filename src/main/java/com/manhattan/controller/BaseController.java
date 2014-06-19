package com.manhattan.controller;

import javax.servlet.http.HttpServletResponse;

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
}
