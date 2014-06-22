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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
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
        String authCode = StringUtils.upperCase(RandomStringUtils.randomAlphanumeric(6));
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
        }else {
            smsSendService.sendSms(tel, authCode);
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
    BigDecimal getBalances(@RequestParam("userId") String userId) {
        if (StringUtils.isNotBlank(userId)) {
            User user=userService.load(userId);
            return user.getWallet()!=null?user.getWallet():new BigDecimal(0);
        }
        return new BigDecimal(0);
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
    OpenPage<TeacherDetail> listTeachers(@FastJson OpenPage<TeacherDetail> openPage,@FastJson String searchKey) {
        OpenPage<TeacherDetail> resultPage = teacherDetailService.findTeacherByPage(openPage,searchKey);
        return resultPage;
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
     * 获取指定学生的预约教师列表
     * @return
     */
    @RequestMapping(value = "/teacher/getOrderTeachersByUserId")
    @ResponseBody
    public OpenPage<TeacherDetail> getOrderTeachersByUserId(@FastJson OpenPage<TeacherDetail> openPage,@RequestParam String userId,HttpServletResponse response){
        OpenPage<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(openPage, userId, MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
        return teachers;
    }

    /**
     * 获取指定学生的试听教师列表
     * @return
     */
    @RequestMapping(value = "/teacher/getListenTeachersByUserId")
    @ResponseBody
    public OpenPage<TeacherDetail> getListenTeachersByUserId(@FastJson OpenPage<TeacherDetail> openPage,@RequestParam String userId,HttpServletResponse response){
        OpenPage<TeacherDetail> teachers =teacherDetailService.findTeachersByUserId(openPage, userId, MhtConstant.USER_ACTION_LISTEN_TEACHER);
        return teachers;
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
    public OpenPage<User> getStudentList(@FastJson OpenPage<User> openPage,@RequestParam String teacherId) {
        OpenPage<User> users=userService.findUserByTeacherId(openPage, teacherId, MhtConstant.USER_ACTION_LISTEN_TEACHER);
        return users;
    }

    /**
     * 充值回调
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping(value = "/charge/callback")
    @ResponseBody
    public Wallet chargeCallback(@RequestParam String userId,@RequestParam BigDecimal money) {
        Assert.notNull(money);
        Wallet wallet = walletService.saveWallet(userId,money);
        return wallet;
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
