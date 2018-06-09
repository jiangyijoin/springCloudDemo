package com.chinaoly.frm.process.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * @author ：
 * @param <T>
 * @date ： 2017年6月6日 下午1:51:06
 * @Desc ：分页工具类
 */
public class PageUtils implements java.io.Serializable {

	private static final long serialVersionUID = 7879540827710060525L;

	private final static int PAGE_SIZE = 10; // 默认显示的记录数
	
	private Long total;// 总记录数
	
	@JsonIgnore
	private int nowpage;//当前页
	@JsonIgnore
	private int pagesize;//页面记录大小
	
	@JsonIgnore
	private int rowStart;//分页起始
	@JsonIgnore
	private int rowEnd;//分页结束
	
	@JsonIgnore
	private String order = "desc";
	
	@JsonIgnore
	private String sort = "id";
	
	/** 查询条件,使用参考ParamsReflect类 */
	@JsonIgnore
	private Map<String, Object> condition = Maps.newHashMap();
	
	@JsonIgnore
	private List<String> selectCondition ;
	
	/** 排序规则,例：order by name desc, id asc 参考addOrders方法*/
	@JsonIgnore
	private String orders;

	public PageUtils() {}
	
	public PageUtils(Integer nowpage, Integer pagesize) {
		// 计算当前页
		if (null == nowpage || nowpage <= 0) {
			this.nowpage = 1;
		} else {// 当前页
			this.nowpage = nowpage;
		} 
		// 记录每页显示的记录数
		if (null == pagesize || pagesize <= 0) {
			this.pagesize = PAGE_SIZE;
		} else {
			this.pagesize = pagesize;
		}
		// 计算开始的记录和结束的记录
		this.rowStart = (this.nowpage - 1) * this.pagesize;
		this.rowEnd = this.pagesize;
	}
	
	public PageUtils(Integer nowpage, Integer pagesize, String sort, String order) {
		this(nowpage, pagesize);
		//排序字段，正序还是反序
		this.sort = sort;
		this.order = order;
		this.orders = "order by "+sort+" "+order;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public List<String> getSelectCondition() {
		return selectCondition;
	}

	public void setSelectCondition(List<String> selectCondition) {
		this.selectCondition = selectCondition;
	}

	public String getOrders() {
		return orders;
	}
	
	public void setOrders(String orders) {
		this.orders = orders;
	}
	/**
	 * 可直接传入自定义排序规则，请参考sql的别名定义，接在优先级最高的地方。
	 * 可多次设置不覆盖
	 * @param orders 传参格式："name asc"
	 */
	public void addOrders(String orders) {
		this.orders = this.orders + ", " + orders;
	}
	
}
