package com.manhattan.service.impl;

import com.google.common.collect.ImmutableList;
import com.manhattan.dao.InformationDao;
import com.manhattan.domain.Information;
import com.manhattan.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Service("informationService")
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;

    @Override
    public Page<Information> getInformations(Pageable pageAble) {
        Page<Information> page = informationDao.findAll(pageAble);
        return page;
    }
}
