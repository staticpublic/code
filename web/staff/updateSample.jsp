<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/7
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>

<%request.setCharacterEncoding("utf-8"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div style="text-align: center;">
    <div class="row">
        <div class="col-md-12 column">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            <small>修改标本资料</small>
                        </h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style type="text/css">
    fieldset
    {
        margin:30px auto;
        width:300px;
        height:300px;
        padding:20px auto;
        color:black;
        text-align:center;
    }

    /*label部分CSS才是重点*/
    label
    {
        display: inline-block;
        width: 110px;
        text-align: justify;
        text-align-last: justify;
        margin-right: 10px;
    }


</style>
<div style="text-align: center;">
    <form action="/staff/updateSample" method="post">
        <p style=" margin:0 auto; text-align:center;">
        <fieldset>
            <label>标本名字:</label><input type="text" name="name" value="${sessionScope.sample.getName()}" readonly="readonly"><br><br>
            <label>标本出土地:</label><input type="text" name="birthplace" value="${sessionScope.sample.getBirthplace()}"><br><br>
            <label>出土时间:</label><input type="text" name="birthtime" value="${sessionScope.sample.getBirthtime()}"><br><br>
            <label>标本材质:</label><input type="text" name="material" value="${sessionScope.sample.getMaterial()}"><br><br>
            <label>所属年代:</label><input type="text" name="years"value="${sessionScope.sample.getYears()}"><br><br>
            <label>录入人:</label><input type="text" name="recordman" value="${sessionScope.sample.getRecordman()}"><br><br>
            <label>标本描述:</label><input type="text" name="description"value="${sessionScope.sample.getDescription()}"><br><br>
            <label>入库时间:</label><input type="text" name="intime" value="${sessionScope.sample.getIntime()}"><br><br>
        <label>标本是否可查看:</label><input type="text" name="isVisible" value="${sessionScope.sample.getIsVisible()}"><br><br>

        <input type="submit" value="确认修改">
        </fieldset>
        </p>
    </form>
</div>
</body>
</html>
