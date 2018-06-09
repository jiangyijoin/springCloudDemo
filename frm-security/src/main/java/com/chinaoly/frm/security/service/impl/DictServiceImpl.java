package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.DictMapper;
import com.chinaoly.frm.security.dao.mapper.DictTypeMapper;
import com.chinaoly.frm.security.Model.Dict;
import com.chinaoly.frm.security.Model.DictType;
import com.chinaoly.frm.security.service.DictService;
import com.chinaoly.frm.security.vo.DictTreeVo;
import com.chinaoly.frm.security.vo.OfficeTreeVo;
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
 * Created by chenyl on 2018/06/03.
 */
@Service
@Transactional
public class DictServiceImpl extends AbstractService<Dict> implements DictService {
	@Resource
	private DictMapper tSysDictMapper;
	@Resource
	private DictTypeMapper dictTypeMapper;

	@Override
	public Long findCountByParameter(Dict dict) {
		Map<String, Object> usmap = new HashMap<String, Object>();
		usmap.put("dictName", dict.getDictName());
		usmap.put("dictId", dict.getId());
		usmap.put("value", dict.getValue());
		usmap.put("dicTypeId", dict.getDictTypeId());
		return tSysDictMapper.findCountByParameter(usmap);
	}

	@Override
	public PageInfo<Dict> findDictList(Integer pageNum, Integer pageSize, String dictName, String dictTypeName,
			String diceId, String dictTypeId) {
		Map<String, Object> usmap = new HashMap<>();
		if (dictName != null && !"".equals(dictName)) {
			dictName = "%" + dictName + "%";
			usmap.put("dictName", dictName);
		}
		if (dictTypeName != null && !"".equals(dictTypeName)) {
			dictTypeName = "%" + dictTypeName + "%";
			usmap.put("dictTypeName", dictTypeName);
		}
		if (diceId != null && !"".equals(diceId)) {
			usmap.put("diceId", diceId);
		}
		if (dictTypeId != null && !"".equals(dictTypeId)) {
			usmap.put("dictTypeId", dictTypeId);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Dict> list = tSysDictMapper.findDictList(usmap);
		return new PageInfo<Dict>(list);
	}

	@Override
	public List<Map<String, Object>> findDictTreeByType(String dictTypeId) {
		List<DictTreeVo> list = tSysDictMapper.findDictTreeByType(dictTypeId);
		return toListAll(list);
	}
	
	public List<Map<String, Object>> toListAll(List<DictTreeVo> list) {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> treeMap = new HashMap<String, Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> usermap = new HashMap<>();
			usermap.put("id", list.get(i).getId());
			usermap.put("title", list.get(i).getDictName());
			usermap.put("parentId", list.get(i).getParentId());
			usermap.put("children", new ArrayList<Map<String, Object>>());
			treeMap.put(list.get(i).getId(),usermap);
		}
		
		for(String id : treeMap.keySet()){
			Map<String, Object> officeMap = treeMap.get(id);
			if(officeMap.get("parentId") == null){
				treeList.add(officeMap);
			}else{
				Map<String, Object> officeMapFath = treeMap.get(officeMap.get("parentId"));
				List<Map<String, Object>> child = (List<Map<String, Object>>) officeMapFath.get("children");
				child.add(officeMap);
			}
		}
		return treeList;
	}

	public List<Map<String, Object>> toList(List<DictTreeVo> list) {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> treeMap = new HashMap<>();
			treeMap.put("id", list.get(i).getId());
			treeMap.put("title", list.get(i).getDictName());
			if (list.get(i).getNum() > 0) {
				List<Map<String, Object>> treeList1 = new ArrayList<Map<String, Object>>();
				treeList1.add(new HashMap<>());
				treeMap.put("children", treeList1);
			} else {
				treeMap.put("children", new ArrayList<>());
			}
			treeList.add(treeMap);
		}
		return treeList;
	}

	@Override
	public List<Map<String, Object>> findDictTreeOneByOne(String dictTypeId, String parentId) {
		List<DictTreeVo> list = tSysDictMapper.findDictTreeOneByOne(dictTypeId, parentId);
		return toList(list);
	}

	public void excelUppload(String addre) {
		List<Dict> list = new ArrayList<Dict>();
		File files = new File(addre);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					Dict de = new Dict();

					// 第一个是列数，第二个是行数
					String dictName = rs.getCell(j++, i).getContents();
					String value = rs.getCell(j++, i).getContents();
					String description = rs.getCell(j++, i).getContents();
					String weight = rs.getCell(j++, i).getContents();

					String parentName = rs.getCell(j++, i).getContents();
					String dictTypeName = rs.getCell(j++, i).getContents();

					if (dictTypeName.length() == 0 || dictName.length() == 0 || value.length() == 0) {// 参数不全
						System.out.println("参数不全");
						continue;
					}
					Map<String, Object> usmap = new HashMap<String, Object>();
					if (parentName.length() > 0) {
						usmap.put("dictName", parentName);
						Dict parentDic = tSysDictMapper.findDictByparameter(usmap);
						if (parentDic == null) {
							System.out.println("为找到字典" + parentName);
							continue;
						} else {
							de.setParentId(parentDic.getId());
						}
					}
					usmap.put("dictTypeName", dictTypeName);
					DictType parentDicType = dictTypeMapper.findDictTypeByparameter(usmap);
					if (parentDicType == null) {
						System.out.println("为找到字典类型" + dictTypeName);
						continue;
					}

					// 判断是否重复
					Map<String, Object> usmap2 = new HashMap<String, Object>();
					usmap2.put("dictName", dictName);
					usmap2.put("value", value);
					usmap2.put("dicTypeId", parentDicType.getDictTypeId());
					Long num = tSysDictMapper.findCountByParameter(usmap2);
					if (num > 0) {// 数据库重复,跳过本条数据
						System.out.println(dictName + "数据已存在");
						continue;
					}

					// System.out.println(" dictTypeName:" + dictTypeName + "
					// dictType:" + dictType + " dictTypeDescription:" +
					// dictTypeDescription);

					de.setId(UUID.randomUUID().toString().replace("-", ""));
					de.setValue(value);
					de.setDictName(dictName);
					de.setDescription(description);
					de.setWeight(weight.length() == 0 ? 0 : Integer.valueOf(weight));
					de.setDictTypeId(parentDicType.getDictTypeId());
					de.setDelFlag(0);
					de.setCreateId("1");
					de.setCreateTime(new Date());

					list.add(de);
				}
			}
			if (list.size() > 0) {
				tSysDictMapper.addDictList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
	}
}
