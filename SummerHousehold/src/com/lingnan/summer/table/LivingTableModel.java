package com.lingnan.summer.table;

import javax.swing.table.AbstractTableModel;

import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.LivingQuery;
import com.lingnan.summer.service.LivingService;
import com.lingnan.summer.service.impl.LivingServiceImpl;

public class LivingTableModel extends AbstractTableModel {
	private String [] columnName = {"id","名称","类型","位置","参数","状态"};

	private LivingService livingService = new LivingServiceImpl();
		
	private Page<Device> page;
	
	public Page<Device> getPage() {
		return page;
	}

	public  void query(LivingQuery livingQuery) {
		//查找全部数据
		page = livingService.query(livingQuery);
	}
	
	
	@Override
	public int getRowCount() {
		return page.getDatas().size();
	}

	@Override
	public int getColumnCount() {  
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Device device = page.getDatas().get(rowIndex);
		if(columnIndex==0) {
			return device.getDid();
		}else if(columnIndex==1) {
			return device.getDname();
		}else if(columnIndex==2) {
			return device.getTid();
		}else if(columnIndex==3) {
			return device.getLocation();
		}else if(columnIndex==4){
			return device.getParam();
		}else if(columnIndex==5){
			return device.getStatus();
		}else{
			return null;
		}
	}
	 
	
	public String getColumnName(int column) {
		return columnName[column];
	}
	
}



