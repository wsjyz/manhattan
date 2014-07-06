package com.manhattan.dao;

import com.manhattan.domain.TeacherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lk.zh on 2014/7/6 0006.
 */
public interface PlaceDao extends JpaRepository<TeacherDetail,String>,JpaSpecificationExecutor<TeacherDetail> {

}
