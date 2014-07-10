package com.manhattan.service.impl;

import com.manhattan.dao.AppointmentDAO;
import com.manhattan.domain.Appointment;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dam on 14-6-22.
 */
@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Override
    public Appointment save(Appointment appointment) {
        return appointmentDAO.saveAndFlush(appointment);
    }

}
