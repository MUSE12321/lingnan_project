package com.lingnan.summer.query;

import java.util.List;

import com.lingnan.summer.util.StringUtil;




/**
 * 封装查询条件的
 * @author Administrator
 *
 */
public class DeviceQuery extends BaseQuery{
	private String dname;
	private String tid;
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}

	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	/**
	 * 动态获取查询条件
	 */
	@Override
	public String getWhereSQL() {//获取查询条件
		return whereSQL.toString();
	}
	@Override
	public List<Object> getWhereParams() {//获取查询参数
		return queryParams;
	}
	
	@Override
	public void init() {
		if(!StringUtil.isEmpty(dname)) {
			whereSQL.append("dname=? and ");
			queryParams.add(dname);
		}
		if(!StringUtil.isEmpty(tid)) {
			whereSQL.append("tid=? and ");
			queryParams.add(tid);
		}
		if(whereSQL.lastIndexOf(" and ")!=-1) {
		    whereSQL.delete(whereSQL.lastIndexOf(" and "),whereSQL.length());
		    whereSQL.insert(0," where ");
		}
	}
	 
}
