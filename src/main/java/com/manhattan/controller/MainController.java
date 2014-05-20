package com.manhattan.controller;

import com.manhattan.domain.User;
import com.manhattan.service.user.UserService;
import com.manhattan.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
	public String index(ModelMap model) {
		model.addAttribute("message", "Hello world!");
        userService.findUserById("a");
		return "index";
	}

    @RequestMapping(value = "/login")
    public @ResponseBody JsonResult login(@ModelAttribute("user")User user) {
        String res=userService.findUserByFilter(user.getUserName(),user.getPassword());
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(!StringUtils.isEmpty(res));
        return jsonResult;
    }
}