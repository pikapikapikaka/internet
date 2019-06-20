<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Teacher"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% Teacher tea = (Teacher)session.getAttribute("teacher");%>
<center>
<table>
<tr><td>教职工号：</td><td><%=tea.getId() %></td></tr>
<tr><td>姓名：</td><td><%=tea.getName() %></td></tr>
<tr><td>性别：</td><td><%if(tea.getSex()==null)out.print("未填写");else out.print(tea.getSex()); %></td></tr>
<tr><td>职称：</td><td><%=tea.getRank() %></td></tr>
<tr><td>手机号：</td><td><%if(tea.getPhone()==null)out.print("未填写");else out.print(tea.getPhone()); %></td></tr>
<tr><td>学院：</td><td><%if(tea.getXy()==null)out.print("未填写");else out.print(tea.getXy()); %></td></tr>
<tr><td>出题量：</td><td><%=tea.getNum() %></td></tr>
</table>
</center>
</body>
</html>