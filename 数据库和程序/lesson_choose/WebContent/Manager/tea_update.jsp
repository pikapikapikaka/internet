<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Teacher"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
<%Teacher tea = (Teacher)request.getAttribute("teacher"); %>
<form method=post action=teacher_update.action>
<table border=1>
<tr><td>教职工号：</td><td><input name="tea.id" value=<%=tea.getId() %> onfocus="this.blur()"></td></tr>
<tr><td>姓名：</td><td><input name="tea.name" value=<%=tea.getName() %>></td></tr>
<tr><td>密码：</td><td><input name="tea.pwd" value=<%=tea.getPwd() %>></td></tr>
<tr><td>性别：</td><td><input name="tea.sex" value=<%=tea.getSex() %>></td></tr>
<tr><td>学院：</td><td><input name="tea.xy" value=<%=tea.getXy() %>></td></tr>
<tr><td>出题数：</td><td><input name="tea.num" value=<%=tea.getNum() %>></td></tr>
<tr><td>职称：</td><td><input name="tea.rank"value="<%=tea.getRank()%>"></td></tr>
<tr><td>手机号：</td><td><input name="tea.phone" value=<%=tea.getPhone() %>></td></tr>
<tr><td colspan="2"><input type="submit" value="提交"><input type="reset" value="重置"></td></tr>
<input type="hidden" name="page" value="<%=request.getParameter("page")%>">
</table>
</form>
</center>
</body>
</html>