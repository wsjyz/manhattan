package com.manhattan.service.impl;

import com.manhattan.dao.SettingDao;
import com.manhattan.domain.Setting;
import com.manhattan.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by lk.zh on 2014/7/15 0015.
 */
@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingDao settingDao;

    @Override
    public Setting save(Setting setting) {

        return settingDao.saveAndFlush(setting);
    }

    @Override
    public Setting getSetting() {
        List<Setting> settings=settingDao.findAll();
        if (CollectionUtils.isEmpty(settings)) {
            return null;
        }
        return settings.get(0);
    }
}
