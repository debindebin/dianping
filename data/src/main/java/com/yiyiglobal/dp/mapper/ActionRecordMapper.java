package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.ActionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActionRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActionRecord record);

    ActionRecord selectByPrimaryKey(Integer id);

    List<ActionRecord> selectAll();

    int updateByPrimaryKey(ActionRecord record);
}