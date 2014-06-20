package com.manhattan.controller;

import com.google.common.collect.ImmutableList;
import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.domain.Question;
import com.manhattan.domain.UserAction;
import com.manhattan.service.CourseService;
import com.manhattan.service.UserActionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by dam on 14-6-20.
 */
@Controller
@RequestMapping("/homeWork")
public class CourseController extends BaseController {
    @Autowired
    private UserActionService userActionService;
    @Autowired
    private CourseService courseService;
    /**
     * 获取精品课程列表
     * @return
     */
    @RequestMapping(value = "/course/getWorthCourses")
    @ResponseBody
    public OpenPage<Course> getWorthCourses(@FastJson OpenPage<Question> openPage,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.findCourses(pageAble);
        return PageConvert.convert(courses);
    }

    /**
     * 获取详情
     * @return
     */
    @RequestMapping(value = "/course/getCourseDetail")
    @ResponseBody
    public Course getCourseDetail(@RequestParam String courseId) {
        Course course = courseService.load(courseId);
        return course;
    }

    /**
     * 发布课程
     * @return
     */
    @RequestMapping(value = "/course/postCourses")
    @ResponseBody
    public Course postCourses(@FastJson Course course,HttpServletResponse response) {
        Course saved=courseService.postCourse(course);
        if (StringUtils.isEmpty(saved.getCourseId())) {
            setResponse("保存课程失败", response);
        }
        return saved;
    }

    /**
     * 根据指定条件搜索可预约课程
     * 搜索条件包括分类，地点，预约日期，教师性别，教学方式
     * @return
     */
    @RequestMapping(value = "/course/getOrderCourses")
    @ResponseBody
    public OpenPage<Course> getOrderCourses(@FastJson OpenPage<Course> openPage,
                                            @FastJson QueryParam queryParam,
                                            HttpServletResponse response) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses = courseService.findCoursesByFilter(pageAble,queryParam);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的课程日历表
     * @return
     */
    @RequestMapping(value = "/course/getSchedule")
    @ResponseBody
    public List<Date> getSchedule(@RequestParam Date startTime,
                                  @RequestParam Date endTime,
                                  @RequestParam String userId){
        Page<Course> courses =courseService.findCoursesByUserId(null, userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE, startTime, endTime);
        List<Date> date= ImmutableList.of();
        for (Course course : courses.getContent()) {
            date.add(course.getStartTime());
        }
        return date;
    }


    /**
     * 获取指定学生的预约课程列表
     * @return
     */
    @RequestMapping(value = "/course/getOrderCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getOrderCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_LISTEN_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的收藏课程列表
     * @return
     */
    @RequestMapping(value = "/course/getCollectCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getCollectCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_COLLECT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 添加预约课程记录
     * @return
     */
    @RequestMapping(value = "/course/addAppointment")
    @ResponseBody
    public void addAppointment(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response) {
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        if (userAction == null) {
            setResponse("预约课程失败", response);
        }
    }

    /**
     * 添加试听课程记录
     * @return
     */
    @RequestMapping(value = "/course/addListen")
    @ResponseBody
    public void addListen(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response){
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_LISTEN_COURSE);
        if (userAction == null) {
            setResponse("试听课程记录保存失败", response);
        }
    }

    /**
     * 收藏试听课程记录
     * @return
     */
    @RequestMapping(value = "/course/addCollect")
    @ResponseBody
    public void addCollect(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response){
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_COLLECT_COURSE);
        if (userAction == null) {
            setResponse("收藏课程失败", response);
        }
    }

    /**
     * 获取指定教师的预约课程列表
     * @return
     */
    @RequestMapping(value = "/course/getOrderCoursesByTeacher")
    @ResponseBody
    public OpenPage<Course> getOrderCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.getCoursesByTeacher(pageAble,userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定教师的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByTeacher")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.getCoursesByTeacher(pageAble,userId, MhtConstant.USER_ACTION_LISTEN_COURSE);
        return PageConvert.convert(courses);
    }
}
