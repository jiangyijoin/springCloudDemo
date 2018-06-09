package com.chinaoly.frm.security.service.impl;

import com.chinaoly.frm.common.utils.CommonUtil;
import com.chinaoly.frm.common.utils.RandomGUID;
import com.chinaoly.frm.common.utils.StringUtil;
import com.chinaoly.frm.core.service.AbstractService;
import com.chinaoly.frm.security.dao.mapper.AccountInfoMapper;
import com.chinaoly.frm.security.Model.Account;
import com.chinaoly.frm.security.Model.AccountInfo;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.service.AccountService;
import com.chinaoly.frm.security.vo.AccountInfoVo;
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
public class AccountInfoServiceImpl extends AbstractService<AccountInfo> implements AccountInfoService {
    @Resource
    private AccountInfoMapper tSysAccountInfoMapper;
    
    @Resource
    private AccountService accountService;

	@Override
	public void setInitPassword(String ids) throws Exception{
		tSysAccountInfoMapper.setInitPassword(ids.split(","));		
	}

	@Override
	public void resetPassword(String id, String password) throws Exception{
		////待修改，改成当前用户
		String uuid = new RandomGUID().getUUID32();
		tSysAccountInfoMapper.resetPassword(id, password, new Date(), uuid);
	}

	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<AccountInfoVo> findPage(Integer pageNum, Integer pageSize, AccountInfo accountInfo, String accountName, String officeName) throws Exception{
		PageHelper.startPage(pageNum, pageSize);
	    List<AccountInfoVo> list = tSysAccountInfoMapper.selectAllAcountInfoes(accountInfo, accountName, officeName);
	    return new PageInfo(list);
	}

	@Override
	public int countForAccountName(String accountName) throws Exception{
		return tSysAccountInfoMapper.countForAccountName(accountName);
	}

	@Override
	public void saveAccountInfo(AccountInfo accountInfo, String userIp) throws Exception {
		if(countForAccountName(accountInfo.getIdCard()) > 0) { throw new Exception("账号已注册"); }
		String uuid =  new RandomGUID().getUUID32();
		Date currentTime = new Date();
		
		Account account = new Account();
		account.setId(uuid);
		accountInfo.setId(uuid);
		account.setPassword("123456");
		account.setDelFlag(0);
		account.setCreateTime(currentTime);
		account.setAccountName(accountInfo.getIdCard());
		
		//待修改，改成当前用户
		account.setCreateId(uuid);
		
		accountService.save(account);
		save(accountInfo);
	}

	@Override
	public AccountInfoVo findAccInfoById(String id)  throws Exception{
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setId(id);
		return tSysAccountInfoMapper.findAccInfoById(id);
	}

	@Override
	public void deleteAccAndAccInfoById(String id)  throws Exception{
		deleteById(id);
		accountService.deleteById(id);		
	}

	@Override
	public AccountInfoVo findAccInfoByAccName(String accountName) throws Exception {
		return tSysAccountInfoMapper.findAccInfoByAccName(accountName);
	}

	@Override
	public void deleteAccAndAccInfoByIds(String ids)  throws Exception{
		deleteByIds(ids);
		accountService.deleteByIds(ids);
	}
	
	public static final String DATEFORMAT1 = "yyyy-MM-dd hh:mm:ss";
	public static final String DATEFORMAT2 = "yyyy-MM-dd";
	
	@Override
	public void excelUppload(String path) throws Exception {
		List<AccountInfo> accInfoes = new ArrayList<AccountInfo>();
		List<Account> acces = new ArrayList<Account>();
		File files = new File(path);
		try {
			Workbook rwb = Workbook.getWorkbook(files);
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			StringBuilder uuid;
			for (int i = 1; i < rows; i++) {
					Account account = new Account();
					AccountInfo accountInfo = new AccountInfo();
					int j = 0;
					
					// 第一个是列数，第二个是行数
					String accountName = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String password = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					int delFlag = CommonUtil.nvlInteger(rs.getCell(j++, i).getContents(), -1);
					String staffCard = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String officeCode = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String userName = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String nickName = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					short sex = CommonUtil.nvlShort(rs.getCell(j++, i).getContents(), (short)-1);
					sex = sex == -1? null:sex;
					String email = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String phone = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String minMobileNo = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String fixedNo = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String idCard = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String serialNumber = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String registerDate = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String endDate = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String accountExpiredStarttime = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String accountExpiredEndtime = CommonUtil.nvl(rs.getCell(j++, i).getContents());
					String userLevel = CommonUtil.nvl(rs.getCell(j++, i).getContents());

					if(!checkContents(accountName, password, delFlag, staffCard, officeCode, userName, nickName, sex,
							email, phone, minMobileNo, fixedNo, idCard, serialNumber, registerDate, endDate,
							accountExpiredStarttime, accountExpiredEndtime, userLevel)) return;

					
					uuid =  new StringBuilder(new RandomGUID().getUUID32());
					Date current = new Date();
					account.setId(uuid.toString());
					account.setAccountName(accountName);
					account.setPassword(password);
					account.setDelFlag(delFlag);
					account.setCreateTime(current);
					account.setCreateId(uuid.toString());
					
					accountInfo.setId(uuid.toString());
					accountInfo.setStaffCard(staffCard);
					accountInfo.setOfficeCode(officeCode);
					accountInfo.setUserName(userName);
					accountInfo.setNickName(nickName);
					accountInfo.setSex(sex);
					accountInfo.setEmail(email);
					accountInfo.setPhone(phone);
					accountInfo.setMinMobileNo(minMobileNo);
					accountInfo.setFixedNo(fixedNo);
					accountInfo.setIdCard(idCard);
					//accountInfo.setSerialNumber(serialNumber);
					SimpleDateFormat dateFromat1 = new SimpleDateFormat(DATEFORMAT1); 
					SimpleDateFormat dateFromat2 = new SimpleDateFormat(DATEFORMAT2); 					

					/*if(StringUtil.notEmpty(registerDate)) {
						accountInfo.setRegisterDate(dateFromat1.parse(registerDate));
					}
					if(StringUtil.notEmpty(endDate)) {
						accountInfo.setEndDate(dateFromat1.parse(endDate));
					}
					if(StringUtil.notEmpty(accountExpiredStarttime)) {
						accountInfo.setAccountExpiredStarttime(dateFromat2.parse(accountExpiredStarttime+" 00:00:00"));						
					}
					if(StringUtil.notEmpty(accountExpiredEndtime)) {
						accountInfo.setAccountExpiredEndtime(dateFromat2.parse(accountExpiredEndtime+" 00:00:00"));
					}*/
					accountInfo.setUserLevel(userLevel);
					
					accountService.save(account);
					save(accountInfo);
					acces.add(account);
					accInfoes.add(accountInfo);
			}
			/*if (acces.size() > 0) {
				accountService.save(acces);
			}
			if (accInfoes.size() > 0) {
				save(accInfoes);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		files.delete();
	}

	//  校验内容
	private boolean checkContents(String accountName, String password, int delFlag, String staffCard,
			String officeCode, String userName, String nickName, short sex, String email, String phone,
			String minMobileNo, String fixedNo, String idCard, String serialNumber, String registerDate,
			String endDate, String accountExpiredStarttime, String accountExpiredEndtime, String userLevel) {
		
		return true;
	}
    
    
    

}
