/**
 *
 */
package com.chinaoly.frm.core.utils.redis;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class RedisKeyConstants {

	public static String STATUS_UNLOGIN = "0"; // 未登录
	public static String STATUS_LOGINED = "1"; // 登录
	public static String STATUS_ENFORCEQUIT = "2"; // 强制退出

	public static String ONLINE_USER_ZSET = "onlineUser:list"; // 在线用户列表(Zset)
	public static String ONLINE_USER_NAME = "online:%s:username"; // 在线用户的基本信息

																	// (Hash)
	public static String SESSIONID_STATUS = "sessionid:%s:status"; // session状态
																	// (String)
	public static String USER_LOGIN_LOG = "userLogin:%s:log"; // 用户登录日志：logintime
																// + ip(List)
	public static String USER_LATE_PASSWORDS = "user:%s:password"; // 用户设置过的密码
																	// (List)
	public static String USER_PASSWORD_ERROR_TIMES = "userPasswordError:%s:times"; // 用户一段时刻内错误密码记录
	public static long LOGIN_TIME_INTERVAL = 2; // 登录时间间隔(分钟)
	public static int ALLOW_PASSWORD_TIMES = 15; // 允许密码错误次数，超过将会锁定用户
	public static String USER_LOCKED_TIME = "user:%s:lockedTime"; // 标识用户锁定状态（到锁定时间(分钟)，自动解锁）(String)

	public static final String USER_PSD_CHANGE_ERROR_TIMES = "userPsdChangeError:%s:times";// 用户修改密码时输入错误密码次数
	public static final int ALLOW_PSD_CHANGE_ERROR_TIMES = 3; // 用户修改密码时,允许密码错误次数，超过将会锁定用户
	public static final int USER_PSD_CHANGE_LOCKED_TIME = 30;// 用户修改密码时，输入密码错误次数过多被锁时间

	// 系统参数配置
	public static String SYSTEM_ARGUMENTS = "system:%s:arguments"; // 系统启动参数设置(Hash)

	// 用户菜单的访问量
	public static String USER_MENUS_VISITED = "menu:%s:userid"; // 用户常用的菜单访问量（Zset）
	public static String USER_VISITEDMENU_RANDOM = "user:%s:visited:menu:random"; // 用户访问菜单生成的随机码，用于标识别是否允许访问(Hash)

	// 部门
	public static String DEPARTMENT_RELATIONSHIP = "department:%s:children"; // 部门父节点与子节点的关系（Set）

	// 资源、权限菜单
	public static String RESOURCE_LIST = "resource:list"; // 所有资源的ID （Set） ???
	public static String RESOURCE_BASE_INFO = "resource:%s:info"; // 资源的基本信息（Hash）		keyi
	public static String RESOURCE_RELATIONSHIP = "resource:%s:children"; // 资源的上下级关系（Set）
	public static String RESOURCE_NAME = "resource:%s:name";// 资源名称与中文名称关系
	public static String MENU_ROLE_PERMISSION = "resource:%s:role"; // 资源与角色关系（Set）	keyi
	public static String ROLE_MENU_PERMISSION = "role:%s:resource";// 角色与资源关系(Set)			keyi
	public static String RESOURCE_NAME_LOCATION_REL = "resource:name:location:rel"; // 资源名称与权限地址的关系(Hash)

	// 用户角色
	public static String USER_ROLE = "user:%s:role"; // 用户拥有角色（Set） keyi

	// 系统通知
	public static String NOTIFICATION_BASE_INFO = "notification:%s:info"; // 通知消息基本信息（String）
	public static String NOTICE_REL_NOTIFICATIONS = "noticerel:%s:notification"; // 关联对象和消息的关系（Set）
	public static String NOTICE_USER_READ_NOTIFICATIONS = "noticeuserread:%s:notification";// 用户和已读消息的关系（Set）

	// 字典维护
	//public static String COMMONDATA_BASE_INFO = "dictdatas:%s:info"; // 字典数据维护基本信息（Hash）
	public static String DICTDATA_RELATIONSHIP = "dictdatas:%s:children"; // 字典数据上下级关系（Set）
	public static String DICTDATA_NAME_ID = "dictdatas:%s:id"; // 字典ID对应关系（String）

	// 用户请求日志记录
	public static String SYS_USER_QUERY_LOG = "USER_%s_REQ_LOG";// 某次登录日志集合（List）

	public static String CRSF_TOKEN = "crsf_token:%s";//每次请求验证的token，防御CRSF攻击


}
