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
                    <small>单位列表 —— 显示所有单位</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row">
         <div class="col-md-4 column">
             <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/toNewUnit">新建单位</a>
         </div>
        <div class="col-md-4 column">
            <form class="form-inline" action="/admin/queryUnit" method="post" style="float: left">
                <input type="text" name="queryUnitName" class="form-control" placeholder="输入查询单位名" required value="${sessionScope.query}">
                <input type="submit" value="查询" class="btn btn-primary">
            </form>
        </div>
    </div>

    <%--<div class="col-md-12 column"></div>

        <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/toNewUnit">新建单位</a>
        </div>--%>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>单位编号</th>
                    <th>单位名称</th>
                    <th>联系人</th>
                    <th>联系方式</th>
                    <th>邮箱</th>
                    <th>联系地址</th>
                    <th>单位属性</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="unit" items="${unitList}">
                    <tr>
                        <td>${unit.getUnitID()}</td>
                        <td>${unit.getUnitName()}</td>
                        <td>${unit.getAcessMan()}</td>
                        <td>${unit.getTelephone()}</td>
                        <td>${unit.getEmail()}</td>
                        <td>${unit.getAddress()}</td>
                        <td>${unit.getProperty()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/toUpdateUnit/${unit.getUnitID()}">修改</a> |
                            <a href="${pageContext.request.contextPath}/admin/unitDetail/${unit.getUnitID()}">详情</a> |
                            <a href="${pageContext.request.contextPath}/admin/deleteUnit/${unit.getUnitID()}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>