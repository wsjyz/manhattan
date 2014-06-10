package com.manhattan.controller;

import com.google.common.collect.ImmutableList;
import com.manhattan.domain.Question;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
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
                                          @RequestParam("password" )String password) {
    	String passwordMd5=DigestUtils.md5Hex(password);
        String userId=userService.findUserIdByFilter(mobile, passwordMd5);
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
                     @RequestParam("type") String type) {
        User user = userService.findUserByFilter(mobile, authCode);
        if (user != null) {
        	String passwordMd5=DigestUtils.md5Hex(password);
            int res = userService.register(user.getUserId(), passwordMd5, type);
            return res!=0;
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
    String getAuthCode(@RequestParam("tel") String tel) {
        String authCode = RandomStringUtils.randomAlphanumeric(6);
        User user=userService.findUserByMobile(tel);
        if(user==null){
        	user=new User();
        	user.setUserId(UUID.randomUUID().toString());
            user.setMobile(tel);
        }
        user.setAuthCode(authCode);
        User user1 = userService.save(user);
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
                                            @RequestParam("authCode" )String authCode) {
        User user=userService.findUserByMobile(tel);
        if (user != null) {
        	String passwordMd5=DigestUtils.md5Hex(newPassword);
            int result = userService.resetPassword(tel, passwordMd5);
            return result>0;
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
    User getUser(@RequestParam("userId") String userId) {
        User user = userService.load(userId);
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
    Boolean updateUser(@ModelAttribute("user")User user) {
        if (StringUtils.isNotBlank(user.getUserId())) {
            User user1=userService.load(user.getUserId());
            BeanUtils.copyProperties(user,user1,"userId");
            userService.save(user1);
            return true;
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
                               @ModelAttribute("question") Page<Question> page) {
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
                                  @ModelAttribute("question") Page<Question> page,
                                  @RequestParam("type") String type) {
        Pageable pageAble = new PageRequest(page.getPageNo(), page.getPageSize());
        org.springframework.data.domain.Page result = questionService.findQuestionByPage(userId,type,pageAble);
        return PageConvert.convert(result);
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
}
