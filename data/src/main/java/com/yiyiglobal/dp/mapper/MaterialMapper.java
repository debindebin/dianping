package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Material;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Material record);

    Material selectByPrimaryKey(Integer id);

    List<Material> selectAll();

    int updateByPrimaryKey(Material record);
}