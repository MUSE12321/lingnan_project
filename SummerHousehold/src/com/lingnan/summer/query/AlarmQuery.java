package com.lingnan.summer.query;

import java.util.List;

import com.lingnan.summer.util.StringUtil;

public class AlarmQuery extends BaseQuery {
	
	private String dname;

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}
	
	
    //动态获取查询条件
	@Override
	public String getWhereSQL() {
		return whereSQL.toString();
	}

	@Override
	public List<Object> getWhereParams() {
		return queryParams;
	}

	@Override
	public void init() {
		if(!StringUtil.isEmpty(dname)) {
			whereSQL.append("dname=? and ");
			queryParams.add(dname);
		}
		if(whereSQL.lastIndexOf(" and ")!=-1) {
		    whereSQL.delete(whereSQL.lastIndexOf(" and "),whereSQL.length());
		    whereSQL.insert(0," where ");
		}
	}

}
