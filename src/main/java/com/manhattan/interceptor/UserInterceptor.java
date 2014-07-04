package com.manhattan.interceptor;

import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            response.sendRedirect(contextPath+"/users/relogin");
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
}
