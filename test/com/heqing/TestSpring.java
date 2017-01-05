package com.heqing;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)		// 表示继承了 SpringJUnit4ClassRunner 类
@ContextConfiguration("classpath:spring-mybatis.xml")
public class TestSpring {

		@Resource
		private SqlSessionFactory sessionFactory;
	
	    // 测试SessionFactory。建表
		@Test
		public void testSpring() throws Exception {
			System.out.println(sessionFactory);
		}
}
