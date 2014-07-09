package com.manhattan.controller;

import com.manhattan.domain.*;
import com.manhattan.service.CourseService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserService;
import com.manhattan.util.ConfigurationFile;
import com.manhattan.util.JsonResult;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
    @Autowired
    private ConfigurationFile confBean;

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

    @RequestMapping(value = "/list/teacher")
    public ModelAndView teacherList(@ModelAttribute OpenPage page, @RequestParam(required = false) String mobile, @RequestParam(required = false) String userName) {
        ModelAndView view = new ModelAndView();
        page = teacherDetailService.findPostCourseTeachers(page,mobile,userName);
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
    public @ResponseBody String postCourse(MultipartHttpServletRequest request) {
        String result = "添加课程成功";
        Course course = new Course();
        course.setCourseTitle(request.getParameter("courseTitle"));
        course.setCourseSubtitle(request.getParameter("courseSubtitle"));
        String expenseStr = request.getParameter("expense");
        if(StringUtils.isNotBlank(expenseStr)){
            BigDecimal expense = new BigDecimal(expenseStr);
            course.setExpense(expense);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        if(StringUtils.isNotBlank(startTimeStr)){
            Date startTime = null;
            try {
                startTime = sdf.parse(startTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            course.setStartTime(startTime);
        }
        if(StringUtils.isNotBlank(endTimeStr)){
            Date endTime = null;
            try {
                endTime = sdf.parse(endTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            course.setEndTime(endTime);
        }
        String periodStr = request.getParameter("period");
        if(StringUtils.isNotBlank(periodStr)){
            BigDecimal expense = new BigDecimal(periodStr);
            course.setPeriod(expense);
        }
        course.setPlace(request.getParameter("place"));
        course.setTeachers(request.getParameter("teachers"));
        course.setCourseIntro(request.getParameter("courseIntro"));

        if(course != null){
            String path = confBean.getFileUploadPath();
            MultipartFile file = request.getFile("coursePicFile");
            String originalFilename = file.getOriginalFilename();
            String fileSuffix = originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
            path = path + new SimpleDateFormat("yyyyMMdd").format(new Date())+"/";
            String picName = UUID.randomUUID().toString().replace("-","")+fileSuffix;
            File targetFile = new File(path, picName);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            String picUrl = path + picName;
            try {
                file.transferTo(targetFile);
                course.setCoursePic(picUrl);
            } catch (IOException e) {
                result = e.getMessage();
                e.printStackTrace();
            }
            course = courseService.postCourse(course);
        }else{
            result = "创建数据时出错";
        }


        return result;
    }
    @RequestMapping(value = "/postPlace",method = RequestMethod.POST)
    public @ResponseBody String postPlace(MultipartHttpServletRequest request){
        String result = "添加课程成功";
        return result;
    }

}
