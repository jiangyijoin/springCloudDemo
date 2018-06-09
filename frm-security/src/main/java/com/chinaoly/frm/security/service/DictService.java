package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.Dict;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.chinaoly.frm.core.service.Service;


/**
 * Created by chenyl on 2018/06/03.
 */
public interface DictService extends Service<Dict> {

	Long findCountByParameter(Dict dict);

	PageInfo<Dict> findDictList(Integer pageNum, Integer pageSize, String dictName, String dictTypeName, String diceId, String dictTypeId);

	List<Map<String, Object>> findDictTreeByType(String dictTypeId);

	List<Map<String, Object>> findDictTreeOneByOne(String dictTypeId, String parentId);

	void excelUppload(String path);

}
