package com.chinaoly.frm.security.vo;

import com.chinaoly.frm.security.Model.Dict;

public class DictTreeVo extends Dict implements Comparable<DictTreeVo>{

	private static final long serialVersionUID = 1L;
	private Long num;
	
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	@Override
	public int compareTo(DictTreeVo o) {
		int i = o.getId().hashCode() - this.getId().hashCode();
		if (i >= 0) {
			return -1;
		} else {
			return 1;
		} 
	}
	
}
