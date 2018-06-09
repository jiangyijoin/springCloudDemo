package com.chinaoly.frm.security.vo;


/**
 * 应用表
 * @author zhaohmz
 *
 */
public class ClientVo {
	private static final long serialVersionUID = 1L;

	private String id; 				//应用id
	private String clientPassword; 	//应用密码
	private String clientName; 		//应用名
	private String remark; 			//应用备注
	private Integer delFlag; 		//删除标记 0可用1不可用
	private String clientUrl; 		//应用路径
	private String clientImg; 		//应用图片
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientPassword() {
		return clientPassword;
	}
	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getClientUrl() {
		return clientUrl;
	}
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}
	public String getClientImg() {
		return clientImg;
	}
	public void setClientImg(String clientImg) {
		this.clientImg = clientImg;
	}
	@Override
	public String toString() {
		return "ClientVo [id=" + id + ", clientPassword=" + clientPassword + ", clientName=" + clientName + ", remark="
				+ remark + ", delFlag=" + delFlag + ", clientUrl=" + clientUrl + ", clientImg=" + clientImg + "]";
	}
	
	
}
