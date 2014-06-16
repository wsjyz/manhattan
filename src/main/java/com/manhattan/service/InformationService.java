package com.manhattan.service;

import com.manhattan.domain.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lk.zh on 2014/5/20.
 */
public interface InformationService {

    Page<Information> getInformations(Pageable pageAble);
}
