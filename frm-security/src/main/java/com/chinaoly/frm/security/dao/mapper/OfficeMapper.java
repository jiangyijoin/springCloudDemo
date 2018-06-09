package com.chinaoly.frm.security.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.Office;
import com.chinaoly.frm.security.vo.OfficeTreeVo;

public interface OfficeMapper extends Mapper<Office> {

	/** 验证是否重复*/
	Long findCountByParameter(Map<String, Object> usmap);

	/** 条件查询*/
	List<Office> findOfficeList(Map<String, Object> usmap);

	/** 逐层查树*/
	List<OfficeTreeVo> findOfficeTreeOneByOne(Office ov);

	/** 查询全部部门树*/
	List<OfficeTreeVo> findOfficeTreeByType();

	/** 条件查详情*/
	Office findOfficeByparameter(Map<String, Object> usmap);
	
	/** 批量上传*/
	void addOfficeList(@Param("list")List<Office> list);
}
