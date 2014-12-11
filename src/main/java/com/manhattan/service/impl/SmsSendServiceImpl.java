package com.manhattan.service.impl;

import com.manhattan.service.SmsSendService;

import com.manhattan.util.MhtConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.net.www.protocol.http.HttpURLConnection;


/**
 * Created by Administrator on 2014/6/5 0005.
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Override
    public boolean sendSms(String tel, String authCode) {
        try {
            URL postUrl = new URL(MhtConstant.SMS_URL);
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());
            String content = "account=" + MhtConstant.SMS_ACOUNT + "&" + "password=" + MhtConstant.SMS_PASSWORD + "&" + "destmobile=" + tel + "&"
                    + "msgText=" + URLEncoder.encode("验证码：[" + authCode + "]您正在申请注册成为本站会员【曼哈顿英语】", "UTF-8");

            System.out.println(content);
            out.writeBytes(content);

            out.flush();
            out.close(); // flush and close
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            System.out.println("=============================");
            System.out.println("Contents of post request ends");
            System.out.println("=============================");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=============================");
            System.out.println("Contents of post request ends");
            System.out.println("=============================");
            reader.close();
            connection.disconnect();
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}
