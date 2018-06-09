package com.chinaoly.frm.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class CreateTable {
	/** 
	 * 创建表
	 */  
	public static void createTable_2() {  
		/*//activiti6.0
		ProcessEngine processEngine = ProcessEngineConfiguration  
				            .createProcessEngineConfigurationFromResource(  
					                    "activiti.cfg.xml").buildProcessEngine();  
				    System.err.println("processEngine" + processEngine);  */
		   // 工作流引擎的全部配置  activiti5.22
	    ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration  
	            .createStandaloneProcessEngineConfiguration();  
	  
	    // 链接数据的配置  
	    processEngineConfiguration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");  
	    processEngineConfiguration  
	            .setJdbcUrl("jdbc:oracle:thin:@192.168.1.151:1521:ORCL");  
	    processEngineConfiguration.setJdbcUsername("chinaoly");  
	    processEngineConfiguration.setJdbcPassword("chinaoly");  
	    
	   /* processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");  
	    processEngineConfiguration  
	            .setJdbcUrl("jdbc:mysql://localhost:3306/activity?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");  
	    processEngineConfiguration.setJdbcUsername("root");  
	    processEngineConfiguration.setJdbcPassword("");  */
	   
	     
	     // public static final String DB_SCHEMA_UPDATE_FALSE = "false"; 
	     // 不能自动创建表，需要表存在 public static final String DB_SCHEMA_UPDATE_CREATE_DROP 
	     // = "create-drop"; 先删除表再创建表 public static final String 
	     // DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表 
	       
	    //如果表不存在，自动创建表  
	    processEngineConfiguration  
	            .setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);  
	    // 工作流的核心对象，ProcessEnginee对象  
	    ProcessEngine processEngine = processEngineConfiguration  
	            .buildProcessEngine();  
	    System.err.println(processEngine);  
	}  
	public static void main(String[] args) {
		createTable_2();
	}
}
