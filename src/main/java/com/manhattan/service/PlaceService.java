package com.manhattan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lk.zh on 2014/7/6 0006.
 */
public interface PlaceService {
    Page listByPage(Pageable pageAble);
}
