package com.yiyiglobal.dp.util.third;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wangzukun on 2018/4/25.
 */
@Service
public class WechatService {

    protected final Logger logger = Logger.getLogger("infoLog");

    /**
     * 获取jsapi的签名
     * @param url
     * @param jsApiTicket
     * @return
     */
    public Map<String, Object> getJsSign(String url, String jsApiTicket) {
        Map<String, Object> ret = new HashMap<String, Object>();

        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        //注意这里参数名必须全部小写，且必须有序
        String str = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;

        String signature = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e) {
            logger.error("Exception{}",e);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("Exception{}",e);
        }

        ret.put("url", url);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
