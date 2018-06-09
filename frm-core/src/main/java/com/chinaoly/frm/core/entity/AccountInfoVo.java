package com.chinaoly.frm.core.entity;

import com.chinaoly.frm.common.utils.DateUtil;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 账户详情表
 * @author zhaohmz
 *
 */
public class AccountInfoVo {
	private static final long serialVersionUID = 1L;

	private String id; 	//id
	private String staffCard; 	//员工编号
	private String officeCode; 	//部门编码
	private String userLevel; 	//职级
	private String userName; 	//姓名
	private String nickName; 	//昵称
	private Integer sex; 		//性别
	private String email; 		//邮箱
	private String phone; 		//手机号
	private String minMobileNo; //移动短号
	private String fixedNo; 	//电话号码
	private String img; 		//头像
	private String idCard; 		//证件号码
	private String loginIp; 	//登录ip
	private Date loginTime; 	//登录时间
	private String serialNumber;//PKI序列号
	private Date registerDate;  //PKI注册时间
	private Date endDate; 		//PKI到期时间
	private Date accountExpiredStarttime;  //账号有效开始时间
	private Date accountExpiredEndtime;  	//账号失效时间

	@Transient
	private String accountName;
	@Transient
	private String password;
	@Transient
	private int accDelFlag;

	@Transient
	private String officeName;

	@Transient
	private String levelName;


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setAccountExpiredStarttime(Date accountExpiredStarttime) {
		this.accountExpiredStarttime = accountExpiredStarttime;
	}
	public void setAccountExpiredEndtime(Date accountExpiredEndtime) {
		this.accountExpiredEndtime = accountExpiredEndtime;
	}
	public String getStaffCard() {
		return staffCard;
	}
	public void setStaffCard(String staffCard) {
		this.staffCard = staffCard;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMinMobileNo() {
		return minMobileNo;
	}
	public void setMinMobileNo(String minMobileNo) {
		this.minMobileNo = minMobileNo;
	}
	public String getFixedNo() {
		return fixedNo;
	}
	public void setFixedNo(String fixedNo) {
		this.fixedNo = fixedNo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		if(registerDate == null ) return;
		this.registerDate = DateUtil.convertStringToDate(DateUtil.fullPattern,registerDate);
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate){
		if(endDate == null ) return;
		this.endDate = DateUtil.convertStringToDate(DateUtil.fullPattern,endDate);
	}

	public Date getAccountExpiredStarttime() {
		return accountExpiredStarttime;
	}
	public void setAccountExpiredStarttime(String accountExpiredStarttime) {
		if(accountExpiredStarttime == null ) return;
		this.accountExpiredStarttime = DateUtil.convertStringToDate(DateUtil.fullPattern,accountExpiredStarttime);
	}
	public Date getAccountExpiredEndtime() {
		return accountExpiredEndtime;
	}
	public void setAccountExpiredEndtime(String accountExpiredEndtime) {
		if(accountExpiredEndtime == null ) return;
		this.accountExpiredEndtime = DateUtil.convertStringToDate(DateUtil.fullPattern,accountExpiredEndtime);
	}

	public int getAccDelFlag() {
		return accDelFlag;
	}
	public void setAccDelFlag(int accDelFlag) {
		this.accDelFlag = accDelFlag;
	}
	@Override
	public String toString() {
		return "AccountInfoVo [id=" + id + ", staffCard=" + staffCard + ", officeCode=" + officeCode + ", userLevel="
				+ userLevel + ", userName=" + userName + ", nickName=" + nickName + ", sex=" + sex + ", email=" + email
				+ ", phone=" + phone + ", minMobileNo=" + minMobileNo + ", fixedNo=" + fixedNo + ", img=" + img
				+ ", idCard=" + idCard + ", loginIp=" + loginIp + ", loginTime=" + loginTime + ", serialNumber="
				+ serialNumber + ", registerDate=" + registerDate + ", endDate=" + endDate
				+ ", accountExpiredStarttime=" + accountExpiredStarttime + ", accountExpiredEndtime="
				+ accountExpiredEndtime + ", accountName=" + accountName + ", password=" + password + ", accDelFlag="
				+ accDelFlag +  ", officeName=" + officeName + ", levelName=" + levelName
				+  "]";
	}

}
