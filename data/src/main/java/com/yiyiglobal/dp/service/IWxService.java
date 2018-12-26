package com.yiyiglobal.dp.service;

import weixin.popular.bean.message.EventMessage;

public interface IWxService {

    public String getAccessToken();
    
    public String getJsApiTicket(String accessToken);

    public void initMenu();

    public void dealCallcack(EventMessage eventMessage);
    
}
