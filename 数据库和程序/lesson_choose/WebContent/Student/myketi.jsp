<%@ page language="java" contentType="text/html; charset=UTF-8" import="util.HibernateUtil,bean.Student,bean.Question,bean.Teacher"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
function sure()
{
	return confirm("你确定取消选择这个课题吗？");
	}
</script>
<body>
<% 
Student s = (Student)session.getAttribute("student");%>
<center>
<h3><font color="red">
<%
String error = (String)request.getAttribute("error");
if(error!=null)out.print(error);%>
</font></h3>
<%
Question q=null;
Teacher t=null;
int id = s.getTh();
if(id != 0)
{
q =  (Question)HibernateUtil.get(Question.class, id);
t = (Teacher)HibernateUtil.get(Teacher.class, q.getTid());

%>
<table><tr><td>
题目：</td><td><%=q.getTitle() %></td></tr>
<tr><td>内容：</td><td><%=q.getCon() %></td></tr>
<tr><td>教师姓名：</td><td><%=t.getName() %></td></tr>
<tr><td>手机号：</td><td><%=t.getPhone() %></td></tr>
</table>
<br><br>
<a href="s_keti_cancel.action" onclick="return sure()">取消</a>
<%}
else
out.print("小朋友，你还没有选题嘞！");%>
</center>
</body>
</html>