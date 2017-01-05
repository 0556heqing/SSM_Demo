package com.heqing.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.heqing.base.BaseDaoImpl;
import com.heqing.bean.User;
import com.heqing.bean.User_Role;
import com.heqing.dao.UserDao;
import com.heqing.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseDaoImpl<User>  implements UserService {

	@Resource
	private UserDao userDao;

	public void setUser_Role(List<User_Role> urs){
		userDao.setUser_Role(urs);
	}
	
	public User getByName(String name){
		return userDao.getByName(name);
	}
}
