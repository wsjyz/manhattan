package com.manhattan.dao;

import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.util.OpenPage;

import java.util.Date;
import java.util.List;

/**
 * Created by lk.zh on 2014/6/21 0021.
 */
public interface ITeacherDetailDao {
    OpenPage<TeacherDetail> findTeachers(OpenPage<TeacherDetail> page, String searchKey);

    OpenPage<TeacherDetail> findTeachersByUserId(OpenPage<TeacherDetail> page, String userId, String resourceType);

    TeacherDetail findByUserId(String userId);

    OpenPage<TeacherDetail> findTeachersByUserIdAndAction(OpenPage<TeacherDetail> page, String userId, String userAction);

    OpenPage<TeacherDetail> findTeachers(OpenPage<TeacherDetail> page, QueryParam queryParam);

    OpenPage findPostCourseTeachers(OpenPage page, String mobile, String userName);

    List<Date> findAppiontByUseIdAndTime(String userId, Date startTime, Date endTime);
}
