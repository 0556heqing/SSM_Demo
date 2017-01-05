package com.heqing.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CutPicture {
	private String srcpath;
	private String subpath;
	private String imageType;
	private int x;
	private int y;
	private int width;
	private int height;
	 
	public CutPicture() {}
	  
	public CutPicture(String srcpath, int x, int y, int width, int height) {
		this.srcpath = srcpath;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public int getHeight() {
	    return height;
	}
	public void setHeight(int height) {
	    this.height = height;
	}
	public String getSrcpath() {
	    return srcpath;
	}
	public void setSrcpath(String srcpath) {
	    this.srcpath = srcpath;
	    if(srcpath != null) this.imageType = srcpath.substring(srcpath.indexOf(".")+1, srcpath.length());
	}
	public String getSubpath() {
	    return subpath;
	}
	public void setSubpath(String subpath) {
	    this.subpath = subpath;
	}
	public int getWidth() {
	    return width;
	}
	public void setWidth(int width) {
	    this.width = width;
	}
	public int getX() {
	    return x;
	}
	public void setX(int x) {
	    this.x = x;
	}
	public int getY() {
	    return y;
    }
    public void setY(int y) {
    	this.y = y;
	}
	public String getImageType() {
	    return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	  
	public void cut() throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			is = new FileInputStream(srcpath);
			this.setSrcpath(srcpath);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(this.imageType);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, this.imageType, new File(subpath));
		} finally {
			if (is != null)
			 is.close();
			if (iis != null)
			 iis.close();
	    }
    } 
	
	public static void cut(String fileName,int startX,int startY,int width,int height) {
		 CutPicture o = new CutPicture(fileName, startX, startY, width, height);
		  o.setSubpath(fileName);
		  try {
			  o.cut();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	}
	
	 public static void main(String[] args) {
		 cut("d:\\img1.jpg", 193, 11, 159, 159);
	}
}
	

