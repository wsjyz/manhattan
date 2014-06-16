package com.manhattan.service;

import com.manhattan.domain.TeacherDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface TeacherDetailService {
    TeacherDetail findTeacherDetail(String userId);

    Page<TeacherDetail> findTeachersByUserId(Pageable pageAble, String userId, String userAction);
}
