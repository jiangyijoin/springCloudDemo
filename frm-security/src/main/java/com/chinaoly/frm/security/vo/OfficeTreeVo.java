package com.chinaoly.frm.security.vo;

import com.chinaoly.frm.security.Model.Office;

public class OfficeTreeVo extends Office implements Comparable<OfficeTreeVo>{

	private static final long serialVersionUID = 1L;
	private Long num;

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Override
	public int compareTo(OfficeTreeVo o) {
		int i = o.getCreateTime().compareTo(this.getCreateTime());
		if (i >= 0) {
			return -1;
		} else {
			return 1;
		} 
	}

}
