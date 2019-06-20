<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Teacher,bean.Question,util.HibernateUtil,bean.Student"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Question q = (Question)HibernateUtil.get(Question.class,Integer.parseInt(request.getParameter("id")) );
Teacher t = (Teacher)HibernateUtil.get(Teacher.class, request.getParameter("tid"));
Student s = (Student)session.getAttribute("student");
%>
<center>
<table><tr><td>
题目：</td><td><%=q.getTitle() %></td></tr>
<tr><td>内容：</td><td><%=q.getCon() %></td></tr>
<tr><td>教师姓名：</td><td><%=t.getName() %></td></tr>
<tr><td>手机号：</td><td><%=t.getPhone() %></td></tr>
</table>
<a href="s_keti_ack.action?q_id=<%=q.getId()%>">选择课题</a>
<br>
<br>
<a href="s_keti_select.action?page=1">返回</a>
</center>

</body>
</html>