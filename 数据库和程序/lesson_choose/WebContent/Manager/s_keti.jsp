<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Student,bean.Question,bean.Teacher"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%Question q = (Question)request.getAttribute("question");
Teacher t = (Teacher)request.getAttribute("teacher");
Student s = (Student)request.getAttribute("student");%>
<h3>学生信息</h3>
<table>
<tr><td>学号：</td><td><%=s.getId() %></td></tr><tr><td>姓名：</td><td><%=s.getName() %></td></tr>
<tr><td>学院：</td><td><%=s.getXy() %></td></tr><tr><td>专业：</td><td><%=s.getZy() %></td></tr>
<tr><td>班级：</td><td><%=s.getBj() %></td></tr>
</table>
<hr>
<h3>课题教师信息</h3>
<table><tr><td>教职工号：</td><td><%=t.getId() %></td></tr>
<tr><td>姓名：</td><td><%=t.getName() %></td></tr>
<tr><td>手机号：</td><td><%=t.getPhone() %></td></tr>
</table>
<hr>
<h3>课题信息</h3>
<table>

<tr><td>标题：</td><td><%=q.getTitle() %></td></tr>
<tr><td>内容：</td><td><%=q.getCon() %></td></tr>
</table>
<a href="m_t_s.action?sid=<%=s.getId()%>">取消该学生课题</a>
</center>

</body>
</html>