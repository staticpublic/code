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
                    <small>标本列表 —— 接入标本</small>
                </h1>
            </div>
        </div>
    </div>
    <div class="col-md-4 column">
        <form class="form-inline" action="/staff/in" method="post" style="float: left">
            <select id="skills" name="select"  required>
                <option value="none" selected disabled hidden>选择状态</option>
                <option value="待审核">待审核</option>
                <option value="已审核">已审核</option>
                <option value="已结束">已结束 </option>
            </select>
            <input type="submit" value="查询" class="btn btn-primary">

        </form>
    </div>
    <a class="btn btn-primary" href="inAndOut.jsp">返回</a>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>借阅ID</th>
                    <th>标本ID</th>
                    <th>标本名字</th>
                    <th>所属单位</th>
                    <th>借阅账户ID</th>
                    <th>借阅人</th>
                    <th>借阅人联系方式</th>
                    <th>借入状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${inList}">
                    <tr>
                        <td>${sample.getBorrowID()}</td>
                        <td>${sample.getSampleID()}</td>
                        <td>${sample.getSampleName()}</td>
                        <td>${sample.getBelongUnit()}</td>
                        <td>${sample.getUserID()}</td>
                        <td>${sample.getBorrowMan()}</td>
                        <td>${sample.getBorrowTele()}</td>

                        <td>${sample.getInState()}</td>
                        <td>
                            <c:if test="${sample.getInState()=='已审核'}">
                                <a href="${pageContext.request.contextPath}/staff/returnSample/${sample.getSampleID()}">归还</a>
                            </c:if>




                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>