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
    <title>完善个人信息</title>
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
                            <small>修改个人资料</small>
                        </h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="text-align: center;">
<form action="/user/change" method="post">
    <p style=" margin:0 auto; text-align:center;">
    <fieldset>
        <br><br>
        <label>用户名</label><input type="text" name="username" value="${sessionScope.userInfo.getUsername()}" readonly="readonly"><br><br>
        <label>真实姓名:</label><input type="text" name="realname" value="${sessionScope.userInfo.getRealname()}"><br><br>
        <label>联系电话:</label><input type="text" name="telephone" value="${sessionScope.userInfo.getTelephone()}"><br><br>
        <label>电子邮箱:</label><input type="email" name="email" value="${sessionScope.userInfo.getEmail()}"><br><br>
        <label>家庭住址:</label><input type="text" name="address"value="${sessionScope.userInfo.getAddress()}"><br><br>
        <label>用户性别:</label><input type="text" name="gender" value="${sessionScope.userInfo.getGender()}"><br><br>
        <label>问题设置:</label><input type="text" name="question"value="${sessionScope.userInfo.getQuestion()}"><br><br>
        <label>问题答案:</label><input type="text" name="answer" value="${sessionScope.userInfo.getAnswer()}"><br><br>
        <input type="submit" value="确认修改">
    </fieldset>
    </p>
</form>
</div>
</body>
</html>
