<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    oldPassword: "required",
                    newPassword: "required",
                    repeatPassword: "required"
                },
                messages: {
                    oldPassword: "请输入原始密码",
                    newPassword: "请输入新的密码",
                    repeatPassword: "请再次输入新的密码"
                }
            });
            return $("form").valid();
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/portal/changepswd.do" novalidate="novalidate">
            <div class="form-group ">
                <label for="oldPassword" class="control-label col-xs-2">原始密码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class=" form-control" id="oldPassword" name="oldPassword" type="password" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled" >
                </div>
            </div>
            <div class="form-group ">
                <label for="newPassword" class="control-label col-xs-2">新的密码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class=" form-control" id="newPassword" name="newPassword" type="password" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
                </div>
            </div>
            <div class="form-group ">
                <label for="repeatPassword" class="control-label col-xs-2">重复密码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class=" form-control" id="repeatPassword" name="repeatPassword" type="password" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
