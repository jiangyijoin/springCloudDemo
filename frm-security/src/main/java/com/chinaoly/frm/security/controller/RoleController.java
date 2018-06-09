package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.Model.Role;
import com.chinaoly.frm.security.service.RoleService;
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
@RequestMapping("/role")
@Api(value = "role" ,tags = {"角色管理"})
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @PostMapping("/save")
    @LogSave(methodType = methodType.SAVE, title = "新增角色信息")
	@ApiOperation(value = "save",notes = "新增角色信息")
    public Result save( Role role) throws Exception{
    	CommonUtil.isNull(role.getRoleName(), "@角色名称");
    	CommonUtil.isNull(role.getNameCn(), "@简称");
    	CommonUtil.isNull(role.getDelFlag(), "@标记");

        roleService.saveRole(role);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteById")
    @LogSave(methodType = methodType.DELETE, title = "单个删除角色信息")
	@ApiOperation(value = "deleteById",notes = "单个删除角色信息")
    public Result deleteById(@RequestParam String id) throws Exception{
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteByIds")
    @LogSave(methodType = methodType.DELETE, title = "批量删除角色信息,用','分割")
	@ApiOperation(value = "deleteByIds",notes = "批量删除角色信息,用','分割")
    public Result deleteByIds(@RequestParam String ids) throws Exception{
        roleService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @LogSave(methodType = methodType.UPDATE, title = "根据角色Id修改角色信息")
	@ApiOperation(value = "update",notes = "根据角色Id修改角色信息")
    public Result update( Role role) throws Exception{
    	CommonUtil.isNull(role.getId() ,"@角色Id");

        roleService.updateRole(role);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    @LogSave(methodType = methodType.SEARCH, title = "根据Id获取角色信息")
	@ApiOperation(value = "detail",notes = "根据Id获取角色信息")
    public Result detail(@RequestParam String id) throws Exception{
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @GetMapping("/queryList")
    @LogSave(methodType = methodType.SEARCH, title = "查询全部角色信息")
	@ApiOperation(value = "queryList",notes = "查询全部角色信息")
    public Result queryList() throws Exception{
        List<Role> list = roleService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/queryPage")
    @LogSave(methodType = methodType.SEARCH, title = "分页查询角色信息")
	@ApiOperation(value = "queryPage",notes = "分页查询角色信息")
    public Result queryPage( Role role, @RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) throws Exception{
        PageInfo<Role> page = roleService.findPage(pageNum, pageSize, role);
        return ResultGenerator.genSuccessResult(page);
    }

	@PostMapping("/setRolesByAccountId")
	@LogSave(methodType = methodType.UPDATE, title = "根据账号Id设置多个角色信息")
	@ApiOperation(value = "setRolesByAccountId",notes = "根据账号Id设置多个角色信息,roleIds用'|'分割")
    public Result setRolesByAccountId(@RequestParam String accountId, @RequestParam String roleIds) throws Exception{
		roleService.setRolesByAccountId(accountId,roleIds);
		return ResultGenerator.genSuccessResult();
    }
	
	@GetMapping("/getRolesByAccountId")
	@LogSave(methodType = methodType.SEARCH, title = "根据账号Id查询角色信息")
	@ApiOperation(value = "getRolesByAccountId",notes = "根据账号Id查询角色信息")
    public Result getRolesByAccountId(@RequestParam String accountId) throws Exception{
		List<Role> roles = roleService.getRolesByAccountId(accountId);
		return ResultGenerator.genSuccessResult(roles);
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
   		roleService.excelUppload(path);
   		return ResultGenerator.genSuccessResult();
   	}
}
