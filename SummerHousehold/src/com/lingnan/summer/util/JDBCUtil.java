package com.lingnan.summer.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;


public class JDBCUtil {
	private static DataSource dataSource;
	static {
		InputStream inputStream = null;
		try {
			inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
 
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConn() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 释放资源
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	public static void close(ResultSet resultSet,Statement statement,Connection connection) {
		try {
		    if(resultSet!=null) {
		    		resultSet.close();
		    }
		    if(statement!=null) {
		    	statement.close();
		    }
		    if(connection!=null) {
		    	connection.close();
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
