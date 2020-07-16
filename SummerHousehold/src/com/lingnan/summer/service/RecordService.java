package com.lingnan.summer.service;

import java.util.List;

import com.lingnan.summer.domain.Alarm;
import com.lingnan.summer.domain.Record;

public interface RecordService {

	List<Record> query();
	
	int add(Alarm alarm);

}
