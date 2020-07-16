package com.lingnan.summer.service.impl;

/**
 * @author yinrui
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lingnan.summer.domain.User;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.UserQuery;
import com.lingnan.summer.service.UserService;
import com.lingnan.summer.util.JDBCUtil;
import com.lingnan.summer.util.SysUtil;

public class UserServiceImpl implements UserService {

	@Override
	public User login(String name, String password) {

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "select * from user where name = ? and password = ?";
		try {
			return queryRunner.query(SQL, new BeanHandler<User>(User.class),
					new Object[] { name, password });

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<User> query(UserQuery userQuery) {
		userQuery.init();

		Page<User> page = new Page<User>();

		String SQL = "select * from user " + userQuery.getWhereSQL();

		List<Object> params = userQuery.getWhereParams();

		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());

		try {
			page.setDatas(queryRunner.query(SQL, new BeanListHandler<User>(
					User.class), params.toArray()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		page.setPageSize(10);

		// 当前查询条件下的记录数
		int count = findCount(
				"select count(*) from user " + userQuery.getWhereSQL(),
				userQuery.getWhereParams());
		page.setCount(count);
		return page;
	}

	@Override
	public int add(User user) {

		user.dualDynamicSQL();

		Connection conn = JDBCUtil.getConn();

		String SQL = "insert into user(" + user.getColumnPlaceholder()
				+ ") values (" + user.getParamPlaceholder() + ")";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(SQL);
			List<Object> params = user.getParams();
			int length = params.size();
			for (int i = 0; i < length; i++) {
				preparedStatement.setObject(i + 1, params.get(i));
			}
			return preparedStatement.executeUpdate();
			// executeUpdate //insert update delete
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, preparedStatement, conn);
		}
		return 0;
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
	public int deleteById(int id) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "delete from user where id = ?";
		try {
			return queryRunner.update(SQL, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public User findUserById(int id) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "select * from user where id = ?";
		try {
			return queryRunner
					.query(SQL, new BeanHandler<User>(User.class), id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
		String SQL = "update user set password=?,address=?,phone=? where id = ?";
		try {
			return queryRunner.update(SQL, new Object[] { user.getPassword(),
					user.getAddress(), user.getPhone(), user.getId() });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
