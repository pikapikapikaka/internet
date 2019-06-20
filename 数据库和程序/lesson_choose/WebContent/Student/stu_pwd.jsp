<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
function check()
{
	var n1 = document.getElementById("n1").value;
	var n2 = document.getElementById("n2").value;
	if(n1 == n2)
		return true;
	else
		{
		alert("两次密码不一致");
		return false;
		}
	}
</script>
<body>
<center>
<br>
<br>
<p><font size="30px" color="red"><%String mess = (String)request.getAttribute("mess");if(mess!=null)out.print(mess); %></font></p>
<h2>密码修改</h2>
<form action="s_stu_pwd.action" method="post">
<table bprder=1>
<tr><td>旧密码：</td><td><input type="password" name="oldpwd"></td></tr>
<tr><td>新密码：</td><td><input type="password" name="newpwd" id="n1"></td></tr>
<tr><td>确认一遍新密码：</td><td><input type="password" name="newpwd" id="n2"></td></tr>
<tr><td colspan="2"><input type="submit" value="提交" onclick="return check()"></td></tr>
</table>
</form>
</center>
</body>
</html>