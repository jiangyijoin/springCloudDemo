package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_DICT_TYPE")
public class DictType implements Serializable {
    /**
     * 类型id
     */
	@Id
    @Column(name = "ID")
    private String dictTypeId;

    /**
     * 字典类型
     */
    @Column(name = "DICT_TYPE")
    private String dictType;

    /**
     * 字典类型名称
     */
    @Column(name = "DICT_TYPE_NAME")
    private String dictTypeName;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "CREATE_ID")
    private String createId;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_ID")
    private String updateId;

    /**
     * 字典类型描述
     */
    @Column(name = "DICT_TYPE_DESCRIPTION")
    private String dictTypeDescription;

    @Column(name = "DEL_FLAG")
    private Integer delFlag;
   

    private static final long serialVersionUID = 1L;

	/**
     * 获取类型id
     *
     * @return DICT_TYPE_ID - 类型id
     */
    public String getDictTypeId() {
        return dictTypeId;
    }

    /**
     * 设置类型id
     *
     * @param dictTypeId 类型id
     */
    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    /**
     * 获取字典类型
     *
     * @return DICT_TYPE - 字典类型
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * 设置字典类型
     *
     * @param dictType 字典类型
     */
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    /**
     * 获取字典类型名称
     *
     * @return DICT_TYPE_NAME - 字典类型名称
     */
    public String getDictTypeName() {
        return dictTypeName;
    }

    /**
     * 设置字典类型名称
     *
     * @param dictTypeName 字典类型名称
     */
    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return CREATE_ID
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * @param createId
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return UPDATE_ID
     */
    public String getUpdateId() {
        return updateId;
    }

    /**
     * @param updateId
     */
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取字典类型描述
     *
     * @return DICT_TYPE_DESCRIPTION - 字典类型描述
     */
    public String getDictTypeDescription() {
        return dictTypeDescription;
    }

    /**
     * 设置字典类型描述
     *
     * @param dictTypeDescription 字典类型描述
     */
    public void setDictTypeDescription(String dictTypeDescription) {
        this.dictTypeDescription = dictTypeDescription;
    }

    /**
     * @return DEL_FLAG
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}