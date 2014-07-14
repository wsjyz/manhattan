package com.manhattan.controller;

import com.manhattan.domain.*;
import com.manhattan.service.*;
import com.manhattan.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
import java.util.List;
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
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SettingService settingService;

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
    @RequestMapping(value = "/setting")
    public ModelAndView setting() {
        ModelAndView view = new ModelAndView();
        Setting setting=settingService.getSetting();
        if (setting != null) {
            view.addObject("setting", setting);
        }
        view.setViewName("views/admin/setting");
        return view;
    }
    @RequestMapping(value = "/exit")
    public ModelAndView exit(HttpSession session) {
        ModelAndView view = new ModelAndView();
        session.removeAttribute(MhtConstant.SEESION_USER_ID);
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
        view.addObject("teachers", page);
        view.setViewName("views/admin/teacherList");
        return view;
    }

    @RequestMapping(value = "/list/{opt}")
    public ModelAndView contentListDetail(@ModelAttribute OpenPage page,
                                          @PathVariable String opt,
                                          @RequestParam(required = false) String mobile,
                                          @RequestParam(required = false) String userName) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        if (opt.equals("teacher")) {
            page = teacherDetailService.findPostCourseTeachers(page,mobile,userName);
            view.addObject("page", page);
            view.setViewName("views/admin/courseList");
            return view;
        } else if (opt.equals("listen")) {
            Page datas = appointmentService.findByPage(pageAble, mobile, userName,MhtConstant.USER_ACTION_LISTEN_TEACHER);
            page = PageConvert.convert(datas);
        } else if (opt.equals("appoint")) {
            Page datas = appointmentService.findByPage(pageAble, mobile, userName,MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
            page = PageConvert.convert(datas);
        } else if (opt.equals("homework")) {
            Page datas = homeWorkService.findByPage(pageAble, userName);
            page = PageConvert.convert(datas);
        } else if (opt.equals("question")) {
            Page datas = questionService.findByPage(pageAble, userName);
            page = PageConvert.convert(datas);
        }
        view.addObject("page", page);
        view.setViewName("views/admin/"+opt+"List");
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

    @RequestMapping(value="view/postCourse")
    public ModelAndView viewPostCourse(String teacherId) {
        ModelAndView view = new ModelAndView();
//        List<Course> courses=courseService.findByPostTeacher(teacherId);
        TeacherDetail teacherDetail = teacherDetailService.findTeacherDetail(teacherId);
        if (teacherDetail!=null) {
            view.addObject("teacherDetail", teacherDetail);
        }
        view.setViewName("views/admin/postCourse");
        return view;
    }

    @RequestMapping(value = "view/appoint")
    public ModelAndView viewAppoint(@RequestParam String appointmentId) {
        ModelAndView view = new ModelAndView();
        Appointment appointment = appointmentService.loadById(appointmentId);
        view.addObject("appointment", appointment);
        view.setViewName("views/admin/appointment");
        return view;
    }

    @RequestMapping(value = "view/question")
    public ModelAndView viewQuestion(@RequestParam String questionId) {
        ModelAndView view = new ModelAndView();
        Question question = questionService.loadById(questionId);
        view.addObject("question", question);
        view.setViewName("views/admin/question");
        return view;
    }

    @RequestMapping("/postCourse/{opt}")
    public @ResponseBody boolean postCourse(@RequestParam String teacherId,@PathVariable String opt) {
        if (opt.equals("disable")||opt.equals("enable")) {
            userService.updateUserStatus(teacherId, opt.equals("disable")?MhtConstant.USER_STATUS_DISABLE:MhtConstant.USER_STATUS_ENABLE);
        }else if (opt.equals("delete")) {
            userService.deleteUser(teacherId);
        }
        return true;
    }

    @RequestMapping("/appoint/delete")
    public @ResponseBody boolean deleteAppoint(@RequestParam String appointmentId) {
        appointmentService.deleteById(appointmentId);
        return true;
    }

    @RequestMapping("/question/delete")
    public @ResponseBody boolean deleteQuestion(@RequestParam String questionId) {
        questionService.deleteById(questionId);
        return true;
    }

    @RequestMapping(value = "/postPlace",method = RequestMethod.POST)
    public @ResponseBody String postPlace(MultipartHttpServletRequest request){
        String result = "添加课程成功";
        return result;
    }

    @RequestMapping(value = "/saveSetting",method = RequestMethod.POST)
    public @ResponseBody String saveSetting(@ModelAttribute Setting setting){
        Setting setting1=settingService.save(setting);
        return setting.getSettingId();
    }
}
