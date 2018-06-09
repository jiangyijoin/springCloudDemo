package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_RESOURCE")
public class Resources implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 模块等级 0,1,2..
     */
    @Column(name = "RESOURCE_LEVEL")
    private Short resourceLevel;

    /**
     * 模块名
     */
    @Column(name = "RESOURCE_NAME")
    private String resourceName;

    /**
     * 模块中文名
     */
    @Column(name = "NAME_CN")
    private String nameCn;

    /**
     * 模块别名
     */
    @Column(name = "RESOURCE_ALIAS")
    private String resourceAlias;

    /**
     * 资源值
     */
    @Column(name = "RESOURCE_VAL")
    private String resourceVal;

    /**
     * 类型 0菜单 1控制器层路径 2方法路径 3普通资源
     */
    @Column(name = "RESOURCE_TYPE")
    private String resourceType;

    /**
     * 父类模块
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 权重
     */
    @Column(name = "WEIGHT")
    private Short weight;

    /**
     * 菜单图标
     */
    @Column(name = "ICON")
    private String icon;

    /**
     * 备注信息
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 资源应用id
     */
    @Column(name = "CLIENT_ID")
    private String clientId;

    /**
     * 删除标记
     */
    @Column(name = "DEL_FLAG")
    private Short delFlag;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建者帐号id
     */
    @Column(name = "CREATE_ID")
    private String createId;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新者帐号id
     */
    @Column(name = "UPDATE_ID")
    private String updateId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return RESOURCE_ID - 主键id
     */
    public String getId() {
		return id;
	}

	/**
     * 设置主键id
     *
     * @param resourceId 主键id
     */
    public void setId(String id) {
		this.id = id;
	}

    /**
     * 获取模块等级 0,1,2..
     *
     * @return RESOURCE_LEVEL - 模块等级 0,1,2..
     */
    public Short getResourceLevel() {
        return resourceLevel;
    }

    /**
     * 设置模块等级 0,1,2..
     *
     * @param resourceLevel 模块等级 0,1,2..
     */
    public void setResourceLevel(Short resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    /**
     * 获取模块名
     *
     * @return RESOURCE_NAME - 模块名
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 设置模块名
     *
     * @param resourceName 模块名
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 获取模块中文名
     *
     * @return NAME_CN - 模块中文名
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * 设置模块中文名
     *
     * @param nameCn 模块中文名
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    /**
     * 获取模块别名
     *
     * @return RESOURCE_ALIAS - 模块别名
     */
    public String getResourceAlias() {
        return resourceAlias;
    }

    /**
     * 设置模块别名
     *
     * @param resourceAlias 模块别名
     */
    public void setResourceAlias(String resourceAlias) {
        this.resourceAlias = resourceAlias;
    }

    /**
     * 获取资源值
     *
     * @return RESOURCE_VAL - 资源值
     */
    public String getResourceVal() {
        return resourceVal;
    }

    /**
     * 设置资源值
     *
     * @param resourceVal 资源值
     */
    public void setResourceVal(String resourceVal) {
        this.resourceVal = resourceVal;
    }

    /**
     * 获取类型 0菜单 1控制器层路径 2方法路径 3普通资源
     *
     * @return RESOURCE_TYPE - 类型 0菜单 1控制器层路径 2方法路径 3普通资源
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * 设置类型 0菜单 1控制器层路径 2方法路径 3普通资源
     *
     * @param resourceType 类型 0菜单 1控制器层路径 2方法路径 3普通资源
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取父类模块
     *
     * @return PARENT_ID - 父类模块
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父类模块
     *
     * @param parentId 父类模块
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取权重
     *
     * @return WEIGHT - 权重
     */
    public Short getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Short weight) {
        this.weight = weight;
    }

    /**
     * 获取菜单图标
     *
     * @return ICON - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取备注信息
     *
     * @return REMARK - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取资源应用id
     *
     * @return CLIENT_ID - 资源应用id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置资源应用id
     *
     * @param clientId 资源应用id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取删除标记
     *
     * @return DEL_FLAG - 删除标记
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记
     *
     * @param delFlag 删除标记
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建者帐号id
     *
     * @return CREATE_ID - 创建者帐号id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建者帐号id
     *
     * @param createId 创建者帐号id
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新者帐号id
     *
     * @return UPDATE_ID - 更新者帐号id
     */
    public String getUpdateId() {
        return updateId;
    }

    /**
     * 设置更新者帐号id
     *
     * @param updateId 更新者帐号id
     */
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

}