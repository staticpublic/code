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
                            <small>修改单位资料</small>
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
        width: 90px;
        text-align: justify;
        text-align-last: justify;
        margin-right: 10px;
    }


</style>
<div style="text-align: center;">
    <form action="/admin/updateUnit" method="post">
        <p style=" margin:0 auto; text-align:center;">
        <fieldset>
            <br><br>
            <label>单位名称:</label><input type="text" name="unitName" value="${sessionScope.unit.getUnitName()}" readonly="readonly"><br><br>
            <label>联系人:</label><input type="text" name="acessMan"value="${sessionScope.unit.getAcessMan()}"><br><br>
            <label>联系方式:</label><input type="text" name="telephone" value="${sessionScope.unit.getTelephone()}"><br><br>
            <label>邮箱地址:</label><input type="email" name="email" value="${sessionScope.unit.getEmail()}"><br><br>
            <label>联系地址:</label><input type="text" name="address" value="${sessionScope.unit.getAddress()}"><br><br>
            <label>单位性质:</label><input type="text" name="property"value="${sessionScope.unit.getProperty()}"><br><br>
            <input type="submit" value="确认修改">
        </fieldset>
        </p>
    </form>
</div>
</body>
</html>
