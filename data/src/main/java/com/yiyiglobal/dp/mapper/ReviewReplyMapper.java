package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.ReviewReply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReviewReply record);

    int insertSelective(ReviewReply record);

    ReviewReply selectByPrimaryKey(Integer id);

    List<ReviewReply> selectAll();

    int updateByPrimaryKey(ReviewReply record);

    int updateByPrimaryKeySelective(ReviewReply record);

    ReviewReply selectBySuppot(ReviewReply reply);
}