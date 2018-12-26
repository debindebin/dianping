package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    Config selectByPrimaryKey(Integer id);

    List<Config> selectAll();

    int updateByPrimaryKey(Config record);

    @Select("SELECT * FROM `config` WHERE ckey = #{ckey}")
    Config selectByKey(@Param("ckey") String ckey);
}