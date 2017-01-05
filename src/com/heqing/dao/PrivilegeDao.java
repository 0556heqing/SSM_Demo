package com.heqing.dao;

import java.util.List;

import com.heqing.base.BaseDao;
import com.heqing.bean.Privilege;
import com.heqing.bean.Role_Privilege;

/**
 * 持久层，数据访问对象
 */
public interface PrivilegeDao extends BaseDao<Privilege> {
	
	public void setRole_Privilege(List<Role_Privilege> rp);
}
