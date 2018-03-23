<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>

    <script type="text/javascript">
        function beforeSubmit() {
            var content = CKEDITOR.instances.contentEditor.getData();
            $("#content").val(content);
        }
        function validateForm() {
            $("form").validate({
                rules: {
                    "templateName": "required"
                },
                messages: {
                    "templateName": "请输入模板名称"
                }
            });
            return $("form").valid();
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/template/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <input type="hidden" id="content" name="content"/>
            <div class="form-group ">
                <label for="templateName" class="control-label col-xs-2">模板名称<span class="required">*</span></label>
                <div class="col-xs-10">
                    <input class=" form-control" id="templateName" name="templateName" value="${entity.templateName}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="categoryId" class="control-label col-xs-2">模板分类</label>
                <div class="col-xs-10">
                    <h:codeDropDownList id="categoryId" name="categoryId" cssClass="form-control" codeType="templateCategory" value="${entity.categoryId}"></h:codeDropDownList>
                </div>
            </div>
            <div class="form-group ">
                <label for="sort" class="control-label col-xs-2">模板排序</label>
                <div class="col-xs-10">
                    <input class="form-control" id="sort" name="sort" value="${entity.sort}"/>
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">模板描述</label>
                <div class="col-xs-10">
                    <textarea class="form-control" id="remark" rows="2" name="remark">${entity.remark}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">模板内容</label>
                <div class="col-xs-10">
                    <textarea class="ckeditor form-control" id="contentEditor" rows="8" name="contentEditor">${entity.content}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
