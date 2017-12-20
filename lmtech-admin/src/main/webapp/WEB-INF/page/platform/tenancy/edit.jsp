<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "name":  {
                        required: true,
                        maxlength: 50
                    }
                },
                messages: {
                    "name": {
                        required: "请输入租户名称",
                        maxlength: "最多输入50个字符"
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
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/tenancy/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">租户名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="code" class="control-label col-xs-2 text-right">租户代码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="code" name="code" value="${entity.code}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户信息</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="info" rows="5" name="info">${entity.info}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户手机</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="mobile" rows="5" name="mobile">${entity.info}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户地址</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="address" rows="5" name="address">${entity.address}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户QQ</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="qq" rows="5" name="qq">${entity.qq}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户微信</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="weixin" rows="5" name="weixin">${entity.weixin}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
