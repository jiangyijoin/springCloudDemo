package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_DICT")
public class Dict implements Serializable {
    /**
     * 字典表主键id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称
     */
    @Column(name = "DICT_NAME")
    private String dictName;

    /**
     * 值
     */
    @Column(name = "VALUE")
    private String value;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 权重
     */
    @Column(name = "WEIGHT")
    private Integer weight;

    /**
     * 父类id
     */
    @Column(name = "PARENT_ID")
    private String parentId;

    /**
     * 删除标记,0可用,1不可用
     */
    @Column(name = "DEL_FLAG")
    private Integer delFlag;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建者账号id
     */
    @Column(name = "CREATE_ID")
    private String createId;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新账号id
     */
    @Column(name = "UPDATE_ID")
    private String updateId;

    /**
     * 类型id
     */
    @Column(name = "DICT_TYPE_ID")
    private String dictTypeId;

    private static final long serialVersionUID = 1L;

	/**
     * 获取字典表主键id
     *
     * @return ID - 字典表主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置字典表主键id
     *
     * @param id 字典表主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return DICT_NAME - 名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置名称
     *
     * @param dictName 名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取值
     *
     * @return VALUE - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取描述
     *
     * @return DESCRIPTION - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取父类id
     *
     * @return PARENT_ID - 父类id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父类id
     *
     * @param parentId 父类id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取删除标记,0可用,1不可用
     *
     * @return DEL_FLAG - 删除标记,0可用,1不可用
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记,0可用,1不可用
     *
     * @param delFlag 删除标记,0可用,1不可用
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
     * 获取创建者账号id
     *
     * @return CREATE_ID - 创建者账号id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建者账号id
     *
     * @param createId 创建者账号id
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
     * 获取更新账号id
     *
     * @return UPDATE_ID - 更新账号id
     */
    public String getUpdateId() {
        return updateId;
    }

    /**
     * 设置更新账号id
     *
     * @param updateId 更新账号id
     */
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

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

	@Override
	public String toString() {
		return "Dict [id=" + id + ", dictName=" + dictName + ", value=" + value + ", description=" + description
				+ ", weight=" + weight + ", parentId=" + parentId + ", delFlag=" + delFlag + ", createTime="
				+ createTime + ", createId=" + createId + ", updateTime=" + updateTime + ", updateId=" + updateId
				+ ", dictTypeId=" + dictTypeId + "]";
	}
    
    
}