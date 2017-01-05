package com.heqing.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.heqing.base.BaseDaoImpl;
import com.heqing.bean.Classes;
import com.heqing.dao.ClassesDao;
import com.heqing.service.ClassesService;

@Service("ClassesService")
public class ClassesServiceImpl extends BaseDaoImpl<Classes>  implements ClassesService {

	@Resource
	private ClassesDao classesDao;

	public Classes testAOP() {
		System.out.println("测试 AOP");
		return (Classes)classesDao.getById(1l);
	}
}
