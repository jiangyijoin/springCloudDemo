package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.DictMapper;
import com.chinaoly.frm.security.dao.mapper.DictTypeMapper;
import com.chinaoly.frm.security.dao.mapper.OfficeMapper;
import com.chinaoly.frm.security.Model.Dict;
import com.chinaoly.frm.security.Model.DictType;
import com.chinaoly.frm.security.Model.Office;
import com.chinaoly.frm.security.service.OfficeService;
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
public class OfficeServiceImpl extends AbstractService<Office> implements OfficeService {
	@Resource
	private OfficeMapper tSysOfficeMapper;
	@Resource
	private DictMapper dictMapper;
	@Resource
	private DictTypeMapper dictTypeMapper;

	@Override
	public Long findCountByParameter(Office ov) {
		Map<String, Object> usmap = new HashMap<String, Object>();
		usmap.put("officeName", ov.getOfficeName());
		usmap.put("officeCode", ov.getOfficeCode());
		usmap.put("officeId", ov.getOfficeId());
		return tSysOfficeMapper.findCountByParameter(usmap);
	}

	@Override
	public PageInfo<Office> findOfficeList(Integer pageNum, Integer pageSize, String officeCode, String officeName) {
		Map<String, Object> usmap = new HashMap<>();
		if (officeCode != null && !"".equals(officeCode)) {
			officeCode = "%" + officeCode + "%";
			usmap.put("officeCode", officeCode);
		}
		if (officeName != null && !"".equals(officeName)) {
			officeName = "%" + officeName + "%";
			usmap.put("officeName", officeName);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Office> list = tSysOfficeMapper.findOfficeList(usmap);
		return new PageInfo<Office>(list);
	}

	@Override
	public List<Map<String, Object>> findOfficeTreeOneByOne(Office ov) {
		List<OfficeTreeVo> list = tSysOfficeMapper.findOfficeTreeOneByOne(ov);
		List<Map<String, Object>> treeList = toList(list);
		return treeList;
	}
	
	public List<Map<String, Object>> toListAll(List<OfficeTreeVo> list) {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> treeMap = new HashMap<String, Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> usermap = new HashMap<>();
			usermap.put("code", list.get(i).getOfficeCode());
			usermap.put("title", list.get(i).getOfficeName());
			usermap.put("id", list.get(i).getOfficeId());
			usermap.put("parentId", list.get(i).getParentId());
			usermap.put("children", new ArrayList<Map<String, Object>>());
			treeMap.put(list.get(i).getOfficeId(),usermap);
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

	public List<Map<String, Object>> toList(List<OfficeTreeVo> list) {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> treeMap = new HashMap<>();
			treeMap.put("code", list.get(i).getOfficeCode());
			treeMap.put("title", list.get(i).getOfficeName());
			treeMap.put("id", list.get(i).getOfficeId());
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
	public List<Map<String, Object>> findOfficeTreeByType() {
		List<OfficeTreeVo> list = tSysOfficeMapper.findOfficeTreeByType();
		List<Map<String, Object>> treeList = toListAll(list);
		return treeList;
	}

	@Override
	public void excelUppload(String path) {
		List<Office> list = new ArrayList<Office>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					Office ov = new Office();

					// 第一个是列数，第二个是行数
					String officeName = rs.getCell(j++, i).getContents();
					String officeCode = rs.getCell(j++, i).getContents();

					String typeName = rs.getCell(j++, i).getContents();// 需要查询,部门类型名字
					String parentName = rs.getCell(j++, i).getContents();// 需要查询,上级部门

					String master = rs.getCell(j++, i).getContents();
					String phone = rs.getCell(j++, i).getContents();
					String remark = rs.getCell(j++, i).getContents();

					if (officeName.length() == 0 || officeCode.length() == 0 || typeName.length() == 0
							|| master.length() == 0 || phone.length() == 0 || remark.length() == 0) {// 参数不全
						System.out.println("参数不全");
						continue;
					}
					Map<String, Object> usmap = new HashMap<String, Object>();
					if (parentName.length() > 0) {
						usmap.put("officeName", parentName);
						Office parentOffice = tSysOfficeMapper.findOfficeByparameter(usmap);
						if (parentOffice == null) {
							System.out.println("为找到部门" + parentName);
							continue;
						} else {
							ov.setParentId(parentOffice.getOfficeId());
						}
					}

					usmap.put("dictTypeName", "部门管理");
					DictType dicTypeN = dictTypeMapper.findDictTypeByparameter(usmap);
					if (dicTypeN == null) {
						System.out.println("未找到部门管理");
						return;
					}
					usmap.put("dictName", typeName);
					usmap.put("dictTypeId", dicTypeN.getDictTypeId());// =======此处填部门类型id
					Dict parentDic = dictMapper.findDictByparameter(usmap);
					if (parentDic == null) {
						System.out.println("为找到字典" + typeName);
						continue;
					}
					ov.setType(parentDic.getDictTypeId());

					// 判断是否重复
					Map<String, Object> usmap2 = new HashMap<String, Object>();
					usmap2.put("officeName", officeName);
					usmap2.put("officeCode", officeCode);
					Long num = tSysOfficeMapper.findCountByParameter(usmap2);
					if (num > 0) {// 数据库重复,跳过本条数据
						System.out.println(officeName + "数据已存在");
						continue;
					}

					// System.out.println(" dictTypeName:" + dictTypeName + "
					// dictType:" + dictType + " dictTypeDescription:" +
					// dictTypeDescription);

					ov.setOfficeId(UUID.randomUUID().toString().replace("-", ""));
					ov.setOfficeCode(officeCode);
					ov.setOfficeNum(officeCode);
					ov.setOfficeName(officeName);
					ov.setRemark(remark);
					ov.setMaster(master);
					ov.setPhone(phone);
					ov.setDelFlag(0);
					ov.setCreateId("1");
					ov.setWeight(0);
					ov.setCreateTime(new Date());

					list.add(ov);
				}
			}
			if (list.size() > 0) {
				tSysOfficeMapper.addOfficeList(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
	}

}
