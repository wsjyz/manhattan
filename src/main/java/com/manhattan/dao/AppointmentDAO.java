package com.manhattan.dao;

import com.manhattan.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dam on 14-6-22.
 */
public interface AppointmentDAO extends JpaRepository<Appointment,String>,JpaSpecificationExecutor<Appointment> {

    @Modifying
    @Transactional
    @Query(value = "update Appointment ap set ap.status='SUCCESS' where appointmentId = (select resourceId from Wallet w where w.payNo=:out_trade_no)")
    int updateAppointByPayNo(String out_trade_no);
}
