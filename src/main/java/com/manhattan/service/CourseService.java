package com.manhattan.service;

import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.util.OpenPage;
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

    OpenPage<Course> findCoursesByQueryParam(OpenPage<Course> page, QueryParam qp);

    OpenPage<Course> findCoursesByUserId(OpenPage<Course> page,String userId,String action);

    OpenPage<Course> getCoursesByTeacher(OpenPage<Course> page,String userId,String action);

    OpenPage<Course> findCoursesByUserId(OpenPage<Course> page, String userId, String actionType, Date startTime, Date endTime);

    OpenPage<Course> findCollectByUserId(OpenPage<Course> openPage, String userId, String userActionCollectCourse);

    List<Course> findByPostTeacher(String teacherId);
}
