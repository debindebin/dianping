package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.domain.Category;
import com.yiyiglobal.dp.domain.Config;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.mapper.CategoryMapper;
import com.yiyiglobal.dp.mapper.ConfigMapper;
import com.yiyiglobal.dp.mapper.ResMapper;
import com.yiyiglobal.dp.service.IOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OtherServiceImpl implements IOtherService{
    @Autowired
    ConfigMapper configMapper;

    @Override
    public List<String> getHotTags() {
        Config config = configMapper.selectByKey(Cons.Config.HOT_TAGS);

        String tagArr[] = config.getCvalue().split("\\|");

        List<String> hotTags = new ArrayList<>();
        for(int i=0 ; i<tagArr.length; i++){
            hotTags.add(tagArr[i]);
        }

        return hotTags;
    }
}
