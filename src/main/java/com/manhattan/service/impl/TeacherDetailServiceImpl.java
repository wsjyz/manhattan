package com.manhattan.service.impl;

import com.manhattan.dao.TeacherDetailDao;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.service.TeacherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
@Service
public class TeacherDetailServiceImpl implements TeacherDetailService {
    @Autowired
    private TeacherDetailDao teacherDetailDao;

    @Override
    public TeacherDetail findTeacherDetail(String userId) {
        return teacherDetailDao.findByUserId(userId);
    }
}
