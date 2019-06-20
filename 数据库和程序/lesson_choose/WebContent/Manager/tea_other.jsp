<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Teacher,java.util.List"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet"href="/lesson_choose/bootstrap.min.css">  
<script src="/lesson_choose/jquery.min.js"></script>
<script src="/lesson_choose/bootstrap.min.js"></script>
<style type="text/css">
table.hovertable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.hovertable th {
	border-width: 5px;
	padding: 15px;
	border-style: solid;
	border-color: #a9c6c9;
	font-size:20px; 
}
table.hovertable tr {
	background-color:#d4e3e5;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	font-size:20px; 
}
</style>
<script>
function check()
{
	if(document.getElementById("name").value=="")
		return false;
	else
		return true;
}
</script>
<body> 
<center>
<form action="tea_select.action" method="post" onsubmit="check()">
<select name="leixing">
<option value="id">教职工号</option>
<option value="name">姓名</option>
<option value="zy">职称</option>
<option value="xy">学院</option>
</select>
<input  id="name" name="name">
<input type="submit" value="提交">
</form>
<hr>
<table border=1 class="hovertable">
<th>教职工号</th><th>姓名</th><th>密码</th><th>职称</th><th>性别</th><th>学院</th><th>出题数</th><th></th>手机号<th colspan="2">操作</th>
<%List<Teacher> list = (List<Teacher>)request.getAttribute("select_teacher");
if(list!=null)
for(Teacher s : list){ %>
<tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
	<td><%=s.getId() %></td><td><%=s.getName() %></td><td><%=s.getPwd() %></td><td><%=s.getRank() %></td>
	<td><%if(s.getSex()==null)	out.print("未填写"); else out.print(s.getSex());%></td>
		<td><%if(s.getXy()==null)out.print("未填写");else out.print(s.getXy()); %></td>
		<td><%=s.getNum()%></td>
		<td><%if(s.getPhone()==null)out.print("未填写");else
			out.print(s.getPhone());%></td>
			<td><a href="stu_delete.action?id=<%=s.getId()%>">删除</a></td>
</tr>
<%} %>
</table>

</center>
</body>
</html>