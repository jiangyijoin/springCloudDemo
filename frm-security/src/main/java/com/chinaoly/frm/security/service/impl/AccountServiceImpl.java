package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.AccountMapper;
import com.chinaoly.frm.security.Model.Account;
import com.chinaoly.frm.security.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@Service
@Transactional
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {
    @Resource
    private AccountMapper tSysAccountMapper;

}
