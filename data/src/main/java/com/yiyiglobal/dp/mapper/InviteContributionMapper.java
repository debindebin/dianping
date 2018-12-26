package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.InviteContribution;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InviteContributionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InviteContribution record);

    InviteContribution selectByPrimaryKey(Integer id);

    List<InviteContribution> selectAll();

    int updateByPrimaryKey(InviteContribution record);

    InviteContribution selectLastOne(Integer fromUserId,Integer toUserId);
}