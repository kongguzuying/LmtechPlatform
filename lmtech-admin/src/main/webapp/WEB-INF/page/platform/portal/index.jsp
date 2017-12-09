<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" /> 
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
<style type="text/css">
.menuActive{
	text-decoration: none;
    color: #65cea7!important;
    background: #2a323f;
}
</style>
    <title>星链通管理后台</title>

    <!--common-->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-my.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->

    <!-- common -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js"></script>

    <script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>

    <script type="text/javascript">
    	var CURRENT_USER_ID = '${userId}';
        $(function () {
            var bodyHeight = $(window).height() - 86;
            $("#mainContent").height(bodyHeight);
            $("#mainFrame").height(bodyHeight);
        });
        function changePswd() {
            var modal = top.showModal({
                width: 600,
                height: 220,
                title: "密码 - 修改",
                url: "${pageContext.request.contextPath}/platform/portal/changepswd.do",
                opener: window,
                refreshFinish: false
            });

        }
        function showStyle(obj){
        	//console.error($(obj));
        	//$(this).addClass("");
        	$(obj).parents("ul").find("a").removeClass("menuActive");
        	$(obj).addClass("menuActive");
        }
        function menuClick(obj, href) {
            showStyle(obj);

            if (href.indexOf("?") > 0) {
                href += "&t=" + new Date();
            } else {
                href += "?t=" + new Date();
            }
            $("#mainFrame").attr("src", href);
        }
        function confirmx(msg,e){
            confirmMessage(msg,function(re){
                if(re){
                    window.location.href=$(e).attr('href');
                }
            });
            return false;
        }
        function changeModel(menuId, menuName, elem) {
            $(elem).parent().parent().find("li").removeClass("model-active");
            $(elem).parent().addClass("model-active");
            $("#modelTitle .model-name").text(menuName);

            $(".top-menu").hide();
            $("#" + menuId).show();
        }
    </script>
</head>

<body class="sticky-header">

<section>
    <!-- left side start-->
    <div class="left-side sticky-left-side">

        <!--logo and iconic logo start-->
        <div class="logo">
            <a href="#"><img src="${pageContext.request.contextPath}/images/logo.png" alt=""></a>
        </div>

        <div class="logo-icon text-center">
            <a><img src="${pageContext.request.contextPath}/images/logo_icon.png" alt=""></a>
        </div>

        <div class="left-side-inner">
            <!--sidebar nav start-->
            <ul class="nav nav-pills nav-stacked custom-nav">
                <li class=""><a href="${pageContext.request.contextPath}/platform/portal/welcome.do" target="mainFrame"><i
                        class="fa fa-home"></i> <span>首  页</span></a></li>
                <c:forEach items="${menus}" var="menu">
                    <li class="menu-list"><a href="javascript:void(0);"><i class="fa ${menu.icon}"></i> <span>${menu.name}</span></a>
                        <ul class="sub-menu-list">
                            <c:forEach items="${menu.childrens}" var="child">
                                <li><a onclick="menuClick(this, '${pageContext.request.contextPath}/${child.href}')" href="javascript:void(0)"
                                       target="mainFrame"><i class="fa ${child.icon}"></i>${child.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
            <!--sidebar nav end-->
        </div>
    </div>
    <!-- left side end-->

    <!-- main content start-->
    <div class="main-content">

        <!-- header section start-->
        <div class="header-section">

            <!--toggle button start-->
            <a class="toggle-btn"><i class="fa fa-bars"></i></a>
            <!--toggle button end-->

            <!--search start-->
            <%--<form class="searchform" action="index.html" method="post">
                <input type="text" class="form-control" name="keyword" placeholder="Search here..."/>
            </form>--%>
            <!--search end-->

            <!--notification menu start -->
            <div class="menu-right">
                <ul class="notification-menu">
                    <li>
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <%--<img src="images/photos/user-avatar.png" alt=""/>--%>
                            ${cur_user.nickName}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="javascript:changePswd()"><i
                                    class="fa fa-lock"></i>修改密码</a></li>
                            <li><a href="${pageContext.request.contextPath}/platform/portal/logout.do" onclick="return confirmx('确定是否注销？',this);"><i
                                    class="fa fa-sign-out"></i>注销用户</a></li>
                        </ul>
                    </li>

                </ul> 
            </div>
            <!--notification menu end -->

        </div>
        <!-- header section end-->

        <!--body wrapper start-->
        <div id="mainContent" class="wrapper">
            <iframe id="mainFrame" name="mainFrame" frameborder="none"
                    src="${pageContext.request.contextPath}/platform/portal/welcome.do" width="100%" height="100%"></iframe>
        </div>
        <!--body wrapper end-->

        <!--footer section start-->
        <footer>
            <b>2016 &copy; </b>
        </footer>
        <!--footer section end-->


    </div>
    <!-- main content end-->
</section>
<jsp:include page="../../common/modal.jsp"></jsp:include>
<!--common scripts for all pages-->
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>

<script src="${pageContext.request.contextPath}/platform/code/codeListScript.do?t=${time}"></script>
<script src="${pageContext.request.contextPath}/js/top.js"></script>
<script src="${pageContext.request.contextPath}/js/modal.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.isloading.min.js"></script>
<style>
    .notification-menu .dropdown-menu li.model-active {
        background: #2a323f;
        color: #fff;
    }
</style>
</body>
</html>
