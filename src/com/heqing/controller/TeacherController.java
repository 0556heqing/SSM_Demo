package com.heqing.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heqing.bean.Classes;
import com.heqing.bean.PageBean;
import com.heqing.bean.Teacher;
import com.heqing.service.ClassesService;
import com.heqing.service.TeacherService;

/**
 * 参数传递，最简单的DEMO
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Resource
	private TeacherService teacherService;
	
	@RequestMapping("/getcid")
	public String toIndex(HttpServletRequest request,Model model) {
		Long teacherId = Long.parseLong(request.getParameter("id"));
		Teacher teacher = this.teacherService.getById(teacherId);
		model.addAttribute("teacher",teacher);
		return "showTeacher";
	}
	
	@RequestMapping("/show")
	public String getPage(HttpServletRequest request,Model model) {
		List<Teacher> teacher = this.teacherService.findAll();
		model.addAttribute("teacher",teacher);
		
		int currentPage = 1;  //当前页
		int pageSize = 1;	   //每页显示5条数据
		if(request.getParameter("pageNum") != null)  currentPage = Integer.parseInt(request.getParameter("pageNum"));
		model.addAttribute("page", new PageBean(currentPage,  pageSize,  this.teacherService.getPageBean((currentPage-1)*pageSize, pageSize),  this.teacherService.findAll().size()));
		return "showTeacher";
	}
}
