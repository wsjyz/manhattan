package com.manhattan.dao;

import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.util.OpenPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
public interface CourseDao extends JpaRepository<Course,String>,JpaSpecificationExecutor<Course> {

}
