package com.chinaoly.frm.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinaoly.frm.core.baseDao.redis.RedisKeyValuesDao;
import com.chinaoly.frm.security.Model.Dict;
import com.chinaoly.frm.security.Model.Resources;
import com.chinaoly.frm.security.vo.ResourceVo;
import com.chinaoly.frm.security.vo.RoleVo;

@Component
public class RedisTableSynService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RedisKeyValuesDao redisKeyValuesDao;
	
	@Autowired
	private DictService dictService;
	
	public static final String DATEFORMAT = "yyyy-MM-dd hh:mm:ss";
	
	/**
	 * 同步资源表
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sycResourceTable() throws Exception{
		// key: RESOURCE_BASE_INFO = "resource:%s:info"; 资源的基本信息（Hash）
		redisKeyValuesDao.deleteFuzzyKeys("resource:", ":info");
		List<String> keys1 = new ArrayList<String>();
		List<Resources> values1 = new ArrayList<Resources>();
		List<Resources> resources = resourcesService.findAll();
		if (resources != null && resources.size() > 0) {
			for (Resources r : resources) {
				keys1.add("resource:" + r.getId() + ":info");
				values1.add(r);
			}
		}
		redisKeyValuesDao.batchSet("map", keys1, values1);

		// key: ROLE_MENU_PERMISSION = "role:%s:resource";角色与资源关系(Set)
		redisKeyValuesDao.deleteFuzzyKeys("role:", ":resource");
		List<String> keys2 = new ArrayList<String>();
		List<ResourceVo> values2 = new ArrayList<ResourceVo>();
		List<ResourceVo> role_resources = resourcesService.getResourcesByAllRoleIds();
		if(role_resources != null && role_resources.size() > 0) {
			for (ResourceVo r : role_resources) {
				keys2.add("role:"+r.getRoleId()+":resource");
				values2.add(r);
			}
		}
		redisKeyValuesDao.batchSet("set", keys2, values2);
	}
	
	/**
	 * 同步角色表
	 * 
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sycRoleTable() throws Exception{
		// key: MENU_ROLE_PERMISSION = "resource:%s:role"; 资源与角色关系（Set）
		redisKeyValuesDao.deleteFuzzyKeys("resource:", ":role");
		List<String> keys1 = new ArrayList<String>();
		List<RoleVo> values1 = new ArrayList<RoleVo>();
		List<RoleVo> resource_roles = roleService.getRolesByAllResources();
		if(resource_roles != null && resource_roles.size() > 0) {
			for (RoleVo r : resource_roles) {
				keys1.add("resource:"+r.getResourceId()+":role");
				values1.add(r);
			}
		}
		redisKeyValuesDao.batchSet("set", keys1, values1);
		
		
		// key: USER_ROLE = "user:%s:role"; 用户拥有角色（Set）
		redisKeyValuesDao.deleteFuzzyKeys("user:", ":role");

		List<String> keys2 = new ArrayList<String>();
		List<RoleVo> values2 = new ArrayList<RoleVo>();
		List<RoleVo> account_roles = roleService.getRolesByAllAccounts();
		if(account_roles != null && account_roles.size() > 0) {
			for (RoleVo r : account_roles) {
				keys2.add("user:"+r.getAccountId()+":role");
				values2.add(r);
			}
		}
		redisKeyValuesDao.batchSet("set", keys2, values2);
	}

	/**
	 * 同步字典表
	 * 
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sycDictTable() throws Exception{
		// DICTDATA_NAME_ID = "dictdatas:%s:id"; 字典ID对应关系（String）
		// DICTDATA_RELATIONSHIP = "dictdatas:%s:children"; 字典数据上下级关系（Set）
		redisKeyValuesDao.deleteFuzzyKeys("dictdatas:", ":id");
		redisKeyValuesDao.deleteFuzzyKeys("dictdatas:", ":children");
		List<Dict> dicts = dictService.findAll();
		if(dicts != null && dicts.size() > 0) {
			/*for (Dict d : dicts) {
				redisKeyValuesDao.setKeyValue("String", "dictdatas:"+d.getId()+":id", d);
				if(d.getParentId() != null && !d.getParentId().isEmpty()) {
					redisKeyValuesDao.setKeyValue("Set", "dictdatas:"+d.getParentId()+":children", d);
				}
			}*/
			List<String> keys1 = new ArrayList<String>();
			List<Dict> values1 = new ArrayList<Dict>();
			List<String> keys2 = new ArrayList<String>();
			List<Dict> values2 = new ArrayList<Dict>();
			for (Dict d : dicts) {
				keys1.add("dictdatas:"+d.getId()+":id");
				values1.add(d);
				if(d.getParentId() != null && !d.getParentId().isEmpty()) {
					keys2.add("dictdatas:"+d.getParentId()+":children");
					values2.add(d);
				}
			}
			redisKeyValuesDao.batchSet("String", keys1, values1);
			redisKeyValuesDao.batchSet("Set", keys2, values2);
			
		}		
		
	}
	
	
	
	
	
	
	
}
