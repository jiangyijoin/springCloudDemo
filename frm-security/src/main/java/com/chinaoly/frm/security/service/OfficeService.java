package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.Office;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.chinaoly.frm.core.service.Service;


/**
 * Created by chenyl on 2018/06/03.
 */
public interface OfficeService extends Service<Office> {

	Long findCountByParameter(Office ov);

	PageInfo<Office> findOfficeList(Integer pageNum, Integer pageSize, String officeCode, String officeName);

	List<Map<String, Object>> findOfficeTreeOneByOne(Office ov);

	List<Map<String, Object>> findOfficeTreeByType();

	void excelUppload(String path);

}
