package com.lingnan.summer.util;

import java.util.HashMap;
import java.util.Map;

import com.lingnan.summer.domain.DeviceProduct;


//容器
public class ShoppingCar {
	
	private Integer totalNumber;
	private Map<Integer,CarItem> container = new HashMap<Integer,CarItem>();
	 
	/**
	 * 添加
	 * @param product
	 */
	public void add(DeviceProduct device) {
		CarItem carItem = container.get(device.getId());
		if(carItem==null) {
			carItem = new CarItem();
			carItem.setDevice(device);
			carItem.setCount(1);
			carItem.setItemNumber(device.getNumber());
			
			//totalNumber=??
		}else {
			//数量+1
			carItem.setCount(carItem.getCount()+1);
			carItem.setItemNumber(device.getNumber()*carItem.getCount());
			//totalNumber=??
		}
	}
	
	
	public void delete(DeviceProduct device) {
		
		CarItem carItem = container.get(device.getId());
		if(carItem!=null) {
			if(carItem.getCount()==1) {
				container.remove(device.getId());
				//totalNumber=??
			}else {
				carItem.setCount(carItem.getCount()-1);
				carItem.setItemNumber(device.getNumber()*carItem.getCount());
				//totalNumber=??
			}
		}
	}
	
	/**
	 * 清空购物车
	 */
	public void clear() {
		container.clear();
	}
}

