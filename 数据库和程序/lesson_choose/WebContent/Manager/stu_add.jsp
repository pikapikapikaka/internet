<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
</style>
<body>
<center>
<br/><br/>
<form action="stu_add.action" method="post">
<table border=1>
<tr><td>学号：</td><td><input type="text" name="stu.id"></td></tr>
<tr><td>姓名：</td><td><input name="stu.name"></td></tr>
<tr><td>性别：</td><td><input name="stu.sex"></td></tr>
<tr><td>专业：</td><td><input name="stu.zy"></td></tr>
<tr><td>班级：</td><td><input name="stu.bj"></td></tr>
<tr><td>学院：</td><td><input name="stu.xy"></td></tr>
<tr><td colspan="2"><input type="submit" value="提交"></td></tr>
</table>
</form>
</center>
</body>
</html>