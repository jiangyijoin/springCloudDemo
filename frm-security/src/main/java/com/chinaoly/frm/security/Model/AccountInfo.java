package com.chinaoly.frm.security.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_SYS_ACCOUNT_INFO")
public class AccountInfo implements Serializable {
    /**
     * 帐号id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 员工编号
     */
    @Column(name = "STAFF_CARD")
    private String staffCard;

    /**
     * 部门编码
     */
    @Column(name = "OFFICE_CODE")
    private String officeCode;

    /**
     * 姓名
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 昵称
     */
    @Column(name = "NICK_NAME")
    private String nickName;

    /**
     * 性别
     */
    @Column(name = "SEX")
    private Short sex;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 手机号
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 移动短号
     */
    @Column(name = "MIN_MOBILE_NO")
    private String minMobileNo;

    /**
     * 电话号码
     */
    @Column(name = "FIXED_NO")
    private String fixedNo;

    /**
     * 头像
     */
    @Column(name = "IMG")
    private String img;

    /**
     * 证件号码
     */
    @Column(name = "ID_CARD")
    private String idCard;

    /**
     * 登录ip
     */
    @Column(name = "LOGIN_IP")
    private String loginIp;

    /**
     * 登录时间
     */
    @Column(name = "LOGIN_TIME")
    private Date loginTime;

    /**
     * PKI序列号
     */
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    /**
     * PKI注册时间
     */
    @Column(name = "REGISTER_DATE")
    private Date registerDate;

    /**
     * PKI到期时间
     */
    @Column(name = "END_DATE")
    private Date endDate;

    /**
     * 账号有效开始时间
     */
    @Column(name = "ACCOUNT_EXPIRED_STARTTIME")
    private Date accountExpiredStarttime;

    /**
     * 账号失效时间
     */
    @Column(name = "ACCOUNT_EXPIRED_ENDTIME")
    private Date accountExpiredEndtime;

    /**
     * 职级
     */
    @Column(name = "USER_LEVEL")
    private String userLevel;

    private static final long serialVersionUID = 1L;

    /**
     * 获取帐号id
     *
     * @return ID - 帐号id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置帐号id
     *
     * @param id 帐号id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取员工编号
     *
     * @return STAFF_CARD - 员工编号
     */
    public String getStaffCard() {
        return staffCard;
    }

    /**
     * 设置员工编号
     *
     * @param staffCard 员工编号
     */
    public void setStaffCard(String staffCard) {
        this.staffCard = staffCard;
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

    /**
     * 获取姓名
     *
     * @return USER_NAME - 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置姓名
     *
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取昵称
     *
     * @return NICK_NAME - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取性别
     *
     * @return SEX - 性别
     */
    public Short getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(Short sex) {
        this.sex = sex;
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
     * 获取手机号
     *
     * @return PHONE - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取移动短号
     *
     * @return MIN_MOBILE_NO - 移动短号
     */
    public String getMinMobileNo() {
        return minMobileNo;
    }

    /**
     * 设置移动短号
     *
     * @param minMobileNo 移动短号
     */
    public void setMinMobileNo(String minMobileNo) {
        this.minMobileNo = minMobileNo;
    }

    /**
     * 获取电话号码
     *
     * @return FIXED_NO - 电话号码
     */
    public String getFixedNo() {
        return fixedNo;
    }

    /**
     * 设置电话号码
     *
     * @param fixedNo 电话号码
     */
    public void setFixedNo(String fixedNo) {
        this.fixedNo = fixedNo;
    }

    /**
     * 获取头像
     *
     * @return IMG - 头像
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置头像
     *
     * @param img 头像
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取证件号码
     *
     * @return ID_CARD - 证件号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置证件号码
     *
     * @param idCard 证件号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取登录ip
     *
     * @return LOGIN_IP - 登录ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置登录ip
     *
     * @param loginIp 登录ip
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取登录时间
     *
     * @return LOGIN_TIME - 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登录时间
     *
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取PKI序列号
     *
     * @return SERIAL_NUMBER - PKI序列号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置PKI序列号
     *
     * @param serialNumber PKI序列号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 获取PKI注册时间
     *
     * @return REGISTER_DATE - PKI注册时间
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置PKI注册时间
     *
     * @param registerDate PKI注册时间
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获取PKI到期时间
     *
     * @return END_DATA - PKI到期时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置PKI到期时间
     *
     * @param endData PKI到期时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取账号有效开始时间
     *
     * @return ACCOUNT_EXPIRED_STARTTIME - 账号有效开始时间
     */
    public Date getAccountExpiredStarttime() {
        return accountExpiredStarttime;
    }

    /**
     * 设置账号有效开始时间
     *
     * @param accountExpiredStarttime 账号有效开始时间
     */
    public void setAccountExpiredStarttime(Date accountExpiredStarttime) {
        this.accountExpiredStarttime = accountExpiredStarttime;
    }

    /**
     * 获取账号失效时间
     *
     * @return ACCOUNT_EXPIRED_ENDTIME - 账号失效时间
     */
    public Date getAccountExpiredEndtime() {
        return accountExpiredEndtime;
    }

    /**
     * 设置账号失效时间
     *
     * @param accountExpiredEndtime 账号失效时间
     */
    public void setAccountExpiredEndtime(Date accountExpiredEndtime) {
        this.accountExpiredEndtime = accountExpiredEndtime;
    }

    /**
     * 获取职级
     *
     * @return USER_LEVEL - 职级
     */
    public String getUserLevel() {
        return userLevel;
    }

    /**
     * 设置职级
     *
     * @param userLevel 职级
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}