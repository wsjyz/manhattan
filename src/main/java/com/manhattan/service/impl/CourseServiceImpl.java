package com.manhattan.service.impl;

import com.manhattan.dao.CourseDao;
import com.manhattan.dao.ICourseDao;
import com.manhattan.dao.TeacherDetailDao;
import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.service.CourseService;
import com.manhattan.util.OpenPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ICourseDao iCourseDao;
    @Autowired
    private TeacherDetailDao teacherDetailDao;

    @Override
    public Page<Course> findCourses(Pageable pageAble) {
        return courseDao.findAll(pageAble);
    }

    @Override
    public Course load(String courseId) {
        return iCourseDao.findCourseById(courseId);
    }

    @Override
    public Course postCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public OpenPage<Course> findCoursesByQueryParam(OpenPage<Course> page, QueryParam qp) {
        OpenPage<Course> courses=iCourseDao.findCourseByQueryParam(qp, page);
        return courses;
    }

    @Override
    public OpenPage<Course> findCoursesByUserId(OpenPage<Course> page,String userId,String action) {
        return findCoursesByUserId(page,userId,action,null,null);
    }

    @Override
    public OpenPage<Course> getCoursesByTeacher(OpenPage<Course> page,String userId,String action) {
        return iCourseDao.finCoursesByTeacher(page,userId,action);
    }

    @Override
    public OpenPage<Course> findCoursesByUserId(OpenPage<Course> page, String userId, String actionType, Date startTime, Date endTime) {
        page=iCourseDao.findCourseByUserId(page, userId, actionType, startTime, endTime);
        return page;
    }

    @Override
    public OpenPage<Course> findCollectByUserId(OpenPage<Course> openPage, String userId, String userActionCollectCourse) {
        return iCourseDao.findCollectByUserId(openPage, userId, userActionCollectCourse);
    }

    @Override
    public List<Course> findByPostTeacher(String teacherId){
        return courseDao.findByPostTeacher(teacherId);
    }

}
