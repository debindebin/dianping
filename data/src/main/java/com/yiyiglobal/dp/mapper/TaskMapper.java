package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Task;
import com.yiyiglobal.dp.vo.task.TaskForListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    int insert(Task record);

    List<TaskForListVo> selectAll(@Param("prefix")String prefix);

    Task selectByPrimaryKey(Integer id);
}