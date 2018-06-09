package com.chinaoly.frm.security.service;

import com.chinaoly.frm.security.Model.Client;
import com.github.pagehelper.PageInfo;
import com.chinaoly.frm.core.service.Service;


/**
 * @author zhaohmz
 * @Date Mar 15, 2018
 */
public interface ClientService extends Service<Client> {

	public void saveClient(Client client) throws Exception;

	public PageInfo<Client> findPage(Integer pageNum, Integer pageSize, Client client) throws Exception;

	public void updateClient(Client client) throws Exception;

	public void setDelFlag(String cilentId, short delFlag) throws Exception;

	public void excelUppload(String path) throws Exception;

}
