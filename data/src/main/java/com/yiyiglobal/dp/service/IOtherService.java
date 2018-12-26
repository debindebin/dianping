package com.yiyiglobal.dp.service;


import com.yiyiglobal.dp.domain.Res;

import java.util.List;

/**
 * Created by wuyang on 2018/4/26
 */
public interface IOtherService {
    /**
     * 查询搜索热门标签
     * @return
     */
    List<String> getHotTags();
}
