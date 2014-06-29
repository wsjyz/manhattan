package com.manhattan.controller;

import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.Wallet;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserService;
import com.manhattan.service.WalletService;
import com.manhattan.util.FastJson;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/data/list/{list}")
    public ModelAndView getRecords(@RequestParam String userId,@PathVariable String list,@ModelAttribute OpenPage page1) {
        ModelAndView view = new ModelAndView();
        if (StringUtils.isNotEmpty(userId)) {
            Pageable pageAble = new PageRequest(page1.getPageNo()-1, page1.getPageSize());
            if (list.equals("walletRecords")) {
                Page<Wallet> wallets = walletService.getRecordList(pageAble,userId);
                OpenPage<Wallet> page = PageConvert.convert(wallets);
                view.addObject("page", page);
            }else if (list.equals("listenList")) {
                OpenPage<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(page1, userId, MhtConstant.USER_ACTION_LISTEN_TEACHER);
                view.addObject("page", teachers);
            }else if (list.equals("myAppointmentList")) {
                OpenPage<TeacherDetail> teachers = teacherDetailService.findTeachersByUserId(page1, userId, MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
                view.addObject("page", teachers);
            }
        }
        view.setViewName("views/user/"+list);
        return view;
    }
}
