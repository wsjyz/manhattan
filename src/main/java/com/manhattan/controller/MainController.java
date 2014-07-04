package com.manhattan.controller;

import com.manhattan.domain.User;
import com.manhattan.service.UserService;
import com.manhattan.util.FastJson;
import com.manhattan.util.JsonResult;
import com.manhattan.util.MhtConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/","/index"})
	public ModelAndView index(HttpSession session) {
        ModelAndView view = new ModelAndView();
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId!=null) {
            User user = userService.findUserById(userId.toString());
            view.addObject("user", user);
        }
        view.setViewName("index");
        return view;
    }

    @RequestMapping("/users/relogin")
    public ModelAndView relogin(HttpSession session) {
        ModelAndView view = new ModelAndView();
        view.addObject("tologin", "true");
        view.setViewName("index");
        return view;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody JsonResult login(@RequestParam String mobile,@RequestParam String password,HttpSession session) {
        String passwordMd5= DigestUtils.md5Hex(password);
        User user=userService.findUserIdByFilter(mobile, passwordMd5);
        if (user != null) {
            session.setAttribute(MhtConstant.SEESION_USER_ID,user.getUserId());
        }
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
        jsonResult.setData(user);
        return jsonResult;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody JsonResult register(@ModelAttribute User user){
        String passwordMd5= DigestUtils.md5Hex(user.getPassword());
        user.setPassword(passwordMd5);
        user = userService.save(user);
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
        jsonResult.setData(user.getUserId());
        return jsonResult;
    }

    @RequestMapping(value = "/main")
    public ModelAndView main(@RequestParam(required = false) String userId) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            view.addObject("user", userService.load(userId));
        }
        view.setViewName("views/main");
        return view;
    }

}