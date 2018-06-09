package com.chinaoly.frm.security.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.Dict;
import com.chinaoly.frm.security.vo.DictTreeVo;

public interface DictMapper extends Mapper<Dict> {

	/** 查重*/
	Long findCountByParameter(Map<String, Object> usmap);

	/** 分页查询列表*/
	List<Dict> findDictList(Map<String, Object> usmap);

	/** 查询部门树*/
	List<DictTreeVo> findDictTreeByType(@Param("dictTypeId")String dictTypeId);

	/** 逐层查询字典树*/
	List<DictTreeVo> findDictTreeOneByOne(@Param("dictTypeId")String dictTypeId, @Param("parentId")String parentId);

	/** 条件查询字典*/
	Dict findDictByparameter(Map<String, Object> usmap);

	/** 批量新增*/
	void addDictList(@Param("list") List<Dict> list);
}
