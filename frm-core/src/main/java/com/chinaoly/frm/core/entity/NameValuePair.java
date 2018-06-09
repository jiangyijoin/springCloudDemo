package com.chinaoly.frm.core.entity;

import java.util.Map;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class NameValuePair {
	private String name;
	private String value;
	private Map Extension;

	public NameValuePair() {
	}

	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map getExtension() {
		return Extension;
	}

	public void setExtension(Map extension) {
		Extension = extension;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameValuePair) {
			return ((NameValuePair) obj).getName().equals(this.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return "name:" + name == null ? ""
				: name + ",value:" + value == null ? "" : value;
	}

}
