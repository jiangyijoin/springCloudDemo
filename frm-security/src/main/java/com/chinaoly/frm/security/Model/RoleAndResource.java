package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "T_SYS_R_ROLE_RESOURCE")
public class RoleAndResource implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 角色id
     */
    @Column(name = "ROLE_ID")
    private String roleId;

    /**
     * 模块id
     */
    @Column(name = "RESOURCE_ID")
    private String resourceId;

    /**
     * 权限分类 0:读写 1:写 2:读
     */
    @Column(name = "PERMISSION")
    private Short permission;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return RELATION_ID - 主键id
     */
    public String getId() {
		return id;
	}

	/**
     * 设置主键id
     *
     * @param relationId 主键id
     */
	public void setId(String id) {
		this.id = id;
	}

    /**
     * 获取角色id
     *
     * @return ROLE_ID - 角色id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取模块id
     *
     * @return RESOURCE_ID - 模块id
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * 设置模块id
     *
     * @param resourceId 模块id
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * 获取权限分类 0:读写 1:写 2:读
     *
     * @return PERMISSION - 权限分类 0:读写 1:写 2:读
     */
    public Short getPermission() {
        return permission;
    }

    /**
     * 设置权限分类 0:读写 1:写 2:读
     *
     * @param permission 权限分类 0:读写 1:写 2:读
     */
    public void setPermission(Short permission) {
        this.permission = permission;
    }
}