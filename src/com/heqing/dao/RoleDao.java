package com.heqing.dao;

import java.util.List;

import com.heqing.base.BaseDao;
import com.heqing.bean.Role;
import com.heqing.bean.Role_Privilege;
import com.heqing.bean.User_Role;

/**
 * 持久层，数据访问对象
 */
public interface RoleDao extends BaseDao<Role> {
	
	public void setRole_Privilege(List<Role_Privilege> rp);
	
	public void setUser_Role(List<User_Role> urs);
}
