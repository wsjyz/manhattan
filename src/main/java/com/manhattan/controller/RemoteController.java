package com.manhattan.controller;

import com.manhattan.domain.Information;
import com.manhattan.domain.User;
import com.manhattan.service.InformationService;
import com.manhattan.service.user.UserService;
import com.manhattan.util.JsonResult;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by lk.zh on 2014/5/20.
 */

@Controller
public class RemoteController {

    @Autowired
    private UserService userService;
    @Autowired
    private InformationService informationService;

    @RequestMapping(value = "/user/login")
    public @ResponseBody User remotelogin(@RequestParam("userName") String userName,
                                          @RequestParam("password" )String password) {
        String userId=userService.findUserByFilter(userName,password);
        if (StringUtils.isNotBlank(userId)) {
           return userService.load(userId);
        }
        return null;
    }
    @RequestMapping(value = "/user/register")
    public @ResponseBody Boolean register(@RequestParam("tel" )String tel,
                                          @RequestParam("password" )String password,
                                          @RequestParam("authCode" )String authCode,
                                          @RequestParam("type" )String type) {
        User user=new User();
        user.setMobile(tel);
        user.setPassword(password);
        user.setAuthCode(authCode);
        user.setType(type);
        User user1=userService.save(user);
        return StringUtils.isNotEmpty(user1.getUserId());
    }
    @RequestMapping(value = "/user/resetPassword")
    public @ResponseBody User resetPassword(@RequestParam("tel") String tel,
                                            @RequestParam("newPassword" )String newPassword,
                                            @RequestParam("authCode" )String authCode) {
        User user=userService.findUserByUserName(tel);
        user.setPassword(newPassword);
        user.setAuthCode(authCode);
        User user1=userService.save(user);
        return user1;
    }
    @RequestMapping(value = "/info/loadLatestInfomation")
    public @ResponseBody
    OpenPage<Information> loadLatestInfomation(@ModelAttribute("openPage")OpenPage<Information> openPage,
                                               @RequestParam("tel") String id,
                                            @RequestParam("title" )String title,
                                            @RequestParam("content" )String content,
                                            @RequestParam("createTime" )Date createTime) {
        openPage=informationService.findInfoByPage(openPage,information);
        return openPage;
    }
}
