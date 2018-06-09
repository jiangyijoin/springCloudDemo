package com.chinaoly.frm.process.service.impl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Group服务Factory
 * @author chenyl
 */
public class ActGroupEntityServiceFactory implements SessionFactory {
    
    @Autowired
    private ActGroupEntityService actGroupEntityService;
   
    @Override
    public Class<?> getSessionType() {
        // 返回原始的GroupIdentityManager类型
        return GroupIdentityManager.class;
    }

	@Override
	public Session openSession() {
        // 返回自定义的GroupEntityManager实例
		return actGroupEntityService;
	}
	
	@Autowired
    public void setCustomGroupEntityManager(ActGroupEntityService actGroupEntityService) {
        this.actGroupEntityService = actGroupEntityService;
    }

}