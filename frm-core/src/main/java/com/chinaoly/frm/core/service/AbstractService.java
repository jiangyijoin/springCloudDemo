package com.chinaoly.frm.core.service;


import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.core.entity.ResultCode;
import com.chinaoly.frm.core.utils.ReflectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public class AbstractService<T> extends BaseService implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        mapper.insertSelective(model);
    }

    public void save(List<T> models) {
        mapper.insertList(models);
    }

    public void deleteById(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }

    public PageInfo<T> findPage(int page,int size) {
        PageHelper.startPage(page, size);
        List<T> list = mapper.selectAll();
        return new PageInfo(list);
    }

    public PageInfo<T> findPage(T t, PageInfo basePage, String orderField, String seType, String... likeFields) {
        if (basePage == null) {
            basePage = new PageInfo();
        }
        Example example = null ;
        try {
            example = resolveExample(t,orderField,seType, likeFields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (basePage.getList()==null) {
            long count = mapper.selectCountByExample(example);
            if (count == 0L)
                return new PageInfo<T>();
            basePage.setTotal(Integer.parseInt(String.valueOf(count)));

        }
        PageHelper.startPage(basePage.getPageNum(), basePage.getPageSize());
        List<T> list = mapper.selectByExample(example);

        PageInfo<T> info = new PageInfo<T>(list);
        return info;
    }

    private Example resolveExample(T t, String orderField, String seType, String... likeFields)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<T> clazz = (Class<T>) ReflectUtil.getGenericInterfaces(this.getClass(), 0);
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        Field[] fields = ReflectUtil.findFields(clazz);
        Set<String> likeFieldsCol = null;
        if (likeFields!=null && likeFields.length > 0) {
            likeFieldsCol = new HashSet<>(Arrays.asList(likeFields));
            for (Field field : Arrays.asList(fields)) {
                String name = field.getName();
                Object val = ReflectUtil.getReadMethod(clazz, name).invoke(t);
                if (val != null) {
                    if (likeFieldsCol.contains(name)) {
                        criteria.andLike(name, "%" + val.toString() + "%");
                    } else {
                        criteria.andEqualTo(name, val.toString());
                    }
                }
            }
        }

        if(orderField!=null && !"".equals(orderField)) {
            if("desc".equals(seType.toLowerCase())) {
                example.orderBy(orderField).desc();
            } else {
                example.orderBy(orderField).asc();
            }
        }
        return example;
    }
}
