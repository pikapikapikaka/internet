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
	if(document.getElementById("name").value!=""&&document.getElementById("con").value!="")
		{
		return true;
		}
	else
		{
		alert("内容不能为空！");
		return false;
		}
}
</script>
<body>
<center>
<form action="t_keti_add.action">
标题<input id=name name="name">
<br>
内容：<textarea id=con name="con" cols="26" rows="5" >
</textarea>
<br>
选题状态：
<input type="radio" value="yes" name="zt" checked="checked">可选<input type="radio" name="zt" value="no">不可选
<br>
<input type="submit" value="提交" onclick="return check()">

</form>
</center>
</body>
</html>