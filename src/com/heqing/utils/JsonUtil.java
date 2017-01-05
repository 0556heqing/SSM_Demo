package com.heqing.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class JsonUtil {

	//输出JSON字符串，并打印日志
    public static void outputJsonMessage(Logger logger,String methodName,HttpServletResponse response,Map<String,Object> jsonMap){

    	logger.info("------"+methodName+"----");
    	//输出json字符串
		PrintWriter out=null;
        //"Access-Control-Allow-Origin: *"
        response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonObject=new JSONObject();
		for(String key:jsonMap.keySet()){
			jsonObject.put(key,jsonMap.get(key));
		}
		try {
			out = response.getWriter();
			out.write(jsonObject.toString());
			logger.info("返回JSON结果："+jsonObject.toString());
		} catch (IOException e) {
			logger.error("流输出异常：",e);
		}finally{
			out.close();
		}
    }
}
