package com.manhattan.dao;

import com.manhattan.domain.HomeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lk.zh on 2014/6/12.
 */
public interface HomeWorkDao extends JpaRepository<HomeWork,String>,JpaSpecificationExecutor<HomeWork> {
    List<HomeWork> findByUserId(String userId);

    List<HomeWork> findByTeacherId(String userId);
}
