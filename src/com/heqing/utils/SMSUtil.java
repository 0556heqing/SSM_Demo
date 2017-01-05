package com.heqing.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SMSUtil {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	public static void sendSMS(Logger logger,String phone, String content) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
				
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {//提交短信
				new NameValuePair("account", "cf_boholo"), 
//			    new NameValuePair("password", "boholo168"), //密码可以使用明文密码或使用32位MD5加密
			    new NameValuePair("password", StringUtil.MD5Encode("boholo168")),
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		
		try {
			client.executeMethod(method);	
			String SubmitResult =method.getResponseBodyAsString();

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();	
			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			logger.info(">>>>>code="+code+",   msg="+msg+",   smsid="+smsid);
			 if("2".equals(code))	System.out.println("短信提交成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
