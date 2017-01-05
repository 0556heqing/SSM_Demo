package com.heqing;

import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.heqing.utils.SMSUtil;

public class TestSMS {

	private static final Logger logger = Logger.getLogger(TestSMS.class);
	
	@Test
	public void sendEmail() throws MessagingException {

		String phone = "18019975051";
		int mobile_code = (int)((Math.random()*9+1)*100000);
//		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		
		String[] phones = phone.split(";");
		for(String p : phones){
			try{
				Thread.sleep(1000L*2);
				String content = new String("您好！he 医生向您分享了病例《test》，请点击查看详情 33");
				content = content.replace("33", "http://www.boholo.com/web/index.html");
				SMSUtil.sendSMS(logger, p, content);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
}