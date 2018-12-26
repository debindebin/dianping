package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List<Advertisement>  selectAdvertisementByType(Integer type,String imagePrefix);
}