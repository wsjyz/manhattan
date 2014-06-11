package com.manhattan.service.impl;

import com.manhattan.dao.CourseDao;
import com.manhattan.dao.InformationDao;
import com.manhattan.domain.Course;
import com.manhattan.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findCourses() {
        return courseDao.findAll();
    }

    @Override
    public Course load(String courseId) {
        return courseDao.getOne(courseId);
    }

    @Override
    public Course postCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public List<Course> findCoursesByFilter(Course course) {
        return null;
    }

    @Override
    public List<Course> findCoursesByUserId(String userId,String action) {
        return null;
    }
}
