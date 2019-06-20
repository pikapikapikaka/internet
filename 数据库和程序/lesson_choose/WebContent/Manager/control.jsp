<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Control,java.util.List"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
function check1()
{
	return cofirm("确定更改状态吗？");
}
function check2()
{
	return cofirm("确定更改状态吗？");
}
</script>
<body>
<% List<Control> list = (List<Control>)request.getAttribute("control");%>
<center>
点击相应链接进行更改
<br>
<a href="con_gg.action?name=<%=list.get(0).getName() %>&&value=<%=list.get(0).getValue() %> " onclick="return check1()">状态： <%=list.get(0).getName() %> 值：  <%=list.get(0).getValue() %></a>
<br>
<a href="con_gg.action?name=<%=list.get(1).getName() %>&&value=<%=list.get(1).getValue() %> ">状态： <%=list.get(1).getName() %> 值：  <%=list.get(1).getValue() %></a>
</center>
</body>
</html>