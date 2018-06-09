package com.chinaoly.frm.process.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;

/**
 * 模型控制器
 * @author chenyl
 *
 */
@RestController
@RequestMapping(value="model") 
public class ActivitiModelController extends BaseController{
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 模型列表
	 * @return
	 */
	 @RequestMapping(value="/selectAll", method = RequestMethod.POST)
	 @ApiOperation(value = "selectAll", notes = "查询模型列表")
	 @ResponseBody
	 public Result  selectAll(HttpServletRequest request, HttpServletResponse response)throws Exception {
	     List<Model> resultList =  repositoryService.createModelQuery().orderByCreateTime().desc().list();
		 return ResultGenerator.genSuccessResult(resultList);
	 }
	 
	 /** 
	  * 新增model
	  * @return 
	  */  
	 @ApiOperation(value = "create", notes = "新增model")
	 @RequestMapping(value = "/create",method = RequestMethod.POST)  
	 public Result getEditor( @RequestParam("description") String description,@RequestParam("name") String name,
	         @RequestParam("key") String key,
	         HttpServletRequest request, HttpServletResponse response)throws Exception {  
	     try {
			 ObjectMapper objectMapper = new ObjectMapper();
	            ObjectNode editorNode = objectMapper.createObjectNode();
	            editorNode.put("id", "canvas");
	            editorNode.put("resourceId", "canvas");
	            ObjectNode stencilSetNode = objectMapper.createObjectNode();
	            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
	            editorNode.put("stencilset", stencilSetNode);
	            Model modelData = repositoryService.newModel();
	            //{"id":"canvas","resourceId":"canvas","stencilset":{"namespace":"http://b3mn.org/stencilset/bpmn2.0#"}}

	            ObjectNode modelObjectNode = objectMapper.createObjectNode();
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	            description = StringUtils.defaultString(description);
	            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
	            modelData.setMetaInfo(modelObjectNode.toString());
	            modelData.setName(name);
	            modelData.setKey(StringUtils.defaultString(key));

	            repositoryService.saveModel(modelData);
	            
	            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
	            //repositoryService.addModelEditorSourceExtra(modelData.getId(), editorNode.toString().getBytes("utf-8"));


	   		 return ResultGenerator.genSuccessResult();
	     } catch (Exception e) {
			return ResultGenerator.genFailResult("创建模型失败");
	     }
	 }  
	 
	 /**
	  * model参数新增,修改
	  * @param modelForm
	 * @throws IOException 
	  */
	 @ApiOperation(value = "saveModel", notes = "model参数新增,修改")
	 @RequestMapping(value = "/saveModel",method = RequestMethod.POST)  
	 protected Result createModelData(String modelId, String jsonFile,String svg, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 Model modelData = repositoryService.getModel(modelId);
	     if (modelData != null) {
				repositoryService.addModelEditorSource(modelId, IOUtils.toByteArray(jsonFile));
				repositoryService.addModelEditorSourceExtra(modelId, svg.getBytes("utf-8"));
		   		 return ResultGenerator.genSuccessResult();
	     }else{
				return ResultGenerator.genFailResult("创建模型失败");
	     }
	 }
	 
	 /**
	  * 批量删除
	  * @param ids
	  * @param request
	  * @return
	  */
	 @ApiOperation(value = "deleteByIds", notes = "批量删除")
	 @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
	 @ResponseBody
	 public Result deleteByIds(String[] ids,HttpServletRequest request, HttpServletResponse response) throws Exception {
	     //JSONObject result = new JSONObject();
	     for(String id : ids){
	         repositoryService.deleteModel(id);
	     }
   		 return ResultGenerator.genSuccessResult();
	 }
	 
	 /**
	  * 导出model的xml文件
	  */
	 @RequestMapping(value = "export", method = RequestMethod.POST)
	 @ApiOperation(value = "export", notes = "导出model的xml文件")
	 public Result export(@PathVariable("modelId") String modelId,HttpServletRequest request, HttpServletResponse response){
	     response.setCharacterEncoding("UTF-8");  
	     response.setContentType("application/json; charset=utf-8");  
	     try {
	         Model modelData = repositoryService.getModel(modelId);
	         BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
	         //获取节点信息
	         byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
	         JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
	         //将节点信息转换为xml
	         BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
	         BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
	         byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

	         ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
	         IOUtils.copy(in, response.getOutputStream());
//	                 String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	         String filename = modelData.getName() + ".bpmn20.xml";
	         response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
	         response.flushBuffer();

	   		 return ResultGenerator.genSuccessResult(filename);
	     } catch (Exception e){
	         PrintWriter out = null;
	         try {
	             out = response.getWriter();
	         } catch (IOException e1) {
	             e1.printStackTrace();
	         }
	         e.printStackTrace();
	         return ResultGenerator.genFailResult("未找到对应数据");
	     }
	 }
	 
	 
	 
	 /**
	  * 部署
	  */
	 @RequestMapping(value = "deploy",method=RequestMethod.POST)
	 @ApiOperation(value = "deploy", notes = "部署")
	 @ResponseBody
	 public Result deploy(@RequestParam("modelId") String modelId, HttpServletRequest request, HttpServletResponse response) throws Exception {
	     //JSONObject result = new JSONObject();
	     try {
	    	 Model modelData = repositoryService.getModel(modelId);
	            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
	            byte[] bpmnBytes = null;

	            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

	            String processName = modelData.getName() + ".bpmn20.xml";
	            
	            Deployment deployment = repositoryService.createDeployment()
	            		.name(modelData.getName())
	            		.addString(processName, new String(bpmnBytes))
	            		.deploy();

	   		 return ResultGenerator.genSuccessResult("部署成功，部署ID=" + deployment.getId());
	     } catch (Exception e) {
	         e.printStackTrace(); 
	         return ResultGenerator.genFailResult("部署失败");
	     }
	 }
}
