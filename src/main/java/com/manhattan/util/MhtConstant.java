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
     * user account status
     */
    public final static String USER_STATUS_DISABLE = "DISABLE";
    public static final String USER_STATUS_ENABLE = "ENABLE";

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

    public static final String ALIPAY_SELLER_PARTNER = "2088511925655681";
    public static final String ALIPAY_SELLER_KEY = "visxmzhnkfu09v7f9dme7w4ftvrdgp6j";
    public static final String ALIPAY_SELLER__PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK1nciy0qOfFzW2IGuGRmLDhSunH3zzrf+mZf9q7tsynHHab7MxTiDvamh0GLD3kzRAYz17RebGJS191gfX8LYAksxypLtHd8/yAOU83ZMV4K/9Wl2BNR9G2axCWkuU4FwHfIDjF2TdOZhTEVbZsUzwTUaVL1oyvcWXAHnUOi/YVAgMBAAECgYA24OdEeMR/3Zz/DjUbsF13lUWFKUlgWVO5FWheTEw1Bqo+a2iM3d7eundNujdkOzYe4ws2AeuWoVHmWyUPOYkYYDcmhyMgVoA6P57t7xDQif5+MKbwhzSyQQO8lWeBeAxIWyQif6nz+UGfkvqzaHmH0ZObaFwN2f8QRsym+vv13QJBAOSUZu1iW0+7MYMwIqJlYjrSS9tFOmkk9YBzoqVsSgsWLiKzbSKkHMzZ7GljsE3SJCebmyJZQ+3rOqYlOOQd2T8CQQDCNJ8Rnyv/dYv7FV/N5ovywC4ncDNE032ufj3NwDZwrmwW0R8ToaynRZJiTFr5G0yvOgIQz4Vh98q7ODKnbOerAkA8M+3sBeTA2i/POqVUmllF3s+F3/Tjbo2OmGY1JZFW3C+oihNrdUf0mE0Q8OWliXxmRjCU2mfuyO64hcM3KblnAkBfrQr85H0JksCOx132k3E4+8MBPP6VFthhQeJy3hIz+0pXB1mXE1x64ASZFuLuvKtP3HUuEP62YBxplesmnrmXAkEAp2/rqqQqfbPQ2/HChbezIXvPKlQMZIc1V8HatGgNcVvzlpbscC9+L0J0o4zq+yOiqht4hYdz5HtyvUMWxE6V9Q==";
    public static final String ALIPAY_SELLER__PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMhWHkVDmgMTcAQnGx27kYta5mw50giyVWMZ9jVByJz25UVU/NbT0AFEj/2DqsSgZaOPz4GunWHnQa6FE3i9aKETlqm3Fjz2jPuQVqugXNVBc4MUzcgwM36l8QTiGIl5vhXrwWNnf1dWR+4oifWmja4HdD8cxn1H2YS3wCwvCScQIDAQAB";
    public static final String ALIPAY_SELLER_EMAIL = "geassccvip@163.com";
}
