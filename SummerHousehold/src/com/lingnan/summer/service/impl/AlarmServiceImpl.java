package com.lingnan.summer.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.AlarmQuery;
import com.lingnan.summer.service.AlarmService;
import com.lingnan.summer.util.JDBCUtil;

public class AlarmServiceImpl implements AlarmService {

	@Override
	public Page<Alarm> query(AlarmQuery alarmQuery) {
		alarmQuery.init();

		Page<Alarm> page = new Page<Alarm>();

		String SQL = "select * from alarm " + alarmQuery.getWhereSQL();

		List<Object> params = alarmQuery.getWhereParams();

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());

		try {
			page.setDatas(queryRunner.query(SQL, new BeanListHandler<Alarm>(
					Alarm.class), params.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setPageSize(10);

		// 当前查询条件下的记录数
		int count = findCount(
				"select count(*) from alarm " + alarmQuery.getWhereSQL(),
				alarmQuery.getWhereParams());
		page.setCount(count); // 当前条件下多少条记录
		return page;
	}

	@Override
	public int findCount(String SQL, List<Object> params) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		try {
			return (int) (queryRunner.query(SQL, new ScalarHandler<Long>(),
					params.toArray()).longValue());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	@Override
	public int add(Alarm alarm) {
		alarm.dualDynamicSQL();

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());

		List<Object> params = alarm.getParams();
		String SQL = "insert into alarm(" + alarm.getColumnPlaceholder()
				+ ") values (" + alarm.getParamPlaceholder() + ")";
		try {
			return queryRunner.update(SQL, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteById(int id) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "delete from alarm where aid = ?";
		try {
			return queryRunner.update(SQL, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Alarm findAlarmById(int id) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "select * from alarm where aid = ?";
		try {
			return queryRunner.query(SQL, new BeanHandler<Alarm>(Alarm.class),
					id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Alarm alarm) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "update alarm set did=? dname=?,risk=?,measure=? where aid = ?";
		try {
			return queryRunner.update(SQL, new Object[] { alarm.getDname(),
					alarm.getRisk(), alarm.getMeasure(), alarm.getDid() });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
