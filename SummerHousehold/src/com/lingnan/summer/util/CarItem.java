package com.lingnan.summer.util;

import com.lingnan.summer.domain.DeviceProduct;



public class CarItem {
	
	private DeviceProduct device;
	
	private Integer count;   
	
	private Integer itemNumber;

	public DeviceProduct getDevice() {
		return device;
	}

	public void setDevice(DeviceProduct device) {
		this.device = device;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}
}
