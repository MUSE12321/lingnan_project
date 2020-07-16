package com.lingnan.summer.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.lingnan.summer.domain.Task;
import com.lingnan.summer.service.TaskService;
import com.lingnan.summer.util.JDBCUtil;

public class TaskServiceImpl implements TaskService {

	@Override
	public int add(Task task) {
		task.dualDynamicSQL();
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		List<Object> params = task.getParams();
		String SQL = "insert into task("+task.getColumnPlaceholder()+") values ("+task.getParamPlaceholder()+")";
		try {
	    	  return queryRunner.update(SQL,params.toArray());
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
