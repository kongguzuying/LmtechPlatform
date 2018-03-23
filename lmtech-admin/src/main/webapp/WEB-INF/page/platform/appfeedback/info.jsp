<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<style type="text/css">
.form-control1{
padding:6px 12px;
height: 34px;
}
</style>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/role/edit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2">用户名称</label>
                <span class="form-control1 col-xs-8">${entity.userName}</span>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">反馈时间</label>
                <span class="form-control1 col-xs-8"><fmt:formatDate  value="${entity.date}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
              
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2">反馈内容</label>                
                <div class="col-xs-8" style="word-break:break-all">${entity.content}</div>
            </div>
            <%-- <div class="form-group">
                <label class="control-label col-xs-2"></label>
                <div class="col-lg-offset-2 col-xs-3">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/appfeedback/list.do">返回</a>
                </div>
            </div> --%>
        </form>
    </div>
</div>
</body>
</html>
