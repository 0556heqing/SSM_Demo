package com.heqing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heqing.bean.Classes;
import com.heqing.bean.Privilege;
import com.heqing.bean.Role;
import com.heqing.bean.Role_Privilege;
import com.heqing.bean.Teacher;
import com.heqing.bean.User;
import com.heqing.bean.User_Role;
import com.heqing.service.ClassesService;
import com.heqing.service.PrivilegeService;
import com.heqing.service.RoleService;
import com.heqing.service.TeacherService;
import com.heqing.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)		// 表示继承了 SpringJUnit4ClassRunner 类
@ContextConfiguration("classpath:spring-mybatis.xml")
public class TestASDU {

	@Resource	
	private ClassesService classesService;
	@Resource	
	private TeacherService teacherService;
	@Resource	
	private UserService userService;
	@Resource	
	private RoleService roleService;
	@Resource	
	private PrivilegeService privilegeService;

//	@Test
	public void testAdd() {
		Privilege menu1, menu2, menu3;		
		menu1 = new Privilege("班级信息", "/classes/show", null, null);
		menu2 = new Privilege("教师信息", "/teacher/show", null, null);
		menu3 = new Privilege("测试json", "/classes/testJson", null, menu1);
		privilegeService.save(menu1);
		privilegeService.save(menu2);
		privilegeService.save(menu3);
		
		Role role1 = new Role();
		role1.setName("教师");
		Role role2 = new Role();
		role2.setName("年级主任");
		roleService.save(role1);
		roleService.save(role2);
		
		List rps = new ArrayList<Role_Privilege>();
		rps.add(new Role_Privilege(role2,menu1));
		rps.add(new Role_Privilege(role2,menu2));
		rps.add(new Role_Privilege(role2,menu3));
		roleService.setRole_Privilege(rps);

		User user = new User();
		user.setLoginName("heqing");
		user.setPassword("heqing");
		userService.save(user);

		List urs = new ArrayList<User_Role>();
		urs.add(new User_Role(user,role1));
		urs.add(new User_Role(user,role2));
		userService.setUser_Role(urs);
	}
	
	@Test
	public void testfind() {
//		User user = (User)userService.getById(2l);
		User user = (User)userService.getByName("heqing");
		System.out.println(">>>"+user.hasPrivilegeByUrl("/classes/show"));
		
//		List<User> users = userService.findAll();
//		System.out.println(users.size());
		
		
	}
	
//	@Test
	public void testUpdate() {
		User user = (User)userService.getById(1l);
		user.setLoginName("hq");
		userService.update(user);
	}
	
//	@Test
	public void testDelete() {
		userService.delete(1l);
	}
}
