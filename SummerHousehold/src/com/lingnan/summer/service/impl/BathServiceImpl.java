package com.lingnan.summer.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.BathQuery;
import com.lingnan.summer.service.BathService;
import com.lingnan.summer.util.JDBCUtil;

public class BathServiceImpl implements BathService {

	@Override
	public int add(Device device) {

		device.dualDynamicSQL();

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		List<Object> params = device.getParams();
		String SQL = "insert into device(" + device.getColumnPlaceholder()
				+ ") values (" + device.getParamPlaceholder() + ")";
		try {
			return queryRunner.update(SQL, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteById(int did) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "delete from device where did = ?";
		try {
			return queryRunner.update(SQL, did);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Page<Device> query(BathQuery bathQuery) {

		bathQuery.init();

		Page<Device> page = new Page<Device>();

		String SQL = "select * from device where location='洗手间'"+ bathQuery.getWhereSQL();

		List<Object> params = bathQuery.getWhereParams();// 获取查询参数

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());

		try {
			page.setDatas(queryRunner.query(SQL, new BeanListHandler<Device>(
					Device.class), params.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setPageSize(10);

		// 当前查询条件下的记录数
		int count = findCount(
				"select count(*) from device where location='洗手间' "
						+ bathQuery.getWhereSQL(), bathQuery.getWhereParams());
		page.setCount(count); // 当前条件下多少条记录
		return page;
	}

	@Override
	public Device findDeviceById(int did) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "select * from device where did = ?";
		try {
			return queryRunner.query(SQL,
					new BeanHandler<Device>(Device.class), did);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Device device) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "update device set dname=?,location=?,param=?,status=? where did = ?";// *
		try {
			return queryRunner.update(SQL, new Object[] { device.getDname(),
					device.getLocation(), device.getParam(),
					device.getStatus(), device.getDid() });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int findCount(String SQL, List<Object> params) {
		// long
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		try {
			return (int) (queryRunner.query(SQL, new ScalarHandler<Long>(),
					params.toArray()).longValue());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

}
