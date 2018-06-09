package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.Model.Dict;
import com.chinaoly.frm.security.service.DictService;
import com.chinaoly.frm.security.service.DictTypeService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenyl on 2018/06/03.
 */
@RestController
@RequestMapping("/dict")
@Api(value = "dict", tags = { "字典管理" })
public class DictController extends BaseController {
	@Resource
	private DictService dictService;
	@Resource
	private DictTypeService dictTypeService;

	@ApiImplicitParams({ @ApiImplicitParam(name = "dictTypeId", value = "字典类型id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictName", value = "字典名字", required = true, paramType = "query"),
			@ApiImplicitParam(name = "value", value = "字典值", required = true, paramType = "query"),
			@ApiImplicitParam(name = "description", value = "字典描述", required = true, paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "字典权重", required = true, paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父节点id", required = false, paramType = "query"), })
	@LogSave(methodType = methodType.SAVE, title = "字典表类型新增")
	@PostMapping("/save")
	@ApiOperation(value = "save", notes = "新增")
	public Result save(Dict dict) throws Exception {
		if (dict.getDictTypeId() == null || dict.getDictName() == null || dict.getValue() == null) {
			return ResultGenerator.genFailResult("字典类型,字典名称,字典值不能为空");
		}
		dict.setId(null);
		Long num = dictService.findCountByParameter(dict);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("字典名或者代码已存在");
		}
		UUID uuid=UUID.randomUUID();
		dict.setId(uuid.toString().replace("-", ""));
		dict.setCreateTime(new Date());
		dict.setCreateId("1");
		dict.setDelFlag(0);
		dictService.save(dict);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	@ApiOperation(value = "delete", notes = "删除")
	@LogSave(methodType = methodType.DELETE, title = "字典表删除")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", value = "字典id,用,隔开", required = true, paramType = "query"), })
	public Result delete(String ids) throws Exception {
		if (ids == null || ids.length() < 1) {
			return ResultGenerator.genFailResult("ids不能为空");
		}
		dictService.deleteByIds(ids);
		return ResultGenerator.genSuccessResult();
	}

	@LogSave(methodType = methodType.UPDATE, title = "字典修改")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "字典id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeId", value = "字典类型id", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictName", value = "字典名字", required = false, paramType = "query"),
			@ApiImplicitParam(name = "value", value = "字典值", required = false, paramType = "query"),
			@ApiImplicitParam(name = "description", value = "字典描述", required = false, paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "字典权重", required = false, paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父节点id", required = false, paramType = "query"), })
	@PostMapping("/update")
	@ApiOperation(value = "update", notes = "修改")
	public Result update(Dict dict) throws Exception {
		if (dict.getId() == null || dict.getId().length() < 1) {
			return ResultGenerator.genFailResult("id不能为空");
		}
		Long num = dictService.findCountByParameter(dict);
		if (num != null && num > 0) {
			return ResultGenerator.genFailResult("字典名或者代码已存在");
		}
		dictService.update(dict);
		return ResultGenerator.genSuccessResult();
	}

	@LogSave(methodType = methodType.SEARCH, title = "通过id查详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "dictId", value = "字典id", required = true, paramType = "query") })
	@PostMapping("/detail")
	@ApiOperation(value = "detail", notes = "查询详情")
	public Result detail(String dictId) throws Exception {
		if (dictId == null || dictId.length() < 1) {
			return ResultGenerator.genFailResult("id不能为空");
		}
		Dict dict = dictService.findById(dictId);
		return ResultGenerator.genSuccessResult(dict);
	}

	@GetMapping("/queryList")
	@ApiOperation(value = "queryList", notes = "查询全部字典列表")
	public Result queryList() throws Exception {
		List<Dict> list = dictService.findAll();
		return ResultGenerator.genSuccessResult(list);
	}

	@PostMapping("/queryPage")
	@ApiOperation(value = "queryPage", notes = "分页查询字典列表")
	@LogSave(methodType = methodType.SEARCH, title = "通过编码,名字查询字典列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, paramType = "query"),
			@ApiImplicitParam(name = "dictName", value = "字典名字", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeName", value = "字典类型名字", required = false, paramType = "query"), })
	public Result queryPage(Integer pageNum, Integer pageSize, String dictName, String dictTypeName, String diceId,
			String dictTypeId) throws Exception {
		if (pageNum == null || pageNum < 0 || pageSize == null || pageSize < 0) {
			return ResultGenerator.genFailResult("每页大小和当前页码不能为空");
		}
		PageInfo<Dict> page = dictService.findDictList(pageNum, pageSize, dictName, dictTypeName, diceId, dictTypeId);
		return ResultGenerator.genSuccessResult(page);
	}

	@PostMapping("/findDictTreeByType")
	@ApiOperation(value = "findDictTreeByType", notes = "查询字典树")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dictTypeId", value = "字典类型id", required = true, paramType = "query") })
	@LogSave(methodType = methodType.SEARCH, title = "根据字典类型查询字典树")
	public Result findDictTreeByType(String dictTypeId) throws Exception {
		if (dictTypeId == null || dictTypeId.length() < 1) {
			return ResultGenerator.genFailResult("字典类型id不能为空");
		}
		List<Map<String, Object>> list = dictService.findDictTreeByType(dictTypeId);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 逐层查字典树
	 * 
	 * @param request
	 * @param response
	 * @param ov
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lv", value = "层级,0查询字典类型,1查询该类型下的字典", required = true, paramType = "query"),
			@ApiImplicitParam(name = "parentId", value = "父节点id,与字典类型id两者必须传一个", required = false, paramType = "query"),
			@ApiImplicitParam(name = "dictTypeId", value = "字典类型id,与父节点id两者必须传一个", required = false, paramType = "query") })
	@ApiOperation(value = "逐层查询部门树", notes = "逐层查询部门树")
	@RequestMapping(value = "/findDictTreeOneByOne", method = RequestMethod.POST)
	@LogSave(methodType = methodType.SEARCH, title = "逐层查询部门树")
	public Result findDictTreeOneByOne(String lv, String dictTypeId, String parentId) throws Exception {
		if (lv == null || lv.length() < 1) {
			return ResultGenerator.genFailResult("lv不能为空");
		}

		List<Map<String, Object>> list = new ArrayList<>();
		if ("0".equals(lv)) {// 查询字典类型
			list = dictTypeService.findDictTreeOneByOne();
		} else if ("1".equals(lv)) {// 查询该类型下的字典
			if (dictTypeId == null && parentId == null) {// 父节点和类型不能同时为空
				return ResultGenerator.genFailResult("parentId不能为空");
			}
			list = dictService.findDictTreeOneByOne(dictTypeId, parentId);
		}
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
		dictService.excelUppload(path);
		return ResultGenerator.genSuccessResult();
	}
}
