<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>书籍列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>用户列表 —— 显示所有用户</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row">
         <div class="col-md-4 column">
             <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/toNewUser">新建用户</a>
         </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>用户编号</th>
                    <th>用户名字</th>
                    <th>用户真名</th>
                    <th>联系方式</th>
                    <th>邮箱地址</th>
                    <th>用户身份</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.getUserID()}</td>
                        <td>${user.getUsername()}</td>
                        <td>${user.getRealname()}</td>
                        <td>${user.getTelephone()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getIdentity()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/toUpdateUser/${user.getUserID()}">修改</a> |
                            <a href="${pageContext.request.contextPath}/admin/userDetail/${user.getUserID()}">详情</a> |
                            <a href="${pageContext.request.contextPath}/admin/deleteUser/${user.getUserID()}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>