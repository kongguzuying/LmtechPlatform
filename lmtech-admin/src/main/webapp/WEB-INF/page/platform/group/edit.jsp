<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="../../common/header.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
    	//dailog方式需要申明验证函数(validateForm)
	    function validateForm() {
	        $("form").validate({
	            rules: {
	                "name": "required"
	            },
	            messages: {
	                "name": "请输入部门名称"	             
	            }
	        });
	        return $("form").valid();
	    }
    </script>
</head>

<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal">
            <input type="hidden" name="id" value="${entity.id}"/>
            <input type="hidden" name="parentId" value="${entity.parentId}"/>
            <div class="form-group">
                <label class="control-label col-xs-3">部门名称</label>
                <div class="col-xs-9">
                    <input class="form-control" type="text" name="name" value="${entity.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3">部门别名</label>
                <div class="col-xs-9">
                    <input class="form-control" type="text" name="alias" value="${entity.alias}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-3">备注</label>
                <div class="col-xs-9">
                            <textarea class="form-control" name="remark" id="remark" maxlength="200" style="height: 80px">${entity.remark}</textarea>
                        <p class="form-error" id="datavalid_guide_error" style="color: rgb(153, 153, 153);">限200个字</p></div>
            </div>
        </form>
    </div>
</div>
</body>
</html>