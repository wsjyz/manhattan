package com.manhattan.service;

import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
public interface CourseService {
    Page<Course> findCourses(Pageable pageAble);

    Course load(String courseId);

    Course postCourse(Course course);

    Page<Course> findCoursesByFilter(Pageable pageAble,QueryParam qp);

    Page<Course> findCoursesByUserId(Pageable pageAble,String userId,String action);

    Page<Course> getCoursesByTeacher(Pageable pageAble,String userId,String action);

    Page<Course> findCoursesByUserId(Pageable pageable, String userId, String actionType, Date startTime, Date endTime);

}
