<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'test.jsp' starting page</title>
    <base href="<%=basePath%>">
    
    <%@ include file="/WEB-INF/jsp/public/common.jspf" %>
	<script src='/SSM/dwr/engine.js'></script>
	<script src='/SSM/dwr/util.js'></script>
	<script src='/SSM/dwr/interface/userServer.js'></script>
	 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/imgareaselect-animated.css">
	 
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery.imgareaselect.min.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/uploadAnimatedIamge.js"></script>
	
  </head>
  
  <body>
  	<button id="testAjax" type="button">测试AJAX</button>
  	<button id="testDwr" type="button">测试DWR</button>
  	<br/><br/>
  	
  	<img id="securitycode"  style=" vertical-align:middle;"/>
    <a id="clickUpdateSecurity">刷新</a><br/>
    ID：  <input id="userID" type="text" /><a id="lgSubmit" >登陆</a>
    <br/><br/>

	<input type="file" id="uFile" name="uFile" multiple="true"/>
    <button id="uploadAll" type="button">上传文件</button>
    <br/><br/>
     
    
    <div class="frame" style="width: 300px; height: 300px;overflow: hidden; float:left;margin-right:50px">
		<img id="photo" style="width: 300px; height: 300px;" src="${pageContext.request.contextPath}/style/images/img.jpg">
	</div>
	<div id="preview" style="width: 100px; height: 100px; overflow: hidden;">
		<img id="view_photo" src="${pageContext.request.contextPath}/style/images/img.jpg" style="width: 100px; ">
	</div><br/><br/>
	x :<input id="startX" name="startX" readonly="readonly"/>
	y :<input id="startY" name="startY" readonly="readonly"/><br/>
	宽:<input id="width" name="width" readonly="readonly"/>
	高:<input id="height" name="height" readonly="readonly"/>
   	<br/>
	<input type="file" id="upload" name="upload" onchange="change(this)" accept="image/*"/>  
	
	<button id="uploadImage" type="button" >上传头像</button>
    <br/><br/>
    
  </body>
  
  <script>  
    $(document).ready(function(e) {
      	var classes = new Object();
	  	classes.id = 1;
	  	classes.name = "heqing";
	  	
	  	var test = new Array();
	  	test.push("test1");
	  	test.push("test2");
	  	
	  	var test1 = new Object();
	  	test1.id = 1;
	  	test1.name = "test1";
	  	var test2 = new Object();
	  	test2.id = 2;
	  	test2.name = "test2";
	  	var testArray = new Array();
	  	testArray.push(test1);
	  	testArray.push(test2);
	  
	  	$("#testAjax").click(function(){
	        $.ajax({
	             type: "GET",
	             url: "/SSM/classes/testJson",
	             data: {
	             	name : "text",
	             	classes : JSON.stringify(classes),
	             	test : JSON.stringify(test),
	             	testArray : JSON.stringify(testArray)
	             },
	             dataType : "json",
	             success: function(data){
	             	alert(data.headTeacher);
	             }
	         });
	    });
	    
	    function getFun(data) {
			alert(data.name);
		}
	    $("#testDwr").click(function(){
	    	userServer.getById(1,getFun);
	    });
    
        $("#securitycode").attr("src",'securitycode.png?id='+Math.random());
          
        $("#clickUpdateSecurity").click(function(e){
	        $("#securitycode").attr("src",'securitycode.png?id='+Math.random());
	    });
	    
	    $("#lgSubmit").click(function(){
	        var userId =$("#userID").val();
	        $.ajax({
	             type: "GET",
	             url: "/SSM/test/loginIn",
	             data: {
	             	userId : userId
	             },
	             dataType : "json",
	             success: function(data){
	             	alert(data.codeInSession);
	             }
	         });
	    });
	    
	    $("#uploadAll").click(function(){
	    	$.ajaxFileUpload({
			   url:"/SSM/test/upload",
			   fileElementId:"uFile",
			   success: function (data, status){
			        alert("上传成功");
			   },
			});
	    });
	    $("#uploadImage").click(function(){
	    	$.ajaxFileUpload({
			   url:"/SSM/test/uploadImage",
			   fileElementId:"upload",
			   data: {
	             	startX : $("#startX").val(),
	             	startY : $("#startY").val(),
	             	width : $("#width").val(),
	             	height : $("#height").val()
	           },
			   success: function (data, status){
			        alert("上传成功");
			   },
			});
	    });

      });
  </script>
</html>
