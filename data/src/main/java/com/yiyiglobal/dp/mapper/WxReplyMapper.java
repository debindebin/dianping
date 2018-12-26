package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.WxReply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WxReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxReply record);

    WxReply selectByPrimaryKey(Integer id);

    List<WxReply> selectAll();

    int updateByPrimaryKey(WxReply record);
}