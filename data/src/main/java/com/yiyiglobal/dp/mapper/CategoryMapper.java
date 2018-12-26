package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    Category selectByPrimaryKey(Integer id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    List<Category> getTopCategory();

    List<Category> getSonCategory(Integer id);
}