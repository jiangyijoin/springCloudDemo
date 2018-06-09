package com.chinaoly.frm.process.entity;

import java.io.Serializable;
import java.util.Date;

public class UserTest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String sex;
	private String msg;
	private Date beginTime;
	private Date endTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
