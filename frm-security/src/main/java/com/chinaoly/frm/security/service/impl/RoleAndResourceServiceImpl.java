package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.RoleAndResourceMapper;
import com.chinaoly.frm.security.Model.RoleAndResource;
import com.chinaoly.frm.security.service.RoleAndResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@Service
@Transactional
public class RoleAndResourceServiceImpl extends AbstractService<RoleAndResource> implements RoleAndResourceService {
    @Resource
    private RoleAndResourceMapper tSysRRoleResourceMapper;

}
