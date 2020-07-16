package com.lingnan.summer.query;

import java.util.List;

import com.lingnan.summer.util.StringUtil;

public class UserQuery extends BaseQuery {
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if(!StringUtil.isEmpty(name)){
			whereSQL.append("name=? and ");
			queryParams.add(name);
		}
		if(whereSQL.lastIndexOf(" and ")!=-1){
			whereSQL.delete(whereSQL.lastIndexOf(" and "), whereSQL.length());
			whereSQL.insert(0, " where ");
		}
	}

}
