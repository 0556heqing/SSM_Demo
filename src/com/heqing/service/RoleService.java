package com.heqing.service;

import java.util.List;

import com.heqing.base.BaseDao;
import com.heqing.bean.Role;
import com.heqing.bean.Role_Privilege;
import com.heqing.bean.User_Role;

public interface RoleService extends BaseDao<Role> {

	public void setRole_Privilege(List<Role_Privilege> rp);
	
	public void setUser_Role(List<User_Role> urs);
}
