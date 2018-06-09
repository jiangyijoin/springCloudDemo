package com.chinaoly.frm.common.utils.security;

/**
 * 加密方式
 * 
 * @author jiangyi
 *
 */
public enum SecureType {
	MD5, SHA;
	private static String _MD5_ = "MD5";
	private static String _SHA_ = "SHA";

	public String toString() {
		String str = "";
		switch (this) {
		case MD5:
			str = _MD5_;
			break;
		case SHA:
			str = _SHA_;
			break;
		}
		return str;
	}
}
