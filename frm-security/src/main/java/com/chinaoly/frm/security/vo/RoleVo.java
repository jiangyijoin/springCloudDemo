package com.chinaoly.frm.security.vo;

import java.util.Date;

import javax.persistence.Transient;

import com.chinaoly.frm.common.utils.DateUtil;

/**
 * 角色表
 * @author zhaohmz
 *
 */
public class RoleVo {
	private static final long serialVersionUID = 1L;

	private String id; //编号
	private String roleName; //资源名称
	private String nameCn; //简称
	private Integer delFlag; //标记 0可用1不可用
	
	@Transient
	private ResourceVo resource;
	@Transient
	private String resourceId;
	@Transient
	private AccountInfoVo accountInfo;
	@Transient
	private String accountId;
	
	
	public AccountInfoVo getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(AccountInfoVo accountInfo) {
		this.accountInfo = accountInfo;
	}
	public String getAccountId() {
		if(accountInfo != null) { return accountInfo.getId(); }
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public ResourceVo getResource() {
		return resource;
	}
	public void setResource(ResourceVo resource) {
		this.resource = resource;
	}
	public String getResourceId() {
		if(resource != null) { return resource.getId(); }
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "RoleVo [id=" + id + ", roleName=" + roleName + ", nameCn=" + nameCn + ", delFlag=" + delFlag
				+ ", resource=" + resource + ", resourceId=" + resourceId + ", accountInfo=" + accountInfo
				+ ", accountId=" + accountId + "]";
	}
	
}
