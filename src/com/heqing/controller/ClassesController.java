package com.heqing.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heqing.bean.Classes;
import com.heqing.bean.PageBean;
import com.heqing.service.ClassesService;
import com.heqing.utils.JsonUtil;

/**
 * 参数传递，最简单的DEMO
 */
@Controller
@RequestMapping("/classes")
public class ClassesController {
	
	private static final Logger logger = Logger.getLogger(ClassesController.class);
	
	@Resource
	private ClassesService classesService;
	
	@RequestMapping("/getcid")
	public String toIndex(HttpServletRequest request,Model model) {
		Long classesId = Long.parseLong(request.getParameter("id"));
		Classes classes = this.classesService.getById(classesId);
		model.addAttribute("classes",classes);
		return "showClasses";
	}
	
	@RequestMapping("/show")
	public String getPage(HttpServletRequest request,Model model) {
		List<Classes> classes = this.classesService.findAll();
		model.addAttribute("classes",classes);
		
		int currentPage = 1;  //当前页
		int pageSize = 5;	   //每页显示5条数据
		if(request.getParameter("pageNum") != null)  currentPage = Integer.parseInt(request.getParameter("pageNum"));
		model.addAttribute("page", new PageBean(currentPage,  pageSize,  this.classesService.getPageBean((currentPage-1)*pageSize, pageSize),  this.classesService.findAll().size()));
		return "showClasses";
	}
	
	@RequestMapping("/test")
	public String test1(HttpServletRequest request,Model model) {
		return "test";
	}
	
	@RequestMapping("/testJson")
	public void test(HttpServletResponse response,HttpServletRequest request) {
	
		String name = request.getParameter("name");
		JSONObject jsonClasses = new JSONObject(request.getParameter("classes"));
		JSONArray jsonArray = new JSONArray(request.getParameter("test"));
		JSONArray testArray = new JSONArray(request.getParameter("testArray"));
		System.out.println("--->name="+name+"	jsonClasses="+jsonClasses.toString()+"	jsonArray="+jsonArray.toString()+"	testArray="+testArray.toString());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			System.out.println("jsonArray---->name="+jsonArray.getString(i));
		}
		for (int i = 0; i < testArray.length(); i++) {
			JSONObject jsonObj = testArray.getJSONObject(i);
			System.out.println("testArray---->id="+jsonObj.getInt("id")+"   name="+jsonObj.getString("name"));
		}
		
		Classes classes = this.classesService.getById(jsonClasses.optLong("id"));
		Map<String,Object> paramsMap=new LinkedHashMap<String,Object>();
		paramsMap.put("headTeacher", classes.getHeadTeacher().getName());
		List<Classes> list = new ArrayList<>();
		list.add(Classes.getClasses(classes));
		paramsMap.put("classes", list);
		
		JsonUtil.outputJsonMessage(logger, "test", response, paramsMap);
	}
}
