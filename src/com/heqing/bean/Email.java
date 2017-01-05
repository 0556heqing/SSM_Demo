package com.heqing.bean;

import java.io.File;
import java.util.ArrayList;

public class Email {

	public ArrayList<String> toUser;
	public String fromUser;
	public String subject;
	public String text;
	public ArrayList<File> files;
	
	public String[] getToUser() {
		String[] users = new String[toUser.size()];
		for(int i=0; i<toUser.size(); i++) users[i] = toUser.get(i);
		return users;
	}
	public void setToUser(ArrayList<String> toUser) {
		this.toUser = toUser;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ArrayList<File> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}
	
}
