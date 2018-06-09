package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.core.entity.DictObject;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.service.RedisKeyValuesService;
import com.chinaoly.frm.core.service.ServiceException;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.service.RedisTableSynService;
import com.chinaoly.frm.security.service.impl.AccountInfoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/redisOp")
@Api(value = "redisOp", tags = { "redis管理" })
public class RedisController extends BaseController {

	@Autowired
	private RedisKeyValuesService redisKeyValuesService;
	
	@Autowired
	private RedisTableSynService redisTableSynService;
	
	@Autowired
	private AccountInfoService accountInfoService;
	
	/**
	 * 批量删除key,以"|"分割
	 * eg: "key"	Or	"Key1|Key2|Key3"
	 * @param key 
	 */
	@PostMapping("/deleteKey")
	@ApiOperation(value = "deleteKey", notes = "(批量)删除key")
	@LogSave(methodType = methodType.DELETE, title = "(批量)删除key")
	public Result deleteKey(HttpServletRequest request , HttpServletResponse response,
			@RequestParam("key") String key) throws ServiceException{
		String[] keys = key.split("\\|");
		if(keys != null && keys.length != 0) {
			redisKeyValuesService.deleteKeys(Arrays.asList(keys));
		}
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 同步资源表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/sycResourceTable",method=RequestMethod.POST)
	@ApiOperation(value = "同步资源表", notes = "同步资源表")
	public Result sycResourceTable(HttpServletRequest request , HttpServletResponse response) throws Exception{
		redisTableSynService.sycResourceTable();
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 同步角色表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/sycRoleTable",method=RequestMethod.POST)
	@ApiOperation(value = "同步角色表", notes = "同步角色表")
	public Result sycRoleTable(HttpServletRequest request , HttpServletResponse response) throws Exception{
		redisTableSynService.sycRoleTable();
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 同步字典表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/sycDictTable",method=RequestMethod.POST)
	@ApiOperation(value = "同步字典表", notes = "同步字典表")
	public Result sycDictTable(HttpServletRequest request , HttpServletResponse response) throws Exception{
		redisTableSynService.sycDictTable();
		return ResultGenerator.genSuccessResult();
	}
	
	
	@GetMapping("/getStringValue")
	@ApiOperation(value = "getStringValue", notes = "获得值")
	public Result getStringValue(@RequestParam("key") String key) throws Exception{
		String value = redisKeyValuesService.getStringValue(key);
		System.out.println("===================== value :" + value +"  ===================");
		return ResultGenerator.genSuccessResult(value);
	}
	
	@GetMapping("/getSetValue")
	@ApiOperation(value = "getSetValue", notes = "获得set值")
	public Result getSetValue(@RequestParam("key") String key) throws Exception{
		Set<String> value = redisKeyValuesService.getSetValue(key);
		for(String v : value) {
			System.out.println("===================== value :" + v +"  ===================");
		}		
		return ResultGenerator.genSuccessResult(value);
	}
	
	@GetMapping("/getZSetValue")
	@ApiOperation(value = "getZSetValue", notes = "获得ZSet值")
	public Result getZSetValue(@RequestParam("key") String key) throws Exception{
		Set<String> value = redisKeyValuesService.getZSetValue(key);
		for(String v : value) {
			System.out.println("===================== value :"+ v +"  ===================");
		}		
		return ResultGenerator.genSuccessResult(value);
	}
	
	@GetMapping("/getMapValue")
	@ApiOperation(value = "getMapValue", notes = "获得map值")
	public Result getMapValue(@RequestParam("key") String key,@RequestParam("mapKey") String mapKey) throws Exception{
		String value = redisKeyValuesService.getMapValue(key, mapKey);
		System.out.println("===================== value :"+ value + "  ===================");
		return ResultGenerator.genSuccessResult(value);
	}
	
	@GetMapping("/getListValue")
	@ApiOperation(value = "getListValue", notes = "获得list值")
	public Result getListValue(@RequestParam("key") String key) throws Exception{
		List<String> value = redisKeyValuesService.getListValue(key);
		for(String v : value) {
			System.out.println("===================== value :" + v + "  ===================");
		}
		return ResultGenerator.genSuccessResult(value);
	}
	
	
	
	
	
	
	
	
	/*===================================================================================*/
	
	@Resource
	private AccountInfoServiceImpl  accountInfoServiceImpl;
	
	//DICTDATA_RELATIONSHIP = "dictdatas:%s:children"; 字典数据上下级关系（Set） 
	@GetMapping("/getDictList")
	@ApiOperation(value = "getDictList", notes = "根据父节点的id，从T_SYS_DICT表中取所有此节点下的数据,eg:dictdatas:%s:children(set)")
	public Result getDictList(@RequestParam("parentId") String parentId) throws Exception{
		Set<DictObject> value = accountInfoServiceImpl.getDictList(parentId);
		for(DictObject v : value) {
			System.out.println("===================== value :" + v + "  ===================");
		}
		return ResultGenerator.genSuccessResult(value);
	}
	
	//DICTDATA_NAME_ID = "dictdatas:%s:id"; 字典ID对应关系（String）
	@GetMapping("/getDictFieldValue")
	@ApiOperation(value = "getDictFieldValue", notes = "从T_SYS_DICT获取已知字段值的指定字段值,eg:dictdatas:%s:id(String)")
	public Result getDictFieldValue(String parentId, String xname, String xvalue, String yname) throws Exception{
		String value = accountInfoServiceImpl.getDictFieldValue(parentId, xname , xvalue, yname);
		System.out.println("===================== value :" + value + "  ===================");
		return ResultGenerator.genSuccessResult(value);
	}
	
	//DICTDATA_NAME_ID = "dictdatas:%s:id"; 字典ID对应关系（String）
	@GetMapping("/getDictFieldValueById")
	@ApiOperation(value = "getDictFieldValueById", notes = " 从T_SYS_DICT获取已知字段值的指定字段值,eg:dictdatas:%s:id(String)")
	public Result getDictFieldValueById(String id,String yname) throws Exception{
		String value = accountInfoServiceImpl.getDictFieldValueById(id, yname);
		System.out.println("===================== value :" + value + "  ===================");
		return ResultGenerator.genSuccessResult(value);
	}
	
	//USER_ROLE = "user:%s:role"; 用户拥有角色（Set）
	@GetMapping("/getUserRoles")
	@ApiOperation(value = "getUserRoles", notes = "根据用户Id查找多个角色,eg:user:%s:role(set)")
	public Result getUserRoles(String accountId) throws Exception{
		Set<String> value = accountInfoServiceImpl.getUserRoles(accountId);
		for(String v : value) {
			System.out.println("===================== value :" + v + "  ===================");
		}
		return ResultGenerator.genSuccessResult(value);
	}
	
}
