package com.manhattan.controller;

import com.manhattan.service.SmsSendService;
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

    @RequestMapping("/authCode")
    public @ResponseBody String getAuthCode(@RequestParam String mobile) {
        String authCode = StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(6));
        Boolean res = smsSendService.sendSms(mobile, "");
        return authCode;
    }
}
