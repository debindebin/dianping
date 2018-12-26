package com.yiyiglobal.dp.service;

import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;

import java.util.List;
/**
 * Created by wuyang on 2018/4/26
 */
public interface INotificationService {
    /**
     * 获取系统通知
     * @param id
     * @return
     */
    List<Notification> getSystemNotification(Integer id);
    /**
     * 获取用户其他行为通知
     * @param id
     * @return
     */
    List<Notification> getPersonalNotification(Integer id);

    PageResult<Notification> getNotifications(PageEntity<Integer> pageEntity);
}
