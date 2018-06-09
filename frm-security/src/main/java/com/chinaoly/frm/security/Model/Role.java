package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_ROLE")
public class Role implements Serializable {
    /**
     * 编号
     */
	@Id
    @Column(name = "ID")
    private String id;

    /**
     * 资源名称
     */
    @Column(name = "ROLE_NAME")
    private String roleName;

    /**
     * 简称
     */
    @Column(name = "NAME_CN")
    private String nameCn;

    /**
     * 删除标记 0可用1不可用
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
     * 获取编号
     *
     * @return ID - 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取资源名称
     *
     * @return ROLE_NAME - 资源名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置资源名称
     *
     * @param roleName 资源名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取简称
     *
     * @return NAME_CN - 简称
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * 设置简称
     *
     * @param nameCn 简称
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    /**
     * 获取删除标记 0可用1不可用
     *
     * @return DEL_FLAG - 删除标记 0可用1不可用
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记 0可用1不可用
     *
     * @param delFlag 删除标记 0可用1不可用
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