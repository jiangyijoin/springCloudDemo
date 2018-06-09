package com.chinaoly.frm.core.common;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class CommonUtils {

	public static ApplicationContext ac;
	private static Logger log = LoggerFactory.getLogger(CommonUtils.class);
	
	public static Object getBean(String name){
		if(StringUtils.isNoneBlank(name)){
			return ac.getBean(name);
		}
		return null;
	}
	
	public static void setApplicationCtx(HttpServletRequest request){
		if(ac==null){
			log.info("ApplicationContext未被初始化");
		}
		Environment env = ac.getEnvironment();
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "ckcest.");
		Map<String, Object> dsMap = propertyResolver.getSubProperties("js.");
		for(Entry<String, Object> entity:dsMap.entrySet()){
			request.setAttribute(entity.getKey(), entity.getValue());
		}
		
		request.setAttribute("ctx", request.getContextPath());
	}
}
