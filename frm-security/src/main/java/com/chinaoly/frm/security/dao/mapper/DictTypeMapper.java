package com.chinaoly.frm.security.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.DictType;
import com.chinaoly.frm.security.vo.DictTypeVo;

public interface DictTypeMapper extends Mapper<DictType> {

	/** 通过参数查详情,排除本身*/
	Long findDictTypeByparameterFlag(Map<String, Object> usmap);

	/** 条件查询列表*/
	List<DictType> findDictTypeList(Map<String, Object> usmap);

	/** 查询树*/
	List<DictTypeVo> findDictTreeOneByOne();

	/** 条件查询详情*/
	DictType findDictTypeByparameter(Map<String, Object> usmap);

	/** 批量上传*/
	void addDictTypeList(@Param("list")List<DictType> list);

}
