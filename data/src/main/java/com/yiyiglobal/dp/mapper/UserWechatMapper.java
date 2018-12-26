package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.UserWechat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserWechatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWechat record);

    UserWechat selectByPrimaryKey(Integer id);

    List<UserWechat> selectAll();

    int updateByPrimaryKey(UserWechat record);

    @Select("SELECT * FROM `user_wechat` WHERE openid = #{openId}")
    UserWechat selectByOpenId(@Param("openId") String openId);
}