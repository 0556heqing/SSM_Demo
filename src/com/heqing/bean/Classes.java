package com.heqing.bean;

import java.io.Serializable;
import java.util.Set;

public class Classes implements Serializable {

	private int id;
	private String name;
	private Teacher headTeacher;			//班主任
	private Set<Teacher> teachers;	   //授课教师
	private Teacher classDirector;		  //年级主任
	
	public Teacher getHeadTeacher() {
		return headTeacher;
	}
	public void setHeadTeacher(Teacher headTeacher) {
		this.headTeacher = headTeacher;
	}
	public Set<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Teacher getClassDirector() {
		return classDirector;
	}
	public void setClassDirector(Teacher classDirector) {
		this.classDirector = classDirector;
	}
	
	public static Classes getClasses(Classes c){
		Classes classes = new Classes();
		classes.setId(c.id);
		classes.setName(c.name);
		return classes;
	}
}
