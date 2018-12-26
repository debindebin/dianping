package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.ResReview;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.vo.res.ResForListVo;
import com.yiyiglobal.dp.vo.res.ResForSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Res record);

    Res selectByPrimaryKey(@Param("id")Integer id, @Param("prefix")String prefix);

    List<Res> selectAll();

    int updateByPrimaryKey(Res record);

    int updateReviewNum(@Param("id")Integer id, @Param("num")Integer num,@Param("score")Integer score);

    List<Res> getHotTags();

    List<ResForListVo> selectResByPage(@Param("pageEntity")PageEntity<ResForSearchVo> pageEntity, @Param("prefix")String prefix);

    Integer selectResNumByPage(@Param("pageEntity")PageEntity<ResForSearchVo> pageEntity);
}