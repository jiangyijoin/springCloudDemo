package com.chinaoly.frm.log.entity;

import java.util.Date;


public class LogEntity implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;		//编号Id
	private String status;	//状态 0正常1异常
	private String requestUrl;//请求URI
	private String title;	//日志标题
	private String method;	//操作方式
	private String methodNotes;//方法说明
	private String params;	//参数
	private String ip;		//操作IP地址
	private int qryusedTime;//模块执行时间
	private String userAgent;//用户代理
	private String exception;//异常原因
	private Date createTime; //操作时间
	private String createId; //操作人id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethodNotes() {
		return methodNotes;
	}
	public void setMethodNotes(String methodNotes) {
		this.methodNotes = methodNotes;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getQryusedTime() {
		return qryusedTime;
	}
	public void setQryusedTime(int qryusedTime) {
		this.qryusedTime = qryusedTime;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	@Override
	public String toString() {
		return "LogEntity [id=" + id + ", status=" + status + ", requestUrl=" + requestUrl + ", title=" + title
				+ ", method=" + method + ", methodNotes=" + methodNotes + ", params=" + params + ", ip=" + ip
				+ ", qryusedTime=" + qryusedTime + ", userAgent=" + userAgent + ", exception=" + exception
				+ ", createTime=" + createTime + ", createId=" + createId + "]";
	}
	
	
}
