package com.lingnan.summer.table;

import javax.swing.table.AbstractTableModel;

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.AlarmQuery;
import com.lingnan.summer.service.AlarmService;
import com.lingnan.summer.service.impl.AlarmServiceImpl;

public class AlarmTableModel extends AbstractTableModel {
	
	private String [] columnName = {"aid","did","名字","风险","措施"};
	
	private AlarmService alarmService = new AlarmServiceImpl();
	
	private Page<Alarm> page;
	
	public Page<Alarm> getPage() {
		return page;
	}
	
	public void query(AlarmQuery alarmQuery) {
		//查找全部数据
		page = alarmService.query(alarmQuery);
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
		Alarm alarm = page.getDatas().get(rowIndex);
		if(columnIndex==0) {
			return alarm.getAid();
		}if(columnIndex==1) {
			return alarm.getDid();
		}else if(columnIndex==2) {
			return alarm.getDname();
		}else if(columnIndex==3) {
			return alarm.getRisk();
		}else if(columnIndex==4) {
			return alarm.getMeasure();
		}else{
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}

}
