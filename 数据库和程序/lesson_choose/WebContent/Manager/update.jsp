<%@ page language="java" contentType="text/html; charset=UTF-8" import = "bean.Manager"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
function submit()
{
	var a = document.getElementById("a").value;
	var b = document.getElementById("b").vaule;
	//document.getElementById("p").innerHTML = document.getElementById("b").value;
	if(a == b)
		{document.getElementById("form1").submit;}//这个submit真他妈坑爹，，一下午呀！如果里面语句错了，整个就会异常
	else
		{
		alert("两次输入密码不一致，请重新输入！");
		}
		
}
</script>
<body>
<center>
<h2><font color="#8a2be21">更改用户信息</font></h2>
<p id="p"><h3><font color="red"><%String msg = (String)request.getAttribute("msg");
if(msg!=null) out.print(msg);
Manager m = (Manager)session.getAttribute("manager");%></font></h3></p>
<form action="man_update.action" name = "form1" id = "form1">
<table>
<tr><td>用户名：</td><td><input type="text" name="name" value=<%=m.getName() %>></td></tr>
<tr><td>新密码：</td><td><input type="password" id = "a" name="newpwd"></td></tr>
<tr><td>确认新密码：：</td><td><input type="password" id = "b" name="newpwd2"></td></tr>
<tr><td>旧密码：：</td><td><input type="password" name="oldpwd"></td></tr>
<tr><td>手机号：</td><td><input type="text" name="phone" value = <%=m.getPhone() %>></td></tr>
<tr><td colspan="2"><input type = "button" value = "提交" onclick = "submit()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type = "reset" value = "重置"></td></tr>
</table>
</form>
</center>
</body>
</html>