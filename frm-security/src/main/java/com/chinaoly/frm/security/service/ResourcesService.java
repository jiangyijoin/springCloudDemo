package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.Resources;
import com.chinaoly.frm.security.Model.RoleAndResource;
import com.chinaoly.frm.security.vo.ResourceVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

import com.chinaoly.frm.core.service.Service;


public interface ResourcesService extends Service<Resources> {

	public void saveResource(Resources resource) throws Exception;

	public void updateResource(Resources resources) throws Exception;

	public PageInfo<ResourceVo> findPage(Integer pageNum, Integer pageSize, Resources resource) throws Exception;

	public void setResourcesByRoleId(String roleId, List<RoleAndResource> roleAndResources) throws Exception;

	public List<Resources> getResourcesByRoleId(String roleId) throws Exception;

	public List<ResourceVo> getResourcesByAllRoleIds() throws Exception;

	public void setDelFlag(String resourceId, int delFlag) throws Exception;

	public ResourceVo findResourceById(String id) throws Exception;

	public void excelUppload(String path) throws Exception;
}
