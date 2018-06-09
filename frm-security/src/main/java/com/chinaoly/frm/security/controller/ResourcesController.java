package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.Model.Resources;
import com.chinaoly.frm.security.Model.RoleAndResource;
import com.chinaoly.frm.security.service.ResourcesService;
import com.chinaoly.frm.security.vo.ResourceVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@RestController
@RequestMapping("/resources")
@Api(value = "resources" ,tags = {"资源管理"})
public class ResourcesController extends BaseController {
    @Resource
    private ResourcesService resourcesService;

    @PostMapping("/save")
    @LogSave(methodType = methodType.SAVE, title = "新增资源信息")
	@ApiOperation(value = "save",notes = "新增资源信息")
    public Result save(Resources resource) throws Exception {
    	CommonUtil.isNull(resource.getResourceLevel(), "@模块等级");
    	CommonUtil.isNull(resource.getNameCn(), "@模块中文名");
    	CommonUtil.isNull(resource.getResourceType(), "@模块类型 ");
    	CommonUtil.isNull(resource.getWeight(), "@模块权重");
    	CommonUtil.isNull(resource.getClientId(), "@资源应用id");
    	CommonUtil.isNull(resource.getDelFlag(), "@标记");

        resourcesService.saveResource(resource);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteById")
    @LogSave(methodType = methodType.DELETE, title = "单个删除资源信息")
	@ApiOperation(value = "deleteById",notes = "单个删除资源信息")
    public Result deleteById(@RequestParam String id) throws Exception{
        resourcesService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteByIds")
    @LogSave(methodType = methodType.DELETE, title = "批量删除资源信息,用','分割")
	@ApiOperation(value = "deleteByIds",notes = "批量删除资源信息,用','分割")
    public Result deleteByIds(@RequestParam String ids) throws Exception{
    	resourcesService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @LogSave(methodType = methodType.UPDATE, title = "根据资源Id修改资源信息")
	@ApiOperation(value = "update",notes = "根据资源Id修改资源信息")
    public Result update( Resources resources) throws Exception{
    	CommonUtil.isNull(resources.getId(), "@资源Id");

        resourcesService.updateResource(resources);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    @LogSave(methodType = methodType.SEARCH, title = "根据Id获取资源信息")
	@ApiOperation(value = "detail",notes = "根据Id获取资源信息")
    public Result detail(@RequestParam String id) throws Exception{
        ResourceVo resources = resourcesService.findResourceById(id);
        return ResultGenerator.genSuccessResult(resources);
    }

    @GetMapping("/queryList")
    @LogSave(methodType = methodType.SEARCH, title = "查询全部资源信息")
	@ApiOperation(value = "queryList",notes = "查询全部资源信息")
    public Result queryList() throws Exception{
        List<Resources> list = resourcesService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/queryPage")
    @LogSave(methodType = methodType.SEARCH, title = "分页查询资源信息")
	@ApiOperation(value = "queryPage",notes = "分页查询资源信息")
    public Result queryPage( Resources resource, @RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) throws Exception{
        PageInfo<ResourceVo> page = resourcesService.findPage(pageNum, pageSize, resource);
        return ResultGenerator.genSuccessResult(page);
    }

	@PatchMapping(value = "/setResourcesByRoleId")
	@LogSave(methodType = methodType.UPDATE, title = "根据角色Id设置多个资源模块信息")
	@ApiOperation(value = "setResourcesByRoleId",notes = "根据角色Id设置多个资源模块信息")
    public Result setResourcesByRoleId(@RequestParam String roleId,List<RoleAndResource> roleAndResources) throws Exception{
		resourcesService.setResourcesByRoleId(roleId,roleAndResources);
		return ResultGenerator.genSuccessResult();
    }

	/**
	 * 根据角色Id获得资源模块信息
	 * @param roleId  角色Id，必输
	 * @return
	 */
    @GetMapping("/getResourcesByRoleId")
	@LogSave(methodType = methodType.SEARCH, title = "根据角色Id获得资源信息")
    @ApiOperation(value = "getResourcesByRoleId",notes = "根据角色Id获得资源信息")
    public Result getResourcesByRoleId(String roleId) throws Exception{
		List<Resources> resources = resourcesService.getResourcesByRoleId(roleId);
		return ResultGenerator.genSuccessResult(resources);
    }
    
    /**
	 * 资源禁用/启用
	 * @param resourceId
	 * @return
	 */
    @GetMapping("/setDelFlag")
	@LogSave(methodType = methodType.UPDATE, title = "资源禁用/启用")
    @ApiOperation(value = "setDelFlag",notes = "资源禁用/启用")
    public Result setDelFlag(String resourceId,int delFlag) throws Exception{
		resourcesService.setDelFlag(resourceId, delFlag);
		return ResultGenerator.genSuccessResult();
    }

    /**
   	 * 批量导入
   	 * 
   	 * @param request
   	 * @param response
   	 * @param mfile
   	 */
   	@ApiOperation(value = "批量导入", notes = "批量导入")
   	@PostMapping(value = "/excelUppload")
   	@LogSave(methodType = methodType.SAVE, title = "批量导入")
   	public Result excelUppload(MultipartHttpServletRequest mfile) throws Exception {
   		File[] roots = File.listRoots();
   		MultipartFile file = mfile.getFile("file");
   		String path = roots[1].toString() + new Date().getTime() + file.getOriginalFilename();
   		file.transferTo(new File(path));
   		resourcesService.excelUppload(path);
   		return ResultGenerator.genSuccessResult();
   	}
}
