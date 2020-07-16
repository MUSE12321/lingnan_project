package com.lingnan.summer.query;

import java.util.List;

import com.lingnan.summer.util.StringUtil;

public class LivingQuery extends BaseQuery{
	private String dname;
	private String tid;
	private String location;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = "����";
	}
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
	 * ��̬��ȡ��ѯ����
	 */
	@Override
	public String getWhereSQL() {//��ȡ��ѯ����
		return whereSQL.toString();
	}
	@Override
	public List<Object> getWhereParams() {//��ȡ��ѯ����
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
		if(!StringUtil.isEmpty(location)) {
			whereSQL.append("location='����' and ");
			queryParams.add(location);
		}
		if(whereSQL.lastIndexOf(" and ")!=-1) {
		    whereSQL.delete(whereSQL.lastIndexOf(" and "),whereSQL.length());
		    whereSQL.insert(0," and ");
		}
	}
	 
}

