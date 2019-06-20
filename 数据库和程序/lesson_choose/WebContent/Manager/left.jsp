<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet"href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--  <link rel="stylesheet"href="/book_choose/bootstrap.min.css">  
<script src="/lesson_choose/jquery.min.js"></script>
<script src="/lesson_choose/bootstrap.min.js"></script>-->
<body >
<a href="control.action" target = "right">平台开放管理</a>
<hr>
<a href = "pic.action" target="right">前台轮播图片管理</a>
<hr>
<a href = "update.jsp" target = "right">修改个人信息</a>
<hr>
<a href = "see.jsp" target = "right">个人信息查看</a>
<hr>
<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" 
                href="#collapseOne">
               	 学生信息管理
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body">
        <a href="stu_in.jsp" target = "right">学生信息批量导入</a><br>
		<a href="stu_add.jsp" target="right">学生信息添加</a><br>
		<a href="stu_man.action?page=1" target="right">学生信息整体显示</a><br>
		<a href="stu_other.jsp" target="right">学生信息查询</a><br>
		<a href="all_delete.jsp" target="right">删除所有学生信息</a>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" 
                href="#collapseTwo">
                教师信息管理
            </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse in">
            <div class="panel-body">
        <a href="tea_in.jsp" target="right">教师信息批量导入</a><br>
		<a href="tea_add.jsp" target="right">教师信息添加</a><br>
		<a href="tea_man.action?page=1" target="right">教师信息查询</a><br>
		<a href="tea_other.jsp" target="right">教师信息条件查询</a><br>
            </div>
        </div>
    </div>
    
</div>

<a href="t_que.jsp" target="right">问题查询</a><br>
</body>
</html>