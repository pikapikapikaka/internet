<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
function remind()
{
	return confirm("真的删除吗？");
	}
</script>
<center>
<h2>请认真思考后决定，删除后将不能还原</h2>
<a href="m_delete_stu.action" onclick="return remind()">删除所有学生信息</a>
</center>
</body>
</html>