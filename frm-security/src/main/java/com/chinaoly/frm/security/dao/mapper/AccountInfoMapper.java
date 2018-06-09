package com.chinaoly.frm.security.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.AccountInfo;
import com.chinaoly.frm.security.vo.AccountInfoVo;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
public interface AccountInfoMapper extends Mapper<AccountInfo> {

	public void setInitPassword(@Param("ids")String[] strings);

	@Select("update t_sys_account set PASSWORD=#{password},UPDATE_TIME=#{updateTime},UPDATE_ID=#{updateId} where ID=#{id}")
	public void resetPassword(@Param("id") String id, @Param("password") String password, @Param("updateTime") Date updateTime, @Param("updateId") String updateId);

	@Select("SELECT COUNT(1) FROM T_SYS_ACCOUNT WHERE ACCOUNT_NAME=#{accountName}")
	public int countForAccountName(String accountName);

	public List<AccountInfoVo> selectAllAcountInfoes(@Param("accountInfo") AccountInfo accountInfo, 
			@Param("accountName") String accountName, @Param("officeName") String officeName);

	public AccountInfoVo findAccInfoById(String id);

	public AccountInfoVo findAccInfoByAccName(String accountName);
	
}