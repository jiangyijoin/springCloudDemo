/**
 * 
 */
package com.chinaoly.frm.core.rest.health;

import java.util.HashMap;

import com.chinaoly.frm.core.rest.BaseRestController;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinaoly.frm.common.utils.DateUtil;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
@RestController
public class HealthController extends BaseRestController {
	@ResponseBody
	@RequestMapping("/HEALTH_CHECK")
	public String healthCheck(){
		logger.info("Health Check !!");
		HashMap<String,String> r = new HashMap<String,String>();
		r.put("timestamp", DateUtil.getCurrentDatetime());
		r.put("health_status", "1");
		r.put("health_info", "");
		return JSONObject.fromObject(r).toString();
	}
}
