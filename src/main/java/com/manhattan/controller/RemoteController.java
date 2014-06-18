package com.manhattan.controller;

import com.google.common.collect.ImmutableList;
import com.manhattan.domain.*;
import com.manhattan.service.*;
import com.manhattan.util.*;
import com.manhattan.util.OpenPage;
import org.springframework.data.domain.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;
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

    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login")
    public
    @ResponseBody
    User remotelogin(@RequestParam("mobile") String mobile,
                                        @RequestParam("password" )String password
                                        ,HttpServletResponse response) {
    	String passwordMd5=DigestUtils.md5Hex(password);
        User user=userService.findUserIdByFilter(mobile, passwordMd5);
        if (user==null) {
            setResponse("用户名或密码错误", response);
        }
        return user;
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
    @RequestMapping(value = "/user/updateUser",method = RequestMethod.POST)
    public
    @ResponseBody
    Boolean updateUser(@FastJson User user,HttpServletResponse response) {
        if (StringUtils.isNotBlank(user.getUserId())) {
            User user1=userService.load(user.getUserId());
            if (user1 == null) {
                setResponse("更新用户信息出错", response);
            }else{
                BeanUtils.copyProperties(user,user1,"userId","mobile");
                user1=userService.save(user1);
                if (user1 == null) {
                    setResponse("更新用户信息出错", response);
                }
                return true;
            }
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
    List<String> getAuthData(@RequestParam("userId") String userId,HttpServletResponse response) {
        TeacherDetail teacherDetail=teacherDetailService.findTeacherDetail(userId);
        if (teacherDetail!=null) {
            return ImmutableList.of(teacherDetail.getEducationCertificate(),
                    teacherDetail.getExamCertificate(),
                    teacherDetail.getTeachingCertificate(),
                    teacherDetail.getStudentMaxScoreCertificate());
        }
        return ImmutableList.of();
    }

    /**
     * 获取教师列表
     *
     * @param openPage
     * @return
     */
    @RequestMapping(value = "/teacher/listPage")
    public
    @ResponseBody
    OpenPage<User> listTeachers(@FastJson OpenPage<User> openPage,@RequestParam(value = "searchKey",required = false) String searchKey) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page resultPage = userService.findTeacherByPage(pageAble,searchKey);
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
    OpenPage<User> listTeachersByName(@FastJson OpenPage<User> openPage,@RequestParam("searchKey") String searchKey) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<User> resultPage=userService.getTeachersByName(pageAble,searchKey);
        return PageConvert.convert(resultPage);
    }

    /**
     * 收藏教师
     *
     * @param userId
     * @param teacherId
     */
    @RequestMapping(value = "/user/collect")
    public void collectTeacher(@RequestParam("userId") String userId,
                               @RequestParam("teacherId") String teacherId
                                ,HttpServletResponse response) {
        UserAction userAction=userActionService.CollectTeacher(userId,teacherId);
        if (userAction == null||StringUtils.isEmpty(userAction.getActionId())) {
            setResponse("收藏教师失败", response);
        }
    }

    /**
     * 取消收藏教师
     * @param userId
     * @param teacherId
     */
    @RequestMapping(value = "/user/cancelCollect")
    public void cancelCollect(@RequestParam("userId") String userId,
                              @RequestParam("teacherId") String teacherId
                                ,HttpServletResponse response) {
        int result=userActionService.CancelCollectTeacher(userId, teacherId);
        if (result<1) {
            setResponse("取消收藏失败", response);
        }
    }

    /**
     * 提问
     * @param question
     * @return
     */
    @RequestMapping(value = "/question/askQuestion")
    public
    @ResponseBody
    Boolean askQuestion(@FastJson Question question,HttpServletResponse response) {
        Question question1=questionService.saveQuestion(question);
        if (question1 == null || StringUtils.isEmpty(question1.getQuestionId())) {
            setResponse("保存提问失败", response);
            return false;
        }
        return true;
    }

    /**
     * 回答问题
     * @param question
     * @return
     */
    @RequestMapping(value = "/question/answerQuestion")
    public
    @ResponseBody
    Boolean answerQuestion(@FastJson Question question,HttpServletResponse response) {
        Question question1=questionService.saveQuestion(question);
        if (question1 == null || StringUtils.isEmpty(question1.getQuestionId())) {
            setResponse("保存提问失败", response);
            return false;
        }
        return true;
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
     * @param openPage
     * @return
     */
    @RequestMapping(value = "/question/myQuestions")
    public
    @ResponseBody
    OpenPage<Question> myQuestions(@RequestParam("userId") String userId,
                               @FastJson OpenPage<Question> openPage) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page result = questionService.findQuestionByPage(userId,pageAble);
        return PageConvert.convert(result);
    }

    /**
     * 获取我要回答列表
     *
     * @param userId
     * @param openPage
     * @param type 指定回答(ASSIGN)已回答(ANSWER)未回答(UNANSWER)
     * @return page
     */
    @RequestMapping(value = "/question/needAnswerList")
    public
    @ResponseBody
    OpenPage<Question> needAnswerList(@RequestParam("userId") String userId,
                                  @FastJson OpenPage<Question> openPage,
                                  @RequestParam("type") String type) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page result = questionService.findQuestionByPage(userId,type,pageAble);
        return PageConvert.convert(result);
    }

    /**
     * 获取精品课程列表
     * @return
     */
    @RequestMapping(value = "/course/getWorthCourses")
    @ResponseBody
    public OpenPage<Course> getWorthCourses(@FastJson OpenPage<Question> openPage,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.findCourses(pageAble);
        return PageConvert.convert(courses);
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
    public Course postCourses(@FastJson Course course,HttpServletResponse response) {
        Course saved=courseService.postCourse(course);
        if (StringUtils.isEmpty(saved.getCourseId())) {
            setResponse("保存课程失败", response);
        }
        return saved;
    }

    /**
     * 根据指定条件搜索可预约课程
     * 搜索条件包括分类，地点，预约日期，教师性别，教学方式
     * @return
     */
    @RequestMapping(value = "/course/getOrderCourses")
    @ResponseBody
    public OpenPage<Course> getOrderCourses(@FastJson OpenPage<Course> openPage,
                                        @FastJson QueryParam queryParam,
                                        HttpServletResponse response) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses = courseService.findCoursesByFilter(pageAble,queryParam);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的课程日历表
     * @return
     */
    @RequestMapping(value = "/course/getSchedule")
    @ResponseBody
    public List<Date> getSchedule(@RequestParam Date startTime,
                                            @RequestParam Date endTime,
                                            @RequestParam String userId){
        Page<Course> courses =courseService.findCoursesByUserId(null, userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE, startTime, endTime);
        List<Date> date=ImmutableList.of();
        for (Course course : courses.getContent()) {
            date.add(course.getStartTime());
        }
        return date;
    }


    /**
     * 获取指定学生的预约课程列表
     * @return
     */
    @RequestMapping(value = "/course/getOrderCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getOrderCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_LISTEN_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的收藏课程列表
     * @return
     */
    @RequestMapping(value = "/course/getCollectCoursesByUserId")
    @ResponseBody
    public OpenPage<Course> getCollectCoursesByUserId(@FastJson OpenPage<Course> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses =courseService.findCoursesByUserId(pageAble,userId,MhtConstant.USER_ACTION_COLLECT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 添加预约课程记录
     * @return
     */
    @RequestMapping(value = "/course/addAppointment")
    @ResponseBody
    public void addAppointment(@RequestParam String userId,@RequestParam String courseId,HttpServletResponse response) {
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
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
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_LISTEN_COURSE);
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
        UserAction userAction=userActionService.save(userId, courseId, MhtConstant.USER_ACTION_COLLECT_COURSE);
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
    public OpenPage<Course> getOrderCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.getCoursesByTeacher(pageAble,userId, MhtConstant.USER_ACTION_APPOINTMENT_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定教师的试听课程列表
     * @return
     */
    @RequestMapping(value = "/course/getListenCoursesByTeacher")
    @ResponseBody
    public OpenPage<Course> getListenCoursesByTeacher(@FastJson OpenPage openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Course> courses=courseService.getCoursesByTeacher(pageAble,userId, MhtConstant.USER_ACTION_LISTEN_COURSE);
        return PageConvert.convert(courses);
    }

    /**
     * 获取指定学生的预约教师列表
     * @return
     */
    @RequestMapping(value = "/teacher/getOrderTeachersByUserId")
    @ResponseBody
    public OpenPage<TeacherDetail> getOrderTeachersByUserId(@FastJson OpenPage<TeacherDetail> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(pageAble, userId, MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
        return PageConvert.convert(teachers);
    }

    /**
     * 获取指定学生的试听教师列表
     * @return
     */
    @RequestMapping(value = "/teacher/getListenTeachersByUserId")
    @ResponseBody
    public OpenPage<TeacherDetail> getListenTeachersByUserId(@FastJson OpenPage<TeacherDetail> openPage,@RequestParam String userId,HttpServletResponse response){
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(pageAble, userId, MhtConstant.USER_ACTION_LISTEN_TEACHER);
        return PageConvert.convert(teachers);
    }

    /**
     * 获取资讯列表
     * @return
     */
    @RequestMapping(value = "/info/getInformations")
    @ResponseBody
    public OpenPage<Information> getInformations(@FastJson OpenPage<Information> openPage,HttpServletResponse response) {
        Pageable pageAble= new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<Information> page=informationService.getInformations(pageAble);
        return PageConvert.convert(page);
    }

    /**
     * 上传附件
     * @param file
     */
    @RequestMapping(value = "/upload/uploadAnnex")
    @ResponseBody
    public String uploadAnnex(@RequestParam MultipartFile file,HttpServletRequest request,HttpServletResponse response){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            setResponse("上传附件失败", response);
        }
        return request.getContextPath()+"/upload/"+fileName;
    }
    /**
     * 根据教师Id获取学生列表信息
     * @param teacherId
     */
    @RequestMapping(value = "/user/getStudentList")
    @ResponseBody
    public OpenPage<UserAction> getStudentList(@FastJson OpenPage<Information> openPage,@RequestParam String teacherId,HttpServletResponse response) {
        Pageable pageAble = new PageRequest(openPage.getPageNo()-1, openPage.getPageSize());
        Page<UserAction> users=userActionService.getUserByTeacher(pageAble,teacherId);
        return PageConvert.convert(users);
    }
    /**
     * 统一异常处理
     * @return
     */
    @ExceptionHandler
    public String exception(Exception exception,HttpServletResponse response) {
        String msg="";
        exception.printStackTrace();
        if(exception instanceof NullPointerException){
            msg="对象为空";
        }else if(exception instanceof SQLException || exception instanceof DataAccessException){
            msg="数据查询错误";
        }else if(exception instanceof RuntimeException){
            msg="系统运行错误";
        }else if(exception instanceof CustomException){
            msg="系统运行错误";
        }
        response.addHeader(MhtConstant.ERROR_CODE,msg);
        return null;
    }

    /**
     * 设置错误消息
     * @param msg
     * @param response
     * 获取方式：new String(conn.getHeaderField("ErrorMsg").getBytes("ISO-8859-1"), "UTF-8")
     */
    public void setResponse(String msg,HttpServletResponse response) {
        response.addHeader("ErrorMsg" ,msg);
    }

    private List<CourseSchedule> fetchSchedule(List<Course> courses) {
        List<CourseSchedule> schedules=ImmutableList.of();
        CourseSchedule courseSchedule=new CourseSchedule();
        schedules.add(courseSchedule);
        return schedules;
    }
}
