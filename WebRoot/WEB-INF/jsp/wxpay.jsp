<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'wxpay.jsp' starting page</title>
    
	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
  </head>
  
  <body>
    	<button id="getOrderInfo" type="button">生成订单获取二维码</button>
    	<br/>
    	<img id="orderImage"  style=" vertical-align:middle;" />
  </body>
  <script>
    $("#getOrderInfo").click(function(){
    	$("#orderImage").attr("src",'/SSM/pay/createWXQRCode');
    });
  </script>
</html>
