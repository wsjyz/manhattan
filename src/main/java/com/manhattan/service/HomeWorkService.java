package com.manhattan.service;

import com.manhattan.domain.HomeWork;

import java.util.List;

/**
 * Created by lk.zh on 2014/6/12.
 */
public interface HomeWorkService {
    List<HomeWork> getHomeworksByUser(String userId);

    List<HomeWork> getHomeworksByTeacher(String userId);

    HomeWork post(HomeWork homeWork);
}
