package com.manhattan.controller;

import com.manhattan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lk.zh on 2014/6/24 0024.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/data")
    public ModelAndView getUserData(@RequestParam String userId) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            view.addObject("user", userService.load(userId));
        }
        view.setViewName("views/user/data");
        return view;
    }

    @RequestMapping(value = "/data/{page}")
    public ModelAndView baseInfo(@RequestParam String userId,@PathVariable String page) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            view.addObject("user", userService.load(userId));
        }
        view.setViewName("views/user/"+page);
        return view;
    }
}
