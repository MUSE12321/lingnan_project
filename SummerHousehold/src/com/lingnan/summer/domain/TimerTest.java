package com.lingnan.summer.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.lingnan.summer.service.RecordService;
import com.lingnan.summer.service.impl.RecordServiceImpl;
import com.lingnan.summer.util.JDBCUtil;

public class TimerTest {
//	RecordService recordService = new RecordServiceImpl();
	/*Record record = new Record();

	
	public static void main(String[] args) {

		Timer timer = new Timer();
		timer.schedule(new MyTask(), 1000, 2000);

	}

}

class MyTask extends TimerTask {

	private void add(Alarm alarm) {
		int did = alarm.getDid();
		String dname =alarm.getDname();
		String risk = alarm.getRisk();
		String measure = alarm.getMeasure();
		Connection conn = JDBCUtil.getConn();
		PreparedStatement prep1 = null;
		try {
			prep1 = conn.prepareStatement("insert into alarm values(?,?,?,?)");
			prep1.setInt(1, did);
			prep1.setString(2, dname);
			prep1.setString(3, risk);
			prep1.setString(4, measure);
			prep1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void run() {
	    query();
	}*/
	
}
