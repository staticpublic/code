<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/9
  Time: 1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>创建单位</small>
                </h1>
            </div>
        </div>
    </div>
    <style type="text/css">
        fieldset
        {
            margin:30px auto;
            width:400px;
            height:500px;
            padding:20px auto;
            color:blue;
            text-align:center;
        }

        /*label部分CSS才是重点*/
        label
        {
            display: inline-block;
            width: 80px;
            text-align: justify;
            text-align-last: justify;
            margin-right: 10px;
        }


    </style>
    <form action="/admin/newUnit" method="post">
        <p style=" margin:0 auto; text-align:center;">
        <fieldset>
            <br><br>
            <label>单位名称:</label>
            <input type="text" name="unitName"  required="required"><br><br>
            <label>联系人:</label><input type="text" name="acessMan" required="required"><br><br>
            <label>联系电话:</label><input type="tel" name="telephone" required="required"><br><br>
            <label>电子邮箱:</label><input type="email" name="email" required="required"><br><br>
            <label>联系地址:</label><input type="text" name="address" required="required"><br><br>
            <label>单位性质:</label><input type="text" name="property" ><br><br>

            <input type="submit" value="创建">
        </fieldset>
        </p>
    </form>
</body>
</html>

