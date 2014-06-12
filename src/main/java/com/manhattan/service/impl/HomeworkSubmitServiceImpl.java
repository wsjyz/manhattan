package com.manhattan.service.impl;

import com.manhattan.dao.HomeworkSubmitDao;
import com.manhattan.domain.HomeworkSubmit;
import com.manhattan.service.HomeworkSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lk.zh on 2014/6/12.
 */
@Service
public class HomeworkSubmitServiceImpl implements HomeworkSubmitService {
    @Autowired
    private HomeworkSubmitDao homeworkSubmitDao;

    @Override
    public HomeworkSubmit submit(HomeworkSubmit homeworkSubmit) {
        return homeworkSubmitDao.saveAndFlush(homeworkSubmit);
    }
}
