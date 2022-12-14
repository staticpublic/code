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
    <div class="row">
       <%-- <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增</a>
        </div>--%>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>标本编号</th>
                    <th>标本名字</th>
                    <th>标本出土地</th>
                    <th>出土时间</th>
                    <th>标本材质</th>
                    <th>所属年代</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${sampleListAll}">
                    <tr>
                        <td>${sample.getSampleID()}</td>
                        <td>${sample.getName()}</td>
                        <td>${sample.getBirthplace()}</td>
                        <td>${sample.getBirthtime()}</td>
                        <td>${sample.getMaterial()}</td>
                        <td>${sample.getYears()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/toUpdateSample/${sample.getSampleID()}">修改</a> |
                            <a href="${pageContext.request.contextPath}/admin/detail/${sample.getSampleID()}">详情</a> |
                            <a href="${pageContext.request.contextPath}/admin/delete/${sample.getSampleID()}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>