package com.chinaoly.frm.core.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class IdEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 唯一ID
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**
	 * 获取唯一ID
	 *
	 * @return ID - 唯一ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置唯一ID
	 *
	 * @param id 唯一ID
	 */
	public void setId(String id) {
		this.id = id;
	}

}
