package com.manhattan.service.impl;

import com.manhattan.dao.CourseDao;
import com.manhattan.dao.InformationDao;
import com.manhattan.domain.Course;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.CourseService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findCourses() {
        return courseDao.findAll();
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
    public List<Course> findCoursesByFilter(Course course,String sex,TeacherDetail teacher) {
        List<Course> courses=courseDao.findAll(Specifications.where(getWhereClause(course,sex,teacher)));
        return courses;
    }

    @Override
    public List<Course> findCoursesByUserId(final String userId,final String action) {
        List<Course> courses=courseDao.findAll(new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                SetJoin<Course,UserAction> userActionSetJoin=root.join(root.getModel().getSet("setUserActions", UserAction.class), JoinType.LEFT);
                if (StringUtils.isNotBlank(userId)) {
                    predicate.getExpressions().add(
                            cb.equal(userActionSetJoin.<String>get("userId"), StringUtils.trim(userId))
                    );
                }
                if (StringUtils.isNotBlank(action)) {
                    predicate.getExpressions().add(
                            cb.equal(userActionSetJoin.<String>get("actionType"), StringUtils.trim(action))
                    );
                }
                return predicate;
            }
        });
        return courses;
    }

    @Override
    public List<Course> getCoursesByTeacher(final String userId,final String action) {
        List<Course> courses=courseDao.findAll(new Specification<Course>() {
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
        });
        return courses;
    }

    private Specification<Course> getWhereClause(final Course course,final String sex,final TeacherDetail teacher) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                SetJoin<Course,User> userJoin = r.join(r.getModel().getSet("setUser", User.class), JoinType.LEFT);
                SetJoin<Course,TeacherDetail> teacherJoin = r.join(r.getModel().getSet("setTeacher", TeacherDetail.class) , JoinType.LEFT);
                if (StringUtils.isNotBlank(course.getCourseCategory())) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String>get("courseCategory"), StringUtils.trim(course.getCourseCategory()))
                    );
                }
                if (StringUtils.isNotBlank(course.getPlace())) {
                    predicate.getExpressions().add(
                            cb.equal(r.<String>get("place"), StringUtils.trim(course.getPlace()) )
                    );
                }
                if (StringUtils.isNotBlank(sex)) {
                    predicate.getExpressions().add(
                            cb.equal(userJoin.<String>get("sex"), StringUtils.trim(sex))
                    );
                }
                if (StringUtils.isNotBlank(teacher.getTutoringWay())) {
                    predicate.getExpressions().add(
                            cb.equal(teacherJoin.<String>get("tutoringWay"), StringUtils.trim(teacher.getTutoringWay()))
                    );
                }
                return predicate;
            }
        };
    }
}
