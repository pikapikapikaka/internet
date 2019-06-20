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
<%
String msg = (String)request.getAttribute("msg");
if(msg == null)
{
Student s = (Student)request.getAttribute("checked_student");
%>
<table>
<tr><td>学号：：</td><td><%=s.getId() %></td></tr>
<tr><td>姓名：</td><td><%=s.getName() %></td></tr>
<tr><td>性别：</td><td><%=s.getSex() %></td></tr>
<tr><td>学院：</td><td><%=s.getXy() %></td></tr>
<tr><td>专业：</td><td><%=s.getZy() %></td></tr>
<tr><td>班级：</td><td><%=s.getBj() %></td></tr>
</table>
<%} 
else
	{
	out.print(msg);
	}%>
</center>
</body>
</html>