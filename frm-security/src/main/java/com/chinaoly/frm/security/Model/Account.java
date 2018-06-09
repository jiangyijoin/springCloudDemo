package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_ACCOUNT")
public class Account implements Serializable {
    /**
     * 主键id
     */
	@Id
	@Column(name = "ID")
    private String id;

    /**
     * 用户名
     */
    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 帐号状态 0:启用 1:禁用
     */
    @Column(name = "DEL_FLAG")
    private int delFlag;

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
     * @return ID - 主键id
     */
    public String getAccountId() {
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
     * 获取用户名
     *
     * @return ACCOUNT_NAME - 用户名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置用户名
     *
     * @param accountName 用户名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取帐号状态 0:启用 1:禁用
     *
     * @return DEL_FLAG - 帐号状态 0:启用 1:禁用
     */
    public int getDelFlag() {
        return delFlag;
    }

    /**
     * 设置帐号状态 0:启用 1:禁用
     *
     * @param delFlag 帐号状态 0:启用 1:禁用
     */
    public void setDelFlag(int delFlag) {
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

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountName=" + accountName + ", password=" + password + ", delFlag=" + delFlag
				+ ", createTime=" + createTime + ", createId=" + createId + ", updateTime=" + updateTime + ", updateId="
				+ updateId + "]";
	}
    
}