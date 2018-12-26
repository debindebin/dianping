package com.yiyiglobal.dp.service;


import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.dto.HotResDto;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.vo.res.ResForListVo;
import com.yiyiglobal.dp.vo.res.ResForSearchVo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

public interface IResService {

    PageResult<ResForListVo> getResList(PageEntity<ResForSearchVo> pageEntity);

    Res  getResDetail(Integer id);

    List<String>  getContacts(Integer id);

    @Cacheable(value = "homeHotRes", key = "#cityId", unless = "#result==null or #result.size()<=0")
    Set<HotResDto> getHotResFromDB(Integer cityId);
}
