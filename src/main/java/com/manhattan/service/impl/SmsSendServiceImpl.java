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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2014/6/5 0005.
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {


    @Override
    public boolean sendSms(String tel, String authCode) {
        String message="&content=恭喜您成为本站荣誉会员，您的验证码为"+authCode+"。【上海曼哈顿英语】";
        String url=MhtConstant.SMS_URL+tel+message;
        HttpPost sendUrl = new HttpPost(url);
        HttpClient http = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = http.execute(sendUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            InputStream in = null;
            try{
                in = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(in != null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        return false;
    }
}
