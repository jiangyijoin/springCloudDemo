package com.chinaoly.frm.core.baseDao.mybatis;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 定制版MyBatis Mapper插件接口，扩展公司持久层的方法
 */
public interface Mapper<T>
        extends
        BaseMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        ExampleMapper<T>,
        InsertListMapper<T> {
}
