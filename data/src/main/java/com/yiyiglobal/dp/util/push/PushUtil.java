package com.yiyiglobal.dp.util.push;

import com.alibaba.fastjson.JSONObject;
import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Payload;
import com.dbay.apns4j.model.PushNotification;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.gson.JsonObject;
import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.mapper.NotificationMapper;
import com.yiyiglobal.dp.mapper.UserMapper;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

/**
 * 这个类确实有问题.需要改动.
 */
@Component
public class PushUtil {
	private static final Logger logger = LoggerFactory.getLogger(PushUtil.class);

	@Value("${getui_appId}")
	public static String appId;
	@Value("${getui_appkey}")
	public static String appkey;
	@Value("${getui_master}")
	public static String master;
	@Value("${getui_host}")
	public static String host;
	@Value("${ios_push_env}")
	public static String IosEnv;
	@Value("${ios_push_pwd}")
	public static String IosPwd;
	@Value("${ios.push.cert}")
	public static String IosCert;
	@Value("${qiniu.image.prefix}")
	private String prefix;

	@Autowired
	private ObjectRedisService objectRedisService;


	private static ExecutorService executorService = new ThreadPoolExecutor(10, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(300) ,new RejectedExecutionHandler() {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			logger.error("PushUtil msg is too many ,discard it.");
		}
	});

	private volatile static IApnsService apnsService;

    private static IApnsService getApnsService() {
		if (apnsService == null) {
			synchronized (IApnsService.class) {
				if ( apnsService == null ) {
					ApnsConfig config = new ApnsConfig();
					InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(IosCert);
					config.setKeyStore(is);
					String ios_push_env =IosEnv;
					config.setDevEnv(ios_push_env.equals("0")?true:false);
					config.setPassword(IosPwd);
					config.setPoolSize(50);
					// 假如需要在同个java进程里给不同APP发送通知，那就需要设置为不同的name
//			config.setName("welove1");
					apnsService = ApnsServiceImpl.createInstance(config);
				}
			}
		}
		return apnsService;
	}

	/**
	 * 方法抽取
	 * @param fromUser
	 * @param content
	 * @param redirectAddr
	 * @param notificationId
	 * @param userMapper
	 * @param notificationMapper
     */
	public void push(final User fromUser, final String content, final Integer redirectAddr, final Integer notificationId, final UserMapper userMapper, final NotificationMapper notificationMapper, final Integer notificationType) {
		try {
			executorService.execute(new Runnable() {
				@Override
				public void run() {

					//step2 - 推送通知
					//0无，1约豆商城，2我的账户，3我的，4钱包，5预约详情，6粉丝，7技能详情，8邀约详情，9我的技能，10html5页面
					String clientID = fromUser.getClientId();
					Integer deviceType = fromUser.getDeviceType();
					Map<String,Object> map = (Map<String,Object>)objectRedisService.get(RedisCons.USER_+fromUser.getId());
					if (deviceType != null && content!=null) {
						if (deviceType == 1) {
							if (map != null) {
								//要推送的用户处于登陆状态，直接推送
								Integer unReadNotificationNum = notificationMapper.getUnReadNotificationNum(fromUser.getId());
								pushToIOSClient(clientID, content, notificationType, String.valueOf(redirectAddr), fromUser.getId(), notificationId, fromUser.getBundleIdentifier(), unReadNotificationNum);
							} else {
								//要推送的用户处于退出状态，将推送消息保存到数据库中，等登陆的时候再次推送
								IOSDelayPush delayPush = new IOSDelayPush();
								delayPush.setNotificationId(notificationId);
								delayPush.setContent(content);
								delayPush.setDeviceToken(clientID);
								delayPush.setType(notificationType);
								delayPush.setRedirectAddr(redirectAddr.toString());
								delayPush.setUserId(fromUser.getId());
								userMapper.addIOSDelyPush(delayPush);
							}
						}
						if (deviceType == 2) {
							//Android
							pushToUser(fromUser.getId(), content, notificationType,
									String.valueOf(redirectAddr), notificationMapper.getUnReadNotificationNum(fromUser.getId()), notificationId);
						}
					}


				}
			});

		} catch (Exception e) {
			logger.error("PushUtil pushInfoQueue error ",e);
		}
	}
	public void pushTransmission(final User toUser, final String content, final Map<String, Object> data, final Integer type, final boolean needNotify, final String redirectAddr, final NotificationMapper notificationMapper, final UserMapper userMapper) {
		try {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					String clientID = toUser.getClientId();
					Integer deviceType = toUser.getDeviceType();
//					Map map = MemSession.getSession("u" + toUser.getId(), false, "accessToken").getMap();
					if (deviceType != null) {
						if (deviceType == 1&&needNotify) {
							String subContent=content;
							if(content.length()>30){
								subContent=content.substring(0,29)+">";
							}
//							if (map != null) {
								// 通知入库

								Integer notificationId = addNotification(toUser.getId(), type, content, redirectAddr,notificationMapper);
								System.out.println("IOS  "+ Thread.currentThread().getName()+" 向 "+toUser.getId()+" 添加通知： "+notificationId+" "+content);
								data.put("redirectAddr", redirectAddr);
								data.put("notificationId", notificationId);
								data.put("userId", toUser.getId());
								data.put("message", null);
								data.remove("message");
//								//要推送的用户处于登陆状态，直接推送
								if(clientID!=null){
									pushTransmissionToIOSClient(clientID,subContent,type, data);
								}
//								pushToIOSClient1(clientID, content, type, String.valueOf(redirectAddr), toUser.getId(), notificationId, null, data);
//							}else{
								// 通知入库
//								Integer notificationId = addNotification(toUser.getId(), ConstantUtil.NotificationType.ASK_DETAIL, content, redirectAddr,notificationMapper);
								// 要推送的用户处于退出状态，将推送消息保存到数据库中，等登陆的时候再次推送
//								IOSDelayPush delayPush = new IOSDelayPush();
//								delayPush.setNotificationId(notificationId);
//								delayPush.setContent(subContent);
//								delayPush.setDeviceToken(clientID);
//								delayPush.setType(type);
//								delayPush.setRedirectAddr(redirectAddr);
//								userMapper.addIOSDelyPush(delayPush);
//							}
						}else
						if (deviceType == 2) {
							//Android
							data.remove("messageId");
							pushTransmissionToAndroidClient(data, toUser.getId());
							if(needNotify){
								// 通知入库

								Integer notificationId = addNotification(toUser.getId(), type, content, redirectAddr,notificationMapper);
								System.out.println("andriod  "+ Thread.currentThread().getName()+" 向 "+toUser.getId()+" 添加通知： "+notificationId+" "+content);
								String title= NotificationTypeEnum.getTitle(type);
								String logo = prefix+"upload/logo.png";
								JsonObject jsonNotification=new JsonObject();
								jsonNotification.addProperty("type", type);
								jsonNotification.addProperty("redirectAddr", redirectAddr);
								jsonNotification.addProperty("id", notificationId);
								pushNotificationTemplate(title,content,logo,jsonNotification.toString(),toUser.getId());
							}
						}
					}


				}
			});

		} catch (Exception e) {
			logger.error("PushUtil pushInfoQueue error ",e);
		}
	}

	private Integer addNotification(Integer userId, Integer type, String content, String redirectAddr, final NotificationMapper notificationMapper1) {
		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setTitle(NotificationTypeEnum.getTitle(type));
		notification.setType(type);
		notification.setReaded(Cons.NotificationStatus.UNREAD);
		notification.setContent(content);
		notification.setRedirectAddr(redirectAddr);
		notification.setCreTime(new Date());
		notification.setDelFlag(0);
		notificationMapper1.insert(notification);
		return notification.getId();
	}
	/**
	 * 这个方法写的太过了...
	 * @param deviceToken
	 * @param content
	 * @param type
	 * @param redirectAddr
	 * @param userId
	 * @param notificationId
	 * @param bundleIdentifier
     * @param unReadNotificationNum
     */
    public void pushToIOSClient(String deviceToken, String content, Integer type, String redirectAddr, Integer userId,
                                Integer notificationId, String bundleIdentifier, Integer unReadNotificationNum){
    	IApnsService service = getApnsService();

		Queue<PushNotification> queue = new LinkedBlockingQueue<PushNotification>();

		List<User> userList = new ArrayList<User>();

		User user = new User();
		user.setClientId(deviceToken);
		userList.add(user);

		Payload payload = new Payload();
		payload.setAlert(content);
		payload.setBadge(1);
		payload.setSound("msg.mp3");
		payload.addParam("type", type);
		payload.addParam("redirectAddr", redirectAddr);
		payload.addParam("userId", userId);
		payload.addParam("notificationId", notificationId);

		try {
			if (userList != null && userList.size() > 0) {
				for (User u : userList) {
					PushNotification notification = new PushNotification();
					notification.setToken(u.getClientId());
					notification.setPayload(payload);
					queue.add(notification);
				}

				if (service != null) {
					while (!queue.isEmpty()) {
						service.sendNotification(queue.poll());
					}
				}
			}
			service.getFeedbacks();
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.toString());
			e.printStackTrace();
			logger.error( user != null ? user.getMobile() : "null" + "推送异常");
		}
    }

	public void pushToIOSClient1(String deviceToken, String content, Integer type, String redirectAddr, Integer userId,
                                 Integer notificationId, String bundleIdentifier, Map<String, Object> data) {
		IApnsService service = getApnsService();

		Queue<PushNotification> queue = new LinkedBlockingQueue<PushNotification>();

		List<User> userList = new ArrayList<User>();

		User user = new User();
		user.setClientId(deviceToken);
		userList.add(user);

		Payload payload = new Payload();
		payload.setAlert(content);
		payload.setBadge(1);
		payload.setSound("msg.mp3");
		payload.addParam("type", type);
		payload.addParam("redirectAddr", redirectAddr);
		payload.addParam("userId", userId);
		payload.addParam("notificationId", notificationId);
		for (String key : data.keySet()) {
			payload.addParam(key, data.get(key));
		}

		try {
			if (userList != null && userList.size() > 0) {
				for (User u : userList) {
					PushNotification notification = new PushNotification();
					notification.setToken(u.getClientId());
					notification.setPayload(payload);
					queue.add(notification);
				}

				if (service != null) {
					while (!queue.isEmpty()) {
						service.sendNotification(queue.poll());
					}
				}
			}
			service.getFeedbacks();
		} catch (Exception e) {
			logger.error(user.getMobile() + "推送异常");
		}
	}

	public void pushTransmissionToIOSClient(String deviceToken, String content, Integer type,
                                            Map<String, Object> data) {
		IApnsService service = getApnsService();

		Queue<PushNotification> queue = new LinkedBlockingQueue<PushNotification>();

        List<User> userList = new ArrayList<User>();

		User user = new User();
		user.setClientId(deviceToken);
		userList.add(user);

		Payload payload = new Payload();
		payload.setAlert(content);
		payload.setBadge(1);
		payload.setSound("msg.mp3");
		payload.addParam("type", type);
		for (String key : data.keySet()) {
			payload.addParam(key, data.get(key));
		}

		try {
			if (userList != null && userList.size() > 0) {
				for (User u : userList) {
					PushNotification notification = new PushNotification();
					notification.setToken(deviceToken);
					notification.setPayload(payload);
					queue.add(notification);
				}

				if (service != null) {
					while (!queue.isEmpty()) {
						service.sendNotification(queue.poll());
					}
				}
			}
			service.getFeedbacks();
		} catch (Exception e) {
			System.out.println(e.getMessage()+e.toString());
			e.printStackTrace();
			logger.error(deviceToken + "推送异常");
		}
	}
	public void pushTransmissionToAndroidClient(Map<String, Object> data, Integer alias){
		IGtPush push = new IGtPush(host, appkey, master);

		TransmissionTemplate template = transmissionTemplate(data);

		SingleMessage message = new SingleMessage();
	    message.setOffline(true);

	    //离线有效时间，单位为毫秒，可选
	    message.setOfflineExpireTime(24 * 3600 * 1000);
	    message.setData(template);
	    message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
	    Target target = new Target();

	    target.setAppId(appId);
	    //target.setClientId(clientID);
	    //用户别名推送，cid和用户别名只能2者选其一
	    //String alias = "个";
	    target.setAlias(alias+"");
	    IPushResult ret = null;
	    try{
	    	ret = push.pushMessageToSingle(message, target);
	    }catch(RequestException e){
	    	logger.info(e.getMessage());
	        try{
	        	ret = push.pushMessageToSingle(message, target, e.getRequestId());
	        }catch(Exception e2){
	        	logger.info(e2.getMessage());
	        }
	    }
	    if(ret != null){
	        System.out.println(ret.getResponse().toString());
	    }else{
	        System.out.println("服务器响应异常");
	    }
	}
	public TransmissionTemplate transmissionTemplate(Map<String, Object> data) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);

//	    JsonObject jsonTransmission=new JsonObject();
//	    for (String key : data.keySet()) {
//			jsonTransmission.addProperty(key,data.get(key).toString());
//		}
	    template.setTransmissionContent(new JSONObject().toJSONString(data));
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	public void pushToUser(Integer alias, String content, int type, String redirectAddr, int unReadNotificationNum, int notificationId){


		String title= NotificationTypeEnum.getTitle(type);
		String logo = prefix+"upload/logo.png";
		//String transmissionContent = "{\"type\":\""+type+"\",\"redirectAddr\":\""+redirectAddr+"\"}";

		JsonObject jsonNotification=new JsonObject();
		jsonNotification.addProperty("type", type);
		jsonNotification.addProperty("redirectAddr", redirectAddr);
		jsonNotification.addProperty("id", notificationId);

		try {
			pushNotificationTemplate(title,content,logo,jsonNotification.toString(),alias);
			pushTransmissionTemplate(unReadNotificationNum,alias);
		} catch (Exception e) {
			logger.error(notificationId + "数据推送异常");
		}
	}

	public void pushNotificationTemplate(String title, String content, String logo, String transmissionContent, Integer alias){
		IGtPush push = new IGtPush(host, appkey, master);
		//NotificationTemplate template = notificationTemplate(title,content,logo,transmissionContent);
		NotificationTemplate template = notificationTemplateDemo(title,content,logo,transmissionContent);
		SingleMessage message = new SingleMessage();
	    message.setOffline(true);

	    //离线有效时间，单位为毫秒，可选
	    message.setOfflineExpireTime(24 * 3600 * 1000);
	    message.setData(template);
	    message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
	    Target target = new Target();

	    target.setAppId(appId);
	    //target.setClientId(clientID);
	    //用户别名推送，cid和用户别名只能2者选其一
	    //String alias = "个";
	    target.setAlias(alias+"");
	    IPushResult ret = null;
	    try{
	    	ret = push.pushMessageToSingle(message, target);
	    }catch(RequestException e){
	    	logger.info(e.getMessage());
	        try{
	        	ret = push.pushMessageToSingle(message, target, e.getRequestId());
	        }catch(Exception e2){
	        	logger.info(e2.getMessage());
	        }
	    }
	    if(ret != null){
	        System.out.println(ret.getResponse().toString());
	    }else{
	        System.out.println("服务器响应异常");
	    }
	}

	public void pushTransmissionTemplate(int unReadNotificationNum,Integer alias){
		IGtPush push = new IGtPush(host, appkey, master);

		TransmissionTemplate template = transmissionTemplate(unReadNotificationNum);

		SingleMessage message = new SingleMessage();
	    message.setOffline(true);

	    //离线有效时间，单位为毫秒，可选
	    message.setOfflineExpireTime(24 * 3600 * 1000);
	    message.setData(template);
	    message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
	    Target target = new Target();

	    target.setAppId(appId);
	    //target.setClientId(clientID);
	    //用户别名推送，cid和用户别名只能2者选其一
	    //String alias = "个";
	    target.setAlias(alias+"");
	    IPushResult ret = null;
	    try{
	    	ret = push.pushMessageToSingle(message, target);
	    }catch(RequestException e){
	    	logger.info(e.getMessage());
	        try{
	        	ret = push.pushMessageToSingle(message, target, e.getRequestId());
	        }catch(Exception e2){
	        	logger.info(e2.getMessage());
	        }
	    }
	    if(ret != null){
	        System.out.println(ret.getResponse().toString());
	    }else{
	        System.out.println("服务器响应异常");
	    }
	}

	public LinkTemplate linkTemplate(String title, String content, String logo, String url) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("push.png");
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl(logo);
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl(url);

        return template;
	}

	public NotificationTemplate notificationTemplate(String title, String content, String logo, String transmissionContent) {
	    NotificationTemplate template = new NotificationTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 设置通知栏标题与内容
	    template.setTitle(title);
	    template.setText(content);
	    // 配置通知栏图标
	    template.setLogo("push.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl(logo);
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent(transmissionContent);
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}

	public TransmissionTemplate transmissionTemplate(int unReadNotificationNum) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appkey);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);

	    JsonObject jsonTransmission=new JsonObject();
		jsonTransmission.addProperty("unReadNotificationNum", unReadNotificationNum);

	    template.setTransmissionContent(jsonTransmission.toString());
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	public NotificationTemplate notificationTemplateDemo(String title, String content, String logo, String transmissionContent) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(transmissionContent);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        // 配置通知栏图标
        style.setLogo("push.png");
        // 配置通知栏网络图标
        style.setLogoUrl(logo);
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        return template;
    }
	public void bindAlias(Integer userId, String clientId) {
		 IGtPush push = new IGtPush(host, appkey, master);
	     IAliasResult bindSCid = push.bindAlias(appId, userId+"", clientId);
	     System.out.println("绑定结果：" + bindSCid.getResult() + "错误码:" + bindSCid.getErrorMsg());
	}
}
