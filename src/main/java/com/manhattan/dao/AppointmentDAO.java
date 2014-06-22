package com.manhattan.dao;

import com.manhattan.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by dam on 14-6-22.
 */
public interface AppointmentDAO extends JpaRepository<Appointment,String>,JpaSpecificationExecutor<Appointment> {

}
