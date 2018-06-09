package com.chinaoly.frm.log.aop;

import com.chinaoly.frm.log.entity.LogEntity;
import com.chinaoly.frm.log.service.LogService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 切点类
 *
 * @author Zhaohmz
 * @since 2018年5月22日
 * @version 1.0
 */
@Aspect
@Component
public class SystemLogAspect {
    // 注入Service用于把日志保存Updates数据库
    @Autowired
    private LogService logService;

    // 本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    // Controller层切点
    @Pointcut("@annotation(com.chinaoly.frm.log.aop.LogSave)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
             * @param joinPoint 切点
     * @throws Throwable
     */
    @Around("controllerAspect() && @annotation(rl)")
    public Object aroundlog(ProceedingJoinPoint joinPoint, LogSave rl) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        // 获取注解赋的值
        String title = rl.title();
        // 请求的IP
        String ip = request.getRemoteAddr();
        // 获取User_Agent
        String userAgent = request.getHeader("User-Agent");
        // 获取请求url
        String requrl = request.getRequestURI();
        // 获取请求方法
        String methodName = joinPoint.getSignature().toString();
        //获取请求参数名
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        // 获取用户请求方法的参数
        String params = toJSONParameters(argNames ,args);

        // *========数据库日志=========*//
        LogEntity mylog = setLog(title,requrl,methodName,rl.methodType().value,params,ip,userAgent,"0",null);
        Long startTime = System.currentTimeMillis();
        Object object = null;
        try {
        	logger.info("==begin to execute method [ "+joinPoint.getSignature().getName()+" ] ==\"[URL] : \" + requrl "+"[Parameters] : " + params);
        	startTime = System.currentTimeMillis();
            object = joinPoint.proceed();
            logger.info("==end method[ "+ joinPoint.getSignature().getName() + " ]");
        } catch (Throwable e1) {
            mylog.setException(e1.getMessage());
            mylog.setStatus("1");
            throw e1;
        }finally {
        	 Long endTime = System.currentTimeMillis();
			 mylog.setQryusedTime((int) (endTime-startTime));
             logService.addLog(mylog);
        }
        return object;
    }




    /**
     * 将参数赋值给一个Log对象
     *
     * @param title
     * @param requrl
     * @param methodName
     * @param methodNotes
     * @param params
     * @param ip
     * @param userAgent
     * @param status
     * @param e
     * @return
     */
    private LogEntity setLog(String title, String requrl, String methodName, String methodNotes,
    		String params, String ip,String userAgent, String status, Throwable e) {

    	LogEntity mylog = new LogEntity();
    	mylog.setTitle(title);
        mylog.setRequestUrl(requrl);
        mylog.setMethod(methodName);
        mylog.setMethodNotes(methodNotes);
        mylog.setParams(params);
        mylog.setIp(ip);
        mylog.setUserAgent(userAgent);
        mylog.setStatus(status);
        if(e != null) {
        	 mylog.setException(e.getMessage());
        }

        return mylog;

    }
    /**
     * 此方法获得所有上传的参数，将之转化为json字符串返回
     *		eg: {"id":"123","name":"mark"}
     * @param request
     * @return
     */
    private String toJSONParameters(String[]  argNames ,Object[] args) {
    	String params = "{";
    	for(int i = 2 ; i<argNames.length ; i++) {
    		if(argNames[i] == null || argNames[i].length() == 0)
    			argNames[i] = "";
    		if(args[i] == null || args[i].toString().trim().length() == 0)
    			args[i] = "";
    		params += "\""+argNames[i]+"\":\""+args[i] + "\",";
    	}
        if(params.length() == 1) {
        	params += "}";
        }else {
        	params = params.substring(0, params.length()-1) + "}";
        }

        return params;
    }

}
