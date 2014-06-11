package com.manhattan.service;

import com.manhattan.domain.Course;

import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
public interface CourseService {
    List<Course> findCourses();

    Course load(String courseId);

    Course postCourse(Course course);

    List<Course> findCoursesByFilter(Course course);

    List<Course> findCoursesByUserId(String userId,String action);
}
