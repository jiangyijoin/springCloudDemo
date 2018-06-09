package com.chinaoly.frm.log.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinaoly.frm.common.utils.RandomGUID;
import com.chinaoly.frm.log.entity.LogEntity;
import com.chinaoly.frm.log.dao.mapper.LogMapper;
import com.chinaoly.frm.log.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;

	@Override
	public int addLog(LogEntity log) throws Exception {
		log.setId(new RandomGUID().getUUID32());
		log.setCreateId(new RandomGUID().getUUID32());
		log.setCreateTime(new Date());
		return logMapper.insertLog(log);
	}

}
