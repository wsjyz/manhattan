package com.manhattan.controller;

import com.manhattan.domain.User;
import com.manhattan.service.UserService;
import com.manhattan.util.FastJson;
import com.manhattan.util.JsonResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
	public String index(@RequestParam(required = false) String userId) {
        if (StringUtils.isNotEmpty(userId)) {
            User user = userService.findUserById(userId);
        }
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody JsonResult login(@RequestParam String mobile,@RequestParam String password) {
        String passwordMd5= DigestUtils.md5Hex(password);
        User user=userService.findUserIdByFilter(mobile, passwordMd5);
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
        jsonResult.setData(user);
        return jsonResult;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes="application/x-www-form-urlencoded")
    public @ResponseBody JsonResult register(@RequestBody User user){
        String passwordMd5= DigestUtils.md5Hex(user.getPassword());
        user.setPassword(passwordMd5);
        user = userService.save(user);
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(user!=null);
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