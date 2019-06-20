<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Picture,java.util.List"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>轮播图片管理</title>
</head>
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
	font-size:15px;
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
function check()
{
	var v = document.getElementById("f").value;
	if(v=="")
		{
		alert("请输入地址");
		return false;
		}
	else 
		return true;
}
</script>
<body><!-- 为啥我的界面显示不出来添加图片的那个框 -->
<%List<Picture> list = (List<Picture>)request.getAttribute("pic"); %>
<center>
<form  action="pic_upload.action" method="post" enctype="multipart/form-data" onsubmit="return check()">
请选择要上传的照片：<input id=f type="file" name="file"/>
<input type ="submit" value = "提交">
</form>
<hr>

<table class="hovertable">
<tr>
	<th>Id</th><th>图片名</th><th>地址</th><th>操作</th>
</tr>
<%for(Picture pic : list){ %>
<tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
	<td><%=pic.getId() %></td><td><%=pic.getName() %></td><td><%=pic.getAddress() %></td><td><a href="pic_delete.action?id=<%=pic.getId()%>">删除</a></td>
</tr>
<%} %>
</table>
</center>
</body>
</html>