/**
 * 
 */
package com.chinaoly.frm.core.rest;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class BaseRestController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获得当前用户IP
	 *
	 * @return
	 */
	protected String getCurrentUserIP(HttpServletRequest request) {

		// 取用户IP
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))  {
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException unknownhostexception) {
			}
		}
		return ip;
	}

	/**
	 * 获取ACTION请求的模块名称
	 *
	 * @param request
	 * @return
	 */
	protected String getModuleName(HttpServletRequest request) {
		String moduleName = "unconfigured";
//		long lStart = Calendar.getInstance().getTimeInMillis();
//		String reqUrl = CommonUtil.getRequestURL(request);
//		if (reqUrl != null && reqUrl.trim().length() > 0) {
//			String modules = baseService.getModuleNameByRequestUrl(reqUrl);
//			if (!"".equals(modules) && modules.indexOf(":") > -1) {
//				String[] moduleArr = modules.split(":");
//				moduleName = moduleArr[0];
//			}
//		}
//		long end = Calendar.getInstance().getTimeInMillis();
//		log.info("moduleName:" + moduleName + "时间：" + (end - lStart));
		return moduleName;
	}

	/**
	 * 获得服务IP
	 *
	 * @return
	 */
	protected String getApp_IP() {
		// 取服务IP
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	/**
	 * 获取当前系统语言
	 *
	 * @return
	 */
	public String getI18NLocale() {
//		String locale = baseService.getCommonFieldValueByName("current_local", "value");
//		if (StringUtils.isBlank(locale)) {
//			locale = "zh_CN";// 默认简体中文
//		}
		return null;
	}

	/**
	 * 获取登录页面是否需要图片验证码
	 *
	 * @return
	 */
	public String getVerifyImage() {
//		String authCodeFlag = baseService.getCommonFieldValueByName("login_verification_code", "value");
//		if (StringUtils.isBlank(authCodeFlag)) {
//			authCodeFlag = "0";// 没有配置则无验证码
//		}
		return null;
	}

	/**
	 * 获取当前国际化配置文件
	 *
	 * @return
	 */
	protected ResourceBundle getCurrentRb() {
//		String[] l = getI18NLocale().split("_");
//		Locale locale = new Locale(l[0], l[1]);
//		if (rb == null || !locale.equals(rb.getLocale())) {
//			rb = ResourceBundle.getBundle("i18n.ApplicationBundle", locale);
//		}
		return null;
	}


	public int getLogsAuditTimeLimit() {
//		String time_limit = baseService.getCommonFieldValueByName("monitor_logs_time_limit", "value");
//		if (StringUtils.isBlank(time_limit)) {
//			time_limit = "5";// 默认监控5分钟
//		}
//		try {
//			int tl = Integer.parseInt(time_limit);
//			return tl;
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			return 5;
//		}
		return 0;
	}

	public int getLogsAuditThreshold() {
//		String threshold = baseService.getCommonFieldValueByName("login_times_threshold", "value");
//		if (StringUtils.isBlank(threshold)) {
//			threshold = "5";// 默认阈值5
//		}
//		try {
//			int th_ = Integer.parseInt(threshold);
//			return th_;
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			return 5;
//		}
		return 0;
	}

	/**
	 * 系统日志记录
	 *
	 * @param request
	 * @param resultState
	 *            操作结果 成功为true,失败为false
	 * @param operateType
	 *            操作类型 在Constants中已经列出全部类型，记录时在其中选择即可
	 * @param moduleName
	 *            模块名称 一般与菜单名一致
	 * @param msg
	 *            操作信息 包括操作的条件
	 * @param opttime
	 *            记录本次操作所花费的时间
	 */
	protected void saveSysLog(HttpServletRequest request, boolean resultState, String operateType, String moduleName, String msg, int opttime) {
//		baseService.saveSysLog(request, getApp_IP(), getCurrentUserIP(request), this.getUser().getUserName(),
//				resultState ? "1" : "0", operateType, moduleName, msg, opttime, DateUtil.getCurrentDatetime());
	}

	/**
	 * 系统日志记录
	 *
	 * @param userIp
	 *            用户IP
	 * @param username
	 *            用户名
	 * @param resultState
	 *            操作结果 成功为true,失败为false
	 * @param operateType
	 *            操作类型 在Constants中已经列出全部类型，记录时在其中选择即可
	 * @param moduleName
	 *            模块名称 一般与菜单名一致
	 * @param msg
	 *            操作信息 包括操作的条件
	 * @param opttime
	 *            记录本次操作所花费的时间
	 */
	protected void saveSysLog(String userIp, String username, boolean resultState, String operateType,
							  String moduleName, String msg, int opttime) {
//		baseService.saveSysLog(getApp_IP(), userIp, username, resultState ? "1" : "0", operateType, moduleName, msg,
//				opttime, DateUtil.getCurrentDatetime());
	}

	/**
	 * 获取当前用户
	 *
	 * @return User
	 */
//	public ShiroUser getUser() {
//		return CommonUtil.getUser();
//	}

	/**
	 * 判断用户是否是超级管理员
	 *
	 * @param user
	 * @return
	 */
//	protected boolean isSuperAdmin(ShiroUser user) {
//		return user.getUserLevel().equals("1") ? true : false;
//	}

	/**
	 * 获取用户角色
	 *
	 * @return
	 */
//	protected List<String> getUserRoles() {
//		ShiroUser user = getUser();
//		return getUserRoles(user.getId());
//	}

	/**
	 * 获取当前用户的角色
	 *
	 * @param userId
	 * @return
	 */
//	protected List<String> getUserRoles(String userId) {
//		return baseService.getUserRoles(userId);
//	}

	/**
	 *
	 * 方法说明：获取当前用户的区域列表
	 *
	 * @Title: getUserAreas
	 * @author: fangqian
	 * @param userId
	 * @return
	 * @return List<String>
	 * @date: 2015年10月26日 下午2:51:17
	 */
//	protected List<String> getUserAreas(String userId) {
//		return baseService.getUserAreas(userId);
//	}

	/**
	 * 根据父节点的名称，从SYS_COMMONDATAS表中取所有此节点下的数据。本函数主要用在取下拉列表框的值
	 *
	 * @param parentName
	 *            父节点的名称，对应预表SYS_COMMONDATAS的NAME_字段。
	 * @return
	 */
//	protected List<CommonObject> getCommonList(String parentName) {
//		return baseService.getCommonList(parentName);
//	}

	/**
	 * 增加返回信息数据
	 *
	 * @param type
	 *            error-失败，success-成功
	 * @param msg
	 *            结果返回消息
	 * @param data
	 *            返回数据
	 */
//	protected void addResultInfo(String type, String msg, Object data) {
//		String rstType = BaseController.SUCCESS.equals(type) ? "true" : "false";
//
//		this.getResult().put("success", rstType);
//		this.getResult().put("msg", msg);
//		this.getResult().put("data", data);
//	}
//
//	protected Map<String, Object> getResult() {
//		if (this.result == null) {
//			this.result = new HashMap<String, Object>();
//		}
//		return result;
//	}
//
//	protected String getResultJSONStr() {
//		return JSONObject.fromObject(getResult()).toString();
//	}

	/**
	 * 将数据转换成JSON字符串数据
	 *
	 * @param data
	 * @return
	 */
	protected String getResultJSONStr(Object data) {
		return JSONObject.fromObject(data).toString();
	}

//	protected void setResult(Map<String, Object> result) {
//		this.result = result;
//	}

	protected void responseResult(HttpServletResponse response, String rslt) {
		try {
			PrintWriter out = response.getWriter();
			out.print(rslt);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * 方法说明：获取系统监控管理刷新时间
	 *
	 * @Title: getI18NLocale
	 * @author: fangqian
	 * @return
	 * @return String
	 * @date: 2015年12月1日 下午2:16:03
	 */
//	public String getSysmonitorRefreshTime() {
//		String refreshTime = baseService.getCommonFieldValueByName("sysmonitor_refreshTime", "value");
//		if (StringUtils.isBlank(refreshTime)) {
//			refreshTime = "300000";// 默认刷新时间为5分钟
//		}
//		return refreshTime;
//	}

//	public SmsResponse sendMsg(String mobileNo, String content) {
//		String beanId = baseService.getCommonFieldValueByName("smsSendMsgServiceId", "value");
//		if (StringUtils.isBlank(beanId)) {
//			beanId = "baseSmsService";
//		}
//		Object _bean = AppCtxUtils.getApplicationContext().getBean(beanId);
//		if (_bean != null) {
//			BaseSmsService service = (BaseSmsService) _bean;
//			SmsReq req = new SmsReq();
//			req.setMobileNo(mobileNo);
//			req.setContent(content);
//
//			SmsResponse resp = service.send(req);
//			return resp;
//		} else {
//			return null;
//		}
//	}

}
