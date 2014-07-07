package com.manhattan.controller;

import com.manhattan.domain.*;
import com.manhattan.service.CourseService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserService;
import com.manhattan.util.JsonResult;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by lk.zh on 2014/7/8 0008.
 */
@Controller
@RequestMapping("/users/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherDetailService teacherDetailService;
    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/accounts");
        return view;
    }

    @RequestMapping(value = "/approve")
    public ModelAndView approve() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/approve");
        return view;
    }
    @RequestMapping(value = "/post")
    public ModelAndView post() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/post");
        return view;
    }
    @RequestMapping(value = "/contentlist")
    public ModelAndView datalist() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/contentlist");
        return view;
    }
    @RequestMapping(value = "/exit")
    public ModelAndView exit(HttpSession session) {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/admin/login");
        return view;
    }

    @RequestMapping(value = "/listUser")
    public ModelAndView listUser(@ModelAttribute OpenPage page,@ModelAttribute User user) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        Page resPage=userService.findUserByPage(pageAble, user);
        view.addObject("page", PageConvert.convert(resPage));
        view.setViewName("views/admin/listUser");
        return view;
    }

    @RequestMapping(value = "/teacherList")
    public ModelAndView teacherList() {
        ModelAndView view = new ModelAndView();
        OpenPage<TeacherDetail> page = new OpenPage<TeacherDetail>();
        page.setPageNo(1);
        page.setPageSize(10);
        page = teacherDetailService.findTeacherByPage(page,new QueryParam());
        view.addObject("teachers", page.getRows());
        view.setViewName("views/admin/teacherList");
        return view;
    }

    @RequestMapping(value = "/{opt}")
    public @ResponseBody JsonResult adminopt(@PathVariable String opt,@RequestParam String userId) {
        JsonResult jsonResult = new JsonResult();
        if (opt.equals("delete")) {
            userService.deleteUser(userId);
        }else if (opt.equals("disable")) {
            userService.updateUserStatus(userId, "DISABLE");
        }else if (opt.equals("enable")) {
            userService.updateUserStatus(userId, "ENABLE");
        }else if (opt.equals("pass")) {
            teacherDetailService.updateUserStatus(userId, "PASS");
        }
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @RequestMapping(value = "/view")
    public ModelAndView view(@RequestParam String userId) {
        ModelAndView view = new ModelAndView();
        User user = userService.load(userId);
        view.addObject("user", user);
        view.setViewName("views/admin/viewUser");
        return view;
    }

    @RequestMapping(value = "/viewTeacher")
    public ModelAndView viewTeacher(@RequestParam String userId) {
        ModelAndView view = new ModelAndView();
        TeacherDetail teacherDetail = teacherDetailService.findTeacherDetail(userId);
        view.addObject("teacher", teacherDetail);
        view.setViewName("views/admin/viewTeacher");
        return view;
    }

    @RequestMapping(value = "/postCourse",method = RequestMethod.POST)
    public @ResponseBody Boolean postCourse(@ModelAttribute Course course) {
        course=courseService.postCourse(course);
        return course!=null;
    }

}
