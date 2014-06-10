package com.manhattan.controller;

import com.manhattan.domain.User;
import com.manhattan.service.UserService;
import com.manhattan.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
	public String index(ModelMap model) {
		model.addAttribute("message", "Hello world!");
        User user=userService.findUserById("a");
		return "index";
	}

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody JsonResult login(@RequestBody User user) {
        String res=userService.findUserIdByFilter(user.getMobile(),user.getPassword());
        JsonResult jsonResult=new JsonResult();
        jsonResult.setSuccess(!StringUtils.isEmpty(res));
        return jsonResult;
    }
}