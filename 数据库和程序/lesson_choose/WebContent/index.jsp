<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>毕业生题目设计系统</title>
</head>
<style type="text/css">
.first{position:absolute; width:100%; height:100%; z-index:-1}
table{border-radius:30px;overflow:hidden;border-collapse:no-collapse;border-color: #172563;}
td{
border-color:#172563;
	border-width: 1px;
	padding: 8px;
	border-style: solid;}
</style>
<script>
function _submit()
{
	var p = document.getElementById("p");
	//alert("aaa");
	var sele = document.getElementById("select").value;
	var name = document.getElementById("name").value;
	var pwd = document.getElementById("pwd").value;
	//alert(name);
	if("" == name)
		alert("姓名不能为空！");
	else if(pwd == "")
		alert("密码不能为空！");
	else 
		{
		if(sele == "manager")
			document.form1.action = "man_yz.action";
		else if(sele == "student")
			document.form1.action = "stu_yz.action";
		else if(sele == "teacher")
			document.form1.action = "tea_yz.action";
		else
			alert("请选择登录类型！");
		}
	//document.getElementById("p1").innerHTML = sele;
	}
	function change()
	{
		document.getElementById("cc").src="Encode?a="+Math.random();
	}
</script>
<body background="gra.jpg" class="first">
<br/><br/>
<center>
<h1><b><i>欢迎来到毕业生设计系统</i></b></h1>
<br/><br/>
<font color="red"><h3>
<%
String fail = (String)session.getAttribute("fail");
if(fail!=null)
	out.print(fail);
%>
</h3></font>
<br/>
<form  method="post" name="form1">
<table border=1>
<tr><td>登录类型</td><td><select id="select" ><option value="teacher">教师登录</option>
<option value="student">学生登录</option><option value="manager">管理员登录</option></select></td></tr>
<tr><td>用户名：</td><td><input type="text" id="name" name="name"></td></tr>
<tr><td>密码：</td><td><input type="password" id="pwd" name="pwd"></td></tr>
<tr><td colspan="2"><center><img id="cc" src="Encode" onclick="change()"></center></td></tr>
<tr><td>请输入验证码：</td><td><input type="text" name="code"></td></tr>
<tr><td colspan="2"><input type = "submit" value="提交" onclick="_submit()">&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"></td></tr>
</table>
</form>
<p id="p"></p>>
</center>
</body>
</html>