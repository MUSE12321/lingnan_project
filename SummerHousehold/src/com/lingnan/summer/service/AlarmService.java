package com.lingnan.summer.service;

import java.util.List;

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.AlarmQuery;

public interface AlarmService {
	
	Page<Alarm> query(AlarmQuery alarmQuery);
	
	int findCount(String SQL,List<Object> params);
	
	int add(Alarm alarm);

	int deleteById(int id);

	Alarm findAlarmById(int id);

	int update(Alarm alarm);
	
}
