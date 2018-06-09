package com.chinaoly.frm.log.aop;

public enum methodType {
	/**
	 * 新增
	 */
	SAVE("新增"),
	/**
	 * 修改
	 */
	UPDATE("修改"),
	/**
	 * 删除
	 */
	DELETE("删除"),
	/**
	 * 查询
	 */
	SEARCH("查询");
	public String value;

	private methodType(String value){
		this.value=value;
	}

}
