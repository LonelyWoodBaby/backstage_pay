package com.pay.database.mybatis.config;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

public interface InsertUidListMapper<T> {
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);
}
