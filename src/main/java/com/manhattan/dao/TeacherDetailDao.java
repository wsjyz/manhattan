package com.manhattan.dao;

import com.manhattan.domain.TeacherDetail;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface TeacherDetailDao extends CrudRepository<TeacherDetail,String> {

    TeacherDetail findByUserId(String userId);
}
