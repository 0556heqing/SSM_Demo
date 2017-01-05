package com.heqing.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.heqing.base.BaseDaoImpl;
import com.heqing.bean.Privilege;
import com.heqing.bean.Role_Privilege;
import com.heqing.dao.PrivilegeDao;
import com.heqing.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege>  implements PrivilegeService {

	@Resource
	private PrivilegeDao privilegeDao;

	@Override
	public void setRole_Privilege(List<Role_Privilege> rp) {
		privilegeDao.setRole_Privilege(rp);
	}
}
