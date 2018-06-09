package com.chinaoly.frm.security.vo;

import java.util.Date;

import javax.persistence.Transient;

import com.chinaoly.frm.common.utils.DateUtil;

/**
 * 资源表
 * @author zhaohmz
 *
 */
public class ResourceVo {
	private static final long serialVersionUID = 1L;

	private String id; 				//主键id
	private Integer resourceLevel; 	//模块等级 0,1,2..
	private String resourceName; 	//模块名
	private String nameCn; 			//模块中文名
	private String resourceAlias; 	//模块别名
	private String resourceVal; 	//资源值
	private Integer resourceType; 	//类型 0菜单 1控制器层路径 2方法路径 3普通资源
	private String parentId;	 	//父类模块
	private Integer weight; 		//权重
	private String icon; 			//菜单图标
	private String remark; 			//备注信息
	private String clientId; 		//资源应用id
	private Integer delFlag; 		//删除标记 0可用1不可用
	
	@Transient
	private RoleVo role;
	@Transient
	private String roleId;
	@Transient
	private String clientName;
	@Transient
	private String parentName;
	
	
	public RoleVo getRole() {
		return role;
	}
	public void setRole(RoleVo role) {
		this.role = role;
	}
	public String getRoleId() {
		if(role != null) { return role.getId(); }
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getResourceLevel() {
		return resourceLevel;
	}
	public void setResourceLevel(Integer resourceLevel) {
		this.resourceLevel = resourceLevel;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String getResourceAlias() {
		return resourceAlias;
	}
	public void setResourceAlias(String resourceAlias) {
		this.resourceAlias = resourceAlias;
	}
	public String getResourceVal() {
		return resourceVal;
	}
	public void setResourceVal(String resourceVal) {
		this.resourceVal = resourceVal;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Override
	public String toString() {
		return "ResourceVo [id=" + id + ", resourceLevel=" + resourceLevel + ", resourceName=" + resourceName
				+ ", nameCn=" + nameCn + ", resourceAlias=" + resourceAlias + ", resourceVal=" + resourceVal
				+ ", resourceType=" + resourceType + ", parentId=" + parentId + ", weight=" + weight + ", icon=" + icon
				+ ", remark=" + remark + ", clientId=" + clientId + ", delFlag=" + delFlag + ", role=" + role
				+ ", roleId=" + roleId + ", clientName=" + clientName + ", parentName=" + parentName + "]";
	}

	
}
