package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "T_SYS_R_ACCOUNT_ROLE")
public class AccountAndRole implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 账户id
     */
    @Column(name = "ACCOUNT_ID")
    private String accountId;

    /**
     * 角色id
     */
    @Column(name = "ROLE_ID")
    private String roleId;

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
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取账户id
     *
     * @return ACCOUNT_ID - 账户id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 设置账户id
     *
     * @param accountId 账户id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
}