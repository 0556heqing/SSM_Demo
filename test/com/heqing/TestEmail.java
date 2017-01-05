package com.heqing;

import java.io.File;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.heqing.bean.Email;
import com.heqing.utils.EmailUtil;

public class TestEmail {

	private static final Logger logger = Logger.getLogger(TestEmail.class);
	
	@Test
	public void sendEmail() throws MessagingException {
		
		Email email = new Email();
		ArrayList<String> toUser = new ArrayList<String>();
		toUser.add("975656343@qq.com");toUser.add("3187344038@qq.com");
		email.setToUser(toUser);
		email.setFromUser("heqing_test@sina.com");
		email.setSubject("测试文件");
		email.setText("<html><head></head><body><h1>hello!!spring image html mail</h1></body></html>");
		ArrayList<File> files = new ArrayList<File>();
		files.add(new File("D:/1.jpg"));
		email.setFiles(files);
		
		EmailUtil.sendEmail(logger, email);

	}
}
