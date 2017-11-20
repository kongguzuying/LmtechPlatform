<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="../common/header.jsp"></jsp:include>
</head>

<body class="body">
<div class="col-md-push-8 col-md-push-2">
  <div  style="word-break:break-all;"><c:if test='${entity.memo==null || entity.memo eq ""}'>此日志没有备注内容</c:if>
  <c:if test='${entity.memo !=null and entity.memo ne ""}'>${entity.memo}</c:if></div>
</div>
</body>
</body>
</html>