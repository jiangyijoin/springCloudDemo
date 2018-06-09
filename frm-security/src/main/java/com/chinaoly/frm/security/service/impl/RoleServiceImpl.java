package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.common.utils.RandomGUID;
import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.RoleMapper;
import com.chinaoly.frm.security.Model.AccountAndRole;
import com.chinaoly.frm.security.Model.Client;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.service.AccountAndRoleService;
import com.chinaoly.frm.security.service.RoleService;
import com.chinaoly.frm.security.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper tSysRoleMapper;
    @Resource
    private AccountAndRoleService accountAndRoleService;


	@Override
	public void saveRole(Role role) throws Exception{
		String uuid =  new RandomGUID().getUUID32();
		Date currentTime = new Date();
		
		role.setId(uuid);
		role.setCreateTime(currentTime);
		
		//待修改，改成当前用户
		role.setCreateId(uuid);
		
		save(role);
	}
	

	@Override
	public void updateRole(Role role) throws Exception{
		//待修改，改成当前用户
		role.setUpdateId(role.getCreateId());
		role.setUpdateTime(new Date());
		
		update(role);
	}
	
	@Override
	public List<Role> getRolesByAccountId(String accountId) throws Exception {
		return tSysRoleMapper.getRolesByAccountId(accountId);
	}
	
	@Override
	public void setRolesByAccountId(String accountId,String roleIdstr) throws Exception{
		
		String[] roleIds= roleIdstr.split(",");
		List<AccountAndRole> roles = new ArrayList<AccountAndRole>();

		accountAndRoleService.deleteAccountAndRole(accountId);
		
		for(int i = 0; i<roleIds.length; i++) {
			if(roleIds[i] == null || roleIds[i].isEmpty()) continue;
			AccountAndRole temp = new AccountAndRole();
			temp.setId(new RandomGUID().getUUID32());
			temp.setAccountId(accountId);
			temp.setRoleId(roleIds[i]);
			
			roles.add(temp);
			accountAndRoleService.save(temp);
		}
		
	}

	@Override
	public List<RoleVo> getRolesByAllAccounts() throws Exception {
		return tSysRoleMapper.getRolesByAllAccounts();
	}

	@Override
	public List<RoleVo> getRolesByAllResources() throws Exception {
		return tSysRoleMapper.getRolesByAllResources();
	}


	@Override
	public PageInfo<Role> findPage(Integer pageNum, Integer pageSize, Role role) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
	    List<Role> list = tSysRoleMapper.selectAllRoles(role);
	    return new PageInfo(list);
	}


	@Override
	public void excelUppload(String path) throws Exception {
		List<Role> roles = new ArrayList<Role>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			StringBuilder uuid;
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					Role role = new Role();
					
					// 第一个是列数，第二个是行数
					String roleName =  CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String nameCn =  CommonUtil.nvl(rs.getCell(j++, i).getContents());
					short delFlag = CommonUtil.nvlShort(rs.getCell(j++, i).getContents(), (short)0);
					
					
					if(!checkContents(roleName, nameCn, delFlag)) return;

					uuid =  new StringBuilder(new RandomGUID().getUUID32());
					role.setId(uuid.toString());
					role.setRoleName(roleName);
					role.setNameCn(nameCn);
					role.setDelFlag(delFlag);
					role.setCreateId(uuid.toString());
					role.setCreateTime(new Date());
					
					roles.add(role);
					save(role);
				}
			}
			/*if (roles.size() > 0) {
				save(roles);
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
		
	}


	private boolean checkContents(String roleName, String nameCn, short delFlag) {
		// TODO Auto-generated method stub
		return true;
	}


}
