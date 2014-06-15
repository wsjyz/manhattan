package com.manhattan.service;

import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;

import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
public interface CourseService {
    List<Course> findCourses();

    Course load(String courseId);

    Course postCourse(Course course);

    List<Course> findCoursesByFilter(QueryParam qp);

    List<Course> findCoursesByUserId(String userId,String action);

    List<Course> getCoursesByTeacher(String userId,String action);
}
