package com.lingnan.summer.domain;

/**
 * @author yinrui
 */

import java.util.ArrayList;
import java.util.List;

import com.lingnan.summer.domain.base.BaseDomain;
import com.lingnan.summer.util.StringUtil;

public class User extends BaseDomain {

	private int id;
	private String name;
	private String password;
	private String address;
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return "User [" + "name=" + name + ", password=" + password
				+ ", address=" + address + ", phone=" + phone + "]";
	}

	public void dualDynamicSQL() {

		if (!StringUtil.isEmpty(name)) {
			columnPlaceholder.append("name").append(",");
			paramPlaceholder.append("?,");
			params.add(name);
		}

		if (!StringUtil.isEmpty(password)) {
			columnPlaceholder.append("password").append(",");
			paramPlaceholder.append("?,");
			params.add(password);
		}

		if (!StringUtil.isEmpty(address)) {
			columnPlaceholder.append("address").append(",");
			paramPlaceholder.append("?,");
			params.add(address);
		}

		if (!StringUtil.isEmpty(phone)) {
			columnPlaceholder.append("phone").append(",");
			paramPlaceholder.append("?,");
			params.add(phone);
		}

		int columnindex = columnPlaceholder.lastIndexOf(",");
		// 获取最后一个逗号，如果没有结束则返回-1，当不等于-1时，说明已经结束了。
		int paramindex = paramPlaceholder.lastIndexOf(",");
		if (columnindex != -1) {
			columnPlaceholder.deleteCharAt(columnindex);
		}
		if (paramindex != -1) {
			paramPlaceholder.deleteCharAt(paramindex);
		}

	}
}
