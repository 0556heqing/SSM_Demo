package com.heqing.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.heqing.pay.wx.WXPayUtil;

@Controller
@RequestMapping("/pay")
public class WXPayController {

	private static final Logger logger = Logger.getLogger(WXPayController.class);
	
	@RequestMapping("/wx")
	public String test1(HttpServletRequest request,Model model) {
		return "wxpay";
	}
	
	/**
	 * 创建二维码
	 */
	@RequestMapping("/createWXQRCode")
	public void createQRCode(HttpServletRequest request,HttpServletResponse response) {
	     
//		String orderId = request.getParameter("orderId"); 
		String orderId = System.currentTimeMillis()+"";
	    //生成订单
	    String orderInfo = WXPayUtil.createOrderInfo(orderId);
	    //调统一下单API
	    String code_url = WXPayUtil.httpOrder(orderInfo);
	    System.out.println(">>>>code_url="+code_url);
	    //将返回预支付交易链接（code_url）生成二维码图片
	    //这里使用的是zxing   <span style="color:#ff0000;"><strong>说明1(见文末)</strong></span>
	    
	    try {
	        int width = 200;
	        int height = 200;
	        String format = "png";
	        Hashtable hints = new Hashtable();
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, width, height, hints);
	        OutputStream out = null;
	        out = response.getOutputStream();

//	        MatrixToImageWriter.writeToStream(bitMatrix, format, out);
	        ImageIO.write(toBufferedImage(bitMatrix), format, out);
	        
	        out.flush();
	        out.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return image;
    }
}
