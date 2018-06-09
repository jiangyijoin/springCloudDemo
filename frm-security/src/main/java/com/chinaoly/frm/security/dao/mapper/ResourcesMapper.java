package com.chinaoly.frm.security.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.Resources;
import com.chinaoly.frm.security.vo.ResourceVo;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
public interface ResourcesMapper extends Mapper<Resources> {

	public List<ResourceVo> selectAllResources(Resources resource) throws Exception;

	@Select("SELECT RE.* FROM T_SYS_R_ROLE_RESOURCE R LEFT JOIN T_SYS_RESOURCE RE ON RE.ID = R.RESOURCE_ID "
			+ "WHERE R.ROLE_ID = #{roleId}")
	@ResultMap("BaseResultMap")
	public List<Resources> getResourcesByRoleId(String roleId) throws Exception;

	@Select("SELECT RE.*, ROLE_ID FROM T_SYS_R_ROLE_RESOURCE R LEFT JOIN T_SYS_RESOURCE RE ON RE.ID = R.RESOURCE_ID ")
	@ResultMap("ResourceVoMap")
	public List<ResourceVo> getResourcesByAllRoleIds() throws Exception;

	@Update("UPDATE T_SYS_RESOURCE SET DEL_FLAG = #{delFlag} WHERE ID = #{resourceId}")
	public void setDelFlag(@Param("resourceId")String resourceId, @Param("delFlag") int delFlag);

	@Select("SELECT C.CLIENT_NAME,R1.RESOURCE_NAME PARENT_NAME,R.* FROM T_SYS_RESOURCE R  "
			+ "LEFT JOIN T_SYS_CLIENT C ON C.ID = R.CLIENT_ID "
			+ "LEFT JOIN T_SYS_RESOURCE R1 ON R1.ID = R.PARENT_ID "
			+ "WHERE R.ID = #{id}")
	@ResultMap("ResourceVoMap")
	public ResourceVo findResourceById(String id);	
	
}