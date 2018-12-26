package com.yiyiglobal.dp.util.third;

import com.alibaba.fastjson.JSONObject;
import com.yiyiglobal.dp.util.third.sms.HttpClientUtil;
import com.yiyiglobal.dp.util.third.sms.SSLClient;
import com.yiyiglobal.dp.util.third.sms.WebNetEncode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzukun on 2018/4/25.
 */
@Service
public class SMSService {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private HttpClient httpClient;

    /**
     * 发送短信，1-发送成功；-1-发送失败
     * @param countryMobileCode
     * @param mobile
     * @param smsContent
     * @return
     */
    public String send(String countryMobileCode,String mobile,String smsContent){
        String result="";
        //发送给国内用户
        if("86".equals(countryMobileCode)) {
            result = send(mobile,smsContent);
        }
        //发送给海外用户
        else {
            result = sendToForeigner(countryMobileCode,mobile,smsContent);
        }
        return result;
    }

    /**
     * 发送短信给国内用户
     * @param mobile
     * @param content
     * @return
     */
    public String send(String mobile,String content) {

        try {
            httpClient = new SSLClient();
            String url = "https://dx.ipyy.net/smsJson.aspx";
            String accountName = "AF00011";
            String password = "AF0001165";

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("action", "send"));
            nvps.add(new BasicNameValuePair("userid", ""));
            nvps.add(new BasicNameValuePair("account", accountName));
            nvps.add(new BasicNameValuePair("password", password));
            nvps.add(new BasicNameValuePair("mobile", mobile));        //多个手机号用逗号分隔
            nvps.add(new BasicNameValuePair("content", content));
            nvps.add(new BasicNameValuePair("sendTime", ""));
            nvps.add(new BasicNameValuePair("extno", ""));
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String r = EntityUtils.toString(entity, "utf8");
                logger.warn("获取 短信["+mobile+"]["+content+"] 结果:" + r );

                JSONObject result = JSONObject.parseObject(r);
                if (result.getString("returnstatus").equals("Success")) {
                    return "1";
                }
            }
        } catch (Exception e) {
            logger.error("Exception{}", e);
        }

        return "-1";
    }

    public String sendToForeigner(String countryMobileCode,String mobile,String smsContent){

        String ip = "m.isms360.com";
        int port = 8085;

        HttpClientUtil util = new HttpClientUtil(ip,  port, "/mt/MT3.ashx");

        String user = "yiyiglobal";     // 用户名
        String pwd = "yiyi123456";      //密码：
        String ServiceID = "SEND";
        String sender = "";             // 原号码
        String hex = WebNetEncode.encodeHexStr(8, smsContent);       // UTF-16BE
        hex = hex.trim() + "&codec=8";

        String msgid=util.sendPostMessage( user,  pwd,  ServiceID,  countryMobileCode + mobile,  sender,  hex);
        if(!msgid.equals("0")&&!msgid.contains("-")){
            return "1";
        }
        return "-1";

    }

}
