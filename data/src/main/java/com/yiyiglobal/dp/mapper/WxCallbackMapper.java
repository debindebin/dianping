package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.WxCallback;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WxCallbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxCallback record);

    WxCallback selectByPrimaryKey(Integer id);

    List<WxCallback> selectAll();

    int updateByPrimaryKey(WxCallback record);
}