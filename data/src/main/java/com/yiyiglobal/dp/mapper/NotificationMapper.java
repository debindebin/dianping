package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.util.PageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notification record);

    Notification selectByPrimaryKey(Integer id);

    List<Notification> selectAll();

    int updateByPrimaryKey(Notification record);

    List<Notification> getSystemNotification(Integer id);

    List<Notification> getPersonalNotification(Integer id);

    List<Notification> selectNotificationByPage(PageEntity<Integer> pageEntity);

    Integer selectNotificationNumByPage(PageEntity<Integer> pageEntity);

    /**
     * 将用户未读的通知置为已读
     * @param userId
     */
    void updateUserReadStatus(Integer userId);

    /**
     * 获取用户未读的总记录数
     * @param userId
     * @return
     */
    public Integer getUnReadNotificationNum(Integer userId);
}