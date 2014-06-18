package com.manhattan.service.impl;

import com.google.common.collect.ImmutableList;
import com.manhattan.dao.CourseDao;
import com.manhattan.dao.InformationDao;
import com.manhattan.dao.TeacherDetailDao;
import com.manhattan.domain.*;
import com.manhattan.service.CourseService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private TeacherDetailDao teacherDetailDao;

    @Override
    public Page<Course> findCourses(Pageable pageAble) {
        return loadTeachers(courseDao.findAll(pageAble));
    }

    @Override
    public Course load(String courseId) {
        return courseDao.getOne(courseId);
    }

    @Override
    public Course postCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public Page<Course> findCoursesByFilter(Pageable pageAble,QueryParam qp) {
        Page<Course> courses=courseDao.findAll(Specifications.where(getWhereClause(qp)),pageAble);
        return loadTeachers(courses);
    }

    @Override
    public Page<Course> findCoursesByUserId(Pageable pageAble,final String userId,final String action) {
        return findCoursesByUserId(pageAble,userId,action,null,null);
    }

    @Override
    public Page<Course> getCoursesByTeacher(Pageable pageAble,final String userId,final String action) {
        Page<Course> courses=courseDao.findAll(new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                SetJoin<Course,UserAction> userActionSetJoin=root.join(root.getModel().getSet("setUserActions", UserAction.class), JoinType.LEFT);
                SetJoin<Course,TeacherDetail> teacherJoin = root.join(root.getModel().getSet("setTeacher", TeacherDetail.class) , JoinType.LEFT);
                if (StringUtils.isNotBlank(userId)) {
                    predicate.getExpressions().add(
                            cb.equal(teacherJoin.<String>get("postTeacher"), StringUtils.trim(userId))
                    );
                }
                if (StringUtils.isNotBlank(action)) {
                    predicate.getExpressions().add(
                            cb.equal(userActionSetJoin.<String>get("actionType"),action)
                    );
                }
                return predicate;
            }
        },pageAble);
        return loadTeachers(courses);
    }

    @Override
    public Page<Course> findCoursesByUserId(Pageable pageable,final String userId,final String actionType,final Date startTime,final Date endTime) {
        Page<Course> courses=courseDao.findAll(new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                SetJoin<Course,UserAction> userActionSetJoin=root.join(root.getModel().getSet("setUserActions", UserAction.class), JoinType.LEFT);
                if (StringUtils.isNotBlank(userId)) {
                    predicate.getExpressions().add(
                            cb.equal(userActionSetJoin.<String>get("userId"), StringUtils.trim(userId))
                    );
                }
                if (StringUtils.isNotBlank(actionType)) {
                    predicate.getExpressions().add(
                            cb.equal(userActionSetJoin.<String>get("actionType"), StringUtils.trim(actionType))
                    );
                }
                if (null!=startTime) {
                    predicate.getExpressions().add(
                            cb.lessThanOrEqualTo(userActionSetJoin.<Date>get("startTime"), startTime)
                    );
                }
                if (null!=endTime) {
                    predicate.getExpressions().add(
                            cb.greaterThanOrEqualTo(userActionSetJoin.<Date>get("endTime"), endTime)
                    );
                }
                return predicate;
            }
        },pageable);
        return loadTeachers(courses);
    }

    private Specification<Course> getWhereClause(final QueryParam qp) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                SetJoin<Course,User> userJoin = r.join(r.getModel().getSet("setUser", User.class), JoinType.LEFT);
                SetJoin<Course,TeacherDetail> teacherJoin = r.join(r.getModel().getSet("setTeacher", TeacherDetail.class) , JoinType.LEFT);
                if (StringUtils.isNotBlank(qp.getCourseCategory())) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String>get("courseCategory"), StringUtils.trim(qp.getCourseCategory()))
                    );
                }
                if (StringUtils.isNotBlank(qp.getPlace())) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String>get("place"), StringUtils.trim(qp.getPlace()) )
                    );
                }
                if (qp.getAppointmentTime()!=null) {
                    predicate.getExpressions().add(
                            cb.lessThanOrEqualTo(r.<Timestamp>get("start_time"), new Timestamp(qp.getAppointmentTime().getTime()))
                    );
                    predicate.getExpressions().add(
                            cb.greaterThanOrEqualTo(r.<Timestamp>get("endTime"), new Timestamp(qp.getAppointmentTime().getTime()))
                    );
                }
                if (StringUtils.isNotBlank(qp.getSex())) {
                    predicate.getExpressions().add(
                            cb.equal(userJoin.<String>get("sex"), StringUtils.trim(qp.getSex()))
                    );
                }
                if (StringUtils.isNotBlank(qp.getTutoringWay())) {
                    predicate.getExpressions().add(
                            cb.equal(teacherJoin.<String>get("tutoringWay"), StringUtils.trim(qp.getTutoringWay()))
                    );
                }
                return predicate;
            }
        };
    }

    /**
     * 获取主讲教师
     * @param page
     * @return
     */
    private Page<Course> loadTeachers(Page<Course> page) {
        List<TeacherDetail> teacherDetails= ImmutableList.of();
        if (page != null) {
            for (Course course : page.getContent()) {
                if (StringUtils.isNotBlank(course.getTeachers())) {
                    String teachers[] = course.getTeachers().split(",");
                    for (String teacher : teachers) {
                        TeacherDetail teacherDetail=teacherDetailDao.findOne(new Specification<TeacherDetail>() {
                            @Override
                            public Predicate toPredicate(Root<TeacherDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                Join<TeacherDetail,User> userJoin =root.join(root.getModel().getSingularAttribute("dep",User.class),JoinType.INNER);
                                return query.getRestriction();
                            }
                        });
                        teacherDetails.add(teacherDetail);
                    }
                }
                course.setTeacherDetailList(teacherDetails);
            }
        }
        return page;
    }
}
