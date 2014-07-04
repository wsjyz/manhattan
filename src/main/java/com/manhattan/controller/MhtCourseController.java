package com.manhattan.controller;

import com.manhattan.domain.Appointment;
import com.manhattan.domain.Course;
import com.manhattan.domain.User;
import com.manhattan.service.AppointmentService;
import com.manhattan.service.CourseService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/mhtCourse")
public class MhtCourseController {

    @Autowired
    private TeacherDetailService teacherDetailService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping("/courses")
    public ModelAndView index(HttpSession session) {
        ModelAndView view = new ModelAndView();
        Object userId=session.getAttribute(MhtConstant.SEESION_USER_ID);
        if (userId!=null) {
            view.addObject("userId", userId);
        }
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
    public String appointment(@RequestParam String courseId, @RequestParam String userId) {
        return "";
    }

}
