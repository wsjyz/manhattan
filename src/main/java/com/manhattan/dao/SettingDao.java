package com.manhattan.dao;

import com.manhattan.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lk.zh on 2014/7/15 0015.
 */
public interface SettingDao extends JpaRepository<Setting,String>,JpaSpecificationExecutor<Setting> {
}
