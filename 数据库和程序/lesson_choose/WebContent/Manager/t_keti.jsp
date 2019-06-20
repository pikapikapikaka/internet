<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List,bean.Question,bean.Teacher"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<center>
<%List<Question>  list = (List<Question>)request.getAttribute("question");
Teacher t = (Teacher)request.getAttribute("teacher");%>
<table>
<tr><td>教职工号：</td><td><%=t.getId() %></td></tr>
<tr><td>姓名：</td><td><%=t.getName()%></td></tr>
<tr><td>性别：</td><td><%=t.getSex() %></td></tr>
<tr><td>出题数量：</td><td><%=t.getNum() %></td></tr>
<tr><td>教职工号：</td><td><%=t.getXy() %></td></tr>
</table>
<hr>
<table border=1>
<tr><th>主题</th><th>内容</th><th>状态</th><th colspan="2">操作</th></tr>
<%for(Question q:list){ %>
<tr><td><%=q.getTitle() %></td><td><%=q.getCon() %></td><td><%if(q.getZt()==0)out.print("不可选");else out.print("可选"); %></td><td><a href="m_s_ischecked.action?id=<%=q.getId()%>">查询</a></td><td><form action="m_s_add.action" method=post><input width="6" name="sid"><input type="hidden" name="tid" value=<%=q.getId() %>><input type="submit" value="添加"></form></td></tr>
<%} %>
</table>
</center>
</body>
</html>