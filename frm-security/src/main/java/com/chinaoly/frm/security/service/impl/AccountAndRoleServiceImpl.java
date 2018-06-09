package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.AccountAndRoleMapper;
import com.chinaoly.frm.security.Model.AccountAndRole;
import com.chinaoly.frm.security.service.AccountAndRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author zhaohmz
 * @Date Mar 15, 2018
 */
@Service
@Transactional
public class AccountAndRoleServiceImpl extends AbstractService<AccountAndRole> implements AccountAndRoleService {
    @Resource
    private AccountAndRoleMapper tSysRAccountRoleMapper;

	@Override
	public void deleteAccountAndRole(String accountId) throws Exception {
		tSysRAccountRoleMapper.deleteAccountAndRole(accountId);
	}

}
