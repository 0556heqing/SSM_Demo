package com.heqing.service;

import java.util.List;

import com.heqing.base.BaseDao;
import com.heqing.bean.Privilege;
import com.heqing.bean.Role_Privilege;

public interface PrivilegeService extends BaseDao<Privilege> {

	public void setRole_Privilege(List<Role_Privilege> rp);
}
