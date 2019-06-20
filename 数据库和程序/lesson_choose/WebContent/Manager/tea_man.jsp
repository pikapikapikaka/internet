<%@page import="org.apache.poi.ss.formula.functions.Now"%>
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
	background-color:#c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable tr {
	background-color:#d4e3e5;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	font-size:15px; 
}
</style>
<script>
function a_submit()
{
	var a = document.getElementById("page").value;
	//alert(a);
	//js中null和“”好像不是一个，，具体以后实验
	if(a!="")
		{
		//document.getElementById("dz").submit;
		return true;
		}
	else
		{
		alert("输入不能为空！！！");
		return false;
		}
	
}
</script>
<body>
<%int nowpage = (Integer)session.getAttribute("tea_page"); %>
<center>
<h3><p><font color="red"><%String res = (String)request.getAttribute("fail"); if(res!=null)out.print(res);%></font></p></h3>
<table class="hovertable">
<th>教职工号</th><th>姓名</th><th>性别</th><th>学院</th><th>手机号</th><th>剩余出题数目</th><th>职称</th><th colspan="2">操作</th>
<%List<Teacher> list = (List<Teacher>)session.getAttribute("teacher");
for(Teacher s : list){ %>
<tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
	<td><%=s.getId() %></td><td><%=s.getName() %></td>
	<td><%if(s.getSex()==null)	out.print("未注册"); else out.print(s.getSex());%></td>
		<td><%if(s.getXy()==null)out.print("未注册");else out.print(s.getXy()); %></td>
		<td><%if(s.getPhone()==null)out.print("未注册");else out.print(s.getPhone()); %></td>
		<td><%=s.getNum() %></td>
		<td><%=s.getRank() %></td>

			<td><a href="tea_delete.action?id=<%=s.getId()%>&&page=<%=nowpage%>">删除</a></td>
			<td><a href="tea_update.action?id=<%=s.getId()%>&&page=<%=nowpage%>">修改</a></td>
</tr>
<%} %>
</table>
<div>
<ul class="pager">
<%if(nowpage!=1&&nowpage!=0) {%>
    <li class = "previous"><a href="tea_man.action?page=<%=nowpage-1%>">上一页</a></li>
    <%}else
    	{%><li class="previous disabled"><a href="#">上一页</a></li>
    	<%} %>
    	当前为第<%=nowpage %>页，共<%=(Integer)session.getAttribute("tea_endpage")%>页
<%if(nowpage == (Integer)session.getAttribute("tea_endpage")) {%>
<li class="next disabled"><a href="#">下一页</a></li>
<%} else{%>
    <li class = "next"><a href="tea_man.action?page=<%=nowpage+1%>">下一页</a></li>
    <%} %>
</ul>
</div>
<form action="tea_man.action" method="post">
<input type=text id="page" name="page">
<input type="submit" value="跳转" onclick="return a_submit()">
</form>
</center>

</body>
</html>