package com.lingnan.summer.domain;

import java.util.ArrayList;
import java.util.List;

import com.lingnan.summer.util.StringUtil;

public class Task {

	private int tid;
	private int did;
	private String dname;
	private String instruction;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	private StringBuilder columnPlaceholder = new StringBuilder();// 添加表的列数
	private StringBuilder paramPlaceholder = new StringBuilder();// 对应多少个的？

	public StringBuilder getColumnPlaceholder() {
		return columnPlaceholder;
	}

	public StringBuilder getParamPlaceholder() {
		return paramPlaceholder;
	}

	@Override
	public String toString() {
		return "task [did=" + did + ", dname=" + dname + ", instruction="
				+ instruction + "]";
	}

	private List<Object> params = new ArrayList<Object>();

	public List<Object> getParams() {
		return params;
	}

	public void dualDynamicSQL() {
		if (!StringUtil.isEmpty(dname)) {
			columnPlaceholder.append("dname").append(",");// 一个属性对应一个？，中间用,隔开
			paramPlaceholder.append("?,");
			params.add(dname);
		}
		if (!StringUtil.isEmpty(instruction)) {
			columnPlaceholder.append("instruction").append(",");// 一个属性对应一个？，中间用,隔开
			paramPlaceholder.append("?,");
			params.add(instruction);
		}
		int index = columnPlaceholder.lastIndexOf(",");
		if (index != -1) {
			columnPlaceholder.deleteCharAt(index);
			paramPlaceholder.deleteCharAt(paramPlaceholder.lastIndexOf(","));
		}
	}

}
