package com.chinaoly.frm.security.controller;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.security.Model.Client;
import com.chinaoly.frm.security.service.ClientService;
import com.chinaoly.frm.log.aop.LogSave;
import com.chinaoly.frm.log.aop.methodType;
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
@RequestMapping("/client")
@Api(value = "client" ,tags = {"应用管理"})
public class ClientController extends BaseController {
    @Resource
    private ClientService clientService;

    @PostMapping("/save")
    @LogSave(methodType = methodType.SAVE, title = "新增应用信息")
	@ApiOperation(value = "save",notes = "新增应用信息")
    public Result save( Client client) throws Exception{
    	CommonUtil.isNull(client.getClientPassword(), "@应用密码");
    	CommonUtil.isNull(client.getClientName(), "@应用名");
    	CommonUtil.isNull(client.getDelFlag(), "@标记");
    	CommonUtil.isNull(client.getClientUrl(), "@应用路径");

        clientService.saveClient(client);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteById")
    @LogSave(methodType = methodType.DELETE, title = "单个删除应用信息")
	@ApiOperation(value = "deleteById",notes = "单个删除应用信息")
    public Result deleteById(@RequestParam String id) throws Exception{
        clientService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteByIds")
    @LogSave(methodType = methodType.DELETE, title = "批量删除应用信息,用','分割")
	@ApiOperation(value = "deleteByIds",notes = "批量删除应用信息,用','分割")
    public Result deleteByIds(@RequestParam String ids) throws Exception{
    	clientService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @LogSave(methodType = methodType.UPDATE, title = "根据应用Id修应用信息")
	@ApiOperation(value = "update",notes = "根据应用Id修改应用信息")
    public Result update( Client client) throws Exception{
    	CommonUtil.isNull(client.getId(), "@应用Id");

        clientService.updateClient(client);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    @LogSave(methodType = methodType.SEARCH, title = "根据Id获取应用信息")
	@ApiOperation(value = "detail",notes = "根据Id获取应用信息")
    public Result detail(@RequestParam String id) throws Exception{
        Client client = clientService.findById(id);
        return ResultGenerator.genSuccessResult(client);
    }

    @PostMapping("/queryList")
    @LogSave(methodType = methodType.SEARCH, title = "查询全部应用信息")
	@ApiOperation(value = "queryList",notes = "查询全部应用信息")
    public Result queryList() throws Exception{
        List<Client> list = clientService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/queryPage")
    @LogSave(methodType = methodType.SEARCH, title = "分页查询应用信息")
	@ApiOperation(value = "queryPage",notes = "分页查询应用信息")
    public Result queryPage(Client client, @RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) throws Exception{
        PageInfo<Client> page = clientService.findPage(pageNum, pageSize, client);
        return ResultGenerator.genSuccessResult(page);
    }
    
    /**
     * 应用禁用/启用
     */
    @PostMapping("/setDelFlag")
    @LogSave(methodType = methodType.UPDATE, title = "应用禁用/启用")
	@ApiOperation(value = "setDelFlag",notes = "应用禁用/启用")
    public Result setDelFlag(String cilentId,short delFlag) throws Exception{
        clientService.setDelFlag(cilentId, delFlag);
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
   		clientService.excelUppload(path);
   		return ResultGenerator.genSuccessResult();
   	}
}
