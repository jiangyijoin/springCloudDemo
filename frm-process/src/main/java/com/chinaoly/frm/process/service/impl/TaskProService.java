package com.chinaoly.frm.process.service.impl;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskProService {

	@Autowired
	private TaskService taskService;
	
	/**
	 * 通过userId查询待办任务
	 * @param userId
	 * @return
	 */
	public List<Task> findTaskByUserId(String userId){
		return taskService.createTaskQuery().taskCandidateUser(userId).list();
	}
	
	/**
	 * 通过groupId查询待办任务
	 * @param userId
	 * @return
	 */
	public List<Task> findTaskByGroupId(String groupId){
		return taskService.createTaskQuery().taskCandidateGroup(groupId).list();
	}
	
	/**
	 * 指定审批人
	 * @param userId
	 * @return
	 */
	public void addApprover(String taskId,String userId){
		// 任务候选人(绑定权限) act_ru_identitylink
		taskService.addCandidateUser(taskId, userId);
	}
}
