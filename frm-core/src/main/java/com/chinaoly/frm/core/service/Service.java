package com.chinaoly.frm.core.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.data.domain.Page;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
    /**
     * 保存对象
     *
     * @param T 要持久化的对象
     */
    void save(T model);//持久化

    /**
     * 保存对象集合
     *
     * @param List<T> 要持久化的对象列表
     */
    void save(List<T> models);//批量持久化

    /**
     * 删除对象
     *
     * @param id 通过主鍵刪除
     */
    void deleteById(String id);//通过主鍵刪除

    /**
     * 批量刪除
     *
     * @param ids 批量刪除 eg：ids -> '1','2','3'
     */
    void deleteByIds(String ids);//批量刪除 eg：ids -> '1','2','3'

    /**
     * 修改对象
     *
     * @param T 要修改的对象
     */
    void update(T model);//更新

    /**
     * 修改对象
     *
     * @param id 通过ID查找
     * @return T
     */
    T findById(String id);//通过ID查找

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     *
     * @param fieldName
     * @param value
     * @return T
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException; //通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束

    /**
     * 通过多个ID查找
     *
     * @param ids ids -> '1','2','3'
     * @return List<T>
     */
    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> '1','2','3'

    /**
     * 根据条件查找
     *
     * @param condition
     * @return List<T>
     */
    List<T> findByCondition(Condition condition);//根据条件查找

    /**
     * 获取所有
     *
     * @return List<T>
     */
    List<T> findAll();//获取所有

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return PageInfo<T>
     */
    PageInfo<T> findPage(int page, int size);//分页查询

    /**
     * 根据调传入条件查询List，可进行排序，可选顺序
     *
     * @param t          带查询条件的查询类
     * @param basePage   分页参数
     * @param orderField 进行排序的字段
     * @param seType     排序类型：desc降序，asc升序,默认升序
     * @param likeFields 使用like条件查询的属性值
     * @return List<T> 查询结果列表
     */
    PageInfo<T> findPage(T t, PageInfo pageInfo, String orderField, String seType, String... likeFields);

}
