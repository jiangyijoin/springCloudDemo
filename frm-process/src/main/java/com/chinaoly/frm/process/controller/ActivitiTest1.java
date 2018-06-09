package com.chinaoly.frm.process.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.process.service.impl.TaskProService;
import com.chinaoly.frm.process.util.Page;
import com.chinaoly.frm.security.service.AccountInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 真实业务测试
 * 
 * @author chenyl
 *
 */
@RestController
@RequestMapping("test")
@Api(value = "test", tags = { "Activiti真实业务测试" })
public class ActivitiTest1 extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private AccountInfoService accountInfoService;// 用户

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskProService taskProService;

	/**
	 * 实例测试创建流程,前提:已部署任务
	 * 
	 * @param request
	 * @param response
	 */
	@PostMapping("test1")
	@ApiOperation(value = "test1", notes = "创建流程")
	@ApiImplicitParams({ @ApiImplicitParam(name = "processDefinitionId", value = "工作流id", required = true, paramType = "query")})
	public Result submitStartFormAndStartProcessInstance(String processDefinitionId) {
		Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("startTime", "2018-05-10");
		formProperties.put("endTime", "2018-05-10");
		formProperties.put("reson", "请假");
		/*
		 * User user = UserUtil.getUserFromSession(request.getSession()); //
		 * 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等 if (user == null ||
		 * StringUtils.isBlank(user.getId())) { return
		 * "redirect:/login?timeout=true"; }
		 */
		ProcessInstance processInstance = null;
		try {
			identityService.setAuthenticatedUserId("baba");// 这放用户id
			processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		return ResultGenerator.genSuccessResult("启动成功，流程ID：" + processInstance.getId());
	}

/*	*//**
	 * 查询待办任务
	 *
	 * @param userId
	 *            用户ID
	 * @return
	 *//*
	@PostMapping("findTodoTask")
	@ApiOperation(value = "findTodoTask", notes = "查询待办任务")
	public Result findTodoTask1() {
		String userId = "papa";
		List<ProcessDefinition> results = new ArrayList<ProcessDefinition>();
		List<Object> results1 = new ArrayList<Object>();

		// 根据当前人的ID查询
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
		List<Task> tasks = taskQuery.list();

		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().singleResult();
			if (processInstance == null) {
				continue;
			}
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
			results.add(processDefinition);
			JSONObject obj = new JSONObject();
			obj.put("deploymentId", processDefinition.getDeploymentId());
			obj.put("id", processDefinition.getId());
			obj.put("category", processDefinition.getCategory());
			obj.put("description", processDefinition.getDescription());
			obj.put("diagramResourceName", processDefinition.getDiagramResourceName());
			obj.put("key", processDefinition.getKey());
			obj.put("name", processDefinition.getName());
			obj.put("resourceName", processDefinition.getResourceName());
			obj.put("tenantId", processDefinition.getTenantId());
			obj.put("version", processDefinition.getVersion());
			results1.add(obj);
		}
		return ResultGenerator.genSuccessResult(results1);
	}*/

	/**
	 * 签收任务
	 */
	@PostMapping(value = "taskClaim")
	@ApiOperation(value = "taskClaim", notes = "任务签收")
	public Result claim1(String taskId) {
		String userId = "666";
		taskService.claim(taskId, userId);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 任务办理
	 * 
	 * @param taskId
	 * @param processType
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@PostMapping(value = "taskComplete")
	@ApiOperation(value = "taskComplete", notes = "任务办理")
	public Result completeTask1(String taskId, String flag) {
		Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("zlpass", flag);
		/*
		 * User user = UserUtil.getUserFromSession(request.getSession());
		 * 
		 * // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等 if (user == null
		 * || StringUtils.isBlank(user.getId())) { return
		 * "redirect:/login?timeout=true"; }
		 */
		try {
			identityService.setAuthenticatedUserId("777");
			formService.submitTaskFormData(taskId, formProperties);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 待办任务列表
	 *
	 * @param model
	 * @return
	 */
	@PostMapping(value = "taskList")
	@ApiOperation(value = "taskList", notes = "待办任务列表")
	public Result taskList(String processType) {
		List<Task> tasks = new ArrayList<Task>();
		if ("all".equals(processType)) {
			//查询全部待办
			tasks = taskService.createTaskQuery().list();
		} else if("group".equals(processType)){
			//查询用户组下的代办任务
			tasks = taskProService.findTaskByGroupId("hr");
		} else{
			//查询某人待办
			tasks = taskProService.findTaskByUserId("baba");
		}
		List<JSONObject> list = taskToJson(tasks);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 运行中的流程实例
	 *
	 * @param model
	 * @return
	 */
	@PostMapping(value = "runningList")
	@ApiOperation(value = "runningList", notes = "运行中的流程实例")
	@ResponseBody
	public Result running(String processType, HttpServletRequest request, Integer pageNum,Integer pageSize, HttpServletResponse response) {
		Page<JSONObject> page = new Page<JSONObject>(pageNum,pageSize);

		if (!StringUtils.equals(processType, "all")) {
			ProcessInstanceQuery leaveDynamicQuery = runtimeService.createProcessInstanceQuery()
					.processDefinitionKey("leave-dynamic-from").orderByProcessInstanceId().desc().active();
			List<ProcessInstance> list = leaveDynamicQuery.listPage(page.getStartRow(), page.getPageSize());

			ProcessInstanceQuery dispatchQuery = runtimeService.createProcessInstanceQuery()
					.processDefinitionKey("process").active().orderByProcessInstanceId().desc();
			List<ProcessInstance> list2 = dispatchQuery.listPage(page.getStartRow(), page.getPageSize());
			list.addAll(list2);

			List<JSONObject> list5 = processInstanceToJson(list);
			page.setResult(list5);
			page.setTotal(Integer.valueOf(String.valueOf(leaveDynamicQuery.count() + dispatchQuery.count())));
		} else {
			
			ProcessInstanceQuery dynamicQuery = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId()
					.desc().active();
			List<ProcessInstance> list = dynamicQuery.listPage(page.getStartRow(), page.getPageSize());
			List<JSONObject> list5 = processInstanceToJson(list);
			page.setResult(list5);
			page.setTotal(Integer.valueOf(String.valueOf(dynamicQuery.count())));
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * ProcessInstance 转成json
	 * */
	public List<JSONObject> processInstanceToJson(List<ProcessInstance> list){
		if(list.size() <= 0){
			return new ArrayList<>();
		}
		List<JSONObject> processInstances = new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("deploymentId", list.get(i).getDeploymentId());
			obj.put("id", list.get(i).getId());
			obj.put("activityId", list.get(i).getActivityId());
			obj.put("description", list.get(i).getDescription());
			obj.put("businessKey", list.get(i).getBusinessKey());
			obj.put("localizedDescription", list.get(i).getLocalizedDescription());
			obj.put("name", list.get(i).getName());
			obj.put("localizedName", list.get(i).getLocalizedName());
			obj.put("tenantId", list.get(i).getTenantId());
			obj.put("processDefinitionId", list.get(i).getProcessDefinitionId());
			obj.put("processDefinitionKey", list.get(i).getProcessDefinitionKey());
			obj.put("processDefinitionName", list.get(i).getProcessDefinitionName());
			obj.put("processDefinitionVersion", list.get(i).getProcessDefinitionVersion());
			obj.put("processInstanceId", list.get(i).getProcessInstanceId());
			obj.put("processVariables", list.get(i).getProcessVariables());
			obj.put("superExecutionId", list.get(i).getSuperExecutionId());
			obj.put("tenantId", list.get(i).getTenantId());
			processInstances.add(obj);
		}
		return processInstances;
	}
	
	/**
	 * task 转成json
	 * */
	public List<JSONObject> taskToJson(List<Task> tasks){
		if(tasks.size() <= 0){
			return new ArrayList<>();
		}
		List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i = 0;i<tasks.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("assignee", tasks.get(i).getAssignee());
				obj.put("category", tasks.get(i).getCategory());
				obj.put("createTime", tasks.get(i).getCreateTime());
				obj.put("delegationState", tasks.get(i).getDelegationState());
				obj.put("description", tasks.get(i).getDescription());
				obj.put("dueDate", tasks.get(i).getDueDate());
				obj.put("executionId", tasks.get(i).getExecutionId());
				obj.put("name", tasks.get(i).getName());
				obj.put("id", tasks.get(i).getId());
				obj.put("parentTaskId", tasks.get(i).getParentTaskId());
				obj.put("processVariables", tasks.get(i).getProcessVariables());
				obj.put("parentTaskId", tasks.get(i).getParentTaskId());
				obj.put("processDefinitionId", tasks.get(i).getProcessDefinitionId());
				list.add(obj);
			}
		return list;
	}
	
	/**
	 * processDefinition 转成json
	 * */
	public List<JSONObject> processDefinitionToJson(List<ProcessDefinition> process){
		if(process.size() <= 0){
			return new ArrayList<>();
		}
		List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i = 0;i<process.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("category", process.get(i).getCategory());
				obj.put("description", process.get(i).getDescription());
				obj.put("name", process.get(i).getName());
				obj.put("id", process.get(i).getId());
				obj.put("deploymentId", process.get(i).getDeploymentId());
				list.add(obj);
			}
		return list;
	}

	   /**
     * 动态form流程列表
     *
     * @param model
     * @return
     */
    @PostMapping(value = "processList")
	@ApiOperation(value = "processList", notes = "运行中的流程实例")
	@ResponseBody
    public Result processDefinitionList( String processType, HttpServletRequest request,Integer pageNum,Integer pageSize) {

        Page<JSONObject> page = new Page<JSONObject>(pageNum,pageSize);

        if (!StringUtils.equals(processType, "all")) {
            /*
             * 只读取动态表单的流程
             */
            ProcessDefinitionQuery query1 = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave-dynamic-from").active().orderByDeploymentId().desc();
            List<ProcessDefinition> list = query1.listPage(page.getStartRow(), page.getPageSize());

            ProcessDefinitionQuery query2 = repositoryService.createProcessDefinitionQuery().processDefinitionKey("dispatch").active().orderByDeploymentId().desc();
            List<ProcessDefinition> dispatchList = query2.listPage(page.getStartRow(), page.getPageSize());

            ProcessDefinitionQuery query3 = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave-jpa").active().orderByDeploymentId().desc();
            List<ProcessDefinition> list3 = query3.listPage(page.getStartRow(), page.getPageSize());

            list.addAll(list3);
            list.addAll(dispatchList);

            page.setResult(processDefinitionToJson(list));
            page.setTotal(Integer.valueOf(String.valueOf(query1.count() + query2.count())));
        } else {
            // 读取所有流程
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().active().orderByDeploymentId().desc();
            List<JSONObject> list = processDefinitionToJson(query.list());
            page.setResult(list);
            page.setTotal(Integer.valueOf(String.valueOf(query.count())));
        }

        return ResultGenerator.genSuccessResult(page);
    }
}
