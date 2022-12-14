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
                    <small>创建标本</small>
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
    <form action="/staff/newSampleByHand" method="post" enctype="multipart/form-data" >
        <p style=" margin:0 auto; text-align:center;">
        <fieldset>
            <br><br>
            <label>标本名称:</label>
            <input type="text" name="name"  required="required"><br><br>
            <label>标本出土地:</label><input type="text" name="birthplace" required="required"><br><br>
            <label>出土时间:</label><input type="text" name="birthtime" required="required"><br><br>
            <label>标本材质:</label><input type="text" name="material" required="required"><br><br>
            <label>所属年代:</label><input type="text" name="years" required="required"><br><br>
            <label>录入人:</label><input type="text" name="recordman" required="required"><br><br>
            <label>标本描述:</label><input type="text" name="description" required="required"><br><br>
            <label>是否可查看:</label><input type="text" name="isVisible" required="required"><br><br>
        <label>上传头像:</label><input type="file" name="file" value="上传图片" style="text-align: center"><br><br>
            <input type="submit" value="录入">
        </fieldset>
        </p>
    </form>
</body>
</html>

