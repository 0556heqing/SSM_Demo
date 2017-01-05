package com.heqing.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.heqing.utils.JsonUtil;
import com.heqing.utils.CutPicture;

/**
 * 参数传递，最简单的DEMO
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	private static final Logger logger = Logger.getLogger(TestController.class);
	
	@RequestMapping("/login")
	public String test1(HttpServletRequest request,Model model) {
		System.out.println(request.getHeader("user-agent"));
		return "test";
	}
	
	@RequestMapping("/mvc/{id}")
	@ResponseBody
	public Map<String, Object> mvc(HttpServletResponse response,String name, int age,@PathVariable("id") Integer id) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println(">>>name="+name+"   age="+age+"   id="+id);
		Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
		paramsMap.put("name", name);
		paramsMap.put("age", age);
		paramsMap.put("id", id);
		return paramsMap;
//		JsonUtil.outputJsonMessage(logger, "login", response, paramsMap);
	}
	
	@RequestMapping("/loginIn")
	public void test(HttpServletResponse response,HttpServletRequest request,Model model) {
	
		HttpSession session = request.getSession();
		session.setAttribute("userId", request.getParameter("userId"));
	  	String codeInSession = (String)session.getAttribute("securitycode");
	  	
		System.out.println("--->codeInSession="+codeInSession+"	userId="+request.getParameter("userId"));
		
		Map<String,Object> paramsMap=new LinkedHashMap<String,Object>();
		paramsMap.put("codeInSession", codeInSession);
		
		JsonUtil.outputJsonMessage(logger, "login", response, paramsMap);
	}
	
	@RequestMapping("/upload")  
    public String upload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {  
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        //重命名上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        String path = "D:/" + fileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println("上传用时："+(finaltime - pre)+"秒");  
            }  
              
        }  
        return "test";  
    }  
	
	@RequestMapping("/uploadImage")  
    public String uploadImage(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {  

		int startX = request.getParameter("startX").equals("")?0:Integer.parseInt(request.getParameter("startX"));
		int startY = request.getParameter("startY").equals("")?0:Integer.parseInt(request.getParameter("startY"));
		int width = request.getParameter("width").equals("")?0:Integer.parseInt(request.getParameter("width"));
		int height = request.getParameter("height").equals("")?0:Integer.parseInt(request.getParameter("height"));
		
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames(); 
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        //重命名上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        fileName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                        System.out.println(">>>"+fileName);
                        //定义上传路径  
                        String path = "D:/" + "test.png";  
                        File localFile = new File(path); 
                        file.transferTo(localFile); 
                        
                        if(width != 0) CutPicture.cut(path, startX, startY, width, height);
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println("上传用时："+(finaltime - pre)+"秒");  
            }    
        } 
        return "test";  
    }  
 
}
