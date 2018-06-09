package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_CLIENT")
public class Client implements Serializable {
    /**
     * 应用id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 应用密码
     */
    @Column(name = "CLIENT_PASSWORD")
    private String clientPassword;

    /**
     * 应用名
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     * 应用备注
     */
    @Column(name = "REMARK")
    private String remark;

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

    /**
     * 应用路径
     */
    @Column(name = "CLIENT_URL")
    private String clientUrl;

    /**
     * 应用图片
     */
    @Column(name = "CLIENT_IMG")
    private String clientImg;

    private static final long serialVersionUID = 1L;

    /**
     * 获取应用id
     *
     * @return ID - 应用id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置应用id
     *
     * @param id 应用id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取应用密码
     *
     * @return CLIENT_PASSWORD - 应用密码
     */
    public String getClientPassword() {
        return clientPassword;
    }

    /**
     * 设置应用密码
     *
     * @param clientPassword 应用密码
     */
    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    /**
     * 获取应用名
     *
     * @return CLIENT_NAME - 应用名
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置应用名
     *
     * @param clientName 应用名
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 获取应用备注
     *
     * @return REMARK - 应用备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置应用备注
     *
     * @param remark 应用备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return DEL_FLAG
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
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

    /**
     * 获取应用路径
     *
     * @return CLIENT_URL - 应用路径
     */
    public String getClientUrl() {
        return clientUrl;
    }

    /**
     * 设置应用路径
     *
     * @param clientUrl 应用路径
     */
    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    /**
     * 获取应用图片
     *
     * @return CLIENT_IMG - 应用图片
     */
    public String getClientImg() {
        return clientImg;
    }

    /**
     * 设置应用图片
     *
     * @param clientImg 应用图片
     */
    public void setClientImg(String clientImg) {
        this.clientImg = clientImg;
    }
}