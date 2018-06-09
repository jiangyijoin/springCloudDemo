package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.AccountAndRole;

import com.chinaoly.frm.core.service.Service;



public interface AccountAndRoleService extends Service<AccountAndRole> {

	public void deleteAccountAndRole(String accountId) throws Exception;

}
