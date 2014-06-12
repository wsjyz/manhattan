package com.manhattan.controller;

import com.google.common.collect.ImmutableList;
import com.manhattan.domain.*;
import com.manhattan.service.*;
import com.manhattan.util.CustomException;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.Page;
import com.manhattan.util.PageConvert;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by lk.zh on 2014/5/20.
 */

@Controller
public class RemoteController {

    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private TeacherDetailService teacherDetailService;
    @Autowired
    private UserActionService userActionService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private HomeworkSubmitService homeworkSubmitService;
    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login")
    public
    @ResponseBody
    String remotelogin(@RequestParam("mobile") String mobile,
                                        @RequestParam("password" )String password
                                        ,HttpServletResponse response) {
    	String passwordMd5=DigestUtils.md5Hex(password);
        String userId=userService.findUserIdByFilter(mobile, passwordMd5);
        if (StringUtils.isEmpty(userId)) {
            setResponse("用户名或密码错误", response);
        }
        return userId;
    }

    /**
     * 注册
     * @param mobile
     * @param password
     * @param authCode
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/register")
    public
    @ResponseBody
    Boolean register(@RequestParam("mobile") String mobile,
                     @RequestParam("password") String password,
                     @RequestParam("authCode") String authCode,
                     @RequestParam("type") String type
                     ,HttpServletResponse response) {
        User user = userService.findUserByFilter(mobile, authCode);
        if (user != null) {
        	String passwordMd5=DigestUtils.md5Hex(password);
            int res = userService.register(user.getUserId(), passwordMd5, type);
            return res!=0;
        }else{
            setResponse("注册失败,验证码错误", response);
        }
        return false;
    }

    /**
     * 获取验证码
     * @param tel
     * @return String
     */
    @RequestMapping(value = "/user/getAuthCode")
    public
    @ResponseBody
    String getAuthCode(@RequestParam("tel") String tel,HttpServletResponse response) {
        String authCode = RandomStringUtils.randomAlphanumeric(6);
        User user=userService.findUserByMobile(tel);
        if(user==null){
        	user=new User();
        	user.setUserId(UUID.randomUUID().toString());
            user.setMobile(tel);
        }
        user.setAuthCode(authCode);
        User user1 = userService.save(user);
        if (user1 == null) {
            setResponse("验证码生成错误，请重新获取", response);
        }
        return authCode;
    }

    /**
     * 重置密码
     * @param tel
     * @param newPassword
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/user/resetPassword")
    public
    @ResponseBody
    Boolean resetPassword(@RequestParam("tel") String tel,
                            @RequestParam("newPassword" )String newPassword,
                            @RequestParam("authCode" )String authCode
                            ,HttpServletResponse response) {
        User user=userService.findUserByMobile(tel);
        if (user != null) {
        	String passwordMd5=DigestUtils.md5Hex(newPassword);
            int result = userService.resetPassword(tel, passwordMd5);
            if (result <= 0) {
                setResponse("重置密码失败", response);
            }
            return result>0;
        }else{
            setResponse("人员不存在，不能重置密码", response);
        }
        return false;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getUser")
    public
    @ResponseBody
    User getUser(@RequestParam("userId") String userId,HttpServletResponse response) {
        User user = userService.load(userId);
        if (user == null) {
            setResponse("人员不存在", response);
        }
        return user;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/updateUser")
    public
    @ResponseBody
    Boolean updateUser(@ModelAttribute("user")User user,HttpServletResponse response) {
        if (StringUtils.isNotBlank(user.getUserId())) {
            User user1=userService.load(user.getUserId());
            BeanUtils.copyProperties(user,user1,"userId");
            user1=userService.save(user1);
            if (user1 == null) {
                setResponse("更新用户信息出错", response);
            }
            return true;
        }else{
            setResponse("人员不存在", response);
        }
        return false;
    }

    /**
     * 获取钱包
     * @param userId
     * @return 余额
     */
    @RequestMapping(value = "/wallet/getBalances")
    public
    @ResponseBody
    int getBalances(@RequestParam("userId") String userId) {
        if (StringUtils.isNotBlank(userId)) {
            User user=userService.load(userId);
            return user.getWallet()!=null?user.getWallet():0;
        }
        return 0;
    }

    /**
     * 教师认证信息
     *
     * @param userId
     * @return 图片路径
     */
    @RequestMapping(value = "/teacher/getAuthData")
    public
    @ResponseBody
    List<String> getAuthData(@RequestParam("userId") String userId) {
        TeacherDetail teacherDetail=teacherDetailService.findTeacherDetail(userId);
        if (teacherDetail!=null) {
            return ImmutableList.of(teacherDetail.getEducationCertificate(),
                    teacherDetail.getExamCertificate(),
                    teacherDetail.getTeachingCertificate(),
                    teacherDetail.getStudentMaxScoreCertificate());
        }
        return null;
    }

    /**
     * 获取教师列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/teacher/listPage")
    public
    @ResponseBody
    Page<User> listTeachers(@ModelAttribute("page") Page<User> page) {
        Pageable pageAble = new PageRequest(page.getPageNo(), page.getPageSize());
        org.springframework.data.domain.Page resultPage = userService.findTeacherByPage(pageAble);
        return PageConvert.convert(resultPage);
    }

    /**
     * 搜索教师
     *
     * @param searchKey
     * @return String[]
     */
    @RequestMapping(value = "/teacher/listByName")
    public
    @ResponseBody
    List<User> listTeachersByName(@RequestParam("searchKey") String searchKey) {
        List<User> userList=userService.getTeachersByName(searchKey);
        return userList;
    }

    /**
     * 收藏教师
     *
     * @param userId
     * @param teacherId
     */
    @RequestMapping(value = "/user/collect")
    public void collectTeacher(@RequestParam("userId") String userId,
                               @RequestParam("teacherId") String teacherId) {
        UserAction userAction=userActionService.CollectTeacher(userId,teacherId);
    }

    /**
     * 取消收藏教师
     * @param userId
     * @param teacherId
     */
    @RequestMapping(value = "/user/cancelCollect")
    public void cancelCollect(@RequestParam("userId") String userId,
                              @RequestParam("teacherId") String teacherId) {
        int result=userActionService.CancelCollectTeacher(userId, teacherId);
    }

    /**
     * 提问
     * @param question
     * @return
     */
    @RequestMapping(value = "/question/askQuestion")
    public
    @ResponseBody
    Boolean askQuestion(@ModelAttribute("question") Question question) {
        return questionService.saveQuestion(question)!=null;
    }

    /**
     * 回答问题
     * @param question
     * @return
     */
    @RequestMapping(value = "/question/answerQuestion")
    public
    @ResponseBody
    Boolean answerQuestion(@ModelAttribute("question") Question question) {
        return questionService.saveQuestion(question)!=null;
    }

    /**
     * 删除问题
     * @param questionId
     */
    @RequestMapping(value = "/question/deleteQuestion")
    public void deleteQuestion(@RequestParam("questionId") String questionId) {
        questionService.deleteQuestion(questionId);
    }

    /**
     * 获取我的问题
     *
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping(value = "/question/myQuestions")
    public
    @ResponseBody
    Page<Question> myQuestions(@RequestParam("userId") String userId,
                               @ModelAttribute("page") Page<Question> page) {
        Pageable pageAble = new PageRequest(page.getPageNo(), page.getPageSize());
        org.springframework.data.domain.Page result = questionService.findQuestionByPage(userId,pageAble);
        return PageConvert.convert(result);
    }

    /**
     * 获取我要回答列表
     *
     * @param userId
     * @param page
     * @param type 指定回答(ASSIGN)已回答(ANSWER)未回答(UNANSWER)
     * @return page
     */
    @RequestMapping(value = "/question/needAnswerList")
    public
    @ResponseBody
    Page<Question> needAnswerList(@RequestParam("userId") String userId,
                                  @ModelAttribute("page") Page<Question> page,
                                  @RequestParam("type") String type) {
        Pageable pageAble = new PageRequest(page.getPageNo(), page.getPageSize());
        org.springframework.data.domain.Page result = questionService.findQuestionByPage(userId,type,pageAble);
        return PageConvert.convert(result);
    }

    /**
     * 获取精品课程列表
     * @return
     */
    @RequestMapping(value = "/course/getWorthCourses")
    @ResponseBody
    public List<Course> getWorthCourses(HttpServletResponse response){
        List<Course> courses=courseService.findCourses();
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("暂无课程", response);
        }
        return courses;
    }

    /**
     * 获取详情
     * @return
     */
    @RequestMapping(value = "/course/getCourseDetail")
    @ResponseBody
    public Course getCourseDetail(@RequestParam String courseId) {
        Course course = courseService.load(courseId);
        return course;
    }

    /**
     * 发布课程
     * @return
     */
    @RequestMapping(value = "/course/postCourses")
    @ResponseBody
    public String postCourses(@ModelAttribute Course course,HttpServletResponse response) {
        Course saved=courseService.postCourse(course);
        if (StringUtils.isEmpty(saved.getCourseId())) {
            setResponse("保存课程失败", response);
        }
        return "";
    }

    /**
     * 根据指定条件搜索可预约课程
     * 搜索条件包括分类，地点，预约日期，教师性别，教学方式
     * @return
     */
    @RequestMapping(value = "/course/getOrderCourses")
    @ResponseBody
    public List<Course> getOrderCourses(@ModelAttribute Course course,
                                        @RequestParam String sex,
                                        @ModelAttribute TeacherDetail teacherDetail,
                                        HttpServletResponse response) {
        List<Course> courses = courseService.findCoursesByFilter(course,sex,teacherDetail);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有可预约的课程", response);
        }
        return courses;
    }

    /**
     * 获取指定学生的课程日历表
     * @return
     */
    @RequestMapping(value = "/course/getSchedule")
    @ResponseBody
    public String getSchedule(@RequestParam String userId){
        List<Course> courses =courseService.findCoursesByUserId(userId,"");
        String scheduleData= fetchSchedule(courses);
        return scheduleData;
    }



    /**
     * 获取指定学生的预约课程列表
     * @return
     */
    @RequestMapping(value = "/course/getOrderCoursesByUserId")
    @ResponseBody
    public List<Course> getOrderCoursesByUserId(@RequestParam String userId,HttpServletResponse response){
        List<Course> courses =courseService.findCoursesByUserId(userId,MhtConstant.USER_ACTION_APPOINTMENT);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有任何预约的课程", response);
        }
        return courses;
    }

    /**
     * 获取指定学生的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByUserId")
    @ResponseBody
    public List<Course> getListenCoursesByUserId(@RequestParam String userId,HttpServletResponse response){
        List<Course> courses =courseService.findCoursesByUserId(userId,MhtConstant.USER_ACTION_LISTEN);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有任何试听的课程", response);
        }
        return courses;
    }

    /**
     * 获取指定学生的收藏课程列表
     * @return
     */
    @RequestMapping(value = "/course/getCollectCoursesByUserId")
    @ResponseBody
    public List<Course> getCollectCoursesByUserId(@RequestParam String userId,HttpServletResponse response){
        List<Course> courses =courseService.findCoursesByUserId(userId,MhtConstant.USER_ACTION_COLLECT);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有任何收藏的课程", response);
        }
        return courses;
    }

    /**
     * 添加预约课程记录
     * @return
     */
    @RequestMapping(value = "/course/addAppointment")
    @ResponseBody
    public void addAppointment(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response) {
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_APPOINTMENT);
        if (userAction == null) {
            setResponse("预约课程失败", response);
        }
    }

    /**
     * 添加试听课程记录
     * @return
     */
    @RequestMapping(value = "/course/addListen")
    @ResponseBody
    public void addListen(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response){
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_LISTEN);
        if (userAction == null) {
            setResponse("试听课程记录保存失败", response);
        }
    }

    /**
     * 收藏试听课程记录
     * @return
     */
    @RequestMapping(value = "/course/addCollect")
    @ResponseBody
    public void addCollect(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response){
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_COLLECT);
        if (userAction == null) {
            setResponse("收藏课程失败", response);
        }
    }

    /**
     * 获取指定教师的预约课程列表
     * @return
     */
    @RequestMapping(value = "/course/getOrderCoursesByTeacher")
    @ResponseBody
    public List<Course> getOrderCoursesByTeacher(@RequestParam String userId,HttpServletResponse response){
        List<Course> courses=courseService.getCoursesByTeacher(userId, MhtConstant.USER_ACTION_APPOINTMENT);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有任何预约的课程", response);
        }
        return courses;
    }

    /**
     * 获取指定教师的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByTeacher")
    @ResponseBody
    public List<Course> getListenCoursesByTeacher(@RequestParam String userId,HttpServletResponse response){
        List<Course> courses=courseService.getCoursesByTeacher(userId, MhtConstant.USER_ACTION_LISTEN);
        if (CollectionUtils.isEmpty(courses)) {
            setResponse("没有任何试听的课程", response);
        }
        return courses;
    }

    /**
     * 获取资讯列表
     * @return
     */
    @RequestMapping(value = "/course/getInformations")
    @ResponseBody
    public List getInformations(HttpServletResponse response) {
        List list=informationService.getInformations();
        if (CollectionUtils.isEmpty(list)) {
            setResponse("暂无资讯", response);
        }
        return list;
    }

    /**
     * 获取指定学生的作业列表
     * @return
     */
    @RequestMapping(value = "/homeWork/getHomeworksByUser")
    @ResponseBody
    public List<HomeWork> getHomeworksByUser(@RequestParam String userId,HttpServletResponse response) {
        List<HomeWork> list = homeWorkService.getHomeworksByUser(userId);
        if (CollectionUtils.isEmpty(list)) {
            setResponse("暂无作业", response);
        }
        return list;
    }

    /**
     * 获取指定教师的作业列表
     * @return
     */
    @RequestMapping(value = "/course/getHomeworksByTeacher")
    @ResponseBody
    public List getHomeworksByTeacher(@RequestParam String userId,HttpServletResponse response){
        List<HomeWork> list = homeWorkService.getHomeworksByTeacher(userId);
        if (CollectionUtils.isEmpty(list)) {
            setResponse("没有发布作业", response);
        }
        return list;
    }

    /**
     * 发布一个新的作业
     * @param homeWork
     */
    @RequestMapping(value = "/homeWork/postHomeWork")
    @ResponseBody
    public void postHomeWork(@ModelAttribute HomeWork homeWork,HttpServletResponse response) {
        HomeWork homeWork1=homeWorkService.post(homeWork);
        if (homeWork1==null) {
            setResponse("作业发布失败", response);
        }
    }

    /**
     * 提交一个新的作业
     * @param homeworkSubmit
     */
    @RequestMapping(value = "/homeWork/submitHomeWork")
    @ResponseBody
    public void submitHomeWork(@ModelAttribute HomeworkSubmit homeworkSubmit,HttpServletResponse response){
        HomeworkSubmit homeworkSubmit1=homeworkSubmitService.submit(homeworkSubmit);
        if (homeworkSubmit1==null) {
            setResponse("作业提交失败", response);
        }
    }

    /**
     * 上传附件
     * @param content
     */
    @RequestMapping(value = "/upload/uploadAnnex")
    @ResponseBody
    public void uploadAnnex(@RequestParam String content){

    }
    /**
     * 根据教师Id获取学生列表信息
     * @param teacherId
     */
    @RequestMapping(value = "/user/getStudentList")
    @ResponseBody
    public List<User> getStudentList(@RequestParam String teacherId,HttpServletResponse response) {
        List<User> users=userActionService.getUserByTeacher(teacherId);
        if (CollectionUtils.isEmpty(users)) {
            setResponse("没有相关学生", response);
        }
        return users;
    }
    /**
     * 异常处理
     * @return
     */
    @ExceptionHandler
    public String exception(Exception exception,HttpServletResponse response) {
        String msg="";
        exception.printStackTrace();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        if(exception instanceof NullPointerException){
            msg="对象为空";
        }else if(exception instanceof SQLException){
            msg="数据查询错误";
        }else if(exception instanceof RuntimeException){
            msg="系统运行错误";
        }else if(exception instanceof CustomException){
            msg="系统运行错误";
        }
        response.addHeader(MhtConstant.ERROR_CODE,msg);
        return null;
    }

    public void setResponse(String msg,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.addHeader(MhtConstant.ERROR_CODE,msg);
    }

    private String fetchSchedule(List<Course> courses) {
        return null;
    }
}
