package com.lingnan.summer.domain;

public class Record {
	
	private int did;
	private String dname;
	private String status;
	private String risk;
	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	@Override
	public String toString() {
		return "Record [did=" + did + ", dname=" + dname + ", status=" + status
				+ ", risk=" + risk + "]";
	}


}
