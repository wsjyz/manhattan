package com.manhattan.controller;

import com.manhattan.domain.*;
import com.manhattan.service.QuestionService;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.service.UserActionService;
import com.manhattan.service.WalletService;
import com.manhattan.service.UserService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.id.UUIDGenerator;
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

    /**
     * 鐧诲綍
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
    	System.out.println(passwordMd5);
        String userId=userService.findUserIdByFilter(mobile, passwordMd5);
        return userId;
    }

    /**
     * 娉ㄥ唽
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
     * 鑾峰彇楠岃瘉鐮�
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
     * 閲嶇疆瀵嗙爜
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
     * 鑾峰彇浜哄憳淇℃伅
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
     * 鏇存柊浜哄憳淇℃伅
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
     * 鑾峰彇閽卞寘浣欓
     * @param userId
     * @return 褰撳墠浣欓锛坕nt锛�
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
     * 鏁欏笀璁よ瘉鏁版嵁
     *
     * @param userId
     * @return 鍖呭惈鍥剧墖璺緞鐨勬暟缁�
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
     * 鑾峰彇鏁欏笀鍒楄〃
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
     * 鑾峰彇鏁欏笀鍒楄〃
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
     * 鏀惰棌鏁欏笀
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
     * 鍙栨秷鏀惰棌鏁欏笀
     * @param userId
     * @param teacherId
     */
    @RequestMapping(value = "/user/cancelCollect")
    public void cancelCollect(@RequestParam("userId") String userId,
                              @RequestParam("teacherId") String teacherId) {
        int result=userActionService.CancelCollectTeacher(userId, teacherId);
    }

    /**
     * 鎻愰棶
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
     * 鍥炵瓟闂
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
     * 鍒犻櫎闂瓟
     * @param questionId
     */
    @RequestMapping(value = "/question/deleteQuestion")
    public void deleteQuestion(@RequestParam("questionId") String questionId) {
        questionService.deleteQuestion(questionId);
    }

    /**
     * 鑾峰彇鎴戠殑闂
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
     * 鑾峰彇闇�鍥炵瓟鍒楄〃
     *
     * @param userId
     * @param page
     * @param type 锛堟寚瀹氬洖绛�ASSIGN)锛涘凡鍥炵瓟(ANSWER)锛涙湭鍥炵瓟(UNANSWER)锛�
     * @return 锛坮ows 鏄寘鍚玵uestion 瀵硅薄鐨勬暟缁勶級
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
