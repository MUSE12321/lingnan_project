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

	private StringBuilder columnPlaceholder = new StringBuilder();// ��ӱ������
	private StringBuilder paramPlaceholder = new StringBuilder();// ��Ӧ���ٸ��ģ�

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
			columnPlaceholder.append("dname").append(",");// һ�����Զ�Ӧһ�������м���,����
			paramPlaceholder.append("?,");
			params.add(dname);
		}
		if (!StringUtil.isEmpty(instruction)) {
			columnPlaceholder.append("instruction").append(",");// һ�����Զ�Ӧһ�������м���,����
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
