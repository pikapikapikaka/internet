<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Student"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%String msg = (String)request.getAttribute("msg"); 
if(msg == null){
Student t = (Student)request.getAttribute("student_check");%>
<table>
<tr><td>学号：</td><td><%=t.getId() %></td></tr>
<tr><td>姓名：</td><td><%=t.getName() %></td></tr>
<tr><td>学院：</td><td><%=t.getXy() %></td></tr>
<tr><td>专业：</td><td><%=t.getZy() %></td></tr>
<tr><td>班级：</td><td><%=t.getBj() %></td></tr>
</table>
<%} 
else
out.print(msg);%>
</center>
</body>
</html>