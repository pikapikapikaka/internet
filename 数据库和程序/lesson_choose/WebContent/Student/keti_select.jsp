<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List,bean.Question"
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
function _submit()
{
	var a = document.getElementById("page").value;
	//alert(a);
	//js中null和“”好像不是一个，，具体以后实验
	if(a!="")
		document.getElementById("dz").submit;
	else
		alert("输入不能为空！！！");
	
}
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
<%
response.setHeader("refresh", "30"); 
%>
<center>
<form method=post action="s_keti_mohu.action" onsubmit="return check()">
按条件查询：<select name="tiaojian"><option value="name">教师</option><option value="title">主题：</option></select>
<input id=con name="con"><input type="submit" value="查询" >
</form>
</from>
<hr>
<h2><font color="red">本界面30秒刷新一次，也可自己手动刷新，点击选题即可</font></h2>
<table class="hovertable">
<th>题目</th><th>点击查看具体内容</th>
<%List<Question> list = (List<Question>)request.getAttribute("ques");
if(list!=null)
for(Question q : list){ 
if(q.getZt()==1){%>
<tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
<td><%=q.getTitle() %></td><td><a href="keti.jsp?tid=<%=q.getTid()%>&&id=<%=q.getId() %>">查看</a></td>
</tr>

<%
}} %>
</table>
<div>
<ul class="pager">
<%int nowpage = (Integer)session.getAttribute("que_page");if(nowpage!=1&&nowpage!=0) {%>
    <li class = "previous"><a href="s_keti_select.action?page=<%=nowpage-1%>">上一页</a></li>
    <%}else
    	{%><li class="previous disabled"><a href="#">上一页</a></li>
    	<%} %>
    	当前为第<%=nowpage %>页，共<%=(Integer)session.getAttribute("que_endpage")%>页
<%if(nowpage == (Integer)session.getAttribute("que_endpage")) {%>
<li class="next disabled"><a href="#">下一页</a></li>
<%} else{%>
    <li class = "next"><a href="s_keti_select.action?page=<%=nowpage+1%>">下一页</a></li>
    <%} %>
</ul>
</div>
<form id="dz" action="s_keti_select.action" method="post">
<input type=text id="page" name="page">
<input type="button" value="跳转" onclick="_submit()">
</form>
</center>
</body>
</html>