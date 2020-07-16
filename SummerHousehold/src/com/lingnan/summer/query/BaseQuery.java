package com.lingnan.summer.query;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery {
	
	//查询条件
    protected StringBuilder whereSQL = new StringBuilder();
	
    //查询参数
    protected List<Object> queryParams = new ArrayList<Object>();
	
    //获取查询条件
	public abstract String getWhereSQL();
	
	//获取查询参数
	public abstract List<Object> getWhereParams();
	
	//构造条件和参数的
	public abstract void init();

}