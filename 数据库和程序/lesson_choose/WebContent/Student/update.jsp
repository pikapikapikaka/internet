<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Student"
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
	return confirm("确定提交,客官？");
	}
</script>
<body>
<%
Student s = (Student)session.getAttribute("student");%>
<center>
<h3><i>学生信息更新</i></h3>
<form method="post" action="s_stu_update.action">
<table border=1>
<tr><td>学号：</td><td><input name="stu.id" value=<%=s.getId() %> onfocus="this.blur()"></td></tr>
<tr><td>姓名：</td><td><input name="stu.name" value=<%=s.getName() %> ></td></tr>
<tr><td>性别：</td><td><input name="stu.sex" value=<%if(s.getSex()!= null)out.print(s.getName());%> ></td></tr>
<tr><td>学院：</td><td><input name="stu.xy" value=<%if(s.getXy()!=null)out.print(s.getXy());%> ></td></tr>
<tr><td>专业：</td><td><input name="stu.zy" value=<%if(s.getZy()!=null)out.print(s.getZy());%> ></td></tr>
<tr><td>班级：</td><td><input name="stu.bj" value=<%if(s.getBj()!=null)out.print(s.getBj());%> ></td></tr>
<tr><td><input type="hidden" name="stu.pwd" value=<%=s.getPwd() %>></td><td><input type="hidden" name="stu.th" value=<%=s.getTh() %>></td></tr>
<tr><td colspan="2"><input type="submit" value="提交" onclick="return check()"></td></tr>
</table>
</form>
</center>
</body>
</html>