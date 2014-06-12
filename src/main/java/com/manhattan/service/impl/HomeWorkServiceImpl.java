package com.manhattan.service.impl;

import com.manhattan.dao.HomeWorkDao;
import com.manhattan.domain.HomeWork;
import com.manhattan.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<HomeWork> getHomeworksByUser(String userId) {
        return homeWorkDao.findByUserId(userId);
    }

    @Override
    public List<HomeWork> getHomeworksByTeacher(String userId) {
        return homeWorkDao.findByTeacherId(userId);
    }

    @Override
    public HomeWork post(HomeWork homeWork) {
        return homeWorkDao.saveAndFlush(homeWork);
    }
}
