package com.heqing.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import com.heqing.bean.Classes;
import com.heqing.bean.Privilege;
import com.heqing.bean.Role;
import com.heqing.bean.Teacher;
import com.heqing.bean.User;
import com.heqing.dao.ClassesDao;
import com.heqing.dao.PrivilegeDao;
import com.heqing.dao.RoleDao;
import com.heqing.dao.TeacherDao;
import com.heqing.dao.UserDao;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	protected Class<T> clazz; 
	protected BaseDao baseDao;
	
	@Resource
	private ClassesDao classesDao;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private PrivilegeDao privilegeDao;
	
	public BaseDaoImpl() {
		// 通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pt.getActualTypeArguments()[0];
	}
	
	private void setDao(){
		if(this.clazz.equals(Classes.class))  baseDao = classesDao;
		if(this.clazz.equals(Teacher.class))  baseDao = teacherDao;
		if(this.clazz.equals(User.class))  baseDao = userDao;
		if(this.clazz.equals(Role.class))  baseDao = roleDao;
		if(this.clazz.equals(Privilege.class))  baseDao = privilegeDao;
	}

	public void save(T entity) {
		setDao();
		baseDao.save(entity);
	}

	public void update(T entity) {
		setDao();
		baseDao.update(entity);
	}

	public void delete(Long id) {
		setDao();
		baseDao.delete(id);
	}

	public T getById(Long id) {
		setDao();
		return (T)baseDao.getById(id);
	}

	public List<T> getByIds(Long[] ids) {
		setDao();
		return baseDao.getByIds(ids);
	}

	public List<T> findAll() {
		setDao();
		return baseDao.findAll();
	}
	
	public List<T> getPageBean(int pageNum, int pageSize){
		setDao();
		return baseDao.getPageBean(pageNum, pageSize);
	}
}
