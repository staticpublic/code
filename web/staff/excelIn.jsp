<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/12
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div>
    <div class="col-md-12 column">
        <div class="page-header">
            <h1>
                <small>文件上传</small>
            </h1>
        </div>
    </div>
    <div class="col-md-6 column">
    <form action="/staff/importExcel" method="post" enctype="multipart/form-data">
        <input type="file" name="excelFile" value="请选择文件"/><br><br>
        <input type="submit" value="导入excel文件"/>
    </form>
    </div>
</div>

</body>
</html>
