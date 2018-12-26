package com.yiyiglobal.dp.mapper;


import com.yiyiglobal.dp.domain.CoinSuply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CoinSuplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoinSuply record);

    int insertSelective(CoinSuply record);

    CoinSuply selectByPrimaryKey(Integer id);

    CoinSuply selectByDate(@Param("date")String date) ;

    int updateByPrimaryKeySelective(CoinSuply record);

    int updateByPrimaryKey(CoinSuply record);
}