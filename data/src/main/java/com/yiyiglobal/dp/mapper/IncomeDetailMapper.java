package com.yiyiglobal.dp.mapper;

import com.yiyiglobal.dp.domain.IncomeDetail;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.HomeIncomeDto;
import com.yiyiglobal.dp.dto.wallet.ContributionDto;
import com.yiyiglobal.dp.util.PageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface IncomeDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeDetail record);

    IncomeDetail selectByPrimaryKey(Integer id);

    List<IncomeDetail> selectAll();

    int updateByPrimaryKey(IncomeDetail record);

    List<IncomeDetail> selectIncomeDetailList(PageEntity<IncomeDetail> pageEntity);

    Integer selectIncomeDetailNum(PageEntity<IncomeDetail> pageEntity);

    HomeIncomeDto getHomeIncomeData(Integer  userId);

    List<ContributionDto> getContributionByDate(@Param("date") String date);

    BigDecimal getContributionSum(@Param("date") String date);
}