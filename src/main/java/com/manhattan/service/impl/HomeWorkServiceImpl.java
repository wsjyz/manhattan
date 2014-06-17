package com.manhattan.service.impl;

import com.manhattan.dao.HomeWorkDao;
import com.manhattan.domain.HomeWork;
import com.manhattan.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lk.zh on 2014/6/12.
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    private HomeWorkDao homeWorkDao;

    @Override
    public Page<HomeWork> getHomeworksByUser(Pageable pageAble,String userId) {
        return homeWorkDao.findByUserId(userId,pageAble);
    }

    @Override
    public Page<HomeWork> getHomeworksByTeacher(Pageable pageAble,String userId) {
        return homeWorkDao.findByTeacherId(userId,pageAble);
    }

    @Override
    public HomeWork post(HomeWork homeWork) {
        return homeWorkDao.saveAndFlush(homeWork);
    }
}
