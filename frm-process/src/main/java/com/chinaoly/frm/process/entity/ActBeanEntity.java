package com.chinaoly.frm.process.entity;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.chinaoly.frm.process.service.impl.ActGroupEntityService;
import com.chinaoly.frm.process.service.impl.ActGroupEntityServiceFactory;
import com.chinaoly.frm.process.service.impl.ActUserEntityService;
import com.chinaoly.frm.process.service.impl.ActUserEntityServiceFactory;


@Configuration
public class ActBeanEntity {
	Log logger = LogFactory.getLog(ActBeanEntity.class);
	
	@Bean(name = "processEngineConfiguration")
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
		logger.info("进来了");
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        List<SessionFactory> customSessionFactories = new ArrayList<SessionFactory>();
        customSessionFactories.add(new ActUserEntityServiceFactory());
        customSessionFactories.add(new ActGroupEntityServiceFactory());
        processEngineConfiguration.setCustomSessionFactories(customSessionFactories);
        processEngineConfiguration.setDbIdentityUsed(false);

		logger.info("结束了");
        return processEngineConfiguration;
    }
}
