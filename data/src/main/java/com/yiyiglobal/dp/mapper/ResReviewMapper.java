package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.ResReview;
import com.yiyiglobal.dp.dto.HotResDto;
import com.yiyiglobal.dp.dto.review.ReviewForListDto;
import com.yiyiglobal.dp.util.PageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResReview record);

    ResReview selectByPrimaryKey(Integer id);

    List<ResReview> selectAll();

    int updateByPrimaryKey(ResReview record);

    List<ReviewForListDto> selectResReviewByPage(@Param("pageEntity")PageEntity<ResReview> pageEntity,@Param("loginUserId")Integer loginUserId,@Param("prefix")String prefix);

    Integer selectResReviewNumByPage(@Param("pageEntity")PageEntity<ResReview> pageEntity);

    List<HotResDto> selectTopRes(@Param("day")Integer day, @Param("prefix")String prefix, @Param("cityId")Integer cityId);

    Integer  addReviewNum(ResReview record);
}