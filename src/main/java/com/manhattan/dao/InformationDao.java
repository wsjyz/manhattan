package com.manhattan.dao;

import com.manhattan.domain.Information;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Repository("informationDao")
public interface InformationDao extends PagingAndSortingRepository<Information, String> {
}
