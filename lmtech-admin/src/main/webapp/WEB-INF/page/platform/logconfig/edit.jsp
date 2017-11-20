<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "targetDesc":  {
                        required: true,
                        maxlength: 50
                    },
                    "target": {
                        required: true,
                        maxlength: 50
                    },
                    "methodDesc": {
                        required: true,
                        maxlength: 50
                    },
                    "method": {
                        required: true,
                        maxlength: 50
                    }
                },
                messages: {
                    "targetDesc": {
                        required: "请输入业务类名称",
                        maxlength: "最多输入50个字符"
                    },
                    "target": {
                        required: "请输入业务类",
                        maxlength: "最多输入50个字符"
                    },
                    "methodDesc": {
                        required: "请输入方法名称",
                        maxlength: "最多输入50个字符"
                    },
                    "target": {
                        required: "请输入方法",
                        maxlength: "最多输入50个字符"
                    }
                }
            });
            return $("form").valid();
        }
        $(function () {
            $("form").validate({
                rules: {
                    "targetDesc":  {
                        required: true,
                        maxlength: 50
                    },
                    "target": {
                        required: true,
                        maxlength: 50
                    },
                    "methodDesc": {
                        required: true,
                        maxlength: 50
                    },
                    "method": {
                        required: true,
                        maxlength: 50
                    }
                },
                messages: {
                    "targetDesc": {
                        required: "请输入业务类名称",
                        maxlength: "最多输入50个字符"
                    },
                    "target": {
                        required: "请输入业务类",
                        maxlength: "最多输入50个字符"
                    },
                    "methodDesc": {
                        required: "请输入方法名称",
                        maxlength: "最多输入50个字符"
                    },
                    "method": {
                        required: "请输入方法",
                        maxlength: "最多输入50个字符"
                    }
                }
            });
        });
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/logconfig/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">业务类名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="targetDesc" name="targetDesc" value="${entity.targetDesc}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">业务类<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="target" name="target" value="${entity.target}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">方法名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="methodDesc" name="methodDesc" value="${entity.methodDesc}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">方法<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="method" name="method" value="${entity.method}" type="text">
                </div>
            </div>
             <%-- <div class="form-group ">
                <label for="remark" class="control-label col-xs-2 text-right">是否启动</label>
                <div class="col-xs-8 text-left">
                    <input type="checkbox" name="enable" <c:if test="${entity.enable || entity.id==null}">checked</c:if>  >
                </div>
            </div> --%>
        </form>
    </div>
</div>
</body>
</html>
