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
<%Teacher t = (Teacher)session.getAttribute("teacher"); %>
<form method=post action="t_tea_update.action">
<table border=1>
<tr><td>姓名：</td><td><input name="name" value=<%=t.getName() %>></td></tr>
<tr><td>性别：</td><td><input name="sex" value=<%if(t.getSex()==null)out.print("");else out.print(t.getSex()); %>></td></tr>
<tr><td>手机号：</td><td><input name="phone" value=<%if(t.getPhone()==null)out.print("");else out.print(t.getPhone()); %>></td></tr>
<tr><td>学院：</td><td><input name="xy" value=<%if(t.getXy()==null)out.print("");else out.print(t.getXy()); %>></td></tr>
<tr><td colspan="2"><input type="submit" value="提交" ><input type="reset" value="重置"></td></tr>
</table>
</form>
</center>
</body>
</html>