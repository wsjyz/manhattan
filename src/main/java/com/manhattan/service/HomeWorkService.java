package com.manhattan.service;

import com.manhattan.domain.HomeWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lk.zh on 2014/6/12.
 */
public interface HomeWorkService {
    Page<HomeWork> getHomeworksByUser(Pageable pageAble,String userId);

    Page<HomeWork> getHomeworksByTeacher(Pageable pageAble,String teacherId);

    HomeWork post(HomeWork homeWork);
}
