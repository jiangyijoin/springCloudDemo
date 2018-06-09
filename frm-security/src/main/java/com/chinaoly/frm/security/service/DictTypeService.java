package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.DictType;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.chinaoly.frm.core.service.Service;


/**
 * Created by chenyl on 2018/06/01.
 */
public interface DictTypeService extends Service<DictType> {

	Long findDictTypeByparameterFlag(DictType dictType);

	PageInfo<DictType> findDictTypeList(Integer pageNum, Integer pageSize, String dictType, String dictTypeName);

	void excelUppload(String path);

	List<Map<String, Object>> findDictTreeOneByOne();

}
