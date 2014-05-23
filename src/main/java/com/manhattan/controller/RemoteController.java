package com.manhattan.controller;

import com.manhattan.domain.*;
import com.manhattan.service.QuestionService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserActionService;
import com.manhattan.service.WalletService;
import com.manhattan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        String userId=userService.findUserIdByFilter(mobile, password);
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
            int res = userService.register(user.getUserId(), password, type);
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
        String authCode = "";
        User user = new User();
        user.setMobile(tel);
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
            int result = userService.resetPassword(tel, newPassword);
            return result>0;
        }
        return false;
    }

    /**
     * 获取人员信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getUser")
    public
    @ResponseBody
    User getUser(@RequestParam("tel") String userId) {
        User user = userService.load(userId);
        return user;
    }

    /**
     * 更新人员信息
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
     * 获取钱包余额
     * @param userId
     * @return 当前余额（int）
     */
    @RequestMapping(value = "/wallet/getBalances")
    public
    @ResponseBody
    Integer getBalances(@RequestParam("userId") String userId) {
        if (StringUtils.isNotBlank(userId)) {
            User user=userService.load(userId);
            return user.getWallet()!=null?user.getWallet():0;
        }
        return 0;
    }

    /**
     * 教师认证数据
     *
     * @param userId
     * @return 包含图片路径的数组
     */
    @RequestMapping(value = "/teacher/getAuthData")
    public
    @ResponseBody
    String[] getAuthData(@RequestParam("userId") String userId) {
        TeacherDetail teacherDetail=teacherDetailService.findTeacherDetail(userId);
        if (teacherDetail!=null) {
            return new String[]{teacherDetail.getEducationCertificate(),
                    teacherDetail.getExamCertificate(),
                    teacherDetail.getTeachingCertificate(),
                    teacherDetail.getStudentMaxScoreCertificate()};
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
        Pageable pageAble = new PageRequest(page.getNumber(), page.getSize());
        page = userService.findTeacherByPage(pageAble);
        return page;
    }

    /**
     * 获取教师列表
     *
     * @param searchKey
     * @return String[]
     */
    @RequestMapping(value = "/teacher/listByName")
    public
    @ResponseBody
    String[] listTeachersByName(@RequestParam("searchKey") String searchKey) {
        List<User> userList=userService.getTeachersByName(searchKey);
        return (String[]) userList.toArray();
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
     * 删除问答
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
        Pageable pageAble = new PageRequest(page.getNumber(), page.getSize());
        page = questionService.findQuestionByPage(userId,pageAble);
        return page;
    }

    /**
     * 获取需要回答列表
     *
     * @param userId
     * @param page
     * @param type （指定回答(ASSIGN)；已回答(ANSWER)；未回答(UNANSWER)）
     * @return （rows 是包含question 对象的数组）
     */
    @RequestMapping(value = "/question/needAnswerList")
    public
    @ResponseBody
    Page<Question> needAnswerList(@RequestParam("userId") String userId,
                                  @ModelAttribute("question") Page<Question> page,
                                  @RequestParam("type") String type) {
        Pageable pageAble = new PageRequest(page.getNumber(), page.getSize());
        page = questionService.findQuestionByPage(userId,type,pageAble);
        return page;
    }
}
