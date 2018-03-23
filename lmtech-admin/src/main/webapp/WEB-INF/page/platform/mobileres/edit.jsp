<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $("form").validate({
                rules: {
                    "id": "required",
                    "name": "required"
                },
                messages: {
                    "id": "请输入资源编码",
                    "name": "请输入资源名称"
                }
            });
        });
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/mobileres/edit.do" novalidate="novalidate">
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2">资源编号<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class=" form-control" id="code" name="code" value="${entity.code}" <c:if test="${!empty entity.code}">readonly="readonly"</c:if> type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2">资源名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class=" form-control" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2">资源类型</label>
                <div class="col-xs-8">
                    <h:codeDropDownList id="categoryCode" name="categoryCode" cssClass="form-control" codeType="mobileResCategory"></h:codeDropDownList>
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">资源描述</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="remark" rows="5" name="remark">${entity.remark}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">是否启用</label>
                <div class="col-xs-8">
                    <input type="checkbox" id="enable" name="enable" <c:if test="${entity.enable}">checked</c:if> />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2"></label>
                <div class="col-lg-offset-2 col-xs-3">
                    <button type="submit" class="btn btn-primary">保存</button>
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/mobileres/list.do">返回</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
