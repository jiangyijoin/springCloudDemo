package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.Model.DictType;
import com.chinaoly.frm.security.service.DictTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by chenyl on 2018/06/01.
 */
@RestController
@RequestMapping("/dict/type")
@Api(value = "dictType", tags = { "字典类型管理" })
public class DictTypeController extends BaseController {
	@Resource
	private DictTypeService dictTypeService;

	@ApiImplicitParams({ @ApiImplicitParam(name = "dictType", value = "字典类型code", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeName", value = "字典类型名字", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeDescription", value = "字典类型描述", required = false, paramType = "query") })
	@LogSave(methodType = methodType.SAVE, title = "字典表类型新增")
	@PostMapping("/save")
	@ApiOperation(value = "save", notes = "新增")
	public Result save(DictType dictType) throws Exception {
		if (dictType.getDictType() == null || dictType.getDictTypeName() == null) {
			return ResultGenerator.genFailResult("字典类型或者字典名称不能为空");
		}
		// 重复查询
		dictType.setDictTypeId(null);
		Long num = dictTypeService.findDictTypeByparameterFlag(dictType);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("字典已存在");
		}
		//生成uuid
		UUID uuid=UUID.randomUUID();
		dictType.setDictTypeId(uuid.toString().replace("-", ""));
		dictType.setCreateTime(new Date());
		dictType.setDelFlag(0);
		dictTypeService.save(dictType);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", value = "字典类型id,用,隔开", required = true, paramType = "query"), })
	@LogSave(methodType = methodType.DELETE, title = "字典表类型删除")
	@PostMapping("/delete")
	@ApiOperation(value = "delete", notes = "批量删除")
	public Result delete(String ids) throws Exception {
		if (ids == null || ids.length() < 1) {
			return ResultGenerator.genFailResult("ids不能为空");
		}
		dictTypeService.deleteByIds(ids);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "dictTypeId", value = "字典类型id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictType", value = "字典类型code", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeName", value = "字典类型名字", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeDescription", value = "字典类型描述", required = false, paramType = "query"), })
	@LogSave(methodType = methodType.UPDATE, title = "字典修改")
	@PostMapping("/update")
	@ApiOperation(value = "update", notes = "修改")
	public Result update(DictType dictType) throws Exception {
		if (dictType.getDictTypeId() == null || dictType.getDictTypeId().length() < 1) {
			return ResultGenerator.genFailResult("字典类型id不能为空");
		}
		// 重复查询
		Long num = dictTypeService.findDictTypeByparameterFlag(dictType);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("字典类型或者字典类型名称不能为空");
		}
		dictTypeService.update(dictType);
		return ResultGenerator.genSuccessResult();
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "dictTypeId", value = "字典类型id", required = true, paramType = "query"), })
	@LogSave(methodType = methodType.SEARCH, title = "通过id查详情")
	@PostMapping("/detail")
	@ApiOperation(value = "detail", notes = "根据id查详情")
	public Result detail(String dictTypeId) throws Exception {
		if (dictTypeId == null || dictTypeId.length() < 1) {
			return ResultGenerator.genFailResult("字典类型id不能为空");
		}
		DictType dictType = dictTypeService.findById(dictTypeId);
		return ResultGenerator.genSuccessResult(dictType);
	}

	@LogSave(methodType = methodType.SEARCH, title = "查询所有字典类型,不分页")
	@GetMapping("/queryList")
	@ApiOperation(value = "queryList", notes = "查询全部字典类型")
	public Result queryList() throws Exception {
		List<DictType> list = dictTypeService.findAll();
		return ResultGenerator.genSuccessResult(list);
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictType", value = "字典类型code", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeName", value = "字典类型名字", required = false, paramType = "query"), })
	@LogSave(methodType = methodType.SEARCH, title = "通过编码,名字查询字典类型列表")
	@PostMapping("/queryPage")
	@ApiOperation(value = "queryPage", notes = "分页查询所有字典类型")
	public Result queryPage(Integer pageNum, Integer pageSize, String dictType, String dictTypeName) throws Exception {
		if (pageNum == null || pageNum < 0 || pageSize == null || pageSize < 0) {
			return ResultGenerator.genFailResult("每页大小和当前页码不能为空");
		}
		PageInfo<DictType> page = dictTypeService.findDictTypeList(pageNum, pageSize, dictType, dictTypeName);
		return ResultGenerator.genSuccessResult(page);
	}

	@PostMapping("/excelUppload")
	@ApiOperation(value = "excelUppload", notes = "批量上传")
	@LogSave(methodType = methodType.SAVE, title = "批量新增")
	public Result excelUppload(MultipartHttpServletRequest mfile) throws Exception {
		File[] roots = File.listRoots();
		MultipartFile file = mfile.getFile("file");
		String path = roots[1].toString() + new Date().getTime() + file.getOriginalFilename();
		file.transferTo(new File(path));
		dictTypeService.excelUppload(path);
		return ResultGenerator.genSuccessResult();
	}
}
