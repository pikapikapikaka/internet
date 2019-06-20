<%@ page language="java" contentType="text/html; charset=UTF-8" import="bean.Picture,java.util.List,util.HibernateUtil"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>轮播图片</title>
</head>
<link rel="stylesheet"href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<body>
<center>
<!-- 这个是程序运行的界面 -->
<div id="myCarousel" class="carousel slide">
	<!-- 轮播（Carousel）指标 -->
  
	<!-- 轮播（Carousel）项目 -->
<div class="carousel-inner">	
<%
List<Picture> list = null;
list = HibernateUtil.query("bean.Picture", "", "");
//为啥设置list=null,就不行，out.print()这局会变成死亡代码？
if(list.size() == 0)
	out.print("<center><h1>管理员比较懒，，还没有设置！</h1><center>");
else
for(int i = 0;i<list.size();i++)
{
%>

<%if(i == 0){%>
<div class="item active">
			<img src="<%=list.get(i).getAddress() %>" width = 500 height = 500 >
		</div>	
<%}
else{%>
<div class="item">
			<img src="<%=list.get(i).getAddress() %>" width = 500 height = 500 >
		</div>
			
<%}} %>
	<!-- 轮播（Carousel）导航 -->
	<a class="carousel-control left" href="#myCarousel" 
	   data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#myCarousel" 
	   data-slide="next">&rsaquo;</a>
</div> 
</center>
</body>
</html>