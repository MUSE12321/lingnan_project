package com.lingnan.summer.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.domain.Record;
import com.lingnan.summer.service.RecordService;
import com.lingnan.summer.util.JDBCUtil;

public class RecordServiceImpl  implements RecordService{


	@Override
	public List<Record> query() {
		 int newdid ;
		 String newdname;
		 String newrisk;
		List<Record> records = new ArrayList<Record>();
		  
		  Connection conn = JDBCUtil.getConn();
			
			Statement stat=null;
			ResultSet rs =null;
	    try {
			 stat=conn.createStatement();
			 String sql ="select * from record where risk='高'";
			 rs =stat.executeQuery(sql);
			
			while(rs.next())
			{
				Record record = new Record();
				record.setDid(rs.getInt("did"));
			    record.setDname(rs.getString("dname"));
			    record.setRisk(rs.getString("risk"));
			    records.add(record);
			    System.out.println(rs.getInt("did"));
			/*    for(int i=0;i<records.size();i++){
			    	 System.out.println(records.get(i));
			    }*/
				
			
			PreparedStatement prep1 = null;
			try {
				prep1 = conn.prepareStatement("insert into alarm(did,dname,risk,measure) values(?,?,?,?)");
				prep1.setInt(1, rs.getInt("did"));
				prep1.setString(2, rs.getString("dname"));
				prep1.setString(3, rs.getString("risk"));
				prep1.setString(4, "及时报修并更换");
				prep1.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  
			}	
		   
		  } catch (SQLException e) {
		   e.printStackTrace();
		  } 
		  return records;
	}
	    
			

	
	
	
	public int add(Alarm alarm) {
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
		return 0;

	}

	}


