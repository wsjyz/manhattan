package com.manhattan.service;

import com.manhattan.domain.Appointment;
import com.manhattan.domain.TeacherDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by dam on 14-6-22.
 */
public interface AppointmentService {

    Appointment save(Appointment appointment);

    Page findByPage(Pageable pageAble, String mobile, String userName,String type);
}
