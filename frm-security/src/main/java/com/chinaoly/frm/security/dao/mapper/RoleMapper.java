package com.chinaoly.frm.security.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.vo.RoleVo;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
public interface RoleMapper extends Mapper<Role> {

	/**
	 * 查找T_SYS_R_ACCOUNT_ROLE对应的所有角色信息
	 */
	@Select("SELECT R.ACCOUNT_ID,O.* FROM T_SYS_R_ACCOUNT_ROLE R "
			+ "LEFT JOIN T_SYS_ROLE O ON R.ROLE_ID = O.ID")
	@ResultMap("RoleVoMap")
	public List<RoleVo> getRolesByAllAccounts() throws Exception;

	/**
	 * 查找T_SYS_R_ROLE_RESOURCE对应的所有角色信息
	 */
	@Select("SELECT R.RESOURCE_ID,O.* FROM T_SYS_R_ROLE_RESOURCE R  "
			+ "LEFT JOIN T_SYS_ROLE O ON R.ROLE_ID = O.ID")
	@ResultMap("RoleVoMap")
	public List<RoleVo> getRolesByAllResources() throws Exception;


	public List<Role> selectAllRoles(Role role);

	@Select("SELECT O.* FROM T_SYS_R_ACCOUNT_ROLE R "
			+ "LEFT JOIN T_SYS_ROLE O ON R.ROLE_ID = O.ID "
			+ "WHERE R.ACCOUNT_ID = #{accountId} ")
	@ResultMap("BaseResultMap")
	public List<Role> getRolesByAccountId(String accountId);


}
