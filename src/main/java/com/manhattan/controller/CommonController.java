package com.manhattan.controller;

import com.manhattan.domain.User;
import com.manhattan.service.SmsSendService;
import com.manhattan.service.UserService;
import com.manhattan.util.JsonResult;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2014/6/19 0019.
 */
@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private UserService userService;

    @RequestMapping("/authCode")
    public @ResponseBody JsonResult getAuthCode(@RequestParam String mobile) {
        JsonResult jsonResult = new JsonResult();
        User user = userService.findUserByMobile(mobile);
        if (user != null) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("此手机号已经注册!");
            return jsonResult;
        }
        String authCode = RandomStringUtils.randomNumeric(6);
        Boolean res = smsSendService.sendSms(mobile, authCode);
        jsonResult.setSuccess(res);
        jsonResult.setData(authCode);
        return jsonResult;
    }
}
