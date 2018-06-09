package com.chinaoly.frm.process.service.impl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义user 服务Factory
 * @author chenyl
 */
public class ActUserEntityServiceFactory implements SessionFactory {
    
    @Autowired
    private ActUserEntityService actUserEntityService;

    @Override
    public Class<?> getSessionType() {
       //注意此处也必须为Activiti原生类
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return actUserEntityService;
    }

    @Autowired
    public void setCustomUserEntityManager(ActUserEntityService actUserEntityService) {
        this.actUserEntityService = actUserEntityService;
    }

}