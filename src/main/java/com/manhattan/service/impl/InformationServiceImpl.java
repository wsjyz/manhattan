package com.manhattan.service.impl;

import com.manhattan.dao.InformationDao;
import com.manhattan.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Service("informationService")
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;
}
