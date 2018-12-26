package com.yiyiglobal.dp.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yiyiglobal.dp.domain.Material;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InviteUserDto implements Serializable{

    private Integer id;

    private Integer sumValue;



    private String nickname;


    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSumValue() {
        return sumValue;
    }

    public void setSumValue(Integer sumValue) {
        this.sumValue = sumValue;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}