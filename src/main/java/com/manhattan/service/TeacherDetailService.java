package com.manhattan.service;

import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.util.OpenPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface TeacherDetailService {
    TeacherDetail findTeacherDetail(String userId);

    OpenPage<TeacherDetail> findTeachersByUserId(OpenPage<TeacherDetail> page, String userId, String userAction);

    OpenPage<TeacherDetail> findTeacherByPage(OpenPage<TeacherDetail> page, String searchKey);

    TeacherDetail postCourse(TeacherDetail teacherDetail);

    OpenPage<TeacherDetail> findTeacherByPage(OpenPage<TeacherDetail> page, QueryParam queryParam);

    void updateUserStatus(String userId, String pass);

    OpenPage findPostCourseTeachers(OpenPage page, String mobile, String userName);

    List<Date> findAppiontByUseIdAndTime(String userId, Date startTime, Date endTime);
}
