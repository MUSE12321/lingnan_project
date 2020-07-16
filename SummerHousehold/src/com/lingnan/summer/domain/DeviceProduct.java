package com.lingnan.summer.domain;

import com.lingnan.summer.domain.base.BaseDomain;

public class DeviceProduct extends BaseDomain {

	private String name;
	private String img;
	private Integer number; // 单位

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
