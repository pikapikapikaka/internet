<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Question,java.util.List,util.HibernateUtil"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
int n = Integer.parseInt(request.getParameter("id"));
Question q =  (Question)HibernateUtil.get(Question.class, n);
 %>
<center>
<h3><font color="red">标题：</font></h3><%=q.getTitle() %>
<br>
<br>
<h3><font color="red">内容：</font></h3><%=q.getCon() %>
<br>
<h3><font color="red">状态：</font></h3>
<%int num = q.getZt();if(num==0)out.print("不可选");else out.print("可选");%>
<hr>
<a href="keti_update.jsp?id=<%=q.getId()%>">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;<a href="t_keti_delete.action?id=<%=q.getId()%>">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;<a href="keti_man.jsp">返回</a>
</center>
</body>
</html>