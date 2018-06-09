/**
 *
 */
package com.chinaoly.frm.core.service;

import com.chinaoly.frm.core.entity.DictObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author jiangyi
 * @Date 2018.5.11
 */
@Component
public class BaseService{
	
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RedisKeyValuesService redisKeyValuesService;
    
    /**
     * 根据父节点的id，从T_SYS_DICT表中取所有此节点下的数据。本函数主要用在取下拉列表框的值
     *
     * @param parentId 父节点的名称，对应预表T_SYS_DICT字段。
     * @return
     * @throws Exception 
     */
    public Set<DictObject> getDictList(String parentId) throws Exception {
        Set<String> childs = redisKeyValuesService.getSetValue("dictdatas:" + parentId + ":children");
        Set<DictObject> dictObjects = new HashSet<DictObject>();
        if(childs.size() > 0) {
        	for(String child : childs) {
        		JSONArray arr = JSONArray.fromObject(child);
        		DictObject dict = (DictObject) JSONObject.toBean(arr.getJSONObject(0), DictObject.class);
        		dictObjects.add(dict);
        	}
        }
        
        return dictObjects;        
    }

    /**
     * 从T_SYS_DICT获取已知字段值的指定字段值
     *
     * @param parentId 父节点Id
     * @param xname      已知字段名 “name”,"label","value"
     * @param xvalue     已经字段的值
     * @param yname      需要返回的字段名 “name”,"label","value"
     * @return yvalue 返回yname的值
     * @throws Exception 
     */
    public String getDictFieldValue(String parentId, String xname, String xvalue, String yname) throws Exception {
    	if(xname == null || xvalue == null || yname == null) return ""; 
    	
    	Set<String> childs = redisKeyValuesService.getSetValue("dictdatas:" + parentId + ":children");
         if(childs.size() > 0) {
         	for(String child : childs) {
         		JSONArray arr = JSONArray.fromObject(child);
         		JSONObject obj = arr.getJSONObject(0);
         		if( xvalue.toString().equals(obj.get(xname).toString())) { 
         			return obj.get(yname).toString();
         		}
         	}
         }
        return "";
    }

    /**
     * 从SYS_COMMONDATAS获取多个已知字段值的指定字段值
     *
     * @param parentName 父节点
     * @param xname      已知字段名 “name”,"label","value"
     * @param xvalueStr  已经字段的值:""可以传多个值 【数值之间用‘,’号隔开】
     * @param yname      需要返回的字段名 “name”,"label","value"
     * @return yvalue 返回yname的值
     */
    public HashMap<String, String> getCommonMultiFieldValue(String parentName, String xname, String xvalueStr, String yname) {
        HashMap<String, String> yvalues = new HashMap<String, String>();
        String[] xvalue = {};
//		if (StringUtils.isNotBlank(xvalueStr)) {
//			xvalue = xvalueStr.split(",");
//		}
//		List<CommonObject> commons = commonRedisDao.getCommondataByParentName(parentName);
//		for (CommonObject common : commons) {
//			Field xfield = null;
//			Field yfield = null;
//			try {
//				xfield = common.getClass().getDeclaredField(xname);
//				yfield = common.getClass().getDeclaredField(yname);
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			}
//			Field.setAccessible(new Field[] { xfield, yfield }, true);
//			String _xvalue = "";
//			String _yvalue = "";
//			try {
//				_xvalue = String.valueOf(xfield.get(common));
//				_yvalue = String.valueOf(yfield.get(common));
//			} catch (IllegalArgumentException e1) {
//				e1.printStackTrace();
//			} catch (IllegalAccessException e1) {
//				e1.printStackTrace();
//			}
//			if (xvalue.length > 0) {
//				for (int i = 0; i < xvalue.length; i++) {
//					if (_xvalue.equals(xvalue[i])) {
//						yvalues.put(_xvalue, _yvalue);
//						break;
//					}
//				}
//			} else {
//				yvalues.put(_xvalue, _yvalue);
//			}
//
//		}
        return yvalues;
    }

    /**
     * 从T_SYS_DICT获取已知字段值的指定字段值
     *
     * @param id  英文名字段的值 "id":id
     * @param yname 需要返回值的字段名 "label","value"
     * @return yvalue 返回yname的值 使用举例：getDictFieldValueByName("usermgr","value",意思是取英文名为“usermgr”的value值
     * @throws Exception 
     */
    public String getDictFieldValueById(String id, String yname) throws Exception {
        String key = "dictdatas:" + id + ":id";
        String value = redisKeyValuesService.getStringValue(key);
        JSONArray jsonObject = JSONArray.fromObject(value);
        if(jsonObject.size() > 0) {
        	JSONObject  dict = jsonObject.getJSONObject(0);
        	return (String)dict.get(yname) ;
        }        		
        return "";
    }

    /**
     * 根据名称获取commonobject
     *
     * @param name
     * @return
     */
    public DictObject getCommondataByName(String name) {

        //return commonRedisDao.getCommondataByName(name);
        return null;
    }

    /**
     * 系统日志记录
     *
     * @param appHost     服务器地址
     * @param ip          ip地址
     * @param userName    用户名
     * @param logLevel    操作结果 成功为1,失败为0
     * @param operateType 操作类型 在Constants中已经列出全部类型，记录时在其中选择即可
     * @param moduleName  模块名称 一般与菜单名一致
     * @param msg         操作信息 包括操作的条件
     * @param opttime     记录本次操作所花费的时间
     * @param currentTime 当前系统时间
     */
    public void saveSysLog(String appHost, String ip, String userName, String logLevel, String operateType,
                           String moduleName, String msg, int opttime, String currentTime) {
        //logDao.saveSysLog(appHost, ip, userName, logLevel, operateType, moduleName, msg, opttime, currentTime);
    }

    /**
     * 系统日志记录
     *
     * @param request     请求头
     * @param appHost     服务器地址
     * @param ip          ip地址
     * @param userName    用户名
     * @param logLevel    操作结果 成功为1,失败为0
     * @param operateType 操作类型 在Constants中已经列出全部类型，记录时在其中选择即可
     * @param moduleName  模块名称 一般与菜单名一致
     * @param msg         操作信息 包括操作的条件
     * @param opttime     记录本次操作所花费的时间
     * @param currentTime 当前系统时间
     */
    @SuppressWarnings("unchecked")
    public void saveSysLog(HttpServletRequest request, String appHost, String ip, String userName, String logLevel, String operateType,
                           String moduleName, String msg, int opttime, String currentTime) {
//		if("true".equals(isSyslogSaveExtColumns)){
//			Map<String,String> baseParam = null;
//			String baseAuthExtParam = request.getParameter("baseAuthExtParam");
//			if(baseAuthExtParam != null
//					&& baseAuthExtParam.length() > 0
//					&& baseAuthExtParam.startsWith("{")){
//				baseParam = JSONObject.fromObject(baseAuthExtParam);
//			}
//
//			//自动纠正模块名称，以前台传递的参数为准（即页面的模块名称，而不是代码写死的，或者页面中action的模块名称）
//			String moduleCode = getRequestParamByName(request, baseParam, "menuName");
//			if(moduleCode != null && moduleCode.llength() > 0){
//				if(!moduleCode.equals(moduleName)){
//					logger.info(request.getRequestURI() + ":页面模块名称(" + moduleCode + ")与代码模块名称(" + moduleName + ")不一致");
//					//自定义鉴权配置信息
//					RedisMap<String, String> configs = commonRedisDao.redisMap("sys:mobileauth:baseauthconfig");
//					String auth_ReplaceCode = "";
//					if(configs != null && configs.size() >0){
//						auth_ReplaceCode = configs.get("authReplaceCode");
//					}
//					if("1".equals(auth_ReplaceCode)){
//						moduleName = moduleCode;
//					}
//				}
//			}
//			//end 自动纠正
//			String mauth_list_id = getRequestParamByName(request, baseParam, "mauth_list_id");
//			if(mauth_list_id == null){
//				mauth_list_id = "";
//			}
//
//			if("HENAN".equals(this.projectAreaId)){
//				if(moduleName != null && moduleName.length() > 0){
//					boolean isFind = false;
//					List<CommonObject> list = this.getCommonList("sys_logs_map");
//					if(list != null && list.size() > 0){
//						for(CommonObject co : list){
//							if(co.getValue() != null && co.getValue().length() > 0
//									&& co.getValue().trim().equals(moduleName)){
//								isFind = true;
//								break;
//							}
//						}
//					}
//					if(isFind){
//						List<String> columns = new ArrayList<String>();
//						List<String> values = new ArrayList<String>();
//						if(mauth_list_id != null && mauth_list_id.length() > 0){
//							Map<String,String> map = logDao.getMauthInfo(mauth_list_id);
//							if(map != null){
//								columns = Arrays.asList("MOBILE_NO","AUTH_FLAG","OPT_REASON","ORDER_ID_","BLEADER_","AUTH_ID");
//								values = Arrays.asList(map.get("mobile_no"),map.get("auth_type"),map.get("opt_reason"),map.get("order_no"),map.get("bleader"),mauth_list_id);
//							}
//						}
//						logDao.saveSysLog("sys_logs_map", appHost, ip, userName, logLevel, operateType, moduleName, msg, opttime, currentTime,
//								columns,values);
//
//						return;
//					}
//				}
//			}
//
//			logDao.saveSysLog(appHost, ip, userName, logLevel, operateType, moduleName, msg, opttime, currentTime,
//					Arrays.asList("AUTH_ID"), Arrays.asList(mauth_list_id));
//		}else{
//			logDao.saveSysLog(appHost, ip, userName, logLevel, operateType, moduleName, msg, opttime, currentTime);
//		}
    }

    public String getModuleNameByRequestUrl(String aurl) {
        //return commonRedisDao.getModuleNameByActionUrl(aurl);
        return null;

    }

    /**
     * 根据用户Id查找多个角色
     * @param userId
     * @return
     * @throws Exception
     */
    public Set<String> getUserRoles(String accountId) throws Exception {
    	return redisKeyValuesService.getSetValue("user:"+ accountId +":role");
    }

    public List<String> getUserAreas(String userId) {
        //return logDao.getUserAreas(userId);
        return null;
    }

    public String getIsSyslogSaveExtColumns() {
        //return this.isSyslogSaveExtColumns;
        return null;
    }

    public String getProjectAreaId() {
        //return projectAreaId;
        return null;
    }

    //获取自定义参数
    private String getRequestParamByName(HttpServletRequest request, Map<String, String> params, String paramName) {
        String paramValue = "";
        if (params != null && params.size() > 0) {
            paramValue = params.get(paramName);
        }
        if (paramValue == null || paramValue.length() == 0) {
            paramValue = request.getParameter("baseAuthExtParam[" + paramName + "]");
        }
        if (paramValue == null || paramValue.length() == 0) {
            paramValue = request.getParameter(paramName);
        }

        if ("null".equals(paramValue)) {
            paramValue = "";
        }

        return paramValue;
    }
}
