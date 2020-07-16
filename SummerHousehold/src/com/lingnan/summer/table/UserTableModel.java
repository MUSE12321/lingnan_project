package com.lingnan.summer.table;

import javax.swing.table.AbstractTableModel;

import com.lingnan.summer.domain.User;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.UserQuery;
import com.lingnan.summer.service.UserService;
import com.lingnan.summer.service.impl.UserServiceImpl;

public class UserTableModel extends AbstractTableModel {
	
	private String [] columnName = {"id","名字","地址","电话"};

	private UserService userService = new UserServiceImpl();
	
	private Page<User> page;
	
	public Page<User> getPage() {
		return page;
	}
	
	public void query(UserQuery userQuery) {
		//查找全部数据
		page = userService.query(userQuery);
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
		User user = page.getDatas().get(rowIndex);
		if(columnIndex==0) {
			return user.getId();
		}else if(columnIndex==1) {
			return user.getName();
		}else if(columnIndex==2) {
			return user.getAddress();
		}else if(columnIndex==3) {
			return user.getPhone();
		}else {
			return null;
		}
	}
	 
	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	
}