package com.heqing.bean;

import java.util.Set;

public class Teacher extends People {

	private String post;
	private Classes superviseClass;				//管理班级
	private Set<Classes> teachClasses;		//授课班级
	private Set<Classes> classDirector;		//班级主任

	public Classes getSuperviseClass() {
		return superviseClass;
	}
	public void setSuperviseClass(Classes superviseClass) {
		this.superviseClass = superviseClass;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Set<Classes> getTeachClasses() {
		return teachClasses;
	}
	public void setTeachClasses(Set<Classes> teachClasses) {
		this.teachClasses = teachClasses;
	}
	public Set<Classes> getClassDirector() {
		return classDirector;
	}
	public void setClassDirector(Set<Classes> classDirector) {
		this.classDirector = classDirector;
	}
}
