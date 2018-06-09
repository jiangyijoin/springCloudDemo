package com.chinaoly.frm.common.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 将数据库查出的结果格式化为拥有指定属性的JSONObject
 * 
 * @author jiangyi
 * @Date Nov 27 2012
 */
public class ParseJSONObject {

	/**
	 * 格式化 注:数据库结果顺序必须和字段列表顺序相对应
	 * 
	 * @param attr
	 *            字段名称列表，如id，name等
	 * @param daoRs
	 *            数据库查询结果
	 * @return
	 */
	public static List<JSONObject> parse(ArrayList<String> attr,
			List<Object> daoRs) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		if (daoRs != null && daoRs.size() > 0) {
			for (Object o : daoRs) {
				JSONArray ja = JSONArray.fromObject(o);
				JSONObject jo = new JSONObject();
				for (int i = 0, len = attr.size(); i < len; i++) {
					String v = ja.getString(i);
					jo.put(attr.get(i), "null".equals(v) ? "" : v);
				}
				result.add(jo);
			}
		}
		return result;
	}

	/**
	 * 格式化 注:数据库结果顺序必须和字段列表顺序相对应
	 * 
	 * @param attr
	 *            字段名称字符串，中间用“,”分隔,“id,name,desc”
	 * @param daoRs
	 *            数据库结果
	 * @return
	 */
	public static List<JSONObject> parse(String attr, List<Object> daoRs) {
		ArrayList<String> attrList = new ArrayList<String>();
		for (String a : attr.split(",")) {
			attrList.add(a);
		}
		return parse(attrList, daoRs);
	}
}
