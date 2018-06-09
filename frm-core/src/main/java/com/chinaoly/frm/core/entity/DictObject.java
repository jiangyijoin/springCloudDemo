/**
 * 用来转换数据格式用
 */
package com.chinaoly.frm.core.entity;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class DictObject {

	private String id;
	private String parentId;
	private String dictName;// 数据项名称
	private String dictTypeId;// 数据项中文标签
	private String value;// 数据项值
	private String order;// 排列顺序
	private String attribute;// 数据项属性
	private String description;//描述

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "DictObject [id=" + id + ", parentId=" + parentId + ", dictName=" + dictName + ", dictTypeId="
				+ dictTypeId + ", value=" + value + ", order=" + order + ", attribute=" + attribute + ", description="
				+ description + "]";
	}

	
	
}
