package com.chinaoly.frm.security.dao.mapper;

import org.apache.ibatis.annotations.Delete;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.AccountAndRole;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
public interface AccountAndRoleMapper extends Mapper<AccountAndRole> {

	@Delete("DELETE FROM T_SYS_R_ACCOUNT_ROLE WHERE ACCOUNT_ID = #{accountId}")
	public void deleteAccountAndRole(String accountId) throws Exception;
}
