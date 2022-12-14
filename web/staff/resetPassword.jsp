<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/8
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>重置密码</title>
</head>
<body>
<div class="row">
    <div class="col-md-12 column">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>重置密码</small>
                    </h1>
                </div>
            </div>
        </div>

        <form action="/staff/reset" method="post">
            <p style=" margin:0 auto; text-align:center;">
            <fieldset>
                <br><br>
                <label>用户名： </label><input type="text" name="username" value="${sessionScope.staffInfo.getUsername()}" readonly="readonly"><br><br>
                <label>新密码: </label><input type="password" name="newPassword" ><br><br>
                <label>问题一: </label><input type="text" name="question" value="${sessionScope.staffInfo.getQuestion()}"><br><br>
                <label>答  案: </label><input type="text" name="answer" ><br><br>

                <input type="submit" value="确认修改">
            </fieldset>
            </p>
        </form>
    </div>
</div>

</body>
</html>
