package com.heqing.pay.wx;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.heqing.utils.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WXPayUtil {

	/**
	 * 生成订单
	 * @param orderId
	 * @return
	 */
	public static String createOrderInfo(String orderId) {
	    //生成订单对象
	    UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
	    unifiedOrderRequest.setAppid("wxd347d43bcdbac2f2");//公众账号ID
	    unifiedOrderRequest.setMch_id("1236568302");//商户号
	    unifiedOrderRequest.setNonce_str(StringUtil.makeUUID());//随机字符串       <span style="color:#ff0000;"><strong>说明2(见文末)</strong></span>
	    unifiedOrderRequest.setBody("this is test");//商品描述
	    unifiedOrderRequest.setOut_trade_no(orderId);//商户订单号
	    unifiedOrderRequest.setTotal_fee("1");  //金额需要扩大100倍:1代表支付时是0.01
	    unifiedOrderRequest.setSpbill_create_ip("127.0.0.1");//终端IP
	    unifiedOrderRequest.setNotify_url("http://127.0.0.1:8080/SSM/pay/wx");//通知地址
	    unifiedOrderRequest.setTrade_type("NATIVE");//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	    unifiedOrderRequest.setSign(createSign(unifiedOrderRequest));//签名<span style="color:#ff0000;"><strong>说明5(见文末，签名方法一并给出)</strong></span>
	    //将订单对象转为xml格式
	    XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_"))); //<span style="color:#ff0000;"><strong>说明3(见文末)</strong></span>
	    xStream.alias("xml", UnifiedOrderRequest.class);//根元素名需要是xml
	    return xStream.toXML(unifiedOrderRequest);
	}
	
	/**
	 * 调统一下单API
	 * @param orderInfo
	 * @return
	 */
	public static String httpOrder(String orderInfo) {
	    String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	    try {
	        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
	        //加入数据  
	        conn.setRequestMethod("POST");  
	        conn.setDoOutput(true);  
	           
	        BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());  
	        buffOutStr.write(orderInfo.getBytes());
	        buffOutStr.flush();  
	        buffOutStr.close();  
	           
	        //获取输入流  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
	           
	        String line = null;  
	        StringBuffer sb = new StringBuffer();  
	        while((line = reader.readLine())!= null){  
	            sb.append(line);  
	        }  
	        
	        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));//说明3(见文末)
	        //将请求返回的内容通过xStream转换为UnifiedOrderRespose对象
	        xStream.alias("xml", UnifiedOrderRespose.class);
	        UnifiedOrderRespose unifiedOrderRespose = (UnifiedOrderRespose) xStream.fromXML(sb.toString());
	         
	        //根据微信文档return_code 和result_code都为SUCCESS的时候才会返回code_url
	        //<span style="color:#ff0000;"><strong>说明4(见文末)</strong></span>
	        if(null!=unifiedOrderRespose 
	                && "SUCCESS".equals(unifiedOrderRespose.getReturn_code()) 
	                && "SUCCESS".equals(unifiedOrderRespose.getResult_code())){
	            return unifiedOrderRespose.getCode_url();
	        }else{
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * 生成签名
	 * 
	 * @param appid_value
	 * @param mch_id_value
	 * @param productId
	 * @param nonce_str_value
	 * @param trade_type 
	 * @param notify_url 
	 * @param spbill_create_ip 
	 * @param total_fee 
	 * @param out_trade_no 
	 * @return
	 */
	public static String createSign(UnifiedOrderRequest unifiedOrderRequest) {
	    //根据规则创建可排序的map集合
	    SortedMap<String, String> packageParams = new TreeMap<String, String>();
	    packageParams.put("appid", unifiedOrderRequest.getAppid());
	    packageParams.put("body", unifiedOrderRequest.getBody());
	    packageParams.put("mch_id", unifiedOrderRequest.getMch_id());
	    packageParams.put("nonce_str", unifiedOrderRequest.getNonce_str());
	    packageParams.put("notify_url", unifiedOrderRequest.getNotify_url());
	    packageParams.put("out_trade_no", unifiedOrderRequest.getOut_trade_no());
	    packageParams.put("spbill_create_ip", unifiedOrderRequest.getSpbill_create_ip());
	    packageParams.put("trade_type", unifiedOrderRequest.getTrade_type());
	    packageParams.put("total_fee", unifiedOrderRequest.getTotal_fee());

	    StringBuffer sb = new StringBuffer();
	    Set es = packageParams.entrySet();//字典序
	    Iterator it = es.iterator();
	    while (it.hasNext()) {
	        Map.Entry entry = (Map.Entry) it.next();
	        String k = (String) entry.getKey();
	        String v = (String) entry.getValue();
	        //为空不参与签名、参数名区分大小写
	        if (null != v && !"".equals(v) && !"sign".equals(k)
	                && !"key".equals(k)) {
	            sb.append(k + "=" + v + "&");
	        }
	    }
	    //第二步拼接key，key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	    sb.append("key=" +"iboholo168iboholo168iboholo168ib");

	    String sign = StringUtil.MD5Encode(sb.toString()).toUpperCase();//MD5加密
	    return sign;
	}
	
	public String getRemortIP(HttpServletRequest request) { 
		if (request.getHeader("x-forwarded-for") == null) { 
	    	return request.getRemoteAddr(); 
	    } 
	    return request.getHeader("x-forwarded-for"); 
	} 
}
