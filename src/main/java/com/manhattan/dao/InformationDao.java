package com.manhattan.dao;

import com.manhattan.domain.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Repository("informationDao")
public interface InformationDao extends JpaRepository<Information,String>,JpaSpecificationExecutor<Information> {

    Information findInformationByInformationId(String informationId);
}
