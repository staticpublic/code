<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2022/12/8
  Time: 1:01
  To change this template use File | Settings | File Templates.
--%>
<%--“在 page directive 中的 isELIgnored 属性用来指定是否忽略。格式为： ＜%@ page isELIgnored＝"true|false"%＞。
如果设定为真，那么JSP中的表达式被当成字符串处理！！！”--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>afterLogin</title>
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
                            <small>欢迎使用文物标本管理平台</small>
                        </h1>
                    </div>
                </div>
            </div>

            <%--            <a class="btn btn-primary" style="width: 100px" href="user/regOrLogin">修改个人信息</a><br><br>--%>

            <a class="btn btn-primary" style="width: 130px" href="/staff/toSampleManagement">标本管理</a><br><br>
            <a class="btn btn-primary" style="width: 130px" href="/staff/toBorrowSample">标本借阅</a><br><br>
            <a class="btn btn-primary" style="width: 130px" href="/staff/toInOut">标本借入借出管理</a><br><br>
            <a class="btn btn-primary" style="width: 100px" href="/staff/count">统计分析管理</a><br><br>
            <a class="btn btn-primary" style="width: 100px" href="/staff/toReset">重置密码</a><br><br>
            <a class="btn btn-primary" style="width: 100px" href="/staff/exit">退出系统</a><br><br>
        </div>
    </div>
</div>

<style type="text/css">
    #rig{
        width:50px;
        height:35px;
        background: coral;
        position: absolute;
        top:0px;
        right: 0px;
    }
</style>


<div id="rig" >
    <a href="/staff/toChange">${sessionScope.staffName}
    </a>
</div>


</body>
</html>
