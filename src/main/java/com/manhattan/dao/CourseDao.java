package com.manhattan.dao;

import com.manhattan.domain.Course;
import com.manhattan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
public interface CourseDao extends JpaRepository<Course, String> {

}
