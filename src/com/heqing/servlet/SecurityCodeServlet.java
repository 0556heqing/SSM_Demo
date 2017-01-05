package com.heqing.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ck.utils.string.StringUtils;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.image.WaterFilter;

public class SecurityCodeServlet extends HttpServlet {

	private String  key   = "securitycode";
	private int     count = 4;					//验证字符个数
	private int     width = 120;				//默认图片宽度
	private int     height = 40;				//默认图片高度
	private boolean transform = true;			//是否旋转
	private boolean randomInterfere = false; 	//随机干预模式
	private String  charater = "QWERTYUPKJHGFDSAZXCVBNMqwertyupkjhgfdsazxcvbnm0123456789";	//生成验证码的内容
	/**
	 * 验证码随即颜色  
	 * 4种类型，constant(常量)，hex(16进制)，rgb,rgba,可以混合使用
	 * 如constant:red,hex:#ffffff,rgb:255.255.255,rgba:255.255.255.100
	 */
	private String color;

	private static Map<String, Color> constantsColor = new HashMap<String, Color>();
	
	private Color[] cs = {Color.RED,Color.GRAY,Color.decode("#F6631E"),Color.decode("#68A522")}; 
	
	static{
		constantsColor.put("black", Color.BLACK);
		constantsColor.put("blue", Color.BLUE);
		constantsColor.put("cyan", Color.CYAN);
		constantsColor.put("darkgray", Color.DARK_GRAY);
		constantsColor.put("gray", Color.GRAY);
		constantsColor.put("green", Color.GREEN);
		constantsColor.put("lightgray", Color.LIGHT_GRAY);
		constantsColor.put("magenta", Color.MAGENTA);
		constantsColor.put("orange", Color.ORANGE);
		constantsColor.put("pink", Color.PINK);
		constantsColor.put("red", Color.RED);
		constantsColor.put("white", Color.WHITE);
		constantsColor.put("yellow", Color.YELLOW);
	}
	
	/**
	 * 生成验证码
	 * @return
	 */
	private String create(){
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		for(int i =0;i<count;i++){
			builder.append(charater.charAt(random.nextInt(charater.length())));
		}
		return builder.toString();
	}
	
	/**
	 * 画出验证码
	 * @param code
	 * @return
	 */
	public BufferedImage draw(String code){
		String[] fonts={"Courier","Calibri","Consolas","Arial"};
		// 创建BufferedImage对象
		BufferedImage image=new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		// 获取Graphics2D
		Graphics2D g2d=(Graphics2D)image.getGraphics();
		//设置图片为透明背景
		image = g2d.getDeviceConfiguration().createCompatibleImage(200, 80, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		//打开抗锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Random random = new Random();
		int a = (200/count)-20;
		int x = a;
		AffineTransform at = new AffineTransform();
		
		for(int i=0;i<code.length();i++){
			if(transform){
				int number = random.nextInt(3)-1;   
	        	double role = number*random.nextDouble()*(Math.PI/(random.nextInt(3)+6));  
	        	double t = Math.abs(role);
	        	if(t>0.8){
	        		t/=5;
	        	}else if(t>0.6){
	        		role/=4;
	        	}else if(t>0.4){
	        		role/=3;
	        	}else if(t>0.25){
	        		role/=2;
	        	}
	        	at.setToRotation(role,25*i+5,55-random.nextInt(20)); //role:旋转角度,后面两个参数是设置围绕坐标点旋转
			}else{
				at.setToRotation(0, 0, 0);
			}
			
        	g2d.setTransform(at); 
			
        	g2d.setFont(new Font(fonts[random.nextInt(fonts.length)], Font.PLAIN, random.nextInt(5)+55));
			int px=g2d.getFont().getSize()*96/72;
			
			g2d.setColor(cs[random.nextInt(cs.length)]);
			g2d.drawString(String.valueOf(code.charAt(i)), x, /*(height-px)/2*/60);
			x+=px/2-5;
			at.setToRotation(0, 0, 0);
			g2d.setTransform(at);
		}
		
		g2d.dispose();
		
		BufferedImage image2 = new BufferedImage(10,10 , BufferedImage.TYPE_3BYTE_BGR);
		g2d=(Graphics2D)image2.getGraphics();
		g2d.setBackground(Color.WHITE);
		image2 = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d=image2.createGraphics();
		g2d.drawImage(image, 0, 0, width,height,null);
		RippleFilter rippleFilter = new RippleFilter();
		WaterFilter waterFilter = new WaterFilter();
		
		if(randomInterfere){
			rippleFilter.setWaveType(random.nextInt(4));
			float f1 = random.nextFloat()*3.0f;
			rippleFilter.setXAmplitude(f1);
			f1 = random.nextFloat()*3.0f;
			rippleFilter.setYAmplitude(f1);
			int i1 = random.nextInt(7)+4;
			rippleFilter.setXWavelength(i1);
			i1 = random.nextInt(2)+3;
			rippleFilter.setYWavelength(i1);
			
			f1 = random.nextFloat()*3.0f+0.5f;
			waterFilter.setAmplitude(f1);
			i1 = random.nextInt(2)+3;
			waterFilter.setPhase(i1);
			i1 = random.nextInt(2)+3;
			waterFilter.setWavelength(i1);
			rippleFilter.setEdgeAction(random.nextInt(3));
		}else{
			rippleFilter.setWaveType(RippleFilter.SINE);
			rippleFilter.setXAmplitude(2.6f);
			rippleFilter.setYAmplitude(1.7f);
			rippleFilter.setXWavelength(8);
			rippleFilter.setYWavelength(3);
			waterFilter.setAmplitude(1.5f);
			waterFilter.setPhase(2);
			waterFilter.setWavelength(2);
			rippleFilter.setEdgeAction(TransformFilter.NEAREST_NEIGHBOUR);
		}
		BufferedImage effectImage = waterFilter.filter(image2, null);
		effectImage = rippleFilter.filter(effectImage, null);
		
		g2d.drawImage(effectImage, 0, 0, null, null);
		g2d.dispose();
		return image2;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("image/jpeg");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		String code = create();
		String skey = request.getParameter("key");
		if(StringUtils.hasValue(skey)){
			session.setAttribute(skey, code);
		}else{
			session.setAttribute(key, code);
		}
		BufferedImage image = draw(code);
		OutputStream out = response.getOutputStream();
		ImageIO.write(image, "png", out);
		out.flush();
		out.close();
	}
	
	/**
	 * 解析配置的color
	 * @param colors
	 * @return
	 */
	private Color[] getColors(String color){
		String [] arr=color.split(",");
		Color[] cs=new Color[arr.length];
		Pattern constantsPattern = Pattern.compile("^[a-zA-Z]+");
		Pattern hexPattern = Pattern.compile("^#[a-zA-Z\\d]{6}");
		Pattern rgbPattern = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
		Pattern rgbaPattern = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");
		Matcher matcher = null;
		for(int i=0;i<arr.length;i++){
			String s = arr[i];
			matcher = constantsPattern.matcher(s);
			if(matcher.find()){
				cs[i]=constantsColor.get(s.toLowerCase());
			}else{
				matcher = hexPattern.matcher(s);
				if(matcher.find()){
					cs[i]=Color.decode(s);
				}else{
					matcher = rgbPattern.matcher(s);
					if(matcher.find()){
						String[] nums=s.split("\\.");
						cs[i]=new Color(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]));
					}else{
						matcher = rgbaPattern.matcher(s);
						if(matcher.find()){
							String[] nums=s.split("\\.");
							cs[i]=new Color(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]),Integer.parseInt(nums[2]),Integer.parseInt(nums[3]));
						}
					}
				}
			}
		}
		return cs;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		String countStr =  config.getInitParameter("count");
		if(StringUtils.hasValue(countStr)){
			try {
				count = Integer.parseInt(countStr);
			} catch (NumberFormatException e) {
			}
		}

		String widthStr = config.getInitParameter("width");
		if(StringUtils.hasValue(countStr)){
			try {
				width = Integer.parseInt(widthStr);
			} catch (NumberFormatException e) {
			}
		}
		
		String heightStr = config.getInitParameter("height");
		if(StringUtils.hasValue(heightStr)){
			try {
				height = Integer.parseInt(heightStr);
			} catch (NumberFormatException e) {
			}
		}
		
		String charater = config.getInitParameter("charater");
		if(StringUtils.hasValue(charater)){
			try {
				this.charater = charater;
			} catch (NumberFormatException e) {
			}
		}
		
		color = config.getInitParameter("color");
		if(color!=null&&!color.equals("")){
			cs=getColors(color);
		}
		
		String interfereStr = config.getInitParameter("randomInterfere");
		if(StringUtils.hasValue(interfereStr)){
			randomInterfere = "true".equals(interfereStr);
		}
		
		String transformStr = config.getInitParameter("transform");
		if(StringUtils.hasValue(transformStr)){
			transform = "true".equals(transformStr);
		}
	}
}
