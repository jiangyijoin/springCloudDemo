package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.common.utils.RandomGUID;
import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.ResourcesMapper;
import com.chinaoly.frm.security.dao.mapper.RoleAndResourceMapper;
import com.chinaoly.frm.security.Model.Resources;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.Model.RoleAndResource;
import com.chinaoly.frm.security.service.ResourcesService;
import com.chinaoly.frm.security.vo.ResourceVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@Service
@Transactional
public class ResourcesServiceImpl extends AbstractService<Resources> implements ResourcesService {
    @Resource
    private ResourcesMapper tSysResourceMapper;
    
    @Resource
    private RoleAndResourceMapper roleAndResourceMapper;

	@Override
	public void saveResource(Resources resource) throws Exception {
		String uuid =  new RandomGUID().getUUID32();
		Date currentTime = new Date();
		
		resource.setId(uuid);
		resource.setCreateTime(currentTime);
		//待修改，改成当前用户
		resource.setCreateId(uuid);
		save(resource);
	}

	@Override
	public void updateResource(Resources resource) throws Exception {
		//待修改，改成当前用户
		resource.setUpdateId(resource.getClientId());
		resource.setUpdateTime(new Date());
		update(resource);
	}

	@Override
	public PageInfo<ResourceVo> findPage(Integer pageNum, Integer pageSize, Resources resource) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
	    List<ResourceVo> list = tSysResourceMapper.selectAllResources(resource);
	    return new PageInfo(list);
	}

	@Override
	public void setResourcesByRoleId(String roleId, List<RoleAndResource> roleAndResources) throws Exception{
		for(RoleAndResource temp : roleAndResources) {
			temp.setRoleId(roleId);
			temp.setId(new RandomGUID().getUUID32());
		}
		roleAndResourceMapper.deleteByCondition("ROLE_ID = "+roleId);
		roleAndResourceMapper.insertList(roleAndResources);
	}

	@Override
	public List<Resources> getResourcesByRoleId(String roleId) throws Exception {		
		return tSysResourceMapper.getResourcesByRoleId(roleId);
	}

	@Override
	public List<ResourceVo> getResourcesByAllRoleIds() throws Exception {
		return tSysResourceMapper.getResourcesByAllRoleIds();
	}

	@Override
	public void setDelFlag(String resourceId, int delFlag) throws Exception {
		tSysResourceMapper.setDelFlag(resourceId, delFlag);
	}

	@Override
	public ResourceVo findResourceById(String id) throws Exception {
		return tSysResourceMapper.findResourceById(id);
	}

	@Override
	public void excelUppload(String path) throws Exception {
		List<Resources> resources = new ArrayList<Resources>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			StringBuilder uuid;
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					Resources resource = new Resources();
					
					// 第一个是列数，第二个是行数
					short resourceLevel = Short.parseShort(rs.getCell(j++, i).getContents());
					String resourceName = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String nameCn = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String resourceAlias = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String resourceVal = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String resourceType = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String parentId = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					short weight = Short.parseShort(rs.getCell(j++, i).getContents());
					String remark = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String clientId = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					short delFlag = Short.parseShort(rs.getCell(j++, i).getContents());
					
					
					if(!checkContents(resourceLevel, resourceName, nameCn, resourceAlias, resourceVal,
							resourceType, parentId, weight, remark, clientId, delFlag)) return;

					uuid =  new StringBuilder(new RandomGUID().getUUID32());
					resource.setId(uuid.toString());
					resource.setResourceLevel(resourceLevel);
					resource.setResourceName(resourceName);
					resource.setNameCn(nameCn);
					resource.setResourceAlias(resourceAlias);
					resource.setResourceVal(resourceVal);
					resource.setResourceType(resourceType);
					resource.setParentId(parentId);
					resource.setWeight(weight);
					resource.setRemark(remark);
					resource.setClientId(clientId);
					resource.setDelFlag(delFlag);
					resource.setCreateId(uuid.toString());
					resource.setCreateTime(new Date());					
					
					resources.add(resource);
					save(resource);
				}
			}
			/*if (resources.size() > 0) {
				save(resources);
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
		
	}

	private boolean checkContents(short resourceLevel, String resourceName, String nameCn, String resourceAlias,
			String resourceVal, String resourceType, String parentId, short weight, String remark, String clientId,
			short delFlag) {
		
		return true;
	}
}
