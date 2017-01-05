package com.heqing.utils;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.heqing.bean.Email;

public class EmailUtil {

	public static void sendEmail(Logger logger,Email email) throws MessagingException{
		
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl(); 
	    // 设定mail server  
	    senderImpl.setHost("smtp.sina.com");  
	    senderImpl.setUsername("heqing_test@sina.com"); // 根据自己的情况,设置username  
	    senderImpl.setPassword("246512"); // 根据自己的情况, 设置password 
	    // 设置属性
	    Properties prop = new Properties();  
        prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put(" mail.smtp.timeout ", " 25000 ");  
        prop.setProperty("mail.transport.protocol", "smtp"); 
	    senderImpl.setJavaMailProperties(prop);  
	   
	    // 建立邮件消息  
	    MimeMessage  mailMessage = senderImpl.createMimeMessage(); 
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true); 
        // 设置收件人，寄件人 用数组发送多个邮件   
        messageHelper.setTo(email.getToUser());
        messageHelper.setFrom(email.getFromUser());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getText(), true);
        FileSystemResource file;
        for(File f : email.getFiles()) {
        	file = new FileSystemResource(f);
        	messageHelper.addAttachment(f.getName(), file);
        }
        // 发送邮件  
        senderImpl.send(mailMessage);
        
        logger.info("------邮件发送成功----");
        System.out.println(" 邮件发送成功.. ");  
	}
}
