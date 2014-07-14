package com.manhattan.service.impl;

import com.manhattan.dao.AppointmentDAO;
import com.manhattan.domain.Appointment;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.service.AppointmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Override
    public Page findByPage(final Pageable pageAble, final String mobile, final String contacts, final String type) {

        return appointmentDAO.findAll(new Specification<Appointment>() {
            @Override
            public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(mobile)) {
                    predicate.getExpressions().add(cb.equal(root.<String>get("mobile"), "%"+mobile+"%"));
                }
                if (StringUtils.isNotBlank(contacts)) {
                    predicate.getExpressions().add(cb.like(root.<String>get("contacts"), "%"+contacts+"%"));
                }
                if (StringUtils.isNotBlank(type)) {
                    predicate.getExpressions().add(cb.equal(root.<String>get("resourceType"), type));
                }
                return predicate;
            }
        }, pageAble);
    }

    @Override
    public Appointment loadById(String appiontId) {
        return appointmentDAO.getOne(appiontId);
    }
}
