/**
 * 
 */
package com.chinaoly.frm.core.utils;

import com.chinaoly.frm.common.utils.security.SecureType;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class Constants {
	/**
	 * 密码在系统中的加密方式 默认SHA，web.xml设置SecureType初始化时会更改此项设置
	 */
	public static SecureType SYS_PASSWORD_ENCODE_MODE = SecureType.SHA;

	/**
	 * 是否需要和ecip对接 默认false，web.xml设置LinkECIP初始化时会更改此项设置
	 */
	public static boolean SYS_LINK_ECIP = false;
	
	/** 下拉列表全选 */
	public static final String COMBOX_SELECT_ALL = "_ALL_";
	public static final String COMBOX_SELECT_ALL_LABEL = "全选";

	/** 日志中的操作类型 */
	public static String SYSLOGSOPERATETYPE_QUERY = "01";// 查询
	public static String SYSLOGSOPERATETYPE_ADD = "02";// 添加
	public static String SYSLOGSOPERATETYPE_EDIT = "03";// 修改
	public static String SYSLOGSOPERATETYPE_DELETE = "04"; // 删除
	public static String SYSLOGSOPERATETYPE_DEPLOY = "05";// 发布
	public static String SYSLOGSOPERATETYPE_EXPORT = "06";// 数据导出
	public static String SYSLOGSOPERATETYPE_IMPORT = "07";// 数据导入
	public static String SYSLOGSOPERATETYPE_UPDATE_MEMORYDATA = "08";// 更新缓存数据
	public static String SYSLOGSOPERATETYPE_OTHER = "09";// 其它

	/** 系统用户对象 */
	public static String SYS_CURRENT_USER = "CurrentUserObject";
	public static String SYS_CURRENT_USER_DETAIL = "CurrentUserDetail";

	/** 高级管理员的用户级别值 */
	public static String SYS_SUPPERADMIN_LEVELVALUE = "0";

	/** 外部调用标识 */
	public static final String EXT_OPEN_FLAG = "EXTOPENFLAG";

	/** 外部调用系统名称 */
	public static final String EXT_SYSTEM_ID = "EXTSYSTEMID";

	/** 外部调用者工号 */
	public static final String EXT_USER_ID = "EXTUSERID";

	/** 外部调用模块名 */
	public static final String EXT_ENTRY_MODULE = "EXTENTRYMODULE";

	/** 外部调用登录参数 */
	public static final String EXT_ENTRYPARAMS = "ENTRYPARAMS";

	/** 外部调用模块参数 */
	public static final String EXT_MODULEPARAMS = "MODULEPARAMS";
	
	/** 外部调用扩展参数 */
	public static final String EXT_EXTENSIONPARAMS = "EXTEXTENSIONPARAMS";

	/** 在线用户与clientIp分割符号*/
	public static final String ONLINE_USER_SPLITER = "@@";
}
