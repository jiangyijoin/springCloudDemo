package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.vo.RoleVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

import com.chinaoly.frm.core.service.Service;


/**
* @author zhaohmz
* @Date Mar 15, 2018
 */
public interface RoleService extends Service<Role> {

	public void saveRole(Role role) throws Exception;
	
	public void updateRole(Role role) throws Exception;
	
	public List<Role> getRolesByAccountId(String accountId) throws Exception;
	
	public void setRolesByAccountId(String accountId, String roleIds) throws Exception;

	public List<RoleVo> getRolesByAllAccounts() throws Exception;
	
	public List<RoleVo> getRolesByAllResources() throws Exception;

	public PageInfo<Role> findPage(Integer pageNum, Integer pageSize, Role role) throws Exception;

	public void excelUppload(String path) throws Exception;
}
