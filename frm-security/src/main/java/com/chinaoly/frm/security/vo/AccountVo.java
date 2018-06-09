package com.chinaoly.frm.security.vo;

/**
 * 账户表
 * @author zhaohmz
 *
 */
public class AccountVo {
	private static final long serialVersionUID = 1L;

	private String id; //id
	private String accountName; //用户名
	private String password; //密码
	private Integer delFlag; //帐号状态 0:启用 1:禁用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "AccountVo [id=" + id + ", accountName=" + accountName + ", password=" + password + ", delFlag="
				+ delFlag + "]";
	}
	
}
