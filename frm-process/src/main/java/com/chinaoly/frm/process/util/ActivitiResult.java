package com.chinaoly.frm.process.util;

import java.io.Serializable;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public class ActivitiResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private ProcessDefinition processDefinition;
	private Deployment deployment;
	
	public ActivitiResult(ProcessDefinition processDefinition, Deployment deployment) {
		super();
		this.processDefinition = processDefinition;
		this.deployment = deployment;
	}
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	public Deployment getDeployment() {
		return deployment;
	}
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}
	
}
