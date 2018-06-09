package com.chinaoly.frm.security.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Update;

import com.chinaoly.frm.core.baseDao.mybatis.Mapper;
import com.chinaoly.frm.security.Model.Client;

/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
public interface ClientMapper extends Mapper<Client> {

	List<Client> selectAllAcountInfoes(Client client) throws Exception;

}
