package com.manhattan.controller;

import com.manhattan.domain.Appointment;
import com.manhattan.domain.Course;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.AppointmentService;
import com.manhattan.service.CourseService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserActionService;
import com.manhattan.util.MhtConstant;
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
import java.sql.Timestamp;
import java.util.Date;


@Controller
@RequestMapping("/mhtCourse")
public class MhtCourseController {

    @Autowired
    private TeacherDetailService teacherDetailService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserActionService userActionService;

    @RequestMapping("/courses")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/course/courses");
        return view;
    }

    @RequestMapping("/courseList")
    public ModelAndView courseList(@ModelAttribute OpenPage page) {
        ModelAndView view = new ModelAndView();
        Pageable pageAble = new PageRequest(page.getPageNo()-1, page.getPageSize());
        Page<Course> courses=courseService.findCourses(pageAble);
        view.addObject("page", PageConvert.convert(courses));
        view.setViewName("views/course/courseList");
        return view;
    }

    @RequestMapping("/courseInfo")
    public ModelAndView courseInfo(@RequestParam String courseId){
        ModelAndView view = new ModelAndView();
        Course course = courseService.load(courseId);
        view.addObject("course", course);
        view.setViewName("views/course/courseInfo");
        return view;

    }

    @RequestMapping("/appointment")
    @ResponseBody
    public String appointment(@ModelAttribute Appointment appointment,HttpSession session) {
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId != null) {
            appointment.setUserId(userId.toString());
        }
        appointment.setAppointmentTime(new Date());
        Appointment am=appointmentService.save(appointment);
        return am.getAppointmentId();
    }

    @RequestMapping("/collect")
    @ResponseBody
    public String collect(@RequestParam String teacherId,HttpSession session) {
        String userId=session.getAttribute(MhtConstant.SEESION_USER_ID).toString();
        UserAction userAction=userActionService.CollectTeacher(userId,teacherId);
        return userAction.getActionId();
    }
}
