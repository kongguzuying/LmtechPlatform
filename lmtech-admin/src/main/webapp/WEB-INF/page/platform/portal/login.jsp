<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />

    <title></title>

    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet">

<style type="text/css">
.container_bg {
    padding-top:100px;
    margin: 50px auto;
    /* padding-left: 15px;
    padding-right: 15px; */
    width: 1030px;
    height: 523px;
    /*background:  url("../images/login_bgicon.png") no-repeat center;*/
    /* background-size: 900px; */
}
.form-signin-div {
    width: 571px;
    height: 452px;
    margin: -30px auto;
    background: #f7f7f7;
    border-radius: 10px;
    border: solid 8px #C0E2DF;
    -webkit-border-radius: 10px;
}
.form-signin {
    max-width: 400px;
    margin: 0px auto;
    padding-top: 50px;
    background: #f7f7f7;
    border-radius: 5px;
    -webkit-border-radius: 5px;
}
</style>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
        $(function () {
            if (window != top) {
                top.location.reload();
            }
        });
    </script>
</head>

<body class="login-body">
<div style="position: absolute;top: 5px;left: 5px;">
</div>
<div class="container_bg">

	<div class="form-signin-div">
		
    <form class="form-signin" action="${pageContext.request.contextPath}/platform/portal/login.do" method="post" onSubmit="return checkForm();">
        <div class="form-signin-heading text-center">
		</div>
        <div class="login-wrap">
            <input type="text" name="loginName" id="loginName"  class="form-control" placeholder="请输入用户名" autofocus>
            <input type="password" name="password"  id="password"  class="form-control" placeholder="请输入密码">

            <button class="btn btn-lg btn-login btn-block" type="submit">登录</button>

            <%--<label class="checkbox">
                <input type="checkbox" value="remember-me"> 记住密码
            </label>--%>
            <div style="height:10px;"><span style="color:red;" id="errInfo">${errMsg}</span></div>
        </div>
    </form>
</div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
 <script type="text/javascript">
 	function checkForm(){
 		var n=$('#loginName').val();
 		var p=$('#password').val();
 		if(!$.trim(n)){
 			$('#errInfo').text("用户名不允许为空。");
 			$('#loginName').focus();
 			return false;
 		}else if(!$.trim(p)){
 			$('#errInfo').text("密码不允许为空。");
 			$('#password').focus();
 			return false;
 		}
 		return true;
 	}

</script>
</body>
</html>
