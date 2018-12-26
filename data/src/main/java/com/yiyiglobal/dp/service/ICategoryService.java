package com.yiyiglobal.dp.service;

import com.yiyiglobal.dp.domain.Category;

import java.util.List;

/**
 * Created by wuyang on 2018/4/25
 */
public interface ICategoryService {
    List<Category> getTopCategory();

    List<Category> getSonCategory(Integer id);
}
