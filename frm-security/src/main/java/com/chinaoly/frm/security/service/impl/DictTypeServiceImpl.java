package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.DictTypeMapper;
import com.chinaoly.frm.security.Model.DictType;
import com.chinaoly.frm.security.service.DictTypeService;
import com.chinaoly.frm.security.vo.DictTypeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;


/**
 * Created by chenyl on 2018/06/01.
 */
@Service
@Transactional
public class DictTypeServiceImpl extends AbstractService<DictType> implements DictTypeService {
    @Resource
    private DictTypeMapper tSysDictTypeMapper;

	@Override
	public Long findDictTypeByparameterFlag(DictType dictType) {
		Map<String, Object> usmap = new HashMap<String, Object>();
		usmap.put("dictTypeId", dictType.getDictTypeId());
		usmap.put("dictType", dictType.getDictType());
		usmap.put("dictTypeName", dictType.getDictTypeName());
		return tSysDictTypeMapper.findDictTypeByparameterFlag(usmap);
	}

	@Override
	public PageInfo<DictType> findDictTypeList(Integer pageNum, Integer pageSize, String dictType,
			String dictTypeName) {
		
		Map<String, Object> usmap = new HashMap<>();
		
		if(dictType != null && !"".equals(dictType)){
			dictType = "%"+dictType+"%";
			usmap.put("dictType", dictType);
		}
		if(dictTypeName != null && !"".equals(dictTypeName)){
			dictTypeName = "%"+dictTypeName+"%";
			usmap.put("dictTypeName", dictTypeName);
		}
		
		PageHelper.startPage(pageNum, pageSize);
		List<DictType> list = tSysDictTypeMapper.findDictTypeList(usmap);
		return new PageInfo<DictType>(list);
	}

	@Override
	public void excelUppload(String path) {
		List<DictType> list = new ArrayList<DictType>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 第一个是列数，第二个是行数
					String dictTypeName = rs.getCell(j++, i).getContents();
					String dictType = rs.getCell(j++, i).getContents();
					String dictTypeDescription = rs.getCell(j++, i).getContents();

					if(dictTypeName.length() == 0 || dictType.length() == 0){//参数不全
						continue;
					}
					//System.out.println(" dictTypeName:" + dictTypeName + " dictType:" + dictType + " dictTypeDescription:" + dictTypeDescription);
					DictType de = new DictType();
					UUID uuid=UUID.randomUUID();
					de.setDictTypeId(uuid.toString().replace("-", ""));
					de.setDictType(dictType);
					de.setDictTypeDescription(dictTypeDescription);
					de.setDictTypeName(dictTypeName);
					de.setCreateId("1");
					de.setCreateTime(new Date());
					
					//判断是否重复
					Map<String, Object> usmap = new HashMap<String, Object>();
					usmap.put("dictTypeId", de.getDictTypeId());
					usmap.put("dictType", de.getDictType());
					usmap.put("dictTypeName", de.getDictTypeName());
					Long num = tSysDictTypeMapper.findDictTypeByparameterFlag(usmap);
					if(num > 0){//数据库重复,跳过本条数据
						continue;
					}
					
					list.add(de);
				}
			}
			if(list.size()>0){
				tSysDictTypeMapper.addDictTypeList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
	}

	@Override
	public List<Map<String, Object>> findDictTreeOneByOne() {
		List<DictTypeVo> list = tSysDictTypeMapper.findDictTreeOneByOne();
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		for(int i = 0; i<list.size(); i++){
			Map<String, Object> treeMap = new HashMap<>();
			treeMap.put("id", list.get(i).getDictTypeId());
			treeMap.put("title", list.get(i).getDictTypeName());
			if(list.get(i).getNum() > 0){
				treeMap.put("children", new HashMap<>());
			}
			treeList.add(treeMap);
		}
		return treeList;
	}
}
