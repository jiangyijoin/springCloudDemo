package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.common.utils.RandomGUID;
import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.ClientMapper;
import com.chinaoly.frm.security.Model.Account;
import com.chinaoly.frm.security.Model.AccountInfo;
import com.chinaoly.frm.security.Model.Client;
import com.chinaoly.frm.security.service.ClientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;



/**
* @author zhaohmz
* @Date Mar 15, 2018
*/
@Service
@Transactional
public class ClientServiceImpl extends AbstractService<Client> implements ClientService {
    @Resource
    private ClientMapper tSysClientMapper;

	@Override
	public void saveClient(Client client) throws Exception {
		String uuid =  new RandomGUID().getUUID32();
		Date currentTime = new Date();
		
		client.setId(uuid);
		client.setCreateTime(currentTime);
		//待修改，改成当前用户
		client.setCreateId(uuid);
		save(client);
	}

	@Override
	public void updateClient(Client client) throws Exception {
		//待修改，改成当前用户
		client.setUpdateId(client.getId());
		client.setUpdateTime(new Date());
		update(client);		
	}
	
	@Override
	public PageInfo<Client> findPage(Integer pageNum, Integer pageSize, Client client) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
	    List<Client> list = tSysClientMapper.selectAllAcountInfoes(client);
	    return new PageInfo(list);
	}

	@Override
	public void setDelFlag(String cilentId, short delFlag) throws Exception {
		Client client = findById(cilentId);
		client.setDelFlag(delFlag);
		updateClient(client);
	}

	@Override
	public void excelUppload(String path) throws Exception {
		List<Client> clients = new ArrayList<Client>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			StringBuilder uuid;
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					Client client = new Client();
					
					// 第一个是列数，第二个是行数
					String clientPassword = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String clientName = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					short delFlag = CommonUtil.nvlShort(rs.getCell(j++, i).getContents(), (short)-1);
					String remark =  CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String clientUrl =  CommonUtil.nvl(rs.getCell(j++, i).getContents());
					
					if(!checkContents(clientPassword, clientName, delFlag, remark, clientUrl)) return;

					uuid =  new StringBuilder(new RandomGUID().getUUID32());
					client.setId(uuid.toString());
					client.setClientName(clientName);
					client.setClientPassword(clientPassword);
					client.setDelFlag(delFlag);
					client.setClientUrl(clientUrl);
					client.setCreateTime(new Date());
					client.setCreateId(uuid.toString());
										
					clients.add(client);
					save(client);
				}
			}
			/*if (clients.size() > 0) {
				save(clients);
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
	}

	private boolean checkContents(String clientPassword, String clientName, short delFlag, String remark, String clientUrl) {
		// TODO Auto-generated method stub
		return true;
	}


}
