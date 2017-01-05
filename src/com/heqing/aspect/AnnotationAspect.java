package com.heqing.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AnnotationAspect {

	// 匹配com.heqing.service.impl包下所有类的、
	// 所有方法的执行作为切入点
	@Before("execution(* com.heqing.service.impl.*.*(..))")
	public void AspectBefore() {
		System.out.println("AOP 模拟方法执行前运行");
	}
	
	// 匹配com.heqing.service.impl包下所有类的、
	// 所有方法的执行作为切入点
	@After("execution(* com.heqing.service.impl.*.*(..))")
	public void AspectAfter() {
		System.out.println("AOP 模拟方法执行后运行");
	}
}
