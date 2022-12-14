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
                    <small>标本列表 —— 显示所有标本</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>标本编号</th>
                    <th>标本名字</th>
                    <th>所属单位</th>
                    <th>出土地</th>
                    <th>标本材质</th>
                    <th>录入人</th>
                    <th>录入时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${samples}">
                    <tr>
                        <td>${sample.getSampleID()}</td>
                        <td>${sample.getName()}</td>
                        <td>${sample.getBelongUnit()}</td>
                        <td>${sample.getBirthplace()}</td>
                        <td>${sample.getMaterial()}</td>
                        <td>${sample.getRecordman()}</td>
                        <td>${sample.getIntime()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/staff/borrowSample/${sample.getSampleID()}">借阅</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>