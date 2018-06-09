package com.chinaoly.frm.process.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.alibaba.fastjson.JSONObject;
import com.chinaoly.frm.core.entity.Result;
import com.chinaoly.frm.core.entity.ResultGenerator;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.process.util.Page;
import com.chinaoly.frm.process.util.PageUtils;
import com.chinaoly.frm.process.util.UserUtil;
import com.chinaoly.frm.process.util.WorkflowUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 流程控制器
 * @author chenyl
 *
 */
@Controller
@RequestMapping("process")
public class ActivitiProcessController extends BaseController{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService ;

    @Autowired
    protected TaskService taskService;

    protected  Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

    /**
     * 流程定义列表
     *
     * @return
     */
    @RequestMapping(value = "/processList",method = RequestMethod.POST)
    @ResponseBody
    public Result processList(Integer nowpage, Integer pagesize, HttpServletRequest request, HttpServletResponse response) {

    /*
     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
     */
        List<Object[]> list = new ArrayList<Object[]>();
        PageUtils page = new PageUtils(nowpage,pagesize);


        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getRowStart(), pagesize);
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            list.add(new Object[]{processDefinition, deployment});
        }
	     return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 查询运行中流程
     * */
    @RequestMapping(value = "/runningList",method = RequestMethod.POST)
    public Result running(Integer nowpage, Integer pagesize, HttpServletRequest request,Integer pageNum,Integer pageSize, HttpServletResponse response) {
    	Page<JSONObject> page = new Page<JSONObject>(pageNum,pageSize);

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> list = processInstanceQuery.listPage(page.getStartRow(), page.getPageSize());
		return ResultGenerator.genSuccessResult(list);
		
    }

    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read",method = RequestMethod.POST)
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance",method = RequestMethod.POST)
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/process/delete",method = RequestMethod.POST)
    public Result delete(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/process/trace/auto/{executionId}",method = RequestMethod.POST)
    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)
            throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessEngineConfiguration  processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 部署
     * @param exportDir
     * @param file
     * @return
     */
    @RequestMapping(value = "/deploy",method = RequestMethod.POST)
    public String deploy(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir, @RequestParam(value = "file", required = false) MultipartFile file) {

        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;

            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }

            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();

            for (ProcessDefinition processDefinition : list) {
                WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
            }

        } catch (Exception e) {
            logger.error("error on deploy process, because of file input stream", e);
        }

        return "redirect:/workflow/process-list";
    }

    @RequestMapping(value = "/process/convert-to-model/{processDefinitionId}",method = RequestMethod.POST)
    public String convertToModel(@PathVariable("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

        return "redirect:/workflow/model/list";
    }

    /**
     * 待办任务--Portlet
     */
    @RequestMapping(value = "/task/todo/list",method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> todoList(HttpSession session) throws Exception {
        User user = UserUtil.getUserFromSession(session);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        // 已经签收的任务
        List<Task> todoList = taskService.createTaskQuery().taskAssignee(user.getId()).active().list();
        for (Task task : todoList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "todo");
            result.add(singleTask);
        }

        // 等待签收的任务
        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(user.getId()).active().list();
        for (Task task : toClaimList) {
            String processDefinitionId = task.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
            singleTask.put("status", "claim");
            result.add(singleTask);
        }

        return result;
    }

    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "processdefinition/update/{state}/{processDefinitionId}",method = RequestMethod.POST)
    public String updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId,
                              RedirectAttributes redirectAttributes) {
        if (state.equals("active")) {
            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processDefinitionId + "]的流程定义。");
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processDefinitionId + "]的流程定义。");
        }
        return "redirect:/workflow/process-list";
    }

    /**
     * 导出图片文件到硬盘
     *
     * @return
     */
    @RequestMapping(value = "export/diagrams",method = RequestMethod.POST)
    @ResponseBody
    public List<String> exportDiagrams(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws IOException {
        List<String> files = new ArrayList<String>();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        for (ProcessDefinition processDefinition : list) {
            files.add(WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir));
        }

        return files;
    }

    @RequestMapping(value = "bpmn/model/{processDefinitionId}",method = RequestMethod.POST)
    @ResponseBody
    public BpmnModel queryBpmnModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        return bpmnModel;
    }

}
