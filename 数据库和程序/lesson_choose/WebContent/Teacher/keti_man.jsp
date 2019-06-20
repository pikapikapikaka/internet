<%@ page language="java" contentType="text/html; charset=UTF-8" import = "bean.Teacher,bean.Question,java.util.List"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课题设计</title>
</head>
<style type="text/css">
table.hovertable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.hovertable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	font-size:15px;
}
table.hovertable tr {
	background-color:#d4e3e5;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	font-size:15px; 
}
</style>
<body>
<center>
<%Teacher t = (Teacher)session.getAttribute("teacher");//老师登陆的时候，我将老师这个变量保存到了session里面
List<Question> list = (List<Question>)session.getAttribute("question");//老师出的题目列表
int n = t.getNum();//看老师还能出几个题

%>
<b><i>尊敬的老师，您还可以<%=n %>道题目</i></b>
<%if(n>0)
{ %>
<br>
<a href="keti_add.jsp">课题添加</a>
<%}else
	{%>
	<br>您已经不能添加题目
	<%} %>
<hr>
<table class=hovertable>
<tr><th>题目编号：</th><th>题目主题</th><th>状态</th><th colspan="2">操作</th></tr>
<% 
if(list!=null)
for(Question ques:list)
{%>
<tr><td><%=ques.getId() %></td><td><%=ques.getTitle() %></td><td><%if(ques.getZt()==1) out.print("可选");
else out.print("不可选");%></td><td><a href="keti_con.jsp?id=<%=ques.getId()%>"> 查看</a></td><td><a href="t_s_check.action?tid=<%=ques.getId() %>">查看选择情况</a></td>
</tr>
<%} %>

</table>
</center>
</body>
</html>