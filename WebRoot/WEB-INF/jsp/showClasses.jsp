<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>测试</title>
		<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
	</head>
<body>	
	<table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align=center  valign=middle  id=TableTitle>
                <td width="100">id</td>
                <td width="100">班级名</td>
            </tr>
        </thead>
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer"  datakey="testList">
	        <c:forEach items="${page.recordList}" var="c"> 
	            <tr class="TableDetail1 template">
	                <td>${c.id}&nbsp;</td>
	                <td>${c.name}&nbsp;</td>
	            </tr>
	         </c:forEach>
            
        </tbody>
    </table>

	<!-- 分页信息 -->
	<form id="page" action="/SSM/classes/show"></form>
	<%@ include file="/WEB-INF/jsp/public/pageView.jspf" %>
</body>
</html>