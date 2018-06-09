package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
import com.chinaoly.frm.security.Model.AccountInfo;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.service.AccountService;
import com.chinaoly.frm.security.vo.AccountInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@RestController
@RequestMapping("/account/info")
@Api(value = "accountInfo" ,tags = {"账户信息管理"})
public class AccountInfoController extends BaseController {
    @Resource
    private AccountInfoService accountInfoService;

    @Resource
    private AccountService accountService;

    @PostMapping("/save")
    @LogSave(methodType = methodType.SAVE, title = "新增账户信息")
	@ApiOperation(value = "save",notes = "新增账户信息")
    public Result save( HttpServletRequest request, AccountInfo accountInfo) throws Exception{
    	CommonUtil.isNull(accountInfo.getIdCard(), "@证件信息");
    	
    	String userIp = getCurrentUserIP(request);
        accountInfoService.saveAccountInfo(accountInfo, userIp);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteById")
    @LogSave(methodType = methodType.DELETE, title = "单个删除账户信息")
	@ApiOperation(value = "delete",notes = "单个删除账户信息")
    public Result deleteById(@RequestParam String id) throws Exception{
        accountInfoService.deleteAccAndAccInfoById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteByIds")
    @LogSave(methodType = methodType.DELETE, title = "批量删除账户信息,用','分割")
	@ApiOperation(value = "deleteByIds",notes = "批量删除账户信息,用','分割")
    public Result deleteByIds(@RequestParam String ids) throws Exception{
        accountInfoService.deleteAccAndAccInfoByIds(ids);       
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @LogSave(methodType = methodType.UPDATE, title = "根据账户Id修改账户信息")
	@ApiOperation(value = "update",notes = "根据账户Id修改账户信息")
    public Result update( AccountInfo accountInfo) throws Exception{
    	CommonUtil.isNull(accountInfo.getId(), "@账户Id");

        accountInfoService.update(accountInfo);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    @LogSave(methodType = methodType.SEARCH, title = "根据Id获取账户信息")
	@ApiOperation(value = "detail",notes = "根据Id获取账户信息")
    public Result detail(@RequestParam String id) throws Exception{
        AccountInfoVo accountInfoVo = accountInfoService.findAccInfoById(id);
        return ResultGenerator.genSuccessResult(accountInfoVo);
    }

    @GetMapping("/queryList")
    @LogSave(methodType = methodType.SEARCH, title = "查询全部账户信息")
	@ApiOperation(value = "queryList",notes = "查询全部账户信息")
    public Result queryList() throws Exception{
        List<AccountInfo> list = accountInfoService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/queryPage")
    @LogSave(methodType = methodType.SEARCH, title = "分页查询账户信息")
	@ApiOperation(value = "queryPage",notes = "分页查询账户信息")
    public Result queryPage( AccountInfo accountInfo, String accountName,String officeName,
    		@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) throws Exception{
        PageInfo<AccountInfoVo> page = accountInfoService.findPage(pageNum, pageSize, accountInfo, accountName, officeName);
        return ResultGenerator.genSuccessResult(page);
    }

    @PatchMapping(value = "/setInitPassword")
    @LogSave(methodType = methodType.UPDATE, title = "根据账户ids单个(批量)重置密码(默认123456),用'|'分割")
    @ApiOperation(value = "setInitPassword",notes = "根据账户ids单个(批量)重置密码(默认123456),用'|'分割")
    public Result setInitPassword(@RequestParam(value="ids") String ids) throws Exception{
		accountInfoService.setInitPassword(ids);
		return ResultGenerator.genSuccessResult();
    }

    @PatchMapping(value = "/resetPassword")
    @LogSave(methodType = methodType.UPDATE, title = "根据账户Id修改密码")
    @ApiOperation(value = "resetPassword",notes = "根据账户Id修改密码")
    public Result resetPassword(@RequestParam(value="id") String id,@RequestParam(value="password") String password) throws Exception{
		accountInfoService.resetPassword(id,password);
		return ResultGenerator.genSuccessResult();
    }
    
    @GetMapping("/findAccInfoByAccName")
    @LogSave(methodType = methodType.SEARCH, title = "根据账号名称查询账户信息")
	@ApiOperation(value = "findAccInfoByAccName",notes = "根据账号名称查询账户信息")
    public Result findAccInfoByAccName(String accountName) throws Exception{
        AccountInfoVo accInfo = accountInfoService.findAccInfoByAccName(accountName);
        return ResultGenerator.genSuccessResult(accInfo);
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
		accountInfoService.excelUppload(path);
		return ResultGenerator.genSuccessResult();
	}
	
	
	
	
}
