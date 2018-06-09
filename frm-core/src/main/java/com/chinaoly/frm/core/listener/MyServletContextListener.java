package com.chinaoly.frm.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chinaoly.frm.core.common.CommonUtils;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */

@WebListener
public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sce.getServletContext());
		CommonUtils.ac = ctx;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
