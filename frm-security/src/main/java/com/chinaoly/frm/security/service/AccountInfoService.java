package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.AccountInfo;
import com.chinaoly.frm.security.vo.AccountInfoVo;
import com.github.pagehelper.PageInfo;
import com.chinaoly.frm.core.service.Service;


/**
 * @author zhaohmz
 * @Date Mar 15, 2018
 */
public interface AccountInfoService extends Service<AccountInfo> {

	/**
	 * 批量初始化密码：默认123456，用"|"分割
	 * @param ids
	 */
	public void setInitPassword(String ids) throws Exception;

	/**
	 * 根据id设置用户密码
	 * @param id
	 * @param password
	 */
	public void resetPassword(String id, String password) throws Exception;
	
	/**
	 * 分页查询账户信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<AccountInfoVo> findPage(Integer pageNum, Integer pageSize, AccountInfo accountInfo, 
			String accountName,  String officeName) throws Exception;
		
	/**
	 * 根据账号统计条数
	 * @param accountName
	 * @return
	 */
	public int countForAccountName(String accountName) throws Exception;

	/**
	 * 新增账号信息
	 * @param accountInfo
	 */
	public void saveAccountInfo(AccountInfo accountInfo, String userIp) throws Exception;
	
	
	/**
	 * 此方法根据id查找账户信息，包括职级名称，账号名称，部门名称
	 */
	public AccountInfoVo findAccInfoById(String id)  throws Exception;

	/**
	 * 根据id同时删除账号信息和账号详情表
	 * @param id
	 */
	public void deleteAccAndAccInfoById(String id)  throws Exception;

	/**
	 * 根据账号名称查询账户信息
	 * @param id
	 */
	public AccountInfoVo findAccInfoByAccName(String accountName)  throws Exception;

	/**
	 * 批量删除账号信息和账号详情表
	 * @param id
	 */
	public void deleteAccAndAccInfoByIds(String ids)  throws Exception;
	
	/**
	 * 批量导入
	 * @param path
	 */
	public void excelUppload(String path) throws Exception;

}
