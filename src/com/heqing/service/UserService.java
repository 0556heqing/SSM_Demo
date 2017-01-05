package com.heqing.service;

import java.util.List;

import com.heqing.base.BaseDao;
import com.heqing.bean.User;
import com.heqing.bean.User_Role;

public interface UserService extends BaseDao<User> {
	
	public void setUser_Role(List<User_Role> urs);

	public User getByName(String name);
}
