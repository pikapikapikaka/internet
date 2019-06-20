<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List,bean.Question"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%List<Question> list = (List<Question>)request.getAttribute("q_list"); %>
<table border=1>
<th>题目</th><th>点击查看具体内容</th>
<%
for(Question q : list){ 
if(q.getZt()==1){%>
<tr>
<td><%=q.getTitle() %></td><td><a href="keti.jsp?tid=<%=q.getTid()%>&&id=<%=q.getId() %>">查看</a></td>
</tr>

<%
}} %>
</table>
</center>

</body>
</html>