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
Question q =  (Question)HibernateUtil.get(Question.class, n);%>
<form action="t_keti_update.action?id=<%=q.getId()%>" method=post>
<table border=1>
<tr><td>标题：</td><td><input name="title" value=<%=q.getTitle() %>></td></tr>
<tr><td>内容：</td><td><textarea id=con name="con" cols="26" rows="5"  ><%=q.getCon() %>
</textarea></td></tr>
<tr><td>选题状态：</td><td>
<input type="radio" value="yes" name="zt" checked="checked">可选<input type="radio" name="zt" value="no">不可选</td></tr>
<tr ><td colspan="2"><input type="submit" value="提交"></td></tr>
</table>
</form>
</body>
</html>