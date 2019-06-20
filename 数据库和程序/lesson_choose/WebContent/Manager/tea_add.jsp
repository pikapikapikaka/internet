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
<form action="tea_add.action" method="post">
<table border=1>
<tr><td>教职工号：</td><td><input name="tea.id"></td></tr>
<tr><td>姓名：</td><td><input name="tea.name" ></td></tr>
<tr><td>密码：</td><td><input name="tea.pwd" ></td></tr>
<tr><td>性别：</td><td><input name="tea.sex"></td></tr>
<tr><td>学院：</td><td><input name="tea.xy"></td></tr>
<tr><td>出题数：</td><td><input name="tea.num"></td></tr>
<tr><td>职称：</td><td><input name="tea.rank"></td></tr>
<tr><td>手机号：</td><td><input name="tea.phone"></td></tr>
<tr><td colspan="2"><input type="submit" value="提交"></td></tr>
</table>
</form>
</center>
</body>
</html>