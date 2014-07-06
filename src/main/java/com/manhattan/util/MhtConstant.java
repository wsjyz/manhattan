package com.manhattan.util;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public class MhtConstant {
    
    /**
     * user type
     */
    public final static String USER_TYPE_TEACHER = "TEACHER";
    public final static String USER_TYPE_STUDENT = "STUDENT";
    public final static String USER_TYPE_VIPSTUDENT = "VIPSTUDENT";

    /**
     * 问题状态
     */
    public final static String QUESTION_STATUS_ANSWERED="ANSWERED";
    public final static String QUESTION_STATUS_UNANSWERED="UNANSWER";

    //短信API
    public final static String SMS_URL="http://sms.uuvio.com:8001/sms.aspx?action=send&userid=8H&account=ccs804&password=ccs804&mobile=";

    public static final String ERROR_CODE = "ErrorMsg";

    /**
     * 用户动作(预约、试听、收藏)
     */
    //课程
    public static final String USER_ACTION_APPOINTMENT_COURSE = "APPOINTMENT_COURSE";
    public static final String USER_ACTION_LISTEN_COURSE = "LISTEN_COURSE";
    public static final String USER_ACTION_COLLECT_COURSE = "COLLECT_COURSE";

    //教师
    public static final String USER_ACTION_APPOINTMENT_TEACHER = "APPOINTMENT_TEACHER";
    public static final String USER_ACTION_LISTEN_TEACHER = "LISTEN_TEACHER";
    public static final String USER_ACTION_COLLECT_TEACHER = "COLLECT_TEACHER";

    //关注、评论
    public static final String USER_ACTION_FOLLOW_TEACHER ="FOLLOW_TEACHER";
    public static final String USER_ACTION_FOLLOW_COURSE ="FOLLOW_COURSE";
    public static final String USER_ACTION_COMMENT_TEACHER ="COMMENT_TEACHER";
    public static final String USER_ACTION_COMMENT_COURSE ="COMMENT_COURSE";

    //充值成功
    public static final String PAY_STATUS_SUCCESS = "SUCCESS";


    public static final String SEESION_USER_ID = "userId";
}
