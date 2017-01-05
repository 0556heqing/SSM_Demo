package com.heqing.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.heqing.base.BaseDaoImpl;
import com.heqing.bean.Teacher;
import com.heqing.dao.TeacherDao;
import com.heqing.service.TeacherService;

@Service("teacherService")
public class TeacherServiceImpl extends BaseDaoImpl<Teacher> implements TeacherService{

	@Resource
	private TeacherDao teacherDao;

}
