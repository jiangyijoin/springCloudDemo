package com.chinaoly.frm.process.service.impl;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.service.RoleService;
import com.chinaoly.frm.security.vo.AccountInfoVo;
import com.google.common.collect.Lists;
 
/**
 * 自定义user 服务
 * @author chenyl
 */
@Service
public class ActUserEntityService extends UserEntityManager {

	@Autowired
	private AccountInfoService accountInfoService;//用户
    @Autowired
    private RoleService roleService;//角色
     
    public User createNewUser(String userId) {
        return new UserEntity(userId);
    }
 
    @Override
    public UserEntity findUserById(String userId) {// 通过userId查用户
    	AccountInfoVo user = null;
    	try {
			user = accountInfoService.findAccInfoById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return toActivitiUser(user);
    }
 
    private UserEntity toActivitiUser(AccountInfoVo user){
        if (user == null){
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getAccountName());
        userEntity.setLastName(StringUtils.EMPTY);
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(null);
        userEntity.setRevision(1);
        return userEntity;
    }
    
    @Override
    public List<Group> findGroupsByUser(final String userId) {
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