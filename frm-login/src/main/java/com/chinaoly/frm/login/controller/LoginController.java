package com.chinaoly.frm.login.controller;

import com.chinaoly.frm.core.baseDao.redis.BaseRedisDao;
import com.chinaoly.frm.core.baseDao.redis.RedisKeyValuesDao;
import com.chinaoly.frm.core.web.BaseController;
import com.chinaoly.frm.login.jwt.JwtHelper;
import com.chinaoly.frm.login.util.CryptoUtilOri;
import com.chinaoly.frm.security.service.AccountInfoService;
import com.chinaoly.frm.security.vo.AccountInfoVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jiangyi on 2018/5/10.
 */

@RestController
public class LoginController extends BaseController {
	// 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private AccountInfoService accountInfoService;
	@Autowired
	private RedisKeyValuesDao redisKeyValuesDao;
	@Autowired
	private BaseRedisDao baseRedisDao;

	/**
	 *
	 * 方法说明：统一登录（验证是否单点登录，去redis验证用户，设置token，验证是否锁定ip，验证码，kpi登录）
	 * 
	 * @Title: login
	 * @author: jiangyi
	 * @return
	 * @date: 2018年5月24日 下午2:42:01
	 */
	@ApiOperation(value = "4.2.4.7.1登录", notes = "登录接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "checkCode", value = "验证码", required = true, dataType = "String", paramType = "query")
			})
	@PostMapping("/login/auth")
	private void login(@RequestParam("username") String username, @RequestParam("password") String password,
					   @RequestParam("checkCode") String checkCode, HttpServletRequest request, HttpServletResponse response) {
	//	String word = (String) request.getSession().getAttribute("checkcode_session");// 从session中获取生成的验证码
		String word = (String) SecurityUtils.getSubject().getSession().getAttribute("checkcode_session");
		System.out.println("word--"+word);
		System.out.println("checkCode--"+checkCode);
		// 验证码校验
		if (checkCode.equals(word.trim())) {
			AccountInfoVo accountInfoVo = null;
			try {
				accountInfoVo = accountInfoService.findAccInfoByAccName(username);
				if (accountInfoVo == null) { // 验证是否存在该用户
					this.addResultInfo(BaseController.FAILURE, "用户名错误", null);
					this.responseResult(response, this.getResultJSONStr());

				} else {
					if (password.equals(accountInfoVo.getPassword())) { // 验证用户密码
						if (accountInfoVo.getAccDelFlag() == 0) { // 验证用户状态 
//							Session session = SecurityUtils.getSubject().getSession();
							String token = JwtHelper.createJWT(username, password);// 生成token令牌
							SecurityUtils.getSubject().getSession().setAttribute(token, username);
							SecurityUtils.getSubject().getSession().setAttribute("token", token);// 存放令牌
							//token存放到redis中，数据类型为map
							long nowMillis = System.currentTimeMillis();
							long expMillis = nowMillis + EXPIRE_TIME;
							Date exp = new Date(expMillis);							
							redisKeyValuesDao.setKeyValue("Map", "token:"+token+":accountInfo", accountInfoVo);
							baseRedisDao.setExpire("token:"+token+":accountInfo", exp); //设置key的失效时间


							this.addResultInfo(BaseController.SUCCESS, "登录成功", token);
							this.responseResult(response, this.getResultJSONStr());
						} else {
							this.addResultInfo(BaseController.FAILURE, "该账户被禁用", null);
							this.responseResult(response, this.getResultJSONStr());
						}

					} else {
						this.addResultInfo(BaseController.FAILURE, "密码错误", null);
						this.responseResult(response, this.getResultJSONStr());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			/*
			 * RedisUtil ru = new RedisUtil(); //获取redis中对应的token值 String
			 * value=ru.getValue(token); if(value != null){
			 */

		}
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/greeting/{Authorization}")
	private String greeting(@PathVariable String Authorization) {
		return "Hello,World!";
	}

	/**
	 *
	 * 方法说明：忘记密码
	 * 
	 * @Title: forgetPassword
	 * @author: jiangyi
	 * @return
	 * @return String
	 * @date: 2015年8月14日 下午2:42:01
	 */

	/**
	 *
	 * 方法说明：根据用户名和手机号码验证该用户是否存在 ，如果存在则向该手机号码发送验证码
	 * 
	 * @Title: validatePhoneNumber
	 * @author: jiangyi
	 * @param request
	 * @param response
	 * @return void
	 * @date: 2015年8月18日 下午4:42:39
	 */

	/**
	 *
	 * 方法说明：设置新密码
	 * 
	 * @Title: updatePassword
	 * @author: jiangyi
	 * @param session
	 * @param request
	 * @param response
	 * @return void
	 * @date: 2015年8月21日 下午2:08:03
	 */
	@SuppressWarnings("null")
	@ApiOperation(value = "设置新密码接口", httpMethod = "POST", notes = "updatePassword")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "newpassword", value = "密码", required = true, dataType = "String", paramType = "form") })
	@PostMapping("/login/updatePassword")
	private void updatePassword(@RequestParam String username, @RequestParam String password,
			@RequestParam String newpassword, HttpServletRequest request, HttpServletResponse response) {
		// 校验是否登录
		String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
		String name = (String) SecurityUtils.getSubject().getSession().getAttribute(token);
		if (name != null || !"".equals(name.trim())) { // 已登录
			AccountInfoVo accountInfoVo;
			try {
				accountInfoVo = accountInfoService.findAccInfoByAccName(username);
				if (accountInfoVo == null || !name.equals(username)) {
					// 验证已登录用户和修改密码的用户是否是同一用户
					this.addResultInfo(BaseController.FAILURE, "用户名错误", null);
					this.responseResult(response, this.getResultJSONStr());

				} else {
					if (password.equals(accountInfoVo.getPassword())) { // 验证密码
						if (accountInfoVo.getAccDelFlag() == 0) { // 验证用户状态
							try {
								// 根据账户Id修改密码
								accountInfoService.resetPassword(accountInfoVo.getId(), newpassword);
								SecurityUtils.getSubject().getSession().removeAttribute(token);// 移除session中的旧token
								String newToken = JwtHelper.createJWT(username, newpassword);// 根据新密码生成新token
								SecurityUtils.getSubject().getSession().setAttribute(newToken, username);
								SecurityUtils.getSubject().getSession().setAttribute("token", newToken);// 存放token
								//token存放到redis中，数据类型为map
								long nowMillis = System.currentTimeMillis();
								long expMillis = nowMillis + EXPIRE_TIME;
								Date exp = new Date(expMillis);	
								redisKeyValuesDao.setKeyValue("Map", "token:"+newToken+":accountInfo", accountInfoVo);
								baseRedisDao.setExpire("token:"+newToken+":accountInfo", exp); //设置key的失效时间
								
								this.addResultInfo(BaseController.SUCCESS, "设置成功", token);
								this.responseResult(response, this.getResultJSONStr());
//								return ResultGenerator.genSuccessResult(token);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							this.addResultInfo(BaseController.FAILURE, "该账户被禁用", null);
							this.responseResult(response, this.getResultJSONStr());
						}

					} else {
						this.addResultInfo(BaseController.FAILURE, "密码错误", null);
						this.responseResult(response, this.getResultJSONStr());
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	/**
	 * 判断用户是否已经登录
	 *
	 * @param sesstion
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("null")
	@ApiOperation(value = "判断用户是否已经登录", httpMethod = "POST", notes = "isLogined")
	@PostMapping("/login/isLogined")
	private void logined(HttpServletRequest request, HttpServletResponse response) {
		String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
		if (token == null || "".equals(token.trim())) {
			this.addResultInfo(BaseController.FAILURE, "未登录", null);
			this.responseResult(response, this.getResultJSONStr());
		} else {
			String name = (String) SecurityUtils.getSubject().getSession().getAttribute(token);
			if (name != null || !"".equals(name.trim())) {
				this.addResultInfo(BaseController.SUCCESS, "已经登录", null);
				this.responseResult(response, this.getResultJSONStr());
			}
		}

	}

	/**
	 * 退出登录
	 *
	 * @param sesstion
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("null")
	@ApiOperation(value = "退出登录", httpMethod = "POST", notes = "logOut")
	@PostMapping("/login/logOut")
	private void logOut(HttpServletRequest request, HttpServletResponse response) {
		// 注销，删除session中对应的值
		String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");// 获取Token令牌
		if (token != null || !"".equals(token.trim())) {
			SecurityUtils.getSubject().getSession().removeAttribute(token);// 删除session中的用户
			SecurityUtils.getSubject().getSession().removeAttribute("token");// 删除session中令牌
			this.addResultInfo(BaseController.SUCCESS, "退出登录", null);
			this.responseResult(response, this.getResultJSONStr());
		} else {
			this.addResultInfo(BaseController.FAILURE, "未登录", null);
			this.responseResult(response, this.getResultJSONStr());
		}
	}

	/**
	 *
	 * 方法说明：单点登录前的加密
	 * 
	 * @Title: ssologin
	 * @param username
	 * @param password
	 * @param usermng
	 * @return entryparams 加密后数据
	 */
	@ApiOperation(value = "sso登录", notes = "登录前的加密")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "usermng", value = "菜单模块", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "checkCode", value = "验证码", required = true, dataType = "String", paramType = "form") })
	@PostMapping("/login/ssolog")
	private ModelAndView ssolog(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("usermng") String usermng, @RequestParam("checkCode") String checkCode,
			HttpServletRequest request, HttpServletResponse response) {
		String word = (String) request.getSession().getAttribute("checkcode_session");// 从session中获取生成的验证码
		// 验证码校验
		if (checkCode.equals(word.trim())) {

			AccountInfoVo accountInfoVo;
			try {
				accountInfoVo = accountInfoService.findAccInfoByAccName(username);
				if (accountInfoVo == null) { // 验证是否存在该用户
					this.addResultInfo(BaseController.FAILURE, "用户名错误", null);
					this.responseResult(response, this.getResultJSONStr());
					return new ModelAndView("/sso?entryparams=" + null);
				} else {
					if (password.equals(accountInfoVo.getPassword())) { // 验证密码
						if (accountInfoVo.getAccDelFlag() == 0) { // 验证用户状态
							String pass = CryptoUtilOri.encrypt(password); // 密码加密
							StringBuffer str = new StringBuffer();
							str.append(username).append("&nics,")
							   .append(pass).append("&nics,")
							   .append(usermng);
							String entryparams = CryptoUtilOri.encrypt(str.toString());

							this.addResultInfo(BaseController.SUCCESS, "登录成功", null);
							this.responseResult(response, this.getResultJSONStr());
							return new ModelAndView("/sso?entryparams=" + entryparams);

						} else {
							this.addResultInfo(BaseController.FAILURE, "该账户被禁用", null);
							this.responseResult(response, this.getResultJSONStr());
							return new ModelAndView("/sso?entryparams=" + null);
						}

					} else {
						this.addResultInfo(BaseController.FAILURE, "密码错误", null);
						this.responseResult(response, this.getResultJSONStr());
						return new ModelAndView("/sso?entryparams=" + null);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new ModelAndView("/sso?entryparams=" + null);
	}
	
	
	/**
	 *
	 * 方法说明：获取当前用户信息
	 * 
	 */
	public AccountInfoVo getUser() {
		AccountInfoVo accountInfoVo = null;
		try {
			String token = (String) SecurityUtils.getSubject().getSession().getAttribute("token");
			String username = (String) SecurityUtils.getSubject().getSession().getAttribute(token);
			accountInfoVo = accountInfoService.findAccInfoByAccName(username);
		} catch (Exception e) {
			log.error("获取当前登录用户失败，请检查是否已经正确登录，当前返回unkonwn用户。");
		}
		return accountInfoVo == null ? new AccountInfoVo() : accountInfoVo;
	}

}
