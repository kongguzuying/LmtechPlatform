<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script type="text/javascript">
    	//dailog方式需要申明验证函数(validateForm)
	    function validateForm() {
	        $("form").validate({
                rules: {
                    "name":  {
                        required: true,
                        maxlength: 50
                    },
                    "email":  {
                        email: true
                    },
                    "mobile":  {
                        number : true
                    },
                    "qq":  {
                        number : true
                    }
                },
                messages: {
                    "name": {
                        required: "请输入姓名",
                        maxlength: "最多输入50个字符"
                    },
                    "email":  {
                        email: "请输入正确格式"
                    },
                    "mobile":  {
                        number: "请输入正确格式"
                    },
                    "qq":  {
                        number: "请输入正确格式"
                    }
                }
	        });
	        return $("form").valid();
	    }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/user/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <input type="hidden" name="groupId" value="${groupId}"/>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    姓名<span class="required">*</span>
                </label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" name="name" value="${entity.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    邮箱
                </label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" name="email" value="${entity.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    手机
                </label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" name="mobile" value="${entity.mobile}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    QQ
                </label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" name="qq" value="${entity.qq}"/>
                </div>
            </div>
        </form>
</body>
</html>