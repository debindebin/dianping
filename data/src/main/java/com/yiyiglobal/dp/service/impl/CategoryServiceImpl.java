package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.domain.Category;
import com.yiyiglobal.dp.mapper.CategoryMapper;
import com.yiyiglobal.dp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public List<Category> getTopCategory() {
        return categoryMapper.getTopCategory();
    }

    @Override
    public List<Category> getSonCategory(Integer id) {
        return categoryMapper.getSonCategory(id);
    }
}
