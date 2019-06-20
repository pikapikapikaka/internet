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
Student s = (Student)session.getAttribute("student");
%>
<table>
<tr><td>学号：</td><td><%=s.getId() %></td></tr>
<tr><td>姓名：</td><td><%if(s.getName()==null)out.print("未填写");else out.print(s.getName());  %></td></tr>
<tr><td>性别：</td><td><%if(s.getSex()==null)out.print("未填写");else out.print(s.getSex());  %></td></tr>
<tr><td>学院：</td><td><%if(s.getXy()==null)out.print("未填写");else out.print(s.getXy());  %></td></tr>
<tr><td>专业：</td><td><%if(s.getZy()==null)out.print("未填写");else out.print(s.getZy());  %></td></tr>
<tr><td>班级：</td><td><%if(s.getBj()==null)out.print("未填写");else out.print(s.getBj());  %></td></tr>
<tr><td>题号：</td><td><%if(s.getTh()==0)out.print("未选");else out.print(s.getTh());  %></td></tr>
</table>
</center>
</body>
</html>