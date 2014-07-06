package com.manhattan.service.impl;

import com.manhattan.dao.PlaceDao;
import com.manhattan.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by lk.zh on 2014/7/6 0006.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Override
    public Page listByPage(Pageable pageAble) {
        return placeDao.findAll(pageAble);
    }
}
