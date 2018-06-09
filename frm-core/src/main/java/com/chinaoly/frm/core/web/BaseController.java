/**
 * 基础控制层方法封装，包括：
 * 日志记录：saveSysLog
 * 获取当前用户IP:getCurrentUserIP
 * 服务器的IP:getApp_IP
 * 当前用户:getUser
 * 基础数据下级节点：getCommonList
 * 包装返回数据：addResultInfo
 */
package com.chinaoly.frm.core.web;


import com.chinaoly.frm.core.common.DateJsonValueProcessor;
import com.chinaoly.frm.core.entity.AccountInfoVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author jiangyi
 * @Date Mar 15, 2018
 */
public class BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";


    public static final String DATEFORMAT = "yyyy-MM-dd hh:mm:ss";


    // 定义一个用来包装返回消息的map变量
    private Map<String, Object> result;

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
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException unknownhostexception) {
            }
        }
        return ip;
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
     * 增加返回信息数据
     *
     * @param type error-失败，success-成功
     * @param msg  结果返回消息
     * @param data 返回数据
     */
    protected void addResultInfo(String type, String msg, Object data) {
        String rstType = BaseController.SUCCESS.equals(type) ? "true" : "false";

        this.getResult().put("success", rstType);
        this.getResult().put("msg", msg);
        this.getResult().put("data", data);
    }

    protected Map<String, Object> getResult() {
        if (this.result == null) {
            this.result = new HashMap<String, Object>();
        }
        return result;
    }

    protected String getResultJSONStr() {
        JSONArray ja = new JSONArray();
        JsonConfig jf = new JsonConfig();
        jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor(DATEFORMAT));
        jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor(DATEFORMAT));
        return ja.fromObject(getResult(), jf).toString();
    }

    /**
     * 将数据转换成JSON字符串数据
     *
     * @param data
     * @return
     */
    protected String getResultJSONStr(Object data) {
        return JSONObject.fromObject(data).toString();
    }

    protected void setResult(Map<String, Object> result) {
        this.result = result;
    }

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

    @ExceptionHandler({Throwable.class})
	@ResponseBody
	protected void operateExp(HttpServletRequest request ,HttpServletResponse response,Exception ex) throws Exception{
		log.info("======================= 异常信息记录   =======================");
		log.error(ex.getMessage(), ex);
		addResultInfo(BaseController.FAILURE, "操作失败", null);
		responseResult(response, this.getResultJSONStr());
	}

    /**
     *
     * 方法说明：获取当前用户信息
     *
     */
    public AccountInfoVo getUser() {
        AccountInfoVo accountInfoVo = null;
        try {
            String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
            String username = (String) SecurityUtils.getSubject().getSession().getAttribute(token);
            //accountInfoVo = accountInfoService.findAccInfoByAccName(username);
        } catch (Exception e) {
            log.error("获取当前登录用户失败，请检查是否已经正确登录，当前返回unkonwn用户。");
        }
        return accountInfoVo == null ? new AccountInfoVo() : accountInfoVo;
    }



}
