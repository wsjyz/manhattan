package com.manhattan.dao;

import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.util.OpenPage;

import java.util.Date;

/**
 * Created by Administrator on 2014/6/21 0021.
 */
public interface ICourseDao {

    OpenPage<Course> findCourseByQueryParam(QueryParam qp, OpenPage<Course> page);

    OpenPage<Course> findCourseByUserId(OpenPage<Course> page,String userId,String actionType,Date startTime,Date endTime);

    OpenPage<Course> finCoursesByTeacher(OpenPage<Course> page, String userId, String action);
}
