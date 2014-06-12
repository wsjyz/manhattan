package com.manhattan.dao;

import com.manhattan.domain.HomeworkSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lk.zh on 2014/6/12.
 */
public interface HomeworkSubmitDao extends JpaRepository<HomeworkSubmit,String>,JpaSpecificationExecutor<HomeworkSubmit> {
}
