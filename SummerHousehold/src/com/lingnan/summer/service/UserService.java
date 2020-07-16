package com.lingnan.summer.service;

/**
 * @author yinrui
 */

import java.util.List;

import com.lingnan.summer.domain.User;
import com.lingnan.summer.page.Page;
import com.lingnan.summer.query.UserQuery;

public interface UserService {
	
	User login(String name,String password);
	
	Page<User> query(UserQuery userquery);
	
	int findCount(String SQL,List<Object> params);

	int add(User user);

	int deleteById(int id);

	User findUserById(int id);

	int update(User user);
	
}
