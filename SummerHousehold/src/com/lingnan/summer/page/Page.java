/**
 * 蔡
 */
package com.lingnan.summer.page;

import java.util.List;

public class Page<T> {
	
	private List<T> datas;
	private int count;   //记录
	private int page;    //多少页
	private int pageSize = 10;   //每页有十行记录
	
	public Page() {
		
	}
	
	public Page(List<T> datas, int count, int page, int pageSize) {
		this.datas = datas;
		this.count = count;
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
		
		page = count%pageSize==0?count/pageSize:count/pageSize+1;
		
		
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
