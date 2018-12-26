package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.user.InviteUserDto;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.push.IOSDelayPush;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    int addCoin(@Param("id")Integer id , @Param("addCoinSum")BigDecimal addCoinSum, @Param("contributionNum")Integer contributionNum);

    @Select("SELECT * FROM `user` WHERE mobile = #{mobile}")
    @ResultMap("com.yiyiglobal.dp.mapper.UserMapper.BaseResultMap")
    User getUserByMobile(@Param("mobile") String mobile);

    @Select("SELECT * FROM `user` WHERE self_invite_code = #{selfInviteCode}")
    @ResultMap("com.yiyiglobal.dp.mapper.UserMapper.BaseResultMap")
    User getUserBySelfInviteCode(@Param("selfInviteCode") String selfInviteCode);

    @Select("SELECT nickname FROM `user` WHERE nickname LIKE concat(concat('%',#{nickname}),'%')")
    List<String> getUserByNickname(@Param("nickname") String nickname);


    List<InviteUserDto> getUsersByInviteCode(@Param("inviteCode") String inviteCode);


    List<User> selectTokenRanks(PageEntity pageEntity);

    Integer selectTokenRankNum(PageEntity pageEntity);

    public void addIOSDelyPush(IOSDelayPush delayPush);

}