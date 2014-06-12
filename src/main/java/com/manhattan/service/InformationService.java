package com.manhattan.service;

import com.manhattan.domain.Information;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lk.zh on 2014/5/20.
 */
public interface InformationService {

    List<Information> getInformations();
}
