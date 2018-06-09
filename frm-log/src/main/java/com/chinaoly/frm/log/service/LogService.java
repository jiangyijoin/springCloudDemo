package com.chinaoly.frm.log.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinaoly.frm.log.entity.LogEntity;

@Service
public interface LogService {

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int addLog(LogEntity log)throws Exception;
	
}
