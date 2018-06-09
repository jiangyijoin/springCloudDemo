package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.security.Model.Office;
import com.chinaoly.frm.security.service.OfficeService;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenyl on 2018/06/03.
 */
@RestController
@RequestMapping("/office")
@Api(value = "office", tags = { "部门管理" })
public class OfficeController extends BaseController {
	@Resource
	private OfficeService officeService;

	@ApiImplicitParams({ @ApiImplicitParam(name = "master", value = "负责人", required = true, paramType = "query"),
			@ApiImplicitParam(name = "type", value = "部门类型", required = true, paramType = "query"),
			@ApiImplicitParam(name = "officeCode", value = "部门code", required = true, paramType = "query"),
			@ApiImplicitParam(name = "officeName", value = "部门名字", required = true, paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "负责人电话", required = true, paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", required = true, paramType = "query"), })
	@PostMapping("/save")
	@ApiOperation(value = "save", notes = "新增")
	@LogSave(methodType = methodType.SAVE, title = "新增")
	public Result save(Office ov) throws Exception {
		if (ov.getOfficeName() == null || ov.getOfficeCode() == null || ov.getMaster() == null || ov.getPhone() == null
				|| ov.getRemark() == null || ov.getType() == null) {
			return ResultGenerator.genFailResult("参数不全");
		}
		ov.setOfficeId(null);
		// 生成uuid
		Long num = officeService.findCountByParameter(ov);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("部门名或者代码已存在");
		}
		UUID uuid = UUID.randomUUID();
		ov.setOfficeId(uuid.toString().replace("-", ""));
		ov.setCreateTime(new Date());
		ov.setCreateId("1");
		ov.setOfficeNum(ov.getOfficeCode());
		ov.setDelFlag(0);
		ov.setWeight(0);
		officeService.save(ov);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", value = "部门id,用,隔开", required = true, paramType = "query"), })
	@PostMapping("/delete")
	@ApiOperation(value = "delete", notes = "批量删除")
	@LogSave(methodType = methodType.DELETE, title = "批量删除")
	public Result delete(String ids) throws Exception {
		if (ids == null || ids.length() < 1) {
			return ResultGenerator.genFailResult("ids不能为空");
		}
		officeService.deleteByIds(ids);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "officeId", value = "部门id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "master", value = "负责人", required = false, paramType = "query"),
			@ApiImplicitParam(name = "type", value = "部门类型", required = false, paramType = "query"),
			@ApiImplicitParam(name = "officeCode", value = "部门code", required = false, paramType = "query"),
			@ApiImplicitParam(name = "officeName", value = "部门名字", required = false, paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "负责人电话", required = false, paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query"), })
	@PostMapping("/update")
	@ApiOperation(value = "update", notes = "修改")
	@LogSave(methodType = methodType.UPDATE, title = "修改")
	public Result update(Office ov) throws Exception {
		if (ov.getOfficeId() == null || ov.getOfficeId().length() < 1) {
			return ResultGenerator.genFailResult("id不能为空");
		}
		Long num = officeService.findCountByParameter(ov);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("部门名或者代码已存在");
		}
		officeService.update(ov);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "officeId", value = "部门id", required = true, paramType = "query") })
	@PostMapping("/detail")
	@ApiOperation(value = "detail", notes = "查询详情")
	@LogSave(methodType = methodType.SEARCH, title = "查询详情")
	public Result detail(String officeId) throws Exception {
		if (officeId == null || officeId.length() < 1) {
			return ResultGenerator.genFailResult("部门id不能为空");
		}
		Office office = officeService.findById(officeId);
		return ResultGenerator.genSuccessResult(office);
	}

	@GetMapping("/queryList")
	@ApiOperation(value = "queryList", notes = "查询全部部门")
	@LogSave(methodType = methodType.SEARCH, title = "查询全部部门")
	public Result queryList() throws Exception {
		List<Office> list = officeService.findAll();
		return ResultGenerator.genSuccessResult(list);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, paramType = "query"),
			@ApiImplicitParam(name = "officeCode", value = "部门code", required = false, paramType = "query"),
			@ApiImplicitParam(name = "officeName", value = "部门名字", required = false, paramType = "query"), })
	@PostMapping("/queryPage")
	@ApiOperation(value = "queryPage", notes = "分页查询部门")
	@LogSave(methodType = methodType.SEARCH, title = "分页查询部门")
	public Result queryPage(Integer pageNum, Integer pageSize, String OfficeCode, String OfficeName) throws Exception {
		if (pageNum == null || pageNum < 0 || pageSize == null || pageSize < 0) {
			return ResultGenerator.genFailResult("每页大小和当前页码不能为空");
		}
		PageInfo<Office> page = officeService.findOfficeList(pageNum, pageSize, OfficeCode, OfficeName);
		return ResultGenerator.genSuccessResult(page);
	}

	@PostMapping("/findOfficeTreeByType")
	@ApiOperation(value = "findOfficeTreeByType", notes = "查询部门树")
	@LogSave(methodType = methodType.SEARCH, title = "查询部门树")
	public Result findOfficeTreeByType() throws Exception {
		List<Map<String, Object>> list = officeService.findOfficeTreeByType();
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 逐层查部门树
	 * 
	 * @param request
	 * @param response
	 * @param ov
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "parentId", value = "父节点id", required = false, paramType = "query") })
	@ApiOperation(value = "逐层查询部门树", notes = "逐层查询部门树")
	@RequestMapping(value = "/findOfficeTreeOneByOne", method = RequestMethod.POST)
	@LogSave(methodType = methodType.SEARCH, title = "逐层查询部门树")
	public Result findOfficeTreeOneByOne(Office ov) throws Exception {
		List<Map<String, Object>> list = officeService.findOfficeTreeOneByOne(ov);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 批量新增
	 * 
	 * @param request
	 * @param response
	 * @param mfile
	 */
	@ApiOperation(value = "批量新增", notes = "批量新增")
	@RequestMapping(value = "/excelUppload", method = RequestMethod.POST)
	@LogSave(methodType = methodType.SAVE, title = "批量新增")
	public Result excelUppload(MultipartHttpServletRequest mfile) throws Exception {
		File[] roots = File.listRoots();
		MultipartFile file = mfile.getFile("file");
		String path = roots[1].toString() + new Date().getTime() + file.getOriginalFilename();
		file.transferTo(new File(path));
		officeService.excelUppload(path);
		return ResultGenerator.genSuccessResult();
	}
}
