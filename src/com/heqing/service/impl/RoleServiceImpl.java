package com.heqing.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.heqing.base.BaseDaoImpl;
import com.heqing.bean.Role;
import com.heqing.bean.Role_Privilege;
import com.heqing.bean.User_Role;
import com.heqing.dao.RoleDao;
import com.heqing.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseDaoImpl<Role>  implements RoleService {

	@Resource
	private RoleDao roleDao;

	@Override
	public void setRole_Privilege(List<Role_Privilege> rp) {
		roleDao.setRole_Privilege(rp);
	}
	
	public void setUser_Role(List<User_Role> urs){
		roleDao.setUser_Role(urs);
	}

}
