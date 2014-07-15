package com.manhattan.controller;

import com.manhattan.domain.HomeWork;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.Wallet;
import com.manhattan.service.HomeWorkService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserService;
import com.manhattan.service.WalletService;
import com.manhattan.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk.zh on 2014/6/24 0024.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    WalletService walletService;
    @Autowired
    TeacherDetailService teacherDetailService;
    @Autowired
    HomeWorkService homeWorkService;

    @RequestMapping(value = "/data")
    public ModelAndView getUserData(@RequestParam String userId,@RequestParam(required = false) String item) {
        ModelAndView view = new ModelAndView();
        view.addObject("user", userService.load(userId));
        if (StringUtils.isNotEmpty(item)) {
            view.addObject("item", item);
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

    @RequestMapping(value = "/data/list/{list}")
    public ModelAndView getRecords(@RequestParam String userId,@PathVariable String list,@ModelAttribute OpenPage page) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
            if (list.equals("walletRecords")) {
                Page<Wallet> wallets = walletService.getRecordList(pageAble,userId);
                view.addObject("page", PageConvert.convert(wallets));
            }else if (list.equals("listenList")) {
                OpenPage<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(page, userId, MhtConstant.USER_ACTION_LISTEN_TEACHER);
                view.addObject("page", teachers);
            }else if (list.equals("myAppointmentList")) {
                OpenPage<TeacherDetail> teachers = teacherDetailService.findTeachersByUserId(page, userId, MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
                view.addObject("page", teachers);
            }else if (list.equals("collectList")) {
                OpenPage<TeacherDetail> teachers = teacherDetailService.findTeachersByUserId(page, userId, MhtConstant.USER_ACTION_COLLECT_TEACHER);
                view.addObject("page", teachers);
            }else if (list.equals("homeworkList")) {
                Page<HomeWork> homeWorks = homeWorkService.getHomeworksByUser(pageAble, userId);
                view.addObject("page", PageConvert.convert(homeWorks));
            }
        }
        view.setViewName("views/user/"+list);
        return view;
    }

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public @ResponseBody String saveUser(@ModelAttribute User user) {

//        if(StringUtils.isNotBlank(vipExpiredTime)){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                user.setVipExpiredTime(sdf.parse(vipExpiredTime));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
        User user1=userService.load(user.getUserId());
        BeanUtils.copyProperties(user, user1, "userId");
        user1=userService.save(user1);
        userService.save(user1);
        return "success";
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public @ResponseBody String updateUser(@ModelAttribute User user) {
        int res=userService.updateUser(user);
        return "success";
    }

    @RequestMapping(value = "/changePass")
    public @ResponseBody JsonResult changePass(@RequestParam String userId,@RequestParam String originalPass,@RequestParam String changePass) {
        JsonResult jsonResult=new JsonResult();
        User user = userService.load(userId);
        String originalPassMd5= DigestUtils.md5Hex(originalPass);
        if (!user.getPassword().equals(originalPassMd5)) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("原密码输入错误!");
            return jsonResult;
        }
        String passwordMd5= DigestUtils.md5Hex(changePass);
        int res=userService.changePassword(userId, passwordMd5);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

}
