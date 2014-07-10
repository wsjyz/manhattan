package com.manhattan.controller;

import com.google.common.collect.ImmutableList;
import com.manhattan.domain.*;
import com.manhattan.service.AppointmentService;
import com.manhattan.service.CourseService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserActionService;
import com.manhattan.util.FastJson;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import com.manhattan.util.PageConvert;
import freemarker.template.SimpleDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Created by dam on 14-6-20.
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    private UserActionService userActionService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private TeacherDetailService teacherDetailService;
    /**
     * 获取精品课程列表
     * @return
     */
    @RequestMapping(value = "/getWorthCourses")
    @ResponseBody
    public OpenPage<Course> getWorthCourses(@FastJson OpenPage<Question> openPage){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.findCourses(pageAble);
        return PageConvert.convert(courses);
    }

    /**
     * 获取详情
     * @return
     */
    @RequestMapping(value = "/getCourseDetail")
    @ResponseBody
    public Course getCourseDetail(@RequestParam String courseId,HttpServletResponse response) {
        System.out.println(courseId);
        Course course = courseService.load(courseId);
        return course;
    }

    /**
     * 发布课程
     * @return
     */
    @RequestMapping(value = "/postCourses")
    @ResponseBody
    public TeacherDetail postCourses(@FastJson TeacherDetail teacherDetail,HttpServletResponse response) {
        TeacherDetail saved=teacherDetailService.postCourse(teacherDetail);
        if (saved!=null) {
            setResponse("发布课程失败", response);
        }
        return saved;
    }

    /**
     * 根据指定条件搜索可预约课程
     * 搜索条件包括分类，地点，预约日期，教师性别，教学方式
     * @return
     */
    @RequestMapping(value = "/getOrderCourses")
    @ResponseBody
    public OpenPage<Course> getOrderCourses(@FastJson OpenPage<Course> openPage,
                                            @FastJson QueryParam queryParam) {
        OpenPage<Course> courses = courseService.findCoursesByQueryParam(openPage, queryParam);
        return courses;
    }

    /**
     * 获取指定学生的课程日历表
     * @return
     */
    @RequestMapping(value = "/getSchedule")
    @ResponseBody
    public List<Date> getSchedule(@RequestParam(required = false) Date startTime,
                                  @RequestParam(required = false) Date endTime,
                                  @RequestParam String userId) {
//        page = courseService.findCoursesByUserId(page, userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE, startTime, endTime);
        List<Date> dates = teacherDetailService.findAppiontByUseIdAndTime(userId,startTime,endTime);
        return dates;
    }


    /**
     * 获取指定学生的预约课程列表
     * @return
     */
    @RequestMapping(value = "/getOrderCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getOrderCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId){
        openPage =courseService.findCoursesByUserId(openPage,userId,MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return openPage;
    }

    /**
     * 获取指定学生的试听课程列表
     * @return
     */
    @RequestMapping(value = "/getListenCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId){
        openPage =courseService.findCoursesByUserId(openPage,userId,MhtConstant.USER_ACTION_LISTEN_COURSE);
        return openPage;
    }

    /**
     * 获取指定学生的收藏课程列表
     * @return
     */
    @RequestMapping(value = "/getCollectCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getCollectCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId){
        openPage =courseService.findCollectByUserId(openPage, userId, MhtConstant.USER_ACTION_COLLECT_COURSE);
        return openPage;
    }

    /**
     * 添加预约\试听课程记录
     * 当resourceType为APPOINTMENT_COURSE为预约课程
     * 当resourceType为LISTEN_COURSE为试听课程
     * @return
     */
    @RequestMapping(value = "/addAppointment")
    @ResponseBody
    public Appointment addAppointment(@FastJson Appointment appointment,HttpServletResponse response) {
        if(appointment != null){
//            appointment.setResourceType(MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
//            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            appointment.setAppointmentTime(new Date());
            Appointment app = appointmentService.save(appointment);
            return app;
        }
        //UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);

        return null;
    }

    /**
     * 收藏试听课程记录
     * @return
     */
    @RequestMapping(value = "/addCollect")
    @ResponseBody
    public UserAction addCollect(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response){
        UserAction ua=userActionService.findUserAction(userId, courseId, MhtConstant.USER_ACTION_COLLECT_COURSE);
        if (ua == null) {
            UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_COLLECT_COURSE);
            if (userAction == null) {
                setResponse("收藏课程失败", response);
            }else{
                return userAction;
            }
        }else{
            setResponse("课程已经被收藏", response);
        }
        return null;
    }

    /**
     * 获取指定教师的预约课程列表
     * @return
     */
    @RequestMapping(value = "/getOrderCoursesByTeacher")
    @ResponseBody
    public OpenPage<Course> getOrderCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId){
        openPage =courseService.getCoursesByTeacher(openPage,userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return openPage;
    }

    /**
     * 获取指定教师的试听课程列表
     * @return
     */
    @RequestMapping(value = "/getListenCoursesByTeacher")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId){
        openPage =courseService.getCoursesByTeacher(openPage,userId, MhtConstant.USER_ACTION_LISTEN_COURSE);
        return openPage;
    }
}
