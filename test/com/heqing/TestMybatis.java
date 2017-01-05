package com.heqing;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heqing.bean.Classes;
import com.heqing.bean.Teacher;
import com.heqing.service.ClassesService;
import com.heqing.service.TeacherService;

@RunWith(SpringJUnit4ClassRunner.class)		// 表示继承了 SpringJUnit4ClassRunner 类
@ContextConfiguration("classpath:spring-mybatis.xml")
public class TestMybatis {

	@Resource	
	private ClassesService classesService;
	@Resource	
	private TeacherService teacherService;

//	@Test
	public void test() {
		classesService.testAOP();
		System.out.println("=========");
		classesService.getById(1l);		//为什么不能执行
	}
	
	@Test
	public void testById() {
		Teacher teacher = (Teacher)teacherService.getById(1l);
		System.out.println("编号'1'的教师名为："+teacher.getName());
		System.out.println("	他是 "+teacher.getSuperviseClass().getName()+" 的班主任");
		System.out.print("	他管理的班级有："); for(Classes c : teacher.getClassDirector()) System.out.print(c.getName()+"  ");System.out.println(); 
		System.out.print("	他教授的班级有："); for(Classes c : teacher.getTeachClasses()) System.out.print(c.getName()+"  ");System.out.println(); 
		
		Classes classes = (Classes)classesService.getById(1l);
		System.out.println("编号'1'的班级名为："+classes.getName());
		System.out.println("	它的班主任为："+classes.getHeadTeacher().getName());
		System.out.println("	它的系主任为："+classes.getClassDirector().getName()); 
		System.out.print("	它的授课教师有："); for(Teacher t : classes.getTeachers()) System.out.print(t.getName()+"  ");System.out.println(); 
	}
	
//	@Test
	public void testfindAll() {
		List<Teacher> teachers = (List<Teacher>)teacherService.getByIds(new Long[]{1l,2l});
		for(Teacher teacher : teachers) {
			System.out.println("教师名为："+teacher.getName());
			System.out.println("	他是 "+teacher.getSuperviseClass().getName()+" 的班主任");
			System.out.print("	他管理的班级有："); for(Classes c : teacher.getClassDirector()) System.out.print(c.getName()+"  ");System.out.println(); 
			System.out.print("	他教授的班级有："); for(Classes c : teacher.getTeachClasses()) System.out.print(c.getName()+"  ");System.out.println(); 
		}
		System.out.println("================");
		List<Classes> classes = (List<Classes>)classesService.findAll();
		for(Classes c : classes) {
			System.out.println("班级名为："+c.getName());
			System.out.println("	它的班主任为："+c.getHeadTeacher().getName());
			System.out.println("	它的系主任为："+c.getClassDirector().getName()); 
			System.out.print("	它的授课教师有："); for(Teacher t : c.getTeachers()) System.out.print(t.getName()+"  ");System.out.println(); 
		}
	}

}
