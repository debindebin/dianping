package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.service.IWxService;
import com.yiyiglobal.dp.util.third.WechatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping(value="/wx")
public class WxController extends BaseController{

    @Autowired
    private IWxService wxService;

    @Autowired
    private WechatService wechatService;

    //从官方获取
    private String token = "wzk871221";

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @ApiOperation(value="获取微信签名授权", notes="")
    @RequestMapping(value="/generateSign", method=RequestMethod.POST)
    public Map<String, Object> generateSign(@RequestParam String url) throws Exception {

        //获取accessToken
        String accessToken = wxService.getAccessToken();

        //获取JsApiTicket
        String jsApiTicket = wxService.getJsApiTicket(accessToken);

        Map<String, Object> map = wechatService.getJsSign(url,jsApiTicket);

        return success(map);
    }

    @ApiOperation(value="初始化微信参数", notes="")
    @RequestMapping(value="/init", method=RequestMethod.POST)
    public void init() throws Exception {

        //初始化菜单
        wxService.initMenu();
    }

    @ApiOperation(value="微信回调", notes="")
    @RequestMapping(value="/callback", method=RequestMethod.POST)
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("callback:"+request.getParameterMap());

        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        String signature = request.getParameter("signature");		// 微信加密签名
        String timestamp = request.getParameter("timestamp");		// 时间戳
        String nonce = request.getParameter("nonce");				// 随机数
        String echostr = request.getParameter("echostr");			// 随机字符串

        //首次请求申请验证,返回echostr
        if(echostr!=null){
            outputStreamWrite(outputStream,echostr);
            return;
        }

        //验证请求签名
        if(!signature.equals(SignatureUtil.generateEventMessageSignature(token,timestamp,nonce))){
            System.out.println("The request signature is invalid");
            return;
        }

        if(inputStream!=null){
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,inputStream);

            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();

            System.out.println("key:"+key);

            if(expireKey.exists(key)){
                //重复通知不作处理
                return;
            }else{
                expireKey.add(key);

                //处理微信回调
                wxService.dealCallcack(eventMessage);

            }
            return;
        }

        outputStreamWrite(outputStream,"");
    }

    /**
     * 数据流输出
     * @param outputStream
     * @param text
     * @return
     */
    private boolean outputStreamWrite(OutputStream outputStream, String text){
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
