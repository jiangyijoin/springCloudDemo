package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_OFFICE")
public class Office implements Serializable {
    /**
     * 编号
     */
    @Id
    @Column(name = "ID")
    private String officeId;

    /**
     * 父级编号
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 所有父级编号
     */
    @Column(name = "PARENT_IDS")
    private String parentIds;

    /**
     * 名称
     */
    @Column(name = "OFFICE_NAME")
    private String officeName;

    /**
     * 权重
     */
    @Column(name = "WEIGHT")
    private Integer weight;

    /**
     * 归属区域
     */
    @Column(name = "AREA_ID")
    private String areaId;

    /**
     * 区域编码
     */
    @Column(name = "AREA_CODE")
    private String areaCode;

    /**
     * 机构类型
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 机构等级
     */
    @Column(name = "GRADE")
    private String grade;

    /**
     * 联系地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 邮编
     */
    @Column(name = "ZIP_CODE")
    private String zipCode;

    /**
     * 负责人
     */
    @Column(name = "MASTER")
    private String master;

    /**
     * 电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 传真
     */
    @Column(name = "FAX")
    private String fax;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 主负责人
     */
    @Column(name = "PRIMARY_PERSON")
    private String primaryPerson;

    /**
     * 副负责人
     */
    @Column(name = "DEPUTY_PERSON")
    private String deputyPerson;

    /**
     * 备注信息
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 名称简称
     */
    @Column(name = "SIMPLE_NAME")
    private String simpleName;

    /**
     * 是否部门领导审批（114）
     */
    @Column(name = "BOOLSP")
    private Integer boolsp;

    /**
     * 删除标记
     */
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

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
     * 部门编号
     */
    @Column(name = "OFFICE_NUM")
    private String officeNum;

    /**
     * 父级部门名称
     */
    @Column(name = "PARENT_NAME")
    private String parentName;

    /**
     * 部门编码
     */
    @Column(name = "OFFICE_CODE")
    private String officeCode;
    
    /**
     * 部门顺序
     */
    @Column(name = "OFFICE_ORDER")
    private String officeOrder;

	private static final long serialVersionUID = 1L;

    public String getOfficeOrder() {
		return officeOrder;
	}

	public void setOfficeOrder(String officeOrder) {
		this.officeOrder = officeOrder;
	}

	/**
     * 获取编号
     *
     * @return OFFICE_ID - 编号
     */
    public String getOfficeId() {
        return officeId;
    }

    /**
     * 设置编号
     *
     * @param officeId 编号
     */
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    /**
     * 获取父级编号
     *
     * @return PARENT_ID - 父级编号
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父级编号
     *
     * @param parentId 父级编号
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取所有父级编号
     *
     * @return PARENT_IDS - 所有父级编号
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置所有父级编号
     *
     * @param parentIds 所有父级编号
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 获取名称
     *
     * @return OFFICE_NAME - 名称
     */
    public String getOfficeName() {
        return officeName;
    }

    /**
     * 设置名称
     *
     * @param officeName 名称
     */
    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    /**
     * 获取权重
     *
     * @return WEIGHT - 权重
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取归属区域
     *
     * @return AREA_ID - 归属区域
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置归属区域
     *
     * @param areaId 归属区域
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取区域编码
     *
     * @return AREA_CODE - 区域编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置区域编码
     *
     * @param areaCode 区域编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取机构类型
     *
     * @return TYPE - 机构类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置机构类型
     *
     * @param type 机构类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取机构等级
     *
     * @return GRADE - 机构等级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置机构等级
     *
     * @param grade 机构等级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取联系地址
     *
     * @return ADDRESS - 联系地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置联系地址
     *
     * @param address 联系地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮编
     *
     * @return ZIP_CODE - 邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取负责人
     *
     * @return MASTER - 负责人
     */
    public String getMaster() {
        return master;
    }

    /**
     * 设置负责人
     *
     * @param master 负责人
     */
    public void setMaster(String master) {
        this.master = master;
    }

    /**
     * 获取电话
     *
     * @return PHONE - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取传真
     *
     * @return FAX - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取邮箱
     *
     * @return EMAIL - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取主负责人
     *
     * @return PRIMARY_PERSON - 主负责人
     */
    public String getPrimaryPerson() {
        return primaryPerson;
    }

    /**
     * 设置主负责人
     *
     * @param primaryPerson 主负责人
     */
    public void setPrimaryPerson(String primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    /**
     * 获取副负责人
     *
     * @return DEPUTY_PERSON - 副负责人
     */
    public String getDeputyPerson() {
        return deputyPerson;
    }

    /**
     * 设置副负责人
     *
     * @param deputyPerson 副负责人
     */
    public void setDeputyPerson(String deputyPerson) {
        this.deputyPerson = deputyPerson;
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
     * 获取名称简称
     *
     * @return SIMPLE_NAME - 名称简称
     */
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * 设置名称简称
     *
     * @param simpleName 名称简称
     */
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    /**
     * 获取是否部门领导审批（114）
     *
     * @return BOOLSP - 是否部门领导审批（114）
     */
    public Integer getBoolsp() {
        return boolsp;
    }

    /**
     * 设置是否部门领导审批（114）
     *
     * @param boolsp 是否部门领导审批（114）
     */
    public void setBoolsp(Integer boolsp) {
        this.boolsp = boolsp;
    }

    /**
     * 获取删除标记
     *
     * @return DEL_FLAG - 删除标记
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记
     *
     * @param delFlag 删除标记
     */
    public void setDelFlag(Integer delFlag) {
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
     * 获取部门编号
     *
     * @return OFFICE_NUM - 部门编号
     */
    public String getOfficeNum() {
        return officeNum;
    }

    /**
     * 设置部门编号
     *
     * @param officeNum 部门编号
     */
    public void setOfficeNum(String officeNum) {
        this.officeNum = officeNum;
    }

    /**
     * 获取父级部门名称
     *
     * @return PARENT_NAME - 父级部门名称
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * 设置父级部门名称
     *
     * @param parentName 父级部门名称
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * 获取部门编码
     *
     * @return OFFICE_CODE - 部门编码
     */
    public String getOfficeCode() {
        return officeCode;
    }

    /**
     * 设置部门编码
     *
     * @param officeCode 部门编码
     */
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
}