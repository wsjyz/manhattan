package com.manhattan.service;

import com.manhattan.domain.Setting;

/**
 * Created by lk.zh on 2014/7/15 0015.
 */
public interface SettingService {
    Setting save(Setting setting);

    Setting getSetting();
}
