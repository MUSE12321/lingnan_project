package com.lingnan.summer.domain;

import java.util.ArrayList;
import java.util.List;

import com.lingnan.summer.util.StringUtil;

public class Alarm {
	private int aid;
	private int did;
	private String dname;
	private String risk;
	private String measure;
	private String temp;

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
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

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	private StringBuilder columnPlaceholder = new StringBuilder();
	private StringBuilder paramPlaceholder = new StringBuilder();

	private List<Object> params = new ArrayList<Object>();

	public List<Object> getParams() {
		return params;
	}

	public StringBuilder getColumnPlaceholder() {
		return columnPlaceholder;
	}

	public StringBuilder getParamPlaceholder() {
		return paramPlaceholder;
	}

	@Override
	public String toString() {
		return "Alarm [did=" + did + ", dname=" + dname + ", risk=" + risk
				+ ", measure=" + measure + "]";
	}

	public void dualDynamicSQL() {

		columnPlaceholder.append("did").append(",");
		paramPlaceholder.append("?,");
		params.add(did);

		if (!StringUtil.isEmpty(dname)) {
			columnPlaceholder.append("dname").append(",");
			paramPlaceholder.append("?,");
			params.add(dname);
		}

		if (!StringUtil.isEmpty(risk)) {
			columnPlaceholder.append("risk").append(",");
			paramPlaceholder.append("?,");
			params.add(risk);
		}

		if (!StringUtil.isEmpty(measure)) {
			columnPlaceholder.append("measure").append(",");
			paramPlaceholder.append("?,");
			params.add(measure);
		}

		int index = columnPlaceholder.lastIndexOf(",");
		if (index != -1) {
			columnPlaceholder.deleteCharAt(index);
			paramPlaceholder.deleteCharAt(paramPlaceholder.lastIndexOf(","));
		}

	}
}
