package com.yiyiglobal.dp.service;

import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.user.InviteUserDto;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

public interface IUserService {

    public User getUserByAccessToken(String accessToken);

    public List<InviteUserDto> getInviteUsers(String inviteCode);

    public User getUserById(int id);

    public String getNickname(String nickname);

    public void update(User user);



    public User getUserByMobile(String mobile);

    public User register(User user);





    public Map<String, Object> generateAccessToken(User user, String deviceId);

    public String validateCode(String mobile , String countryMobileCode, String verifyCode);

    public void updateToken(User user);

}
