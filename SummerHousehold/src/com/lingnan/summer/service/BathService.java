package com.lingnan.summer.service;

import java.util.List;

import com.lingnan.summer.domain.Device;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.BathQuery;

public interface BathService {
	int add(Device device);

	Page<Device> query(BathQuery bathQuery);

	int findCount(String SQL, List<Object> params);

	int deleteById(int did);

	Device findDeviceById(int did);

	int update(Device device);


}
