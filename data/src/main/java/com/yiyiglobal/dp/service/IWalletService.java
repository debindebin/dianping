package com.yiyiglobal.dp.service;


import com.yiyiglobal.dp.domain.IncomeDetail;
import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.HomeIncomeDto;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IWalletService {

    Map<String,Object> getTokenRanks(PageEntity pageEntity);


    PageResult<IncomeDetail> getMyIncomeDetailList(PageEntity<IncomeDetail> pageEntity);

    void  addContributionByTask(Integer taskId,Integer userId,Notification notification);

    void  SettlementContribution2Coin();



}
