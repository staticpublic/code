<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/14
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>找回密码</title>
</head>
<body>
<div style="text-align: center">
    <form action="/admin/find" method="post">
        <label>用户名：</label><input type="text" name="username" value="${sessionScope.userInfo.getUsername()}"><br><br>
        <input type="submit" value="确认"><br><br>
    </form>
    <form action="/admin/findout" method="post">
        问题一：<input name="question" value="${sessionScope.userInfo.getQuestion()}" type="text"><br><br>
        答  案：<input name="answer" type="text"><br><br>
        <input type="submit"><br><br>
    </form>
    <div style="text-align: center">
        <%
            String pwd= (String) session.getAttribute("pwd");
        %>
        <a><%=pwd%></a>
        <a class="btn btn-primary" style="width: 100px" href="/homePage.jsp"></a>
    </div>
</div>
</body>
</html>
