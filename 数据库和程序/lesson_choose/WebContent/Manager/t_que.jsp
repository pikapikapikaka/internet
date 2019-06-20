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
	var v = document.getElementById("con").value;
	if(v == "")
		{
		alert("内容为空！");
		return false;
		}
	else
		return true;
		
}
</script>
<body>
<center>
<form method=post action="m_keti_select.action" onsubmit="return check()">
具体查询：<select name="tj"><option value="stu">学号</option><option value="tea">教职工号</option></select>
<input id=con name="con"><input type="submit" value="查询" >
</form>
</center>
</body>
</html>