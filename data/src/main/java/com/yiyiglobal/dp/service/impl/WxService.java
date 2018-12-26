package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.domain.Config;
import com.yiyiglobal.dp.domain.WxCallback;
import com.yiyiglobal.dp.mapper.ConfigMapper;
import com.yiyiglobal.dp.mapper.WxCallbackMapper;
import com.yiyiglobal.dp.service.IWxService;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.redis.service.StringRedisService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import weixin.popular.api.MenuAPI;
import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.token.Token;

import java.lang.reflect.InvocationTargetException;

@Service
public class WxService implements IWxService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${wx.mp.appID}")
    private String wxMpAppId;

    @Value("${wx.mp.appSecret}")
    private String wxMpAppSecret;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private WxCallbackMapper wxCallbackMapper;

    /**
     * 获取微信accessToken
     * @return
     */
    public String getAccessToken(){
        String accessToken = stringRedisService.get(RedisCons.WX_ACCESS_TOKEN);
        if(ValidateUtil.isEmpty(accessToken)) {
            Token token = TokenAPI.token(wxMpAppId,wxMpAppSecret);
            accessToken = token.getAccess_token();
            stringRedisService.set(RedisCons.WX_ACCESS_TOKEN,accessToken, 7000l);
        }

        return accessToken;
    }

    public String getJsApiTicket(String accessToken){
        String jsApiTicket = stringRedisService.get(RedisCons.WX_JS_API_TICKET);
        if(ValidateUtil.isEmpty(jsApiTicket)){
            weixin.popular.bean.ticket.Ticket ticket = TicketAPI.ticketGetticket(accessToken);
            jsApiTicket = ticket.getTicket();
            stringRedisService.set(RedisCons.WX_JS_API_TICKET,jsApiTicket,7000l);
        }
        return jsApiTicket;
    }

    public void initMenu(){
        Config config = configMapper.selectByKey(Cons.Config.WX_MENU);
        //配置错误
        if(config == null) {

        }

        String access_token = this.getAccessToken();
		MenuAPI.menuCreate(access_token,config.getCvalue());

    }

    public void dealCallcack(EventMessage eventMessage){

        //回调信息入库
        WxCallback wxCallback = new WxCallback();
        try {
            BeanUtils.copyProperties(wxCallback,eventMessage);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        wxCallbackMapper.insert(wxCallback);

        //订阅
        if("event".equals(eventMessage.getMsgType()) && "subscribe".equals(eventMessage.getEvent())) {
            System.out.println("用户订阅啦！");
            //
        }

        //取消订阅
        if("event".equals(eventMessage.getMsgType()) && "unsubscribe".equals(eventMessage.getEvent())) {
            System.out.println("用户取消订阅啦！");
            //
        }

        //获取位置
        if("event".equals(eventMessage.getMsgType()) && "LOCATION".equals(eventMessage.getEvent())) {
            System.out.println("用户获取位置啦！");
            //
        }

        //点击菜单
        if("event".equals(eventMessage.getMsgType()) && "VIEW".equals(eventMessage.getEvent())) {
            System.out.println("用户点击菜单啦！");
            //
        }

        //扫描二维码关注公众号，带场景id
        if("event".equals(eventMessage.getMsgType()) && "SCAN".equals(eventMessage.getEvent())) {
            System.out.println("用户扫描活动的二维码啦！");
            //
        }

    }


}
