package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.mapper.NotificationMapper;
import com.yiyiglobal.dp.service.INotificationService;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public List<Notification> getSystemNotification(Integer id) {
        return notificationMapper.getSystemNotification(id);
    }

    @Override
    public List<Notification> getPersonalNotification(Integer id) {
        return notificationMapper.getPersonalNotification(id);
    }

    @Override
    public PageResult<Notification> getNotifications(PageEntity<Integer> pageEntity){
        PageResult<Notification> pageResult = new PageResult<>();
        List<Notification> notifications = notificationMapper.selectNotificationByPage(pageEntity);
        pageResult.setResultList(notifications);
        pageResult.setTotalSize(notificationMapper.selectNotificationNumByPage(pageEntity));
        notificationMapper.updateUserReadStatus(pageEntity.getParams());
        return pageResult;
    }
}
