package com.manhattan.service;

import com.manhattan.domain.Course;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface UserActionService {
    UserAction CollectTeacher(String userId, String teacherId);

    int CancelCollectTeacher(String userId, String teacherId);

    UserAction save(String userId, String courseId, String userActionAppointment);

    List<Course> getListenCoursesByTeacher(String userId);

    Page<UserAction> getUserByTeacher(Pageable pageAble,String teacherId);
}
