package com.chinaoly.frm.process.service.impl;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.service.RoleService;
import com.chinaoly.frm.security.vo.AccountInfoVo;
import com.google.common.collect.Lists;

/**
 * 自定义 Group服务 
 * @author chenyl
 */
@Service
public class ActGroupEntityService extends GroupEntityManager {

	@Autowired
	private AccountInfoService accountInfoService;//用户
    
    @Autowired
    private RoleService roleService;//角色

    public Group createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {//通过userId查角色
    	if (userId == null)
    		 return null;
        List<Group> list = Lists.newArrayList();
        AccountInfoVo accountInfo = null;
		try {
			accountInfo = accountInfoService.findAccInfoById(userId);
	        if (accountInfo != null){
	            List<Role> roles = roleService.getRolesByAccountId(accountInfo.getId());
	            for(int i = 0;i < roles.size();i++ ){
		            list.add(toActivitiGroup(roles.get(i)));
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
    
    private  GroupEntity toActivitiGroup(Role role){
        if (role == null){
            return null;
        }
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(role.getId());
        groupEntity.setName(role.getRoleName());
        groupEntity.setType(role.getNameCn());
        groupEntity.setRevision(1);
        return groupEntity;
    }

}