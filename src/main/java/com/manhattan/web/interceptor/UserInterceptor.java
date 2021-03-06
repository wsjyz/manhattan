package com.manhattan.web.interceptor;

import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lk.zh on 2014/6/24.
 */
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath=request.getContextPath();
        String url=request.getServletPath().toString();
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (StringUtils.isEmpty(userId)) {
            if (url.contains("admin")&&!url.contains("admin/login")) {
                if (!isAjaxRequest(request)) {
                    response.sendRedirect(contextPath + "/admin/login");
                }else{
                    response.reset();
                    try {
                        PrintWriter writer = response.getWriter();
                        writer.write("RELOGIN");
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }else{
                response.sendRedirect(contextPath + "/relogin");
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        return isAjax;
    }
}
