package com.manhattan.dao;

import com.manhattan.domain.TeacherDetail;
import com.manhattan.util.OpenPage;

/**
 * Created by lk.zh on 2014/6/21 0021.
 */
public interface ITeacherDetailDao {
    OpenPage<TeacherDetail> findTeachers(OpenPage<TeacherDetail> page, String searchKey);

    OpenPage<TeacherDetail> findTeachersByUserId(OpenPage<TeacherDetail> page, String userId, String userAction);

    TeacherDetail findByUserId(String userId);
}
